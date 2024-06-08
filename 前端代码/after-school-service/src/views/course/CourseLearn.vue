<script setup>
import { ref } from 'vue'
import { getCourseDetail } from '@/api/course'
import { useRoute } from 'vue-router'
import { getRecommend } from '@/api/schedule'
import {
  getCommentList,
  sendComment,
  deleteCom,
  thumbsUp,
  cancel
} from '@/api/comment'
import { Pointer, Position, Delete } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores'
import router from '@/router'
const good = ref(true)
const userStore = useUserStore()
const route = useRoute()
const course = ref({})
const recommendList = ref([])
const commentList = ref([])
const input = ref('')
const total = ref(0)
const page = ref({
  pageNum: 1,
  pageSize: 16
})
const init = async () => {
  const res1 = await getCourseDetail(route.params.id)
  course.value = res1.data.data
  const res2 = await getRecommend()
  recommendList.value = res2.data.data
  const data = {
    cid: course.value.id,
    pageNum: page.value.pageNum,
    pageSize: page.value.pageSize
  }
  const res3 = await getCommentList(data)
  commentList.value = res3.data.data.items
  total.value = res3.data.data.total
}
init()
//每页条数切换
const handleSizeChange = (size) => {
  page.value.size = size
  page.value.pageNum = 1
  init()
}
//切换页数
const handleCurrentChange = (num) => {
  page.value.pageNum = num
  init()
}
const send = async () => {
  const data = {
    cid: course.value.id,
    context: input.value
  }
  await sendComment(data)
  ElMessage.success('发布成功')
  input.value = ''
  init()
}
const deleteComment = async (id) => {
  await deleteCom(id)
  ElMessage.success('删除成功')
  init()
}
const thumbs = async (id) => {
  if (good.value === true) {
    await thumbsUp(id)
    init()
    good.value = false
  } else {
    await cancel(id)
    init()
    good.value = true
  }
}
const toCourse = (id) => {
  router.replace(`/course/detail/${id}`)
}
</script>
<template>
  <h1 class="title">课程学习</h1>
  <div class="container">
    <div class="course-info">
      <span class="course-name">{{ course.cname }}</span>
      <div class="video-container">
        <video
          controls
          :src="'http://localhost:8080/file/' + course.resources"
        ></video>
      </div>
    </div>
    <div class="sidebar">
      <h2 class="sidebar-title">推荐课程</h2>
      <ul>
        <li
          v-for="item in recommendList"
          :key="item.id"
          class="recommend-item"
          @click="toCourse(item.cid)"
        >
          <span class="course-name">{{ item.cname }}</span>
          <span class="teacher">讲师：{{ item.tname }}</span>
        </li>
      </ul>
    </div>
  </div>
  <!-- 评论区 -->
  <div class="comment">
    <span>评论区</span>
    <div style="display: flex; align-items: center">
      <el-input v-model="input"></el-input>
      <el-button @click="send" type="primary" :icon="Position" circle />
    </div>

    <div
      v-for="item in commentList"
      :key="item.id"
      style="display: flex; align-items: center"
    >
      <img
        :src="'http://localhost:8080/file/' + item.avatar"
        style="border-radius: 50%"
      />
      <div style="display: flex; flex-direction: column">
        <span>{{ item.context }}</span>
        <div style="display: flex">
          <span>{{ item.createTime }}</span>
          <el-button @click="thumbs(item.id)" :icon="Pointer" circle />
          <span>{{ item.good }}</span>
          <el-button
            v-if="
              userStore.user.usertype === 4 || userStore.user.id === item.userId
            "
            @click="deleteComment(item.id)"
            type="danger"
            :icon="Delete"
            circle
          />
        </div>
      </div>
    </div>
  </div>
  <el-pagination
    style="margin-top: 20px; justify-content: flex-end"
    v-model:current-page="page.pageNum"
    v-model:page-size="page.pageSize"
    :page-sizes="[16, 20, 24, 28]"
    background
    layout="total, sizes, prev, pager, next, jumper"
    :total="total"
    @size-change="handleSizeChange"
    @current-change="handleCurrentChange"
  />
</template>
<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  margin-top: 50px;
}

.title {
  font-size: 32px;
  color: #333;
  text-align: center;
}

.course-info {
  background-color: #f8f8f8;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.course-name {
  display: block;
  font-size: 20px;
  font-weight: bold;
  color: #333;
  margin-bottom: 10px;
}

.video-container {
  max-width: 800px;
  margin: 0 auto;
}

video {
  width: 100%;
  height: auto;
  display: block;
  border-radius: 8px;
}

.sidebar {
  width: 300px;
  background-color: #f8f8f8;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.sidebar h2 {
  font-size: 20px;
  margin-bottom: 10px;
  padding: 10px 0;
  text-align: center;
  background-color: #f5f5f5;
  border: 1px solid #eee;
  border-radius: 5px;
  color: #333;
}

.recommend-item {
  height: 50px;
  padding: 15px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-bottom: 15px;
}

.teacher {
  display: block;
  font-size: 18px;
  color: #666;
}
/* 评论 */
.comment {
  background-color: #fff;
  padding: 20px;
}
.comment {
  background-color: #f5f5f5; /* 设置评论区背景色 */
  padding: 20px; /* 设置内边距 */
  border-radius: 8px; /* 设置边框圆角 */
  border: 1px solid #ddd; /* 添加边框 */
}

.comment span {
  margin-bottom: 10px; /* 设置下方间距 */
  font-size: 14px; /* 设置字体大小 */
}

.comment img {
  max-width: 40px; /* 设置头像最大宽度 */
  height: auto; /* 设置头像高度自适应 */
  border-radius: 50%; /* 设置头像为圆形 */
  margin-right: 10px; /* 设置右侧间距 */
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
}

.el-input {
  margin-bottom: 10px; /* 设置输入框下方间距 */
}

.el-button {
  margin-bottom: 10px; /* 设置按钮下方间距 */
  border: none; /* 移除按钮边框 */
  background-color: #00a1d6; /* 设置按钮背景色 */
  color: #fff; /* 设置按钮文字颜色 */
  transition: background-color 0.3s ease; /* 添加过渡效果 */
}

.el-button:hover {
  background-color: #007fad; /* 设置按钮悬停时背景色 */
}

.el-pagination {
  margin-top: 20px; /* 设置分页器顶部间距 */
  justify-content: flex-end; /* 设置分页器内容右对齐 */
}

.el-pagination .el-pagination__sizes {
  margin-right: 20px; /* 调整分页器尺寸选择器与分页器的间距 */
}
</style>

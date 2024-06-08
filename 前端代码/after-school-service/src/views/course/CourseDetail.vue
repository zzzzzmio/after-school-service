<script setup>
import { getCourseDetail } from '@/api/course'
import { getDetail } from '@/api/user'
import { apply } from '@/api/apply'
import { addSchedule } from '@/api/schedule'
import {
  getCommentList,
  sendComment,
  thumbsUp,
  cancel,
  deleteCom
} from '@/api/comment'
import { ref } from 'vue'
import { useRoute } from 'vue-router'
import { Position, Pointer, Delete } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores'
const route = useRoute()
const course = ref({})
const teacher = ref({})
const list = ref([])
const total = ref(0)
const page = ref({
  pageNum: 1,
  pageSize: 16
})
const userStore = useUserStore()
const input = ref('')
const good = ref(true)
const getList = async () => {
  const data = {
    cid: course.value.id,
    pageNum: page.value.pageNum,
    pageSize: page.value.pageSize
  }
  const res = await getCommentList(data)
  list.value = res.data.data.items
  total.value = res.data.data.total
}
const init = async () => {
  const res1 = await getCourseDetail(route.params.id)
  course.value = res1.data.data
  const res2 = await getDetail(course.value.tid)
  teacher.value = res2.data.data
  getList()
}
init()
//每页条数切换
const handleSizeChange = (size) => {
  page.value.size = size
  page.value.pageNum = 1
  getList()
}
//切换页数
const handleCurrentChange = (num) => {
  page.value.pageNum = num
  getList()
}
const send = async () => {
  const data = {
    cid: course.value.id,
    context: input.value
  }
  await sendComment(data)
  ElMessage.success('发布成功')
  input.value = ''
  getList()
}
const thumbs = async (id) => {
  if (good.value === true) {
    await thumbsUp(id)
    good.value = false
    getList()
  } else {
    await cancel(id)
    good.value = true
    getList()
  }
}
const deleteComment = async (id) => {
  await deleteCom(id)
  getList()
}
const applyFriend = async () => {
  const data = {
    fromUser: userStore.user.id,
    fromName: userStore.user.username,
    fromAvatar: userStore.user.userPic,
    toUser: teacher.value.id,
    toName: teacher.value.username,
    toAvatar: teacher.value.userPic
  }
  await apply(data)
  ElMessage.success('已发送申请')
}
const addMyCourse = async () => {
  await addSchedule(course.value.id)
  ElMessage.success('已加入您的课程')
}
</script>
<template>
  <h1>课程详情</h1>
  <div class="container">
    <div class="course-details">
      <div class="course">
        <span>课程名称：{{ course.cname }}</span>
        <span>课程老师：{{ course.tname }}</span>
        <img :src="'http://localhost:8080/file/' + course.coverImg" />
        <span>课程简介：{{ course.content }}</span>
        <span>上课时间：{{ course.ctime }}</span>
        <span>价格：{{ course.price }}</span>
        <el-button
          v-show="userStore.user.usertype === 1"
          type="primary"
          @click="addMyCourse"
          >加入课程</el-button
        >
      </div>
    </div>
    <div class="teacher-details">
      <div class="teacher">
        <img
          :src="'http://localhost:8080/file/' + teacher.userPic"
          style="border-radius: 50%"
        />

        <span>邮箱：{{ teacher.email }}</span>
        <el-button type="primary" @click="applyFriend">加好友</el-button>
      </div>
    </div>
  </div>
  <div class="comment">
    <span>评论区</span>
    <div style="display: flex; align-items: center">
      <el-input v-model="input"></el-input>
      <el-button @click="send" type="primary" :icon="Position" circle />
    </div>

    <div
      v-for="item in list"
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
  </div>
</template>
<style scoped>
.container {
  display: flex;
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  overflow: hidden; /* 防止阴影溢出 */
}

.course-details {
  flex: 2;
  padding: 20px;
  background-color: #fff;
}

.teacher-details {
  flex: 1;
  padding: 20px;
  background-color: #fff;
}

.course,
.teacher,
.comment {
  margin-bottom: 20px;
  border-radius: 8px;
  box-shadow: 0px 0px 8px rgba(0, 0, 0, 0.1);
}

.course {
  padding: 20px;
}

.course span {
  display: block;
  margin-bottom: 10px;
  font-size: 18px;
  color: #333;
}

.course img {
  max-width: 100%;
  height: auto;
  border-radius: 8px;
  margin-bottom: 10px;
}

.course .content {
  font-size: 16px;
  color: #666;
}

.teacher {
  padding: 20px;
}

.teacher img {
  max-width: 100%;
  height: auto;
  border-radius: 50%; /* 圆形图片 */
  margin-bottom: 10px;
}

.teacher span {
  display: block;
  font-size: 16px;
  color: #333;
  margin-top: 10px;
}

.teacher span.email {
  font-weight: bold;
}

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

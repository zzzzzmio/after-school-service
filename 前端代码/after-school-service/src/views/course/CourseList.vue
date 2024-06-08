<script setup>
import { ref } from 'vue'
import { getList, search, deleteCourse } from '@/api/course'
import { useUserStore } from '@/stores'
import router from '@/router'
const userStore = useUserStore()
const total = ref(0)
const page = ref({
  pageNum: 1,
  pageSize: 16
})
const list = ref([])
const input = ref('')
const init = async () => {
  if (input.value === '') {
    const res = await getList(page.value)
    list.value = res.data.data.items
    total.value = res.data.data.total
  }
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
const find = async () => {
  const data = {
    cname: input.value,
    pageNum: 1,
    pageSize: page.value.pageSize
  }
  page.value.pageNum = 1
  const res = await search(data)
  list.value = res.data.data.items
  total.value = res.data.total
}
const reset = async () => {
  page.value.pageNum = 1
  input.value = ''
  init()
}
const open = (id) => {
  router.replace(`/course/detail/${id}`)
}
const remove = async (id) => {
  await deleteCourse(id)
  ElMessage.success('删除成功')
  init()
}
</script>
<template>
  <h1>课程列表</h1>
  <div class="container">
    <span>搜索栏</span>
    <el-input class="el-input" v-model="input"></el-input>
    <el-button type="primary" @click="find">搜索</el-button>
    <el-button @click="reset">重置</el-button>
  </div>
  <div class="video-container">
    <div
      class="video-row"
      v-for="(row, index) in Math.ceil(list.length / 4)"
      :key="index"
    >
      <!-- 渲染实际的视频项 -->
      <div
        v-for="(item, innerIndex) in list.slice(index * 4, (index + 1) * 4)"
        :class="['video-item', { empty: !item }]"
        :key="innerIndex"
        @click="open(item.id)"
      >
        <img v-if="item" :src="'http://localhost:8080/file/' + item.coverImg" />
        <div class="video-details" v-if="item">
          <div class="video-info">
            <span class="video-title">{{ item.cname }}</span>
            <span class="video-teacher">教师：{{ item.tname }}</span>
            <span class="video-time">更新时间：{{ item.updateTime }}</span>
            <el-button
              @click.stop="remove(item.id)"
              v-show="
                userStore.user.usertype === 4 || userStore.user.id === item.tid
              "
              type="primary"
              >删除</el-button
            >
          </div>
        </div>
      </div>
      <!-- 补充占位的空白视频项 -->
      <div
        v-for="i in 4 - list.slice(index * 4, (index + 1) * 4).length"
        class="video-item empty"
        :key="'placeholder-' + i"
      ></div>
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
.video-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}

.video-row {
  width: 100%;
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px; /* 调整行与行之间的间距 */
}

.video-item {
  width: calc(
    23% - 10px
  ); /* 让每个视频项占据容器宽度的四分之一，并留出一些间距 */
  margin-bottom: 20px; /* 调整视频项之间的间距 */
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
  transition: box-shadow 0.3s ease; /* 添加过渡效果 */
}

.video-item:hover {
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.2); /* 鼠标悬停时增加阴影效果 */
}

.video-item img {
  width: 100%;
  height: auto;
}

.video-details {
  padding: 10px;
}

.video-info {
  font-size: 14px;
}

.video-title {
  font-weight: bold;
  display: block;
  margin-bottom: 5px;
}

.video-teacher,
.video-time {
  display: block;
}
.container {
  display: flex;
  align-items: center;
  justify-content: flex-start; /* 将内容靠右对齐 */
  margin-bottom: 20px; /* 添加底部间距 */
}

.el-input {
  width: 200px; /* 设置输入框的固定宽度 */
  margin-right: 10px; /* 添加右边距 */
}

.el-button {
  margin-right: 10px; /* 添加右边距 */
}
</style>

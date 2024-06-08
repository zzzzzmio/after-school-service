<script setup>
import { ref } from 'vue'
import { getPostList, getHotPosts, addPosts, deletePosts } from '@/api/posts'
import { useUserStore } from '@/stores'
import router from '@/router'
const userStore = useUserStore()
const total = ref(0)
const page = ref({
  pageNum: 1,
  pageSize: 10,
  userId: ''
})
const formModel = ref({
  title: '',
  context: ''
})
const form = ref()
const Dialog = ref(false)
const list = ref([])
const hotList = ref([])
const checked = ref(false)
const init = async () => {
  const res1 = await getPostList(page.value)
  list.value = res1.data.data.items
  total.value = res1.data.data.total
  const res2 = await getHotPosts()
  hotList.value = res2.data.data
}

//每页条数切换
const handleSizeChange = (size) => {
  page.value.pageSize = size
  page.value.pageNum = 1
  init()
}

//切换页数
const handleCurrentChange = (num) => {
  page.value.pageNum = num
  init()
}
init()
const truncateText = (text, length = 50) => {
  if (text.length > length) {
    return text.slice(0, length) + '...'
  }
  return text
}
const open = () => {
  Dialog.value = true
}
const cancel = () => {
  Dialog.value = false
  formModel.value = {
    title: '',
    context: ''
  }
}
const submitForm = async () => {
  await addPosts(formModel.value)
  ElMessage.success('发布成功')
  Dialog.value = false
  formModel.value = {
    title: '',
    context: ''
  }
  init()
}
const openPosts = (item) => {
  router.replace(`/posts/detail/${item.id}`)
}
const remove = async (id) => {
  await deletePosts(id)
  ElMessage.success('删除成功')
  init()
}
const only = () => {
  if (checked.value === false) {
    page.value.userId = userStore.user.id
    init()
  } else {
    page.value.userId = ''
    init()
  }
}
</script>
<template>
  <div class="container">
    <div class="main-content">
      <div class="forum-header">
        <h1 class="forum-title">论坛</h1>
        <button class="new-post-button" @click="open">发帖</button>
      </div>
      <el-checkbox v-model="checked" @click="only">只看我的</el-checkbox>
      <div class="post-list">
        <div
          class="post"
          v-for="item in list"
          :key="item.id"
          @click="openPosts(item)"
        >
          <h2 class="post-title">{{ item.title }}</h2>
          <p class="post-content">{{ truncateText(item.context) }}</p>
          <div class="post-details">
            <span class="post-info">作者: {{ item.username }}</span>
            <span class="post-info">发表时间: {{ item.createTime }}</span>
            <span class="post-info">赞: {{ item.good }}</span>
            <button
              class="custom-button"
              v-if="
                userStore.user.usertype === 4 ||
                userStore.user.username === item.username
              "
              @click.stop="remove(item.id)"
            >
              删除
            </button>
          </div>
        </div>
      </div>
      <el-pagination
        class="pagination"
        :current-page="page.pageNum"
        :page-sizes="[10, 20, 25, 30]"
        :page-size="page.pageSize"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>

    <!-- 侧边栏 -->
    <div class="sidebar">
      <div class="sidebar-title">热门帖子</div>
      <ul class="hot-posts">
        <li v-for="post in hotList" :key="post.id" class="hot-post">
          <span class="hot-post-title">{{ post.title }}</span>
          <span class="hot-post-time">{{ post.createTime }}</span>
        </li>
      </ul>
    </div>
    <el-dialog v-model="Dialog" title="发帖" width="500">
      <el-form
        ref="form"
        :model="formModel"
        label-width="80px"
        style="max-width: 600px"
      >
        <el-form-item label="标题">
          <el-input
            v-model="formModel.title"
            placeholder="请输入标题"
          ></el-input>
          <!-- 添加占位符 -->
        </el-form-item>
        <el-form-item label="内容">
          <el-input
            type="textarea"
            v-model="formModel.context"
            placeholder="请输入内容"
            autosize
            :rows="6"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">发布</el-button>
          <!-- 绑定发布按钮事件 -->
          <el-button @click="cancel">取消</el-button>
          <!-- 添加取消按钮 -->
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<style scoped>
.container {
  display: flex;
  justify-content: space-between;
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.main-content {
  flex-grow: 1;
}

.forum-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.forum-title {
  font-size: 24px;
  color: #333;
}

.new-post-button {
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.new-post-button:hover {
  background-color: #0056b3;
}

.post-list {
  border-top: 2px solid #ddd;
  padding-top: 20px;
}

.post {
  background-color: #fff;
  border: 1px solid #eee;
  border-radius: 5px;
  padding: 15px;
  margin-bottom: 20px;
}

.post-title {
  font-size: 18px;
  margin-bottom: 10px;
  color: #333;
}

.post-content {
  font-size: 14px;
  color: #555;
  margin-bottom: 10px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.post-details {
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  color: #888;
}

.post-info {
  margin-right: 10px;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.sidebar {
  width: 300px;
}

.sidebar-title {
  font-size: 20px;
  margin-bottom: 10px;
  padding: 10px 0;
  text-align: center;
  background-color: #f5f5f5;
  border: 1px solid #eee;
  border-radius: 5px;
  color: #333;
}

.hot-posts {
  list-style: none;
  padding: 0;
}

.hot-post {
  margin-bottom: 10px;
  padding: 10px;
  background-color: #f9f9f9;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

.hot-post:hover {
  background-color: #e9e9e9;
}

.hot-post-title {
  font-size: 16px;
  color: #333;
  margin-bottom: 5px;
  display: block;
}

.hot-post-time {
  font-size: 12px;
  color: #888;
  display: block;
}
.custom-button {
  background: none;
  border: none;
  color: inherit;
  cursor: pointer;
  outline: none;
  transition: color 0.3s ease;
}
.custom-button:hover {
  color: #007bff; /* 更改高亮颜色 */
}
</style>

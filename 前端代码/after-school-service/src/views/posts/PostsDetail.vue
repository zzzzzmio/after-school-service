<script setup>
import { ref } from 'vue'
import { getPostsDetail, upPosts, cancelPosts } from '@/api/posts'
import { getDetail } from '@/api/user'
import {
  getAnswerList,
  addAnswer,
  deleteAnswer,
  upAnswer,
  cancelAnswer
} from '@/api/answer'
import { useRoute } from 'vue-router'
import { useUserStore } from '@/stores'
import { apply } from '@/api/apply'
const userStore = useUserStore()
const route = useRoute()
const posts = ref({})
const user = ref({})
const answer = ref([])
const total = ref(0)
const page = ref({
  pageNum: 1,
  pageSize: 15
})
const people = ref({
  fromUser: userStore.user.id,
  fromName: userStore.user.username,
  fromAvatar: userStore.user.userPic,
  toUser: '',
  toName: '',
  toAvatar: ''
})
const reply = ref('')
const postsGood = ref(true)
const answerGood = ref(true)
const init = async () => {
  const res = await getPostsDetail(route.params.id)
  posts.value = res.data.data
  const {
    data: { data }
  } = await getDetail(posts.value.userId)
  user.value = data
  const res2 = await getAnswerList({
    pid: posts.value.id,
    pageNum: page.value.pageNum,
    pageSize: page.value.pageSize
  })
  answer.value = res2.data.data.items
  total.value = res2.data.data.total
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
const submitReply = async () => {
  await addAnswer({ pid: posts.value.id, context: reply.value })
  ElMessage.success('回复成功')
  reply.value = ''
  init()
}
const remove = async (id) => {
  await deleteAnswer(id)
  ElMessage.success('删除成功')
  init()
}
const send = async (id) => {
  const {
    data: { data }
  } = await getDetail(id)
  people.value.toUser = data.id
  people.value.toName = data.username
  people.value.toAvatar = data.userPic
  await apply(people.value)
  ElMessage.success('已发送好友申请')
}
const thumpPosts = async (id) => {
  if (postsGood.value === true) {
    await upPosts(id)
    init()
    postsGood.value = false
  } else {
    await cancelPosts(id)
    init()
    postsGood.value = true
  }
}
const thumpAnswer = async (id) => {
  if (answerGood.value === true) {
    await upAnswer(id)
    init()
    answerGood.value = false
  } else {
    await cancelAnswer(id)
    init()
    answerGood.value = true
  }
}
</script>
<template>
  <div class="post-detail">
    <div class="post-header">
      <h1 class="post-title">{{ posts.title }}</h1>
      <div class="post-meta">
        <img
          :src="'http://localhost:8080/file/' + user.userPic"
          class="user-avatar"
        />
        <span class="author">作者: {{ user.username }}</span>
        <span class="create-time">发表时间: {{ posts.createTime }}</span>
        <el-button
          type="primary"
          class="add-friend-btn"
          @click="send(posts.userId)"
          >加好友</el-button
        >
      </div>
    </div>
    <div class="post-content">
      <p>{{ posts.context }}</p>
      <div class="post-actions">
        <span>赞：{{ posts.good }}</span>
        <!-- 按钮或链接等 -->
        <button class="like-button" @click="thumpPosts(posts.id)">
          <img
            class="like-icon"
            src="@/assets/thumb-icon.png"
            alt="Thumb Icon"
          />
        </button>
      </div>
    </div>
    <!-- 回复帖子部分 -->
    <div class="reply-section">
      <textarea v-model="reply" class="reply-textarea"></textarea>
      <el-button type="primary" @click="submitReply">回复</el-button>
    </div>
    <div class="post-comments">
      <div v-for="item in answer" :key="item.id" class="comment-item">
        <div class="comment-header">
          <span class="comment-author">{{ item.username }}</span>
          <span class="comment-time">{{ item.createTime }}</span>
          <el-button
            type="primary"
            class="add-friend-btn"
            @click="send(item.userId)"
            >加好友</el-button
          >
        </div>
        <p class="comment-content">{{ item.context }}</p>
        <button
          class="custom-button"
          v-if="
            userStore.user.usertype === 4 || userStore.user.id === item.userId
          "
          @click="remove(item.id)"
        >
          删除
        </button>
        <div class="comment-actions">
          <span>赞：{{ item.good }}</span>
          <!-- 其他操作按钮 -->
          <button class="like-button" @click="thumpAnswer(item.id)">
            <img
              class="like-icon"
              src="@/assets/thumb-icon.png"
              alt="Thumb Icon"
            />
          </button>
        </div>
      </div>
      <el-pagination
        class="pagination"
        v-model:current-page="page.pageNum"
        v-model:page-size="page.pageSize"
        :page-sizes="[15, 20, 25, 30]"
        background
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<style scoped>
.post-detail {
  font-family: 'Microsoft YaHei', Arial, sans-serif;
  color: #333;
  padding: 20px;
  background-color: #f8f9fa;
  border-radius: 10px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
}

.post-title {
  font-size: 28px;
  margin-bottom: 15px;
  color: #333;
  font-weight: bold;
}

.post-meta {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 15px;
}

.author,
.create-time {
  font-size: 16px;
  color: #666;
  margin-right: 5px;
}

.add-friend-btn {
  font-size: 14px;
  border-radius: 20px;
  padding: 8px 20px;
  background-color: #409eff;
}

.add-friend-btn:hover {
  background-color: #66b1ff;
}

.post-content {
  margin-top: 20px;
}

.post-actions {
  margin-top: 15px;
  display: flex;
  align-items: center;
  color: #666;
}

.post-actions span {
  margin-right: 25px;
  font-size: 14px;
}

.post-actions span:last-child {
  margin-right: 0;
}

.post-comments {
  margin-top: 20px;
}

.comment-item {
  margin-bottom: 20px;
  border-radius: 5px;
  padding: 15px;
  background-color: #fff;
  box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
}

.comment-header {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.comment-avatar {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  margin-right: 10px;
}

.comment-author,
.comment-time {
  font-size: 14px;
  color: #666;
}

.comment-content {
  margin-bottom: 10px;
}

.comment-actions {
  display: flex;
  align-items: center;
  color: #666;
}

.comment-actions span {
  margin-right: 20px;
  font-size: 14px;
}

.comment-actions span:last-child {
  margin-right: 0;
}

.pagination {
  margin-top: 20px;
}
.comment-author {
  margin-right: 5px; /* 可以根据需要调整间距大小 */
}
/* 点赞按钮样式 */
.like-button {
  display: inline-block;
  background-color: transparent;
  border: none;
  padding: 0;
  cursor: pointer;
  outline: none;
}

.like-icon {
  width: 24px;
  height: 24px;
  vertical-align: middle;
}

.like-count {
  vertical-align: middle;
  margin-left: 5px;
  color: #999;
}

/* 点赞按钮动画 */
@keyframes like-animation {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.2);
  }
  100% {
    transform: scale(1);
  }
}

.liked {
  animation: like-animation 0.4s ease;
  color: #fb7299; /* Bilibili 主题色 */
}
.reply-section {
  margin-top: 20px;
}

.reply-title {
  font-size: 20px;
  font-weight: bold;
}

.reply-textarea {
  width: 100%;
  height: 100px;
  margin-top: 10px;
  padding: 5px;
  resize: none;
}

.reply-textarea:focus {
  outline: none;
}

.reply-button {
  margin-top: 10px;
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

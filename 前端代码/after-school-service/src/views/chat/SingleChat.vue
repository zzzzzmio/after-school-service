<script setup>
import { ref } from 'vue'
import { getFriendList } from '@/api/friend'
import { getChatList, toRead } from '@/api/chat'
import { useUserStore } from '@/stores'
import { PictureRounded, Folder } from '@element-plus/icons-vue'
import { onBeforeRouteLeave } from 'vue-router'
const emojis = [
  '😀',
  '😃',
  '😄',
  '😁',
  '😆',
  '😅',
  '😂',
  '🤣',
  '😊',
  '😇',
  '😉',
  '😌',
  '😍',
  '😘',
  '😗',
  '😙',
  '😚',
  '☺️',
  '🙂',
  '🤗',
  '🤩',
  '🤔',
  '🤨',
  '😐',
  '😑',
  '😶',
  '😏',
  '😒',
  '🙄',
  '😬',
  '😮',
  '😯',
  '😪',
  '😫',
  '😴',
  '😌',
  '😛',
  '😜',
  '😝',
  '🤤',
  '😋',
  '😛',
  '😜',
  '😝',
  '🤑',
  '😲',
  '🙁',
  '😟',
  '😦',
  '😧',
  '😢',
  '😥',
  '😰',
  '😨',
  '😭',
  '😱',
  '😖',
  '😣',
  '😞',
  '😓',
  '😩',
  '😫',
  '😤',
  '😡',
  '😠',
  '🤬',
  '😈',
  '👿',
  '💀',
  '☠️',
  '💩',
  '🤡',
  '👹',
  '👺',
  '👻',
  '👽',
  '🤖',
  '😺',
  '😸',
  '😹',
  '😻',
  '😼',
  '😽',
  '🙀',
  '😿',
  '😾'
]
const emojiShow = ref(false)
const emojiPopover = ref()
const userStore = useUserStore()
const friendList = ref([])
const chatList = ref([])
const fromUser = ref({
  fromuser: userStore.user.username,
  fromavatar: userStore.user.userPic
})
const toUser = ref({
  touser: '',
  toavatar: ''
})
const input = ref('')
const show = ref(false)
const video = ref()
let socket
const init = async () => {
  const res = await getFriendList(userStore.user.id)
  friendList.value = res.data.data
  const socketUrl = 'ws://localhost:8080/imserver'
  socket = new WebSocket(socketUrl)
  socket.onopen = () => {
    console.log('WebSocket open')
  }
  socket.onmessage = (message) => {
    const data = JSON.parse(message.data)
    if (
      (fromUser.value.fromuser === data.fromuser &&
        toUser.value.touser === data.touser) ||
      (toUser.value.touser === data.fromuser &&
        fromUser.value.fromuser === data.touser)
    ) {
      chatList.value.push(data)
      toRead({
        fromuser: fromUser.value.fromuser,
        touser: toUser.value.touser
      })
    }
  }
  socket.onclose = () => {
    console.log('WebSocket close')
  }
}
init()
onBeforeRouteLeave(() => {
  socket.onclose()
})
const getMsg = async (touser, toavatar) => {
  show.value = true
  toUser.value.touser = touser
  toUser.value.toavatar = toavatar
  toRead({ fromuser: fromUser.value.fromuser, touser: toUser.value.touser })
  const res = await getChatList({
    fromuser: fromUser.value.fromuser,
    touser: toUser.value.touser
  })
  chatList.value = res.data.data
}
const send = async () => {
  const msg = {
    fromuser: fromUser.value.fromuser,
    touser: toUser.value.touser,
    content: input.value,
    fromavatar: fromUser.value.fromavatar,
    toavatar: toUser.value.toavatar,
    type: 'text'
  }
  socket.send(JSON.stringify(msg))
  input.value = ''
}
const selectEmoji = (emoji) => {
  input.value = input.value + emoji
}
const success = async (res, file) => {
  console.log(file.raw.type, res.data)
  const type = file.raw.type
  if (type.startsWith('image/')) {
    const msg = {
      fromuser: fromUser.value.fromuser,
      touser: toUser.value.touser,
      content: res.data,
      fromavatar: fromUser.value.fromavatar,
      toavatar: toUser.value.toavatar,
      type: 'image'
    }
    socket.send(JSON.stringify(msg))
  } else if (type.startsWith('video/')) {
    const msg = {
      fromuser: fromUser.value.fromuser,
      touser: toUser.value.touser,
      content: res.data,
      fromavatar: fromUser.value.fromavatar,
      toavatar: toUser.value.toavatar,
      type: 'video'
    }
    socket.send(JSON.stringify(msg))
  } else if (type.startsWith('text/')) {
    const msg = {
      fromuser: fromUser.value.fromuser,
      touser: toUser.value.touser,
      content: res.data,
      fromavatar: fromUser.value.fromavatar,
      toavatar: toUser.value.toavatar,
      type: 'txt'
    }
    socket.send(JSON.stringify(msg))
  } else if (
    type.startsWith(
      'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
    )
  ) {
    const msg = {
      fromuser: fromUser.value.fromuser,
      touser: toUser.value.touser,
      content: res.data,
      fromavatar: fromUser.value.fromavatar,
      toavatar: toUser.value.toavatar,
      type: 'word'
    }
    socket.send(JSON.stringify(msg))
  }
}
</script>
<template>
  <div class="wechat-container">
    <!-- 左侧好友列表 -->
    <div class="friend-list-container">
      <div
        v-for="item in friendList"
        :key="item.id"
        class="friend-item"
        @click="getMsg(item.fname, item.favatar)"
      >
        <img
          class="avatar"
          :src="'http://localhost:8080/file/' + item.favatar"
        />
        <span class="name">{{ item.fname }}</span>
      </div>
    </div>
    <!-- 右侧聊天内容 -->
    <div class="chat-container">
      <br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
      <div v-for="item in chatList" :key="item.id" class="message">
        <div v-if="item.fromuser !== fromUser.fromuser" class="message-left">
          <img
            class="avatar"
            :src="'http://localhost:8080/file/' + item.fromavatar"
          />
          <template v-if="item.type === 'text'">
            <span class="content">{{ item.content }}</span>
          </template>
          <template v-if="item.type === 'txt' || item.type === 'word'">
            <div class="file-message">
              <span>对方发送了一个文件：</span>
              <a :href="'http://localhost:8080/file/' + item.content" download
                >点击下载</a
              >
            </div>
          </template>
          <template v-else-if="item.type === 'image'">
            <img
              :src="'http://localhost:8080/file/' + item.content"
              alt="image"
            />
          </template>
          <template v-else-if="item.type === 'video'">
            <div class="message-video-container">
              <video ref="video" controls class="message-video">
                <source
                  :src="'http://localhost:8080/file/' + item.content"
                  type="video/mp4"
                />
              </video>
            </div>
          </template>
        </div>
        <div v-else class="message-right">
          <template v-if="item.type === 'text'">
            <span class="content">{{ item.content }}</span>
          </template>
          <template v-else-if="item.type === 'txt' || item.type === 'word'">
            <div class="file-message">
              <span>您发送了一个文件：</span>
              <a :href="'http://localhost:8080/file/' + item.content" download
                >点击下载</a
              >
            </div>
          </template>
          <template v-else-if="item.type === 'image'">
            <img
              :src="'http://localhost:8080/file/' + item.content"
              alt="image"
            />
          </template>
          <template v-else-if="item.type === 'video'">
            <div class="message-container">
              <div class="message-content">
                <video ref="video" controls class="message-video">
                  <source
                    :src="'http://localhost:8080/file/' + item.content"
                    type="video/mp4"
                  />
                </video>
              </div>
            </div>
          </template>
          <img
            class="avatar"
            :src="'http://localhost:8080/file/' + item.fromavatar"
          />
        </div>
      </div>
      <!-- 底部输入框 -->
      <!-- 表情 -->
      <div class="input-box" v-show="show">
        <div class="emoji-selector">
          <el-popover
            ref="emojiPopover"
            placement="top-start"
            width="250"
            trigger="click"
            v-model:visible-arrow="emojiShow"
          >
            <template #reference>
              <el-button class="emoji-button" :icon="PictureRounded" circle />
            </template>
            <div class="emoji-list">
              <el-row>
                <el-col v-for="(emoji, index) in emojis" :key="index" :span="3">
                  <span @click="selectEmoji(emoji)">{{ emoji }}</span>
                </el-col>
              </el-row>
            </div>
          </el-popover>
        </div>
        <!-- 上传文件 -->
        <el-upload
          action="/api/file"
          :on-success="success"
          :show-file-list="false"
        >
          <el-button :icon="Folder" circle />
        </el-upload>

        <el-input v-model="input" placeholder="请输入消息"></el-input>
        <el-button type="primary" @click="send">发送</el-button>
      </div>
    </div>
  </div>
</template>
<style scoped>
/* 通用样式 */
.wechat-container {
  display: flex;
  height: 100vh;
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: 'Helvetica Neue', Arial, sans-serif; /* 微信默认字体 */
}

/* 左侧好友列表 */
.friend-list-container {
  width: 25%;
  background-color: #f2f2f2;
  overflow-y: auto;
  padding: 10px;
}

.friend-item {
  display: flex;
  align-items: center;
  padding: 10px 0;
  cursor: pointer;
  border-bottom: 1px solid #e5e5e5;
}

.friend-item:last-child {
  border-bottom: none;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
}

.name {
  flex: 1;
  font-size: 14px;
  color: #333;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

/* 右侧聊天内容 */
.chat-container {
  height: 650px;
  width: 75%;
  background-color: #fefefe; /* 调整背景色 */
  padding: 10px;
  overflow-y: auto;
  flex: 1; /* 确保占据剩余空间 */
  min-height: 0; /* 修复浏览器高度问题 */
  max-height: calc(100vh - 60px); /* 固定高度，剩余部分由输入框占据 */
}

.message {
  margin-bottom: 10px;
}

.message-left {
  text-align: left;
}

.message-right {
  text-align: right;
}

.avatar + .content {
  margin-left: 8px;
}

.content {
  display: inline-block;
  background-color: #eee; /* 聊天气泡背景色 */
  padding: 8px 12px;
  border-radius: 8px; /* 更大的圆角 */
  max-width: 70%; /* 调整最大宽度 */
  word-wrap: break-word;
}

.message-right .content {
  background-color: #07bec1;
  color: #fff;
}

/* 底部输入框 */
/* 底部输入框 */
.input-box {
  position: sticky;
  bottom: 0;
  padding: 10px;
  background-color: #fff;
  display: flex;
  align-items: center;
  border-top: 1px solid #e5e5e5;
  margin-bottom: 0;
  z-index: 1; /* 确保输入框始终处于顶部 */
}

.input-box > * {
  margin-right: 10px;
}

.el-input__inner {
  flex: 1;
  height: 30px;
  line-height: 30px;
}
.emoji-selector {
  display: inline-block;
}

.emoji-list {
  padding: 10px;
}

.emoji-list span {
  cursor: pointer;
  font-size: 24px;
  margin: 5px;
  transition: transform 0.3s ease;
}

.emoji-list span:hover {
  transform: scale(1.2);
}

.emoji-button {
  font-size: 20px;
  color: #666;
}
.message-video-container {
  max-width: 250px; /* 设置视频容器的最大宽度 */
  margin: 10px 0; /* 设置视频容器的外边距 */
  border-radius: 12px; /* 设置视频容器的边框圆角 */
  overflow: hidden; /* 隐藏视频超出容器的部分 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
}

.message-video {
  width: 100%; /* 让视频充满整个容器 */
}

/* 你也可以自定义视频播放按钮样式 */
.message-video::before {
  content: '\25B6'; /* 添加播放按钮图标 */
  font-size: 32px; /* 设置图标大小 */
  position: absolute; /* 绝对定位，位于视频居中 */
  top: 50%; /* 距离顶部 50% */
  left: 50%; /* 距离左侧 50% */
  transform: translate(-50%, -50%); /* 居中 */
  color: #fff; /* 图标颜色 */
}
.message-container {
  display: flex;
  flex-direction: row-reverse; /* 将消息内容放在右侧 */
  align-items: flex-start;
}

.message-content {
  position: relative;
}

.message-video {
  max-width: 250px; /* 设置视频的最大宽度 */
  border-radius: 12px; /* 设置视频容器的边框圆角 */
  overflow: hidden; /* 隐藏视频超出容器的部分 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
}
/* 文件消息样式 */
.file-message {
  display: inline-block;
  background-color: #eee; /* 文件消息气泡背景色 */
  padding: 8px 12px;
  border-radius: 8px; /* 圆角大小与文本消息一致 */
  max-width: 70%; /* 与文本消息一致的最大宽度 */
  word-wrap: break-word;
}

.message-right .file-message {
  background-color: #07bec1; /* 发送文件消息时的背景色 */
  color: #fff;
}
</style>

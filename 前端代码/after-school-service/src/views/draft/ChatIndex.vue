<script setup>
import { getList, getInfo, getMsg } from '@/api/draft'
import { ref, onMounted } from 'vue'
const list = ref([])
const username = ref('')
const msg = ref([])
const toUser = ref('')
const text = ref('')
const fromavatar = ref('')
const toavatar = ref('')
const flag = ref('1716810928822')
let socket
onMounted(async () => {
  const res = await getList()
  list.value = res.data.data
  const {
    data: { data }
  } = await getInfo()
  username.value = data.username
  fromavatar.value = data.userPic
  console.log(username.value)
  console.log(list.value)
  const socketUrl = 'ws://localhost:8080/imserver'
  socket = new WebSocket(socketUrl)
  socket.onopen = () => {
    console.log('WebSocket open')
  }
  socket.onmessage = (message) => {
    const data = JSON.parse(message.data)
    if (
      (data.fromuser === username.value && data.touser === toUser.value) ||
      (data.fromuser === toUser.value && data.touser === username.value)
    ) {
      msg.value.push(data)
    }
  }
  socket.onclose = () => {
    console.log('WebSocket close')
  }
})
const getMessage = async (touser, avatar) => {
  toUser.value = touser
  toavatar.value = avatar
  console.log(toUser.value, toavatar.value)
  const res = await getMsg(username.value, touser)
  msg.value = res.data.data
}
const send = () => {
  const message = {
    fromuser: username.value,
    touser: toUser.value,
    content: text.value,
    fromavatar: fromavatar.value,
    toavatar: toavatar.value
  }
  socket.send(JSON.stringify(message))
  text.value = ''
}
const success = (res) => {
  console.log(res)
  flag.value = res.data
}
</script>
<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="200px">
        <h1>好友列表</h1>
        <div>
          <h2
            v-for="item in list"
            :key="item.id"
            @click="getMessage(item.username, item.userPic)"
          >
            {{ item.username }}
          </h2>
        </div>
      </el-aside>
      <el-main>
        <h1>聊天框</h1>
        <div v-for="(message, index) in msg" :key="index">
          {{ message.content }}
        </div>
        <el-input v-model="text"></el-input>
        <el-button type="primary" @click="send">发送</el-button>
      </el-main>
      <el-upload action="/api/file" :on-success="success">
        <el-button type="primary">Click to upload</el-button>
        <template #tip>
          <div class="el-upload__tip">
            jpg/png files with a size less than 500KB.
          </div>
        </template>
      </el-upload>
      <!-- <el-image
        style="width: 100px; height: 100px"
        :src="'http://localhost:8080/file/' + flag"
      /> -->
    </el-container>
  </div>
  <!-- <div class="wrapper-jk">
    <iframe
      :src="'http://localhost:8080/file/' + flag"
      style="width: 100%; height: 100%"
      webkitallowfullscreen="true"
      mozallowfullscreen="true"
      allowfullscreen="true"
      allow="autoplay; fullscreen; microphone;"
      frameborder="0"
    ></iframe>
  </div> -->
  <video
    :src="'http://localhost:8080/file/' + flag"
    controls
    width="100%"
    height="100%"
  ></video>
</template>

<style scoped></style>

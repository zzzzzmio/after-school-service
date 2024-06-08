<script setup>
import { Search } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { getUnreaded } from '@/api/chat'
import { useUserStore } from '@/stores'
import router from '@/router'
const userStore = useUserStore()
const list = ref([])
const init = async () => {
  const res = await getUnreaded({ touser: userStore.user.username })
  list.value = res.data.data
  console.log(list.value)
}
init()
const toMessage = () => {
  router.replace('/chat/single')
}
</script>
<template>
  <h1>未读消息</h1>
  <el-table :data="list" style="width: 100%">
    <el-table-column label="头像">
      <template #default="{ row }">
        <img :src="'http://localhost:8080/file/' + row.fromavatar" />
      </template>
    </el-table-column>
    <el-table-column prop="fromuser" label="用户名称" width="180" />
    <el-table-column prop="time" label="发送时间" />
    <el-table-column prop="count" label="消息数量" />
    <el-table-column label="操作">
      <el-button @click="toMessage" :icon="Search" circle />
    </el-table-column>
  </el-table>
</template>
<style scoped></style>

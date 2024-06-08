<script setup>
import { ref } from 'vue'
import { getFriendList, deleteFriend } from '@/api/friend'
import { getApplyList, agreeApply, refuseApply } from '@/api/apply'
import { findFriend } from '@/api/user'
import { apply } from '@/api/apply'
import { useUserStore } from '@/stores'
import { Delete, Check, Close } from '@element-plus/icons-vue'
const userStore = useUserStore()
const list = ref([])
const applyList = ref([])
const Dialog = ref(false)
const dia = ref(false)
const input = ref('')
const searchList = ref([])
const init = async () => {
  const res = await getFriendList(userStore.user.id)
  list.value = res.data.data
}
init()
const getList = async () => {
  const res = await getApplyList(userStore.user.id)
  applyList.value = res.data.data
}
const open = () => {
  Dialog.value = true
  getList()
}
const agree = async (id) => {
  await agreeApply(id)
  ElMessage.success('添加成功')
  getList()
  init()
}
const refuse = async (id) => {
  await refuseApply(id)
  ElMessage.success('已拒绝')
  getList()
  init()
}
const remove = async (row) => {
  await deleteFriend(row.ownerId, row.fid)
  ElMessage.success('删除成功')
  init()
}
const find = () => {
  dia.value = true
  input.value = ''
  searchList.value = []
}
const search = async () => {
  const res = await findFriend(input.value)
  searchList.value = res.data.data
  input.value = ''
}
const applyFriend = async (row) => {
  const data = {
    fromUser: userStore.user.id,
    fromName: userStore.user.username,
    fromAvatar: userStore.user.userPic,
    toUser: row.id,
    toName: row.username,
    toAvatar: row.userPic
  }
  await apply(data)
  ElMessage.success('发送好友申请')
}
</script>
<template>
  <div>
    <h1>好友列表</h1>
    <el-button type="primary" plain @click="open">申请列表</el-button>
    <el-button @click="find">搜索</el-button>
    <el-table :data="list" style="width: 100%" border>
      <el-table-column type="index" width="50" />
      <el-table-column label="头像">
        <template #default="{ row }">
          <figure class="avatar-container">
            <img
              :src="'http://localhost:8080/file/' + row.favatar"
              class="avatar-image"
            />
          </figure>
        </template>
      </el-table-column>
      <el-table-column prop="fname" label="名称" width="180" />
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button type="danger" :icon="Delete" circle @click="remove(row)" />
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="Dialog" title="申请列表">
      <el-table :data="applyList" border>
        <el-table-column type="index" width="50" />
        <el-table-column label="头像">
          <template #default="{ row }">
            <figure class="avatar-container">
              <img
                :src="'http://localhost:8080/file/' + row.fromAvatar"
                class="avatar-image"
              />
            </figure>
          </template>
        </el-table-column>
        <el-table-column prop="fromName" label="名称" width="180" />
        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button
              type="success"
              :icon="Check"
              circle
              @click="agree(row.id)"
            />
            <el-button
              type="info"
              :icon="Close"
              circle
              @click="refuse(row.id)"
            />
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
    <el-dialog v-model="dia" title="搜索" class="search-dialog">
      <div class="search-input">
        <el-input v-model="input" placeholder="输入搜索内容"></el-input>
        <el-button type="primary" class="search-button" @click="search"
          >搜索</el-button
        >
      </div>
      <el-table :data="searchList" style="width: 100%">
        <el-table-column label="头像" width="120">
          <template #default="{ row }">
            <img
              :src="'http://localhost:8080/file/' + row.userPic"
              class="user-avatar"
            />
          </template>
        </el-table-column>
        <el-table-column prop="username" label="用户名称" />
        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button
              @click="applyFriend(row)"
              type="primary"
              class="add-friend-button"
              >加好友</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<style scoped>
.avatar-container {
  width: 50px;
  height: 50px;
  overflow: hidden;
  border-radius: 50%;
  margin: 0 auto;
}

.avatar-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.search-dialog {
  max-width: 600px;
  margin: 0 auto;
}

.search-input {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 20px;
}

.search-button {
  margin-left: 10px;
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

.add-friend-button {
  font-size: 14px;
  padding: 8px 16px;
}
</style>

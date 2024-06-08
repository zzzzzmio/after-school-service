<script setup>
import { ref } from 'vue'
import { Delete, Search } from '@element-plus/icons-vue'
import {
  getApplyList,
  getParentList,
  getFamilyList,
  apply,
  agree,
  deleteFamily
} from '@/api/family'
import { getFriendList } from '@/api/friend'
import { getList } from '@/api/study'
import { useUserStore } from '@/stores'
const userStore = useUserStore()
const familyList = ref([])
const applyList = ref([])
const friendList = ref([])
const Dialog = ref(false)
const dia = ref(false)
const studyList = ref([])
const dialog = ref(false)
const init = async () => {
  const res1 = await getFamilyList(userStore.user.id)
  familyList.value = res1.data.data
  const res3 = await getFriendList(userStore.user.id)
  friendList.value = res3.data.data
}
init()
const openBind = () => {
  Dialog.value = true
}
const binding = async (row) => {
  if (userStore.user.usertype === 1) {
    const data = {
      parentId: row.fid,
      parentName: row.fname,
      parentAvatar: row.favatar,
      stuId: userStore.user.id,
      stuName: userStore.user.username,
      stuAvatar: userStore.user.userPic,
      state: 0
    }
    await apply(data)
    ElMessage.success('发送绑定申请')
  } else {
    const data = {
      parentId: userStore.user.id,
      parentName: userStore.user.username,
      parentAvatar: userStore.user.userPic,
      stuId: row.fid,
      stuName: row.fname,
      stuAvatar: row.favatar,
      state: 1
    }
    await apply(data)
    ElMessage.success('发送绑定申请')
  }
}
const openApply = async () => {
  dia.value = true
  if (userStore.user.usertype === 1) {
    const res = await getApplyList(userStore.user.id)
    applyList.value = res.data.data
  } else {
    const res = await getParentList(userStore.user.id)
    applyList.value = res.data.data
  }
}
const argeeBinding = async (id) => {
  await agree(id)
  ElMessage.success('绑定成功')
  if (userStore.user.usertype === 1) {
    const res = await getApplyList(userStore.user.id)
    applyList.value = res.data.data
  } else {
    const res = await getParentList(userStore.user.id)
    applyList.value = res.data.data
  }
  init()
}
const remove = async (id) => {
  await deleteFamily(id)
  ElMessage.success('解绑成功')
  init()
}
const getStudyDetail = async (stuId) => {
  const res = await getList(stuId)
  studyList.value = res.data.data
  dialog.value = true
}
</script>
<template>
  <div class="family-container">
    <h1>我的家庭</h1>
    <div class="button-container">
      <el-button type="primary" @click="openApply">申请列表</el-button>
      <el-button @click="openBind" type="success">绑定家庭</el-button>
    </div>
    <div class="family-table">
      <el-table :data="familyList" style="width: 100%">
        <el-table-column type="index" />
        <el-table-column label="头像">
          <template #default="{ row }">
            <img
              v-if="userStore.user.usertype === 1"
              :src="'http://localhost:8080/file/' + row.parentAvatar"
            />
            <img v-else :src="'http://localhost:8080/file/' + row.stuAvatar" />
          </template>
        </el-table-column>
        <el-table-column
          :prop="userStore.user.usertype === 1 ? 'parentName' : 'stuName'"
          :label="userStore.user.usertype === 1 ? '家长名称' : '孩子名称'"
          width="180"
        />
        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button
              v-if="userStore.user.usertype === 2"
              @click="getStudyDetail(row.stuId)"
              :icon="Search"
              circle
            />
            <el-button
              @click="remove(row.id)"
              type="danger"
              :icon="Delete"
              circle
            />
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
  <el-dialog v-model="Dialog" title="绑定家庭">
    <el-table :data="friendList" style="width: 100%">
      <el-table-column label="头像">
        <template #default="{ row }">
          <img :src="'http://localhost:8080/file/' + row.favatar" />
        </template>
      </el-table-column>
      <el-table-column prop="fname" label="用户名称"></el-table-column>
      <el-table-column>
        <template #default="{ row }">
          <el-button @click="binding(row)" type="primary">绑定</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>
  <el-dialog v-model="dia" title="申请列表">
    <el-table :data="applyList" style="width: 100%">
      <el-table-column label="用户头像">
        <template #default="{ row }">
          <img
            v-if="userStore.user.usertype === 1"
            :src="'http://localhost:8080/file/' + row.parentAvatar"
          />
          <img v-else :src="'http://localhost:8080/file/' + row.stuAvatar" />
        </template>
      </el-table-column>
      <el-table-column
        label="用户名称"
        :prop="userStore.user.usertype === 1 ? 'parentName' : 'stuName'"
      />
      <el-table-column>
        <template #default="{ row }">
          <el-button @click="argeeBinding(row.id)" type="primary"
            >同意</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>
  <el-dialog v-model="dialog" style="width: 80%; max-width: 600px">
    <div style="padding: 20px">
      <div v-for="item in studyList" :key="item.id" style="margin-bottom: 20px">
        <span style="font-weight: bold">打卡时间：</span>
        <span>{{ item.year }}年{{ item.month }}月{{ item.date }}日</span><br />
        <img
          :src="'http://localhost:8080/file/' + item.img"
          style="max-width: 100%; margin-top: 10px"
        />
      </div>
    </div>
  </el-dialog>
</template>

<style scoped>
.family-container {
  padding: 20px;
}

.button-container {
  margin-bottom: 20px;
}

.family-table {
  overflow-x: auto;
}
</style>

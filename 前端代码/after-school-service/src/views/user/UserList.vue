<script setup>
import { getList, getDetail, changeInfo } from '@/api/user'
import { ref } from 'vue'
import { Search } from '@element-plus/icons-vue'
const total = ref(0)
const page = ref({
  pageNum: 1,
  pageSize: 5
})
const list = ref([])
const Dialog = ref(false)
const form = ref()
const formModel = ref({
  nickname: '',
  email: ''
})
const init = async () => {
  const res = await getList(page.value)
  list.value = res.data.data.items
  total.value = res.data.data.total
}
init()
const formatLocation = (row, column, cellValue) => {
  if (cellValue === 1) {
    return '学生'
  } else if (cellValue === 2) {
    return '家长'
  } else if (cellValue === 3) {
    return '教师'
  } else {
    return '后台管理人员'
  }
}
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
const open = async (id) => {
  Dialog.value = true
  const res = await getDetail(id)
  formModel.value = res.data.data
  console.log(formModel.value)
}
const confirm = async () => {
  await changeInfo(formModel.value)
  ElMessage.success('修改成功')
  Dialog.value = false
  formModel.value = {
    nickname: '',
    email: ''
  }
  init()
}
</script>
<template>
  <div class="user-management">
    <h1>用户管理</h1>
    <el-table :data="list" class="user-table">
      <el-table-column type="index" width="50" />
      <el-table-column label="用户头像">
        <template #default="{ row }">
          <div class="avatar-container">
            <img
              :src="'http://localhost:8080/file/' + row.userPic"
              class="user-avatar"
            />
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="username" label="用户名称" width="180" />
      <el-table-column prop="nickname" label="昵称" width="180" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column
        prop="usertype"
        label="用户类型"
        :formatter="formatLocation"
      />
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button :icon="Search" circle @click="open(row.id)" />
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      class="pagination"
      v-model:current-page="page.pageNum"
      v-model:page-size="page.pageSize"
      :page-sizes="[2, 3, 5, 10]"
      background
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
    <el-dialog v-model="Dialog" title="用户详情" width="500">
      <el-form
        :model="formModel"
        ref="form"
        label-width="auto"
        class="user-form"
      >
        <el-form-item label="用户昵称">
          <el-input v-model="formModel.nickname"></el-input>
        </el-form-item>
        <el-form-item label="用户邮箱">
          <el-input v-model="formModel.email"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="confirm">确认</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<style scoped>
.user-management {
  padding: 20px;
}

.user-table {
  width: 100%;
  margin-bottom: 20px;
}

.avatar-container {
  width: 50px;
  height: 50px;
  overflow: hidden;
  border-radius: 50%;
}

.user-avatar {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.pagination {
  margin-top: 20px;
  justify-content: flex-end;
}

.user-form {
  max-width: 600px;
}
</style>

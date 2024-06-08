<script setup>
import {
  getList,
  getDetail,
  addNotice,
  updateNotice,
  deleteNotice
} from '@/api/notice'
import { Search, Delete } from '@element-plus/icons-vue'
import { ref } from 'vue'
const total = ref(0)
const page = ref({
  pageNum: 1,
  pageSize: 5
})
const Dialog = ref(false)
const list = ref([])
const formModel = ref({
  title: '',
  content: ''
})
const rules = {
  title: [{ require: true, message: '请输入标题', trigger: 'blur' }],
  content: [{ require: true, message: '请输入内容', trigger: 'blur' }]
}
const update = ref(false)
const form = ref()
const init = async () => {
  const res = await getList(page.value)
  list.value = res.data.data.items
  total.value = res.data.data.total
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
const check = async (row) => {
  update.value = true
  Dialog.value = true
  const res = await getDetail(row.id)
  formModel.value = res.data.data
}
const add = () => {
  update.value = false
  Dialog.value = true
  formModel.value = {
    title: '',
    content: ''
  }
}
const confirm = async () => {
  if (update.value === true) {
    await updateNotice(formModel.value)
    ElMessage.success('修改成功')
    formModel.value = {
      title: '',
      content: ''
    }
    update.value = false
    Dialog.value = false
  } else {
    await addNotice(formModel.value)
    ElMessage.success('添加成功')
    formModel.value = {
      title: '',
      content: ''
    }
    Dialog.value = false
  }
  init()
}
const remove = async (row) => {
  await deleteNotice(row.id)
  ElMessage.success('删除成功')
  init()
}
</script>
<template>
  <div>
    <div
      style="display: flex; justify-content: space-between; align-items: center"
    >
      <h1>公告管理</h1>
      <el-button type="primary" @click="add">添加公告</el-button>
    </div>
    <el-table :data="list" style="width: 100%">
      <el-table-column type="index" label="序号" width="60" align="center" />
      <el-table-column prop="title" label="标题" width="180" />
      <el-table-column prop="content" label="内容" width="auto">
        <template v-slot="scope">
          <div class="ellipsis-text">{{ scope.row.content }}</div>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template v-slot="scope">
          <el-button
            :icon="Search"
            @click="check(scope.row)"
            circle
            class="action-button"
          />
          <el-button
            type="danger"
            :icon="Delete"
            circle
            class="action-button"
            @click="remove(scope.row)"
          />
        </template>
      </el-table-column>
    </el-table>
  </div>
  <el-pagination
    style="margin-top: 20px; justify-content: flex-end"
    v-model:current-page="page.pageNum"
    v-model:page-size="page.pageSize"
    :page-sizes="[2, 3, 5, 10]"
    background
    layout="total, sizes, prev, pager, next, jumper"
    :total="total"
    @size-change="handleSizeChange"
    @current-change="handleCurrentChange"
  />
  <el-dialog
    v-model="Dialog"
    title="公告管理"
    width="500"
    :before-close="handleClose"
  >
    <el-form
      :model="formModel"
      ref="form"
      :rules="rules"
      label-width="auto"
      style="max-width: 600px"
    >
      <el-form-item label="标题" prop="title">
        <el-input v-model="formModel.title"></el-input>
      </el-form-item>
      <el-form-item label="内容" prop="content">
        <el-input v-model="formModel.content"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="confirm">确认</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<style scoped>
.page-title {
  font-size: 24px;
  margin-bottom: 20px;
}

.ellipsis-text {
  max-width: 180px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.action-button {
  margin-left: 5px;
  margin-right: 5px;
}
</style>

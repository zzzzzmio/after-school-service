<script setup>
import { ref } from 'vue'
import { Search, Delete, Plus } from '@element-plus/icons-vue'
import {
  getTeaList,
  addCourse,
  updateCourse,
  getCourseDetail,
  deleteCourse
} from '@/api/course'
import { useUserStore } from '@/stores'
const userStore = useUserStore()
const isUpdate = ref(false)
const list = ref([])
const formModel = ref({
  cname: '',
  tname: userStore.user.username,
  content: '',
  ctime: '',
  price: '',
  resources: '',
  coverImg: ''
})
const Dialog = ref(false)
const init = async () => {
  const res = await getTeaList(userStore.user.id)
  list.value = res.data.data
}
init()
const open = () => {
  formModel.value = {
    cname: '',
    tname: userStore.user.username,
    content: '',
    ctime: '',
    price: '',
    resources: '',
    coverImg: ''
  }
  Dialog.value = true
}
const success = (uploadFile) => {
  formModel.value.resources = uploadFile.data
}
const imgSuccess = (uploadFile) => {
  formModel.value.coverImg = uploadFile.data
}
const add = async () => {
  isUpdate.value = false
  await addCourse(formModel.value)
  ElMessage.success('添加成功')
  Dialog.value = false
  init()
}
const check = async (row) => {
  const res = await getCourseDetail(row.id)
  formModel.value = res.data.data
  isUpdate.value = true
  Dialog.value = true
}
const update = async () => {
  await updateCourse(formModel.value)
  ElMessage.success('更新成功')
  Dialog.value = false
  init()
}
const remove = async (row) => {
  await deleteCourse(row.id)
  ElMessage.success('删除成功')
  init()
}
</script>
<template>
  <div class="course-table-container">
    <h1>我的课程</h1>
    <el-button type="primary" class="add-course-button" @click="open()"
      >添加课程</el-button
    >
    <el-table :data="list" class="course-table">
      <el-table-column type="index" />
      <el-table-column label="封面">
        <template #default="{ row }">
          <img
            class="course-cover-img"
            :src="'http://localhost:8080/file/' + row.coverImg"
          />
        </template>
      </el-table-column>

      <el-table-column label="课程名称" prop="cname"></el-table-column>
      <el-table-column label="上课时间" prop="ctime" />
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button @click="check(row)" :icon="Search" circle />
          <el-button @click="remove(row)" type="danger" :icon="Delete" circle />
        </template>
      </el-table-column>
    </el-table>
  </div>

  <el-dialog v-model="Dialog">
    <el-form :model="formModel" label-width="auto" style="max-width: 600px">
      <el-form-item label="课程名称">
        <el-input v-model="formModel.cname"></el-input>
      </el-form-item>
      <el-form-item label="讲师名称">
        <el-input v-model="formModel.tname"></el-input>
      </el-form-item>
      <el-form-item label="课程描述">
        <el-input v-model="formModel.content"></el-input>
      </el-form-item>
      <el-form-item label="价格">
        <el-input v-model="formModel.price"></el-input>
      </el-form-item>
      <el-form-item label="上课时间">
        <el-input v-model="formModel.ctime"></el-input>
      </el-form-item>
      <el-form-item>
        <el-upload
          class="upload-demo"
          :headers="{ Authorization: userStore.token }"
          action="/api/file"
          :limit="3"
          :on-success="success"
        >
          <video
            style="height: 300px; width: 250px"
            v-if="formModel.resources !== ''"
            :src="'http://localhost:8080/file/' + formModel.resources"
          ></video>
          <el-button v-else type="primary">上传课程</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item>
        <el-upload
          class="avatar-uploader"
          :headers="{ Authorization: userStore.token }"
          action="/api/file"
          :show-file-list="false"
          :on-success="imgSuccess"
        >
          <img
            v-if="formModel.coverImg"
            :src="'http://localhost:8080/file/' + formModel.coverImg"
            class="avatar"
          />
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item>
        <el-button v-if="!isUpdate" @click="add" type="primary">添加</el-button>
        <el-button v-else @click="update" type="primary">更新</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>
<style scoped>
/* 设置表格容器的样式 */
.course-table-container {
  padding: 20px;
}

/* 设置表格的边框和阴影 */
.course-table {
  border-collapse: collapse;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  width: 100%;
}

/* 设置表头的背景色和文字颜色 */
.course-table th {
  background-color: #f0f0f0;
  color: #333;
  padding: 10px;
  text-align: left;
}

/* 设置单元格的边框样式 */
.course-table td {
  border-bottom: 1px solid #e0e0e0;
  padding: 10px;
}

/* 设置按钮的样式 */
.el-button {
  margin-right: 10px;
  padding: 8px 16px;
  font-size: 14px;
}

/* 设置添加课程按钮的样式 */
.add-course-button {
  margin-bottom: 20px;
}
/* 设置封面图片的固定大小 */
.course-cover-img {
  width: 100px; /* 设置宽度为100像素 */
  height: 100px; /* 设置高度为100像素 */
  object-fit: cover; /* 使用 cover 填充方式，保持图片比例并填充整个容器 */
  border-radius: 4px; /* 设置圆角 */
}
</style>

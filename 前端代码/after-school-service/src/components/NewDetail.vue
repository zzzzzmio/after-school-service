<script setup>
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'
import { ref } from 'vue'
import { getNewsDetail, updateNews, addNews } from '@/api/news'
import { useUserStore } from '@/stores'
const userStore = useUserStore()
const editorRef = ref()
const formModel = ref({
  id: '',
  title: '',
  coverImg: '',
  context: ''
})
const rules = {}
const form = ref()
const update = ref(false)
const open = async (row) => {
  update.value = true
  const res = await getNewsDetail(row.id)
  formModel.value = res.data.data
}
const reflash = () => {
  formModel.value = {
    id: '',
    title: '',
    coverImg: '',
    context: ''
  }
  editorRef.value.setHTML('')
}
const emit = defineEmits(['success'])
const onUploadFile = (uploadFile) => {
  formModel.value.coverImg = uploadFile.data
}
const confirm = async () => {
  if (update.value === true) {
    formModel.value.context = stripHtml(formModel.value.context)
    await updateNews(formModel.value)
    ElMessage.success('更新成功')
    formModel.value = {
      id: '',
      title: '',
      coverImg: '',
      context: ''
    }
    editorRef.value.setHTML('')
    update.value = false
    emit('success')
  } else {
    formModel.value.context = stripHtml(formModel.value.context)
    await addNews(formModel.value)
    ElMessage.success('添加成功')
    formModel.value = {
      id: '',
      title: '',
      coverImg: '',
      context: ''
    }
    editorRef.value.setHTML('')
    emit('success')
  }
}
const stripHtml = (html) => {
  let doc = new DOMParser().parseFromString(html, 'text/html')
  return doc.body.textContent || ''
}
defineExpose({
  open,
  reflash
})
</script>
<template>
  <h1>新闻公告详情</h1>
  <el-form :model="formModel" :rules="rules" ref="form" class="form">
    <el-form-item label="标题" prop="title">
      <el-input v-model="formModel.title"></el-input>
    </el-form-item>
    <el-form-item label="商品封面" prop="coverImg">
      <el-upload
        class="avatar-uploader"
        auto-upload="true"
        :show-file-list="false"
        name="file"
        :headers="{ Authorization: userStore.token }"
        action="/api/file"
        :on-success="onUploadFile"
      >
        <img
          v-if="formModel.coverImg !== ''"
          :src="'http://localhost:8080/file/' + formModel.coverImg"
          class="avatar"
        />
        <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        <div class="upload-text">点击上传图片</div>
      </el-upload>
    </el-form-item>
    <el-form-item label="内容">
      <div class="editor">
        <quill-editor
          theme="snow"
          contentType="html"
          v-model:content="formModel.context"
          ref="editorRef"
        ></quill-editor>
      </div>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" round @click="confirm">确认</el-button>
    </el-form-item>
  </el-form>
</template>
<style>
.product-details {
  padding: 20px;
}

.form {
  max-width: 600px;
  margin: 0 auto;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: border-color 0.3s ease;
}

.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}

.avatar {
  width: 100px;
  height: 100px;
  object-fit: cover;
}

.upload-text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.editor {
  margin-bottom: 20px;
  border: 1px solid #d9d9d9;
  border-radius: 6px;
}

.button-container {
  display: flex;
  justify-content: flex-end; /* 将按钮容器推到右边 */
  margin-top: 20px;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>

<script setup>
import { getList, getNews, addHome, deleteHomeNews } from '@/api/home'
import { ref, onMounted } from 'vue'
import { Delete } from '@element-plus/icons-vue'
const list = ref([])
const Dialog = ref(false)
const nList = ref([])
onMounted(async () => {
  const res = await getList()
  list.value = res.data.data
})
const formatLocation = (row, column, cellValue) => {
  if (cellValue === 1) {
    return '顶部'
  } else if (cellValue === 2) {
    return '底部'
  } else {
    return '未知位置'
  }
}
const open = async () => {
  Dialog.value = true
  const res = await getNews()
  nList.value = res.data.data
}
const add = async (row, location) => {
  const data = {
    nid: row.id,
    coverImg: row.coverImg,
    location: location
  }
  await addHome(data)
  ElMessage.success('添加成功')
  Dialog.value = false
  const res = await getList()
  list.value = res.data.data
}
const deleteHome = async (row) => {
  await deleteHomeNews(row.id)
  ElMessage.success('删除成功')
  const res = await getList()
  list.value = res.data.data
}
</script>
<template>
  <div class="page-container">
    <div class="header">
      <h1>首页轮换管理</h1>
      <el-button type="primary" @click="open">添加首页元素</el-button>
    </div>
    <el-table :data="list" style="width: 100%">
      <el-table-column type="index" width="50" />
      <el-table-column label="封面">
        <template #default="{ row }">
          <div class="cover-container">
            <img
              :src="'http://localhost:8080/file/' + row.coverImg"
              class="cover-image"
            />
          </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="location"
        label="位置"
        :formatter="formatLocation"
      ></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button
            type="danger"
            :icon="Delete"
            circle
            @click="deleteHome(row)"
          />
        </template>
      </el-table-column>
    </el-table>
    <el-dialog v-model="Dialog" title="添加首页元素" width="800px">
      <el-table :data="nList" style="width: 100%">
        <el-table-column type="index" width="50" />
        <el-table-column label="封面">
          <template #default="{ row }">
            <div class="cover-container">
              <img
                :src="'http://localhost:8080/file/' + row.coverImg"
                class="cover-image"
              />
            </div>
          </template>
        </el-table-column>
        <el-table-column property="title" label="标题" width="200" />
        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="add(row, 1)"
              >加入至顶部</el-button
            >
            <el-button type="success" size="small" @click="add(row, 2)"
              >加入至底部</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<style scoped>
.page-container {
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.header h1 {
  margin: 0;
  font-size: 24px;
}
.cover-container {
  width: 120px;
  height: 80px;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
}

.cover-image {
  width: 100%;
  height: auto;
  transition: transform 0.3s ease;
}

.cover-image:hover {
  transform: scale(1.1);
}

.el-dialog {
  --el-dialog-border-radius: 10px;
}

.el-table th,
.el-table td {
  padding: 12px 15px;
  border-bottom: 1px solid #ebeef5;
}

.el-table th {
  background-color: #f8f9fa;
  color: #606266;
}

.el-button {
  margin: 0 5px;
}
</style>

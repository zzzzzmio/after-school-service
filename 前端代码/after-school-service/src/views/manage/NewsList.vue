<script setup>
import { ref, nextTick } from 'vue'
import { getNewsList, deleteNew } from '@/api/news'
import { Edit, Delete } from '@element-plus/icons-vue'
import NewDetail from '@/components/NewDetail.vue'
const Detail = ref()
const drawer = ref(false)
const total = ref(0)
const page = ref({
  pageNum: 1,
  pageSize: 5
})
const list = ref([])
//获取新闻列表
const getList = async () => {
  const res = await getNewsList(page.value)
  list.value = res.data.data.items
  total.value = res.data.data.total
}
getList()
//每页条数切换
const handleSizeChange = (size) => {
  page.value.size = size
  page.value.pageNum = 1
  getList()
}
//切换页数
const handleCurrentChange = (num) => {
  page.value.pageNum = num
  getList()
}
//编辑
const edit = (row) => {
  drawer.value = true
  // 使用 nextTick 来确保 Detail 被正确创建和挂载
  nextTick(() => {
    // 访问 ref 并确保 Detail 组件已经被正确创建
    if (Detail.value && Detail.value.open) {
      Detail.value.open(row)
    }
  })
}
//删除
const deleteNews = async (row) => {
  await deleteNew(row.id)
  ElMessage.success('删除成功')
  getList()
}
//添加
const add = () => {
  drawer.value = true
  // 使用 nextTick 来确保 Detail 被正确创建和挂载
  nextTick(() => {
    // 访问 ref 并确保 Detail 组件已经被正确创建
    if (Detail.value && Detail.value.reflash) {
      Detail.value.reflash()
    }
  })
}
const onSuccess = () => {
  drawer.value = false
  getList()
}
</script>
<template>
  <div>
    <div
      style="display: flex; justify-content: space-between; align-items: center"
    >
      <h1>公告管理</h1>
      <el-button type="primary" @click="add">添加新闻</el-button>
    </div>

    <el-table
      :data="list"
      stripe
      style="width: 100%"
      :row-style="{ height: '60px' }"
      border
    >
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
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="createTime" label="发布时间" />
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button type="primary" @click="edit(row)" :icon="Edit" circle />
          <el-button
            type="danger"
            @click="deleteNews(row)"
            :icon="Delete"
            circle
          />
        </template>
      </el-table-column>
    </el-table>
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
  </div>
  <el-drawer v-model="drawer">
    <NewDetail ref="Detail" @success="onSuccess"></NewDetail>
  </el-drawer>
</template>

<style scoped>
.cover-container {
  height: 100px;
  width: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.cover-image {
  max-width: 100%;
  max-height: 100%;
}

.action-button {
  margin: 0 5px;
}
</style>

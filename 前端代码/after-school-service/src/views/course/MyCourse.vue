<script setup>
import { ref } from 'vue'
import { getSchedule } from '@/api/schedule'
import { Right } from '@element-plus/icons-vue'
import router from '@/router'
const list = ref([])
const total = ref(0)
const page = ref({
  pageNum: 1,
  pageSize: 5
})
const init = async () => {
  const res = await getSchedule(page.value)
  list.value = res.data.data.items
  total.value = res.data.data.total
  console.log(list.value)
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
const toStudy = (row) => {
  router.replace(`/course/learn/${row.cid}`)
}
</script>
<template>
  <div class="course-list">
    <h1 class="page-title">我的课程</h1>
    <el-table :data="list" class="course-table" stripe border fit>
      <el-table-column type="index" width="50" />
      <el-table-column prop="cname" label="课程名称" width="180" />
      <el-table-column prop="tname" label="上课老师" width="180" />
      <el-table-column prop="ctime" label="上课时间" />
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button
            @click="toStudy(row)"
            type="primary"
            size="mini"
            :icon="Right"
            circle
          />
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      class="pagination"
      v-model:current-page="page.pageNum"
      v-model:page-size="page.pageSize"
      :page-sizes="[3, 5, 7, 10]"
      background
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<style scoped>
.course-list {
  padding: 20px;
}

.page-title {
  font-size: 24px;
  margin-bottom: 20px;
}

.course-table {
  width: 100%;
}

.el-table__header th {
  background-color: #f5f7fa;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>

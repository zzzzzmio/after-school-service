<script setup>
import { getHeader, getFooter } from '@/api/home'
import { ref, onMounted } from 'vue'
import { getAll } from '@/api/notice'
import { getNewsDetail } from '@/api/news'
const header = ref([])
const footer = ref([])
const list = ref([])
const Dialog = ref(false)
const news = ref({
  title: '',
  coverImg: '',
  context: ''
})
onMounted(async () => {
  const res1 = await getHeader()
  header.value = res1.data.data
  const res2 = await getFooter()
  footer.value = res2.data.data
  const res3 = await getAll()
  list.value = res3.data.data
})
const activeNames = ref([])
const open = async (id) => {
  Dialog.value = true
  const res = await getNewsDetail(id)
  news.value = res.data.data
}
</script>
<template>
  <!-- 新闻顶部 -->
  <el-carousel :interval="5000" arrow="always" indicator-position="outside">
    <el-carousel-item v-for="item in header" :key="item">
      <img
        :src="'http://localhost:8080/file/' + item.coverImg"
        class="carousel-image"
        @click="open(item.nid)"
      />
    </el-carousel-item>
  </el-carousel>
  <div class="container">
    <div class="left-section">
      <!-- 新闻底部 -->
      <el-carousel :interval="5000" arrow="always" indicator-position="outside">
        <el-carousel-item v-for="item in footer" :key="item">
          <img
            :src="'http://localhost:8080/file/' + item.coverImg"
            class="carousel-image"
            @click="open(item.nid)"
          />
        </el-carousel-item>
      </el-carousel>
    </div>

    <div class="right-section">
      <!-- 公告 -->
      <el-collapse v-model="activeNames">
        <el-collapse-item
          v-for="item in list"
          :key="item.id"
          :title="item.title"
          :name="item.id"
        >
          <div>
            {{ item.content }}
          </div>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
  <el-dialog v-model="Dialog" :title="news.title" width="500">
    <img
      :src="'http://localhost:8080/file/' + news.coverImg"
      class="carousel-image"
    />
    <span>{{ news.context }}</span>
  </el-dialog>
</template>

<style scoped>
.el-carousel__item {
  height: 300px; /* 设置轮播项的高度 */
  text-align: center;
}

.carousel-image {
  width: 100%; /* 图片宽度铺满轮播项 */
  height: 100%; /* 图片高度铺满轮播项 */
  object-fit: cover; /* 图片保持比例铺满 */
}

.el-carousel__indicator {
  bottom: 10px; /* 调整指示器位置 */
}

.el-carousel__indicator-item.is-active {
  background-color: #409eff; /* 激活状态的指示器颜色 */
}

.el-carousel__arrow {
  color: #409eff; /* 箭头颜色 */
  background-color: rgba(255, 255, 255, 0.8); /* 箭头背景色，半透明 */
}

.el-carousel__arrow:hover {
  color: #66b1ff; /* 鼠标悬停时箭头颜色 */
}
.container {
  display: grid;
  grid-template-columns: 1fr 1fr; /* 将页面分为两列 */
  gap: 20px; /* 设置列之间的间隔 */
}

.left-section {
  grid-column: 1; /* 将轮播部分放在第一列 */
}

.right-section {
  grid-column: 2; /* 将公告部分放在第二列 */
}
</style>

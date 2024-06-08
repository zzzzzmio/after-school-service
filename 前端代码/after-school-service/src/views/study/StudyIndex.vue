<script setup>
import { ref } from 'vue'
import { addStudy, check } from '@/api/study'
import { Plus } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores'
const userStore = useUserStore()
const now = ref(new Date())
const weeks = ['日', '一', '二', '三', '四', '五', '六']
const year = ref('')
const month = ref('')
const date = ref('')
const firstDay = ref('')
const data = ref([])
const checkList = ref([])
const imgUrl = ref('')
const flag = ref({
  year: '',
  month: '',
  date: '',
  img: ''
})
const Dialog = ref(false)
const getNow = () => {
  year.value = now.value.getFullYear()
  month.value = now.value.getMonth() + 1
  date.value = now.value.getDate()
  now.value.setDate(1)
  firstDay.value = now.value.getDay()
  initData()
}
const getMonthDay = (month) => {
  if ([1, 3, 5, 7, 8, 10, 12].includes(month)) {
    return 31
  } else if ([4, 6, 9, 11].includes(month)) {
    return 30
  } else if (month === 2) {
    //  判断当年是否为闰年
    if (
      (year.value % 4 === 0 && year.value % 100 !== 0) ||
      year.value % 400 === 0
    ) {
      return 29
    } else {
      return 28
    }
  }
}
const initData = () => {
  data.value = []
  let days = getMonthDay(month.value)
  for (let i = 0; i < firstDay.value; i++) {
    data.value.push({
      year: '',
      month: '',
      date: ''
    })
  }
  for (let i = 0; i < days; i++) {
    data.value.push({
      year: year.value,
      month: month.value,
      date: i + 1
    })
  }
}

const lastMonth = () => {
  now.value.setMonth(now.value.getMonth() - 1)
  getNow()
  checkDate()
}

const nextMonth = () => {
  now.value.setMonth(now.value.getMonth() + 1)
  getNow()
  checkDate()
}

const compareToNow = (item) => {
  if (item.year && item.month && item.date) {
    let date1 = new Date()
    date1.setFullYear(item.year)
    date1.setMonth(item.month - 1)
    date1.setDate(item.date)
    date1.setHours(0)
    date1.setMinutes(0)
    date1.setSeconds(0)
    let now = new Date()
    now.setHours(0)
    now.setMinutes(0)
    now.setSeconds(0)
    if (date1.getTime() > now.getTime()) {
      return 1
    } else if (date1.getTime() === now.getTime()) {
      return 0
    } else if (date1.getTime() < now.getTime()) {
      return -1
    }
  }
}
// 初始化数据
getNow()
const todo = (item) => {
  flag.value.year = item.year
  flag.value.month = item.month
  flag.value.date = item.date
  Dialog.value = true
  imgUrl.value = ''
}
const checkDate = () => {
  checkList.value = []
  data.value.forEach(async (element) => {
    const res = await check(element)
    checkList.value.push(res.data.data)
  })
  console.log(checkList.value)
}
checkDate()
const success = (uploadFile) => {
  imgUrl.value = uploadFile.data
}
const upload = async () => {
  flag.value.img = imgUrl.value
  await addStudy(flag.value)
  ElMessage.success('打卡成功')
  checkDate()
  console.log(checkList.value)
  Dialog.value = false
}
</script>
<template>
  <div>
    <div class="top-title">
      <div><span @click="lastMonth" class="link">⋘</span></div>
      <div>
        <span>{{ year }}年{{ month }}月</span>
      </div>
      <div><span @click="nextMonth" class="link">⋙</span></div>
    </div>
    <div class="container" style="border-bottom: 1px solid #cccccc">
      <div v-for="(item, index) in weeks" :key="index">{{ item }}</div>
    </div>
    <div class="container" style="padding: 1vh 1vh 3vh 1vh">
      <div v-for="(item, index) in data" :key="index">
        <div
          v-if="compareToNow(item) === 0"
          style="color: #2d8cf0"
          @click="todo(item)"
        >
          <div v-if="checkList[index] === 1" class="date-desc">已打卡</div>
          {{ item.date }}
        </div>
        <div v-if="compareToNow(item) === 1">
          {{ item.date }}
        </div>
        <div
          v-if="compareToNow(item) === -1"
          @click="todo(item)"
          class="otherDay"
        >
          <div>{{ item.date }}</div>
          <div v-if="checkList[index] === 0" class="date-desc">补卡</div>
          <div v-else class="date-desc">已打卡</div>
        </div>
      </div>
    </div>
  </div>
  <el-dialog v-model="Dialog" title="打卡记录">
    <el-upload
      class="avatar-uploader"
      :headers="{ Authorization: userStore.token }"
      action="/api/file"
      :show-file-list="false"
      :on-success="success"
    >
      <img
        v-if="imgUrl"
        :src="'http://localhost:8080/file/' + imgUrl"
        class="avatar"
      />
      <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
    </el-upload>
    <el-button @click="upload" type="primary">确认</el-button>
  </el-dialog>
</template>

<style scoped>
.top-title {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #f2f2f2;
  padding: 10px 20px;
  border-bottom: 1px solid #cccccc;
}
.link {
  cursor: pointer;
  color: #2d8cf0;
}
.container {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 4px;
  background-color: #ffffff;
}
.container > div {
  text-align: center;
  padding: 8px;
}
.today {
  background-color: #2d8cf0;
  color: #ffffff;
  border-radius: 50%;
}
.otherDay {
  position: relative;
}
.date-desc {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 12px;
  color: green;
}
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>

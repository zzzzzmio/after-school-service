<script setup>
import { ref } from 'vue'
import { useUserStore } from '@/stores'
import { userMoney } from '@/api/user'
const userStore = useUserStore()
const money = userStore.user.money
const Dialog = ref(false)
const input = ref('')
const img = ref(false)
const open = () => {
  Dialog.value = true
  input.value = ''
}
const addMoney = async () => {
  await userMoney(input.value)
  img.value = true
  setTimeout(() => {}, 5000)
  img.value = false
  ElMessage.success('充值成功')
  Dialog.value = false
}
</script>
<template>
  <h1>账户充值</h1>
  <span>账户余额：{{ money }}</span>
  <el-button @click="open" type="primary">充值</el-button>
  <el-dialog v-model="Dialog" title="充值">
    <div v-if="!img">
      <span>请输入充值金额</span>
      <el-input v-model="input"></el-input>
      <el-button @click="addMoney" type="success">确认充值</el-button>
    </div>
    <img v-else src="@/assets/money.jpg" />
  </el-dialog>
</template>
<style></style>

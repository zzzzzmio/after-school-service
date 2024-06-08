import { defineStore } from 'pinia'
import { ref } from 'vue'
import { userInfo } from '@/api/user'

// 用户模块
export const useUserStore = defineStore(
  'after-school-service',
  () => {
    const token = ref('') // 定义 token
    const setToken = (t) => (token.value = t) // 设置 token
    const user = ref({})
    const getUser = async () => {
      const res = await userInfo()
      user.value = res.data.data
    }
    const removeToken = () => (token.value = '')
    const removeUserInfo = () => (user.value = {})
    return { token, setToken, getUser, removeToken, removeUserInfo, user }
  },
  {
    persist: true // 持久化
  }
)

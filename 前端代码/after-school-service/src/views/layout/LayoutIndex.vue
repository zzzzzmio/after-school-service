<script setup>
import {
  User,
  Crop,
  EditPen,
  SwitchButton,
  CaretBottom,
  House,
  Calendar,
  Money,
  Avatar,
  UserFilled,
  Menu,
  Grid,
  Odometer,
  Reading,
  ChatSquare,
  Discount,
  ChatDotSquare,
  ChatLineSquare,
  Edit,
  HelpFilled,
  Tickets,
  Management
} from '@element-plus/icons-vue'
import avatar from '@/assets/default.png'
import router from '@/router'
import { useUserStore } from '@/stores'
import { onMounted } from 'vue'
const userStore = useUserStore()
onMounted(() => {
  userStore.getUser()
})
// 点击头像的下拉菜单进行跳转
const handleCommand = async (command) => {
  if (command === 'logout') {
    await ElMessageBox.confirm('你确认退出智课启学吗?', '温馨提示', {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning'
    })
    userStore.removeToken()
    userStore.removeUserInfo()
    router.replace('/login')
  } else {
    router.push(`/user/${command}`)
  }
}
</script>
<template>
  <div>
    <el-container class="layout-container-demo" style="height: 100vh">
      <el-aside width="200px">
        <div class="el-aside__logo"></div>
        //学生
        <el-menu
          text-color="black"
          background-color="#fff"
          active-text-color="var(--el-text-color-primary)"
          :default-active="$route.path"
          router
          v-if="userStore.user.usertype === 1"
        >
          <el-menu-item index="/news/recommend">
            <el-icon><House /></el-icon>
            <span>新闻公告</span>
          </el-menu-item>
          <el-menu-item index="/study">
            <el-icon><Calendar /></el-icon>
            <span>每日打卡</span>
          </el-menu-item>
          <el-menu-item index="/family">
            <el-icon><Avatar /></el-icon>
            <span>家庭</span>
          </el-menu-item>
          <el-sub-menu index="/course">
            <template #title>
              <el-icon><Menu /></el-icon>
              <span>课程</span>
            </template>
            <el-menu-item index="/course/list">
              <el-icon><Grid /></el-icon>
              <span>课程列表</span>
            </el-menu-item>
            <el-menu-item index="/course/my">
              <el-icon><Odometer /></el-icon>
              <span>我的课程</span>
            </el-menu-item>
          </el-sub-menu>
          <el-menu-item index="/posts/list">
            <el-icon><Reading /></el-icon>
            <span>论坛</span>
          </el-menu-item>
          <el-sub-menu index="/chat">
            <template #title>
              <el-icon><ChatSquare /></el-icon>
              <span>聊天</span>
            </template>
            <el-menu-item index="/chat/friend">
              <el-icon><Discount /></el-icon>
              <span>我的好友</span>
            </el-menu-item>
            <el-menu-item index="/chat/message">
              <el-icon><ChatDotSquare /></el-icon>
              <span>未读消息</span>
            </el-menu-item>
            <el-menu-item index="/chat/single">
              <el-icon><ChatLineSquare /></el-icon>
              <span>好友聊天</span>
            </el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="/user">
            <template #title>
              <el-icon><UserFilled /></el-icon>
              <span>个人中心</span>
            </template>
            <el-menu-item index="/user/info">
              <el-icon><User /></el-icon>
              <span>基本资料</span>
            </el-menu-item>
            <el-menu-item index="/user/avatar">
              <el-icon><Crop /></el-icon>
              <span>更换头像</span>
            </el-menu-item>
            <el-menu-item index="/user/password">
              <el-icon><EditPen /></el-icon>
              <span>重置密码</span>
            </el-menu-item>
            <el-menu-item index="/user/money">
              <el-icon><Money /></el-icon>
              <span>充值</span>
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
        //家长
        <el-menu
          text-color="black"
          background-color="#fff"
          active-text-color="var(--el-text-color-primary)"
          :default-active="$route.path"
          router
          v-if="userStore.user.usertype === 2"
        >
          <el-menu-item index="/news/recommend">
            <el-icon><House /></el-icon>
            <span>新闻公告</span>
          </el-menu-item>
          <el-menu-item index="/family">
            <el-icon><Avatar /></el-icon>
            <span>家庭</span>
          </el-menu-item>
          <el-menu-item index="/course/list">
            <el-icon><Menu /></el-icon>
            <span>课程</span>
          </el-menu-item>
          <el-menu-item index="/posts/list">
            <el-icon><Reading /></el-icon>
            <span>论坛</span>
          </el-menu-item>
          <el-sub-menu index="/chat">
            <template #title>
              <el-icon><ChatSquare /></el-icon>
              <span>聊天</span>
            </template>
            <el-menu-item index="/chat/friend">
              <el-icon><Discount /></el-icon>
              <span>我的好友</span>
            </el-menu-item>
            <el-menu-item index="/chat/message">
              <el-icon><ChatDotSquare /></el-icon>
              <span>未读消息</span>
            </el-menu-item>
            <el-menu-item index="/chat/single">
              <el-icon><ChatLineSquare /></el-icon>
              <span>好友聊天</span>
            </el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="/user">
            <template #title>
              <el-icon><UserFilled /></el-icon>
              <span>个人中心</span>
            </template>
            <el-menu-item index="/user/info">
              <el-icon><User /></el-icon>
              <span>基本资料</span>
            </el-menu-item>
            <el-menu-item index="/user/avatar">
              <el-icon><Crop /></el-icon>
              <span>更换头像</span>
            </el-menu-item>
            <el-menu-item index="/user/password">
              <el-icon><EditPen /></el-icon>
              <span>重置密码</span>
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
        //老师
        <el-menu
          text-color="black"
          background-color="#fff"
          active-text-color="var(--el-text-color-primary)"
          :default-active="$route.path"
          router
          v-if="userStore.user.usertype === 3"
        >
          <el-menu-item index="/news/recommend">
            <el-icon><House /></el-icon>
            <span>新闻公告</span>
          </el-menu-item>
          <el-sub-menu index="/course">
            <template #title>
              <el-icon><Menu /></el-icon>
              <span>课程</span>
            </template>
            <el-menu-item index="/course/list">
              <el-icon><Grid /></el-icon>
              <span>课程列表</span>
            </el-menu-item>
            <el-menu-item index="/course/teacher">
              <el-icon><Odometer /></el-icon>
              <span>我的课程</span>
            </el-menu-item>
          </el-sub-menu>
          <el-menu-item index="/posts/list">
            <el-icon><Reading /></el-icon>
            <span>论坛</span>
          </el-menu-item>
          <el-sub-menu index="/chat">
            <template #title>
              <el-icon><ChatSquare /></el-icon>
              <span>聊天</span>
            </template>
            <el-menu-item index="/chat/friend">
              <el-icon><Discount /></el-icon>
              <span>我的好友</span>
            </el-menu-item>
            <el-menu-item index="/chat/message">
              <el-icon><ChatDotSquare /></el-icon>
              <span>未读消息</span>
            </el-menu-item>
            <el-menu-item index="/chat/single">
              <el-icon><ChatLineSquare /></el-icon>
              <span>好友聊天</span>
            </el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="/user">
            <template #title>
              <el-icon><UserFilled /></el-icon>
              <span>个人中心</span>
            </template>
            <el-menu-item index="/user/info">
              <el-icon><User /></el-icon>
              <span>基本资料</span>
            </el-menu-item>
            <el-menu-item index="/user/avatar">
              <el-icon><Crop /></el-icon>
              <span>更换头像</span>
            </el-menu-item>
            <el-menu-item index="/user/password">
              <el-icon><EditPen /></el-icon>
              <span>重置密码</span>
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
        //后台管理人员
        <el-menu
          text-color="black"
          background-color="#fff"
          active-text-color="var(--el-text-color-primary)"
          :default-active="$route.path"
          router
          v-if="userStore.user.usertype === 4"
        >
          <el-sub-menu index="/news">
            <template #title>
              <el-icon><House /></el-icon>
              <span>新闻公告</span>
            </template>
            <el-menu-item index="/news/recommend">
              <el-icon><House /></el-icon>
              <span>新闻公告</span>
            </el-menu-item>
            <el-menu-item index="/manage/list">
              <el-icon><Edit /></el-icon>
              <span>新闻管理</span>
            </el-menu-item>
            <el-menu-item index="/manage/notice">
              <el-icon><Management /></el-icon>
              <span>公告管理</span>
            </el-menu-item>
            <el-menu-item index="/manage/change">
              <el-icon><HelpFilled /></el-icon>
              <span>首页轮换管理</span>
            </el-menu-item>
          </el-sub-menu>
          <el-menu-item index="/user/list">
            <el-icon><Tickets /></el-icon>
            <span>用户管理</span>
          </el-menu-item>
          <el-menu-item index="/course/list">
            <el-icon><Grid /></el-icon>
            <span>课程管理</span>
          </el-menu-item>
          <el-menu-item index="/posts/list">
            <el-icon><Reading /></el-icon>
            <span>论坛</span>
          </el-menu-item>
          <el-sub-menu index="/chat">
            <template #title>
              <el-icon><ChatSquare /></el-icon>
              <span>聊天</span>
            </template>
            <el-menu-item index="/chat/friend">
              <el-icon><Discount /></el-icon>
              <span>我的好友</span>
            </el-menu-item>
            <el-menu-item index="/chat/message">
              <el-icon><ChatDotSquare /></el-icon>
              <span>未读消息</span>
            </el-menu-item>
            <el-menu-item index="/chat/single">
              <el-icon><ChatLineSquare /></el-icon>
              <span>好友聊天</span>
            </el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="/user">
            <template #title>
              <el-icon><UserFilled /></el-icon>
              <span>个人中心</span>
            </template>
            <el-menu-item index="/user/info">
              <el-icon><User /></el-icon>
              <span>基本资料</span>
            </el-menu-item>
            <el-menu-item index="/user/avatar">
              <el-icon><Crop /></el-icon>
              <span>更换头像</span>
            </el-menu-item>
            <el-menu-item index="/user/password">
              <el-icon><EditPen /></el-icon>
              <span>重置密码</span>
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header>
          <div>
            江西师范大学：<strong>{{
              userStore.user.nickname || userStore.user.username
            }}</strong>
          </div>
          <el-dropdown placement="bottom-end" @command="handleCommand">
            <span class="el-dropdown__box">
              <el-avatar
                :src="
                  'http://localhost:8080/file/' + userStore.user.userPic ||
                  avatar
                "
              />
              <el-icon><CaretBottom /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="info" :icon="User"
                  >基本资料</el-dropdown-item
                >
                <el-dropdown-item command="avatar" :icon="Crop"
                  >更换头像</el-dropdown-item
                >
                <el-dropdown-item command="password" :icon="EditPen"
                  >重置密码</el-dropdown-item
                >
                <el-dropdown-item command="logout" :icon="SwitchButton"
                  >退出登录</el-dropdown-item
                >
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </el-header>

        <el-main>
          <router-view></router-view>
        </el-main>
        <el-footer style="color: black"
          >智课启学 ©2024 Created by 朱兆权</el-footer
        >
      </el-container>
    </el-container>
  </div>
</template>
<style scoped>
/* .layout-container-demo .el-header {
  position: relative;
  background-color: var(--el-color-primary-light-7);
  color: var(--el-text-color-primary);
} */
.layout-container-demo .el-aside {
  color: white;
  background: white;
}
.layout-container-demo .el-menu {
  border-right: none;
}
.layout-container-demo .el-main {
  padding: 0;
}
.layout-container-demo .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}
.el-header {
  background-color: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  .el-dropdown__box {
    display: flex;
    align-items: center;
    .el-icon {
      color: white;
      margin-left: 10px;
    }

    &:active,
    &:focus {
      outline: none;
    }
  }
}
.el-aside__logo {
  /* 设置背景图片的路径，相对于当前CSS文件的位置 */
  background-image: url('@/assets/image.png');
  background-size: cover;
  width: 100%;
  height: 200px;
}
.el-footer {
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
  color: white;
}
</style>

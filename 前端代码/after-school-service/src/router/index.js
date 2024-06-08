import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/draft',
      component: () => import('@/views/draft/ChatIndex.vue')
    },
    {
      path: '/login',
      component: () => import('@/views/login/LoginIndex.vue')
    },
    {
      path: '/',
      component: () => import('@/views/layout/LayoutIndex.vue'),
      redirect: '/news/recommend',
      children: [
        {
          path: '/chat/friend',
          component: () => import('@/views/chat/MyFriend.vue')
        },
        {
          path: '/chat/message',
          component: () => import('@/views/chat/UnreadMessage.vue')
        },
        {
          path: '/chat/single',
          component: () => import('@/views/chat/SingleChat.vue')
        },
        {
          path: '/course/detail/:id',
          component: () => import('@/views/course/CourseDetail.vue')
        },
        {
          path: '/course/learn/:id',
          component: () => import('@/views/course/CourseLearn.vue')
        },
        {
          path: '/course/list',
          component: () => import('@/views/course/CourseList.vue')
        },
        {
          path: '/course/teacher',
          component: () => import('@/views/course/TeacherCourse.vue')
        },
        {
          path: '/course/my',
          component: () => import('@/views/course/MyCourse.vue')
        },
        {
          path: '/family',
          component: () => import('@/views/family/FamilyIndex.vue')
        },
        {
          path: '/manage/change',
          component: () => import('@/views/manage/ChangeNews.vue')
        },
        {
          path: '/manage/list',
          component: () => import('@/views/manage/NewsList.vue')
        },
        {
          path: '/manage/recommend',
          component: () => import('@/views/manage/RecommendManage.vue')
        },
        {
          path: '/manage/notice',
          component: () => import('@/views/manage/NoticeManage.vue')
        },
        {
          path: '/news/recommend',
          component: () => import('@/views/news/NewsIndex.vue')
        },
        {
          path: '/posts/list',
          component: () => import('@/views/posts/PostsIndex.vue')
        },
        {
          path: '/posts/detail/:id',
          component: () => import('@/views/posts/PostsDetail.vue')
        },
        {
          path: '/schedule',
          component: () => import('@/views/schedule/ScheduleIndex.vue')
        },
        {
          path: '/study',
          component: () => import('@/views/study/StudyIndex.vue')
        },
        {
          path: '/user/avatar',
          component: () => import('@/views/user/UserAvatar.vue')
        },
        {
          path: '/user/list',
          component: () => import('@/views/user/UserList.vue')
        },
        {
          path: '/user/info',
          component: () => import('@/views/user/UserInfo.vue')
        },
        {
          path: '/user/password',
          component: () => import('@/views/user/UserPassword.vue')
        },
        {
          path: '/user/money',
          component: () => import('@/views/user/UserMoney.vue')
        }
      ]
    }
  ]
})

// 登录访问拦截
router.beforeEach((to) => {
  const userStore = useUserStore()
  if (!userStore.token && to.path !== '/login') return '/login'
})

export default router

import request from '@/utils/request'
//获取主页元素列表
export const getList = () => {
  return request.get('/home')
}
//获取未加入主页元素的新闻公告列表
export const getNews = () => {
  return request.get('/home/news')
}
//添加首页元素
export const addHome = (data) => {
  return request.post('/home', data)
}
//删除
export const deleteHomeNews = (id) => {
  return request.delete(`/home?id=${id}`)
}
//获取顶部新闻
export const getHeader = () => {
  return request.get('/home/header')
}
//获取底部公告
export const getFooter = () => {
  return request.get('/home/footer')
}

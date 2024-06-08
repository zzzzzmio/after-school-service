import request from '@/utils/request'
//获取新闻列表
export const getNewsList = ({ pageNum, pageSize }) => {
  return request.get('/news/list', { params: { pageNum, pageSize } })
}
//获取新闻公告详情
export const getNewsDetail = (id) => {
  return request.get(`/news?id=${id}`)
}
//更新新闻公告
export const updateNews = (data) => {
  return request.put('/news', data)
}
//添加新闻公告
export const addNews = (data) => {
  return request.post('/news', data)
}
//删除新闻公告
export const deleteNew = (id) => {
  return request.delete(`/news?id=${id}`)
}

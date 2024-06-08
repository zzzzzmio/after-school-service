import request from '@/utils/request'
//获取帖子列表
export const getPostList = ({ pageNum, pageSize, userId }) => {
  return request.get('/posts/list', { params: { pageNum, pageSize, userId } })
}
//获取热门帖子
export const getHotPosts = () => {
  return request.get('/posts/hot')
}
//发帖
export const addPosts = (data) => {
  return request.post('/posts', data)
}
//删贴
export const deletePosts = (id) => {
  return request.delete(`/posts?id=${id}`)
}
//获取帖子详情
export const getPostsDetail = (id) => {
  return request.get(`/posts?id=${id}`)
}
//点赞
export const upPosts = (id) => {
  return request.put(`/posts/good?id=${id}`)
}
//取消
export const cancelPosts = (id) => {
  return request.put(`/posts/cancel?id=${id}`)
}

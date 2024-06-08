import request from '@/utils/request'
//获取评论列表
export const getCommentList = ({ cid, pageNum, pageSize }) => {
  return request.get('/comment', { params: { cid, pageNum, pageSize } })
}
//发送评论
export const sendComment = (data) => {
  return request.post('/comment', data)
}
//点赞
export const thumbsUp = (id) => {
  return request.put(`/comment?id=${id}`)
}
//取消点赞
export const cancel = (id) => {
  return request.put(`/comment/cancel?id=${id}`)
}
//删除评论
export const deleteCom = (id) => {
  return request.delete(`/comment?id=${id}`)
}

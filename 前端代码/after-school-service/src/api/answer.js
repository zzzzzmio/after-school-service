import request from '@/utils/request'
//获取回复列表
export const getAnswerList = ({ pid, pageNum, pageSize }) => {
  return request.get('/answer', { params: { pid, pageNum, pageSize } })
}
//添加回复
export const addAnswer = (data) => {
  return request.post('/answer', data)
}
//删除
export const deleteAnswer = (id) => {
  return request.delete(`/answer?id=${id}`)
}
//点赞
export const upAnswer = (id) => {
  return request.put(`/answer?id=${id}`)
}
//取消点赞
export const cancelAnswer = (id) => {
  return request.put(`/answer/cancel?id=${id}`)
}

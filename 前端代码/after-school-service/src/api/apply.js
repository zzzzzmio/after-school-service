import request from '@/utils/request'
export const apply = (data) => {
  return request.post('/apply', data)
}
export const getApplyList = (id) => {
  return request.get(`/apply?toUser=${id}`)
}
export const agreeApply = (id) => {
  return request.delete(`/apply?id=${id}`)
}
export const refuseApply = (id) => {
  return request.delete(`/apply/refuse?id=${id}`)
}

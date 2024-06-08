import request from '@/utils/request'
export const getApplyList = (id) => {
  return request.get(`/family/apply?id=${id}`)
}
export const getParentList = (id) => {
  return request.get(`/family/parent?id=${id}`)
}
export const getFamilyList = (id) => {
  return request.get(`/family?id=${id}`)
}
export const apply = (data) => {
  return request.post('/family', data)
}
export const agree = (id) => {
  return request.put(`/family?id=${id}`)
}
export const deleteFamily = (id) => {
  return request.delete(`/family?id=${id}`)
}

import request from '@/utils/request'
export const addStudy = (data) => {
  return request.post('/study', data)
}
export const check = (data) => {
  return request.post('/study/check', data)
}
export const getList = (stuId) => {
  return request.get(`/study?stuId=${stuId}`)
}

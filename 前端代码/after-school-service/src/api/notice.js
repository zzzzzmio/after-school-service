import request from '@/utils/request'
export const getList = ({ pageNum, pageSize }) => {
  return request.get('/notice/list', { params: { pageNum, pageSize } })
}
export const getDetail = (id) => {
  return request.get(`/notice?id=${id}`)
}
export const addNotice = (data) => {
  return request.post('/notice', data)
}
export const updateNotice = (data) => {
  return request.put('/notice', data)
}
export const deleteNotice = (id) => {
  return request.delete(`/notice?id=${id}`)
}
export const getAll = () => {
  return request.get('/notice/all')
}

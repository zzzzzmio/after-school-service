import request from '@/utils/request'
export const addSchedule = (cid) => {
  return request.post(`/schedule?cid=${cid}`)
}
export const getSchedule = ({ pageNum, pageSize }) => {
  return request.get('/schedule', { params: { pageNum, pageSize } })
}
export const getRecommend = () => {
  return request.get('/schedule/recommend')
}

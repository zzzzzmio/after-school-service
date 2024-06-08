import request from '@/utils/request'
export const getChatList = ({ fromuser, touser }) => {
  return request.get(`/imsingle/list?fromuser=${fromuser}&touser=${touser}`)
}
export const getUnreaded = ({ touser }) => {
  return request.get(`/imsingle/unread?touser=${touser}`)
}
export const toRead = ({ fromuser, touser }) => {
  return request.put(`/imsingle/read?fromuser=${fromuser}&touser=${touser}`)
}

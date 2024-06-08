import request from '@/utils/request'
export const getFriendList = (id) => {
  return request.get(`/friend?id=${id}`)
}
export const deleteFriend = (ownerId, fid) => {
  return request.delete(`/friend?ownerId=${ownerId}&fid=${fid}`)
}

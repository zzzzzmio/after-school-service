import request from '@/utils/request'
export const userLogin = (loginData) => {
  //借助于UrlSearchParams完成传递
  const params = new URLSearchParams()
  for (let key in loginData) {
    params.append(key, loginData[key])
  }
  return request.post('/user/login', params)
}
export const getList = () => {
  return request.get('/user/list')
}
export const getInfo = () => {
  return request.get('/user/userinfo')
}
export const getMsg = (fromuser, touser) => {
  return request.get(`/imsingle/list?fromuser=${fromuser}&touser=${touser}`)
}

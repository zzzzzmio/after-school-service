import request from '@/utils/request'
//注册
export const userRegist = (registerData) => {
  //借助于UrlSearchParams完成传递
  const params = new URLSearchParams()
  for (let key in registerData) {
    params.append(key, registerData[key])
  }
  return request.post('/user/register', params)
}
//登录
export const userLogin = (loginData) => {
  //借助于UrlSearchParams完成传递
  const params = new URLSearchParams()
  for (let key in loginData) {
    params.append(key, loginData[key])
  }
  return request.post('/user/login', params)
}
//获取个人详情
export const userInfo = () => {
  return request.get('/user/userinfo')
}
//获取用户列表
export const getList = ({ pageNum, pageSize }) => {
  return request.get('/user/list', { params: { pageNum, pageSize } })
}
//根据id获取用户信息
export const getDetail = (id) => {
  return request.get(`/user/detail?id=${id}`)
}
//更新用户信息
export const changeInfo = (data) => {
  return request.put('/user/change', data)
}
//搜索
export const findFriend = (name) => {
  return request.get(`/user/friend?name=${name}`)
}
//个人更新用户信息
export const userUpdate = (data) => {
  return request.put('/user/update', data)
}
//更新用户头像
export const userAvatar = (avatar) => {
  return request.put(`/user/avatar?avatar=${avatar}`)
}
//修改密码
export const userPwd = (data) => {
  const params = new URLSearchParams()
  for (let key in data) {
    params.append(key, data[key])
  }
  return request.patch('/user/password', params)
}
//充值
export const userMoney = (money) => {
  return request.post(`/user/money?money=${money}`)
}

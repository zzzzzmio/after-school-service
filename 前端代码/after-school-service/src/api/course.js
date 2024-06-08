import request from '@/utils/request'
//获取课程列表
export const getList = ({ pageNum, pageSize }) => {
  return request.get('/course/getlist', { params: { pageNum, pageSize } })
}
//搜索
export const search = ({ cname, pageNum, pageSize }) => {
  return request.get('/course/search', { params: { cname, pageNum, pageSize } })
}
//获取课程详情
export const getCourseDetail = (id) => {
  return request.get(`/course/get?id=${id}`)
}
//删除课程
export const deleteCourse = (id) => {
  return request.delete(`/course/delete?id=${id}`)
}
//老师获取课程列表
export const getTeaList = (id) => {
  return request.get(`/course/teacher?id=${id}`)
}
//添加课程
export const addCourse = (data) => {
  return request.post('/course/add', data)
}
//更新课程
export const updateCourse = (data) => {
  return request.put('/course/update', data)
}

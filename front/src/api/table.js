import request from '@/utils/request'

const api_name = '/api/project'

// export default {
//   // 项目列表
//   projectList(page, limit, searchObj) {
//     return request({
//       url: api_name + '/' + page + '/' + limit,
//       method: 'post',
//       data: searchObj
//     })
//   }
// }

export function projectList(page, limit, searchObj) {
  return request({
    url: api_name + '/projectListByLimit' + '/' + page + '/' + limit,
    method: 'post',
    data: searchObj
  })
}

export function addProject(form) {
  return request({
    url: api_name + '/addProject',
    method: 'post',
    data: form
  })
}

export function updateProject(updateData) {
  return request({
    url: api_name + '/updateProject',
    method: 'post',
    data: updateData
  })
}

export function addProjectFile(formData) {
  return request({
    url: api_name + '/addProjectFile',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data;charset=utf-8'
    },
    data: formData
  })
}

export function getList(params) {
  return request({
    url: '/api/project/allProjectInfo',
    method: 'get',
    params
  })
}

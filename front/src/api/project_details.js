import request from '@/utils/request'

export function getProjectInfo(queryData) {
  return request({
    url: '/api/project/getProjectById',
    method: 'post',
    data: queryData
  })
}

export function getOrderList(page, limit, orderQueryData) {
  return request({
    url: '/api/order/getOrderList' + '/' + page + '/' + limit,
    method: 'post',
    data: orderQueryData
  })
}

export function postStopProject(id) {
  return request({
    url: '/api/project/stopProject/' + id,
    method: 'post'
  })
}

export function postStartProject(id) {
  return request({
    url: '/api/project/startProject/' + id,
    method: 'post'
  })
}

export function deleteProject(id) {
  return request({
    url: '/api/project/deleteProject/' + id,
    method: 'post'
  })
}

export function getOrderPackageCount(id) {
  return request({
    url: '/api/project/getOrderPackageCount/' + id,
    method: 'post'
  })
}


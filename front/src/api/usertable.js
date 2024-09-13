import request from '@/utils/request'

export function getUserList(params) {
  return request({
    url: '/api/adminuser/userList',
    method: 'get',
    params
  })
}

export function postAddAdminUser(params) {
  return request({
    url: '/api/adminuser/register',
    method: 'post',
    data: params
  })
}

export function postAlterAdminUser(params) {
  return request({
    url: '/api/adminuser/alterUserScope',
    method: 'post',
    data: params
  })
}

export function postDeleteAdminUser(params) {
  return request({
    url: '/api/adminuser/deleteAdminUser',
    method: 'post',
    data: {
      id: params
    }
  })
}

export function postAlterPassword(params) {
  return request({
    url: '/api/adminuser/alterPassword',
    method: 'post',
    data: params
  })
}

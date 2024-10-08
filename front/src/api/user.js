import request from '@/utils/request'




export function login(data) {
  return request({
    url: '/api/adminuser/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/api/adminuser/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/api/adminuser/logout',
    method: 'post'
  })
}

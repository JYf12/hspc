import request from '@/utils/request'

export function getOrderInfo(id) {
  return request({
    url: '/api/order/getOrderById/' + id,
    method: 'post'
  })
}

export function updateOrderStatus(id) {
  return request({
    url: '/api/order/updateOrderStatus/' + id,
    method: 'post'
  })
}

export function updateOrderStatusError(postData) {
  return request({
    url: '/api/order/updateOrderStatusError',
    method: 'post',
    data: postData
  })
}

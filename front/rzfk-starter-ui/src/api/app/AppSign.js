import request from '@/utils/request'

// 查询用户签到记录列表
export function listAppSign(query) {
  return request({
    url: '/app/AppSign/list',
    method: 'get',
    params: query
  })
}

// 查询用户签到记录详细
export function getAppSign(id) {
  return request({
    url: '/app/AppSign/' + id,
    method: 'get'
  })
}

// 新增用户签到记录
export function addAppSign(data) {
  return request({
    url: '/app/AppSign',
    method: 'post',
    data: data
  })
}

// 修改用户签到记录
export function updateAppSign(data) {
  return request({
    url: '/app/AppSign',
    method: 'put',
    data: data
  })
}

// 删除用户签到记录
export function delAppSign(id) {
  return request({
    url: '/app/AppSign/' + id,
    method: 'delete'
  })
}
// 导出用户签到记录
export function exportAppSign(query) {
    return request({
        url: '/app/AppSign/export',
        method: 'get',
        params: query
    })
}

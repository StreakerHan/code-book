import request from '@/utils/request'

// 查询app用户登录记录列表
export function listAppLoginRecord(query) {
  return request({
    url: '/app/appLoginRecord/list',
    method: 'get',
    params: query
  })
}

// 查询app用户登录记录详细
export function getAppLoginRecord(id) {
  return request({
    url: '/app/appLoginRecord/' + id,
    method: 'get'
  })
}

// 新增app用户登录记录
export function addAppLoginRecord(data) {
  return request({
    url: '/app/appLoginRecord',
    method: 'post',
    data: data
  })
}

// 修改app用户登录记录
export function updateAppLoginRecord(data) {
  return request({
    url: '/app/appLoginRecord',
    method: 'put',
    data: data
  })
}

// 删除app用户登录记录
export function delAppLoginRecord(id) {
  return request({
    url: '/app/appLoginRecord/' + id,
    method: 'delete'
  })
}
// 导出app用户登录记录
export function exportAppLoginRecord(query) {
    return request({
        url: '/app/appLoginRecord/export',
        method: 'get',
        params: query
    })
}

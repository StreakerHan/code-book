import request from '@/utils/request'

// 查询用户经验记录列表
export function listAppExpRecord(query) {
  return request({
    url: '/app/AppExpRecord/list',
    method: 'get',
    params: query
  })
}

// 查询用户经验记录详细
export function getAppExpRecord(id) {
  return request({
    url: '/app/AppExpRecord/' + id,
    method: 'get'
  })
}

// 新增用户经验记录
export function addAppExpRecord(data) {
  return request({
    url: '/app/AppExpRecord',
    method: 'post',
    data: data
  })
}

// 修改用户经验记录
export function updateAppExpRecord(data) {
  return request({
    url: '/app/AppExpRecord',
    method: 'put',
    data: data
  })
}

// 删除用户经验记录
export function delAppExpRecord(id) {
  return request({
    url: '/app/AppExpRecord/' + id,
    method: 'delete'
  })
}
// 导出用户经验记录
export function exportAppExpRecord(query) {
    return request({
        url: '/app/AppExpRecord/export',
        method: 'get',
        params: query
    })
}

import request from '@/utils/request'

// 查询积分记录列表
export function listAppPointsRecord(query) {
  return request({
    url: '/app/AppPointsRecord/list',
    method: 'get',
    params: query
  })
}

// 查询积分记录详细
export function getAppPointsRecord(id) {
  return request({
    url: '/app/AppPointsRecord/' + id,
    method: 'get'
  })
}

// 新增积分记录
export function addAppPointsRecord(data) {
  return request({
    url: '/app/AppPointsRecord',
    method: 'post',
    data: data
  })
}

// 修改积分记录
export function updateAppPointsRecord(data) {
  return request({
    url: '/app/AppPointsRecord',
    method: 'put',
    data: data
  })
}

// 删除积分记录
export function delAppPointsRecord(id) {
  return request({
    url: '/app/AppPointsRecord/' + id,
    method: 'delete'
  })
}
// 导出积分记录
export function exportAppPointsRecord(query) {
    return request({
        url: '/app/AppPointsRecord/export',
        method: 'get',
        params: query
    })
}

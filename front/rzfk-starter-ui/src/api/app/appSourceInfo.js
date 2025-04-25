import request from '@/utils/request'

// 查询资源信息列表
export function listAppSourceInfo(query) {
  return request({
    url: '/app/appSourceInfo/list',
    method: 'get',
    params: query
  })
}

// 查询资源信息详细
export function getAppSourceInfo(id) {
  return request({
    url: '/app/appSourceInfo/' + id,
    method: 'get'
  })
}

// 新增资源信息
export function addAppSourceInfo(data) {
  return request({
    url: '/app/appSourceInfo',
    method: 'post',
    data: data
  })
}

// 修改资源信息
export function updateAppSourceInfo(data) {
  return request({
    url: '/app/appSourceInfo',
    method: 'put',
    data: data
  })
}

// 删除资源信息
export function delAppSourceInfo(id) {
  return request({
    url: '/app/appSourceInfo/' + id,
    method: 'delete'
  })
}
// 导出资源信息
export function exportAppSourceInfo(query) {
    return request({
        url: '/app/appSourceInfo/export',
        method: 'get',
        params: query
    })
}

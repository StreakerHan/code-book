import request from '@/utils/request'

// 查询资源分类列表
export function listAppSourceType(query) {
  return request({
    url: '/app/appSourceType/list',
    method: 'get',
    params: query
  })
}

// 查询资源分类详细
export function getAppSourceType(id) {
  return request({
    url: '/app/appSourceType/' + id,
    method: 'get'
  })
}

// 新增资源分类
export function addAppSourceType(data) {
  return request({
    url: '/app/appSourceType',
    method: 'post',
    data: data
  })
}

// 修改资源分类
export function updateAppSourceType(data) {
  return request({
    url: '/app/appSourceType',
    method: 'put',
    data: data
  })
}

// 删除资源分类
export function delAppSourceType(id) {
  return request({
    url: '/app/appSourceType/' + id,
    method: 'delete'
  })
}
// 导出资源分类
export function exportAppSourceType(query) {
    return request({
        url: '/app/appSourceType/export',
        method: 'get',
        params: query
    })
}

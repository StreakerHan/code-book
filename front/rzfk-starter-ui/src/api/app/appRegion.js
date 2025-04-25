import request from '@/utils/request'

// 查询全国区划列表
export function listAppRegion(query) {
  return request({
    url: '/app/appRegion/list',
    method: 'get',
    params: query
  })
}

// 查询全国区划详细
export function getAppRegion(id) {
  return request({
    url: '/app/appRegion/' + id,
    method: 'get'
  })
}

// 新增全国区划
export function addAppRegion(data) {
  return request({
    url: '/app/appRegion',
    method: 'post',
    data: data
  })
}

// 修改全国区划
export function updateAppRegion(data) {
  return request({
    url: '/app/appRegion',
    method: 'put',
    data: data
  })
}

// 删除全国区划
export function delAppRegion(id) {
  return request({
    url: '/app/appRegion/' + id,
    method: 'delete'
  })
}
// 导出全国区划
export function exportAppRegion(query) {
    return request({
        url: '/app/appRegion/export',
        method: 'get',
        params: query
    })
}

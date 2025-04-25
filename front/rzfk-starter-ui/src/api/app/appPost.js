import request from '@/utils/request'

// 查询app文章列表
export function listAppPost(query) {
  return request({
    url: '/app/appPost/list',
    method: 'get',
    params: query
  })
}

// 查询app文章详细
export function getAppPost(id) {
  return request({
    url: '/app/appPost/' + id,
    method: 'get'
  })
}

// 新增app文章
export function addAppPost(data) {
  return request({
    url: '/app/appPost',
    method: 'post',
    data: data
  })
}

// 修改app文章
export function updateAppPost(data) {
  return request({
    url: '/app/appPost',
    method: 'put',
    data: data
  })
}

// 删除app文章
export function delAppPost(id) {
  return request({
    url: '/app/appPost/' + id,
    method: 'delete'
  })
}
// 导出app文章
export function exportAppPost(query) {
    return request({
        url: '/app/appPost/export',
        method: 'get',
        params: query
    })
}

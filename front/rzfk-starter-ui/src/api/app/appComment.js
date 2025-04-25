import request from '@/utils/request'

// 查询文章评论列表
export function listAppComment(query) {
  return request({
    url: '/app/appComment/list',
    method: 'get',
    params: query
  })
}

// 查询文章评论详细
export function getAppComment(id) {
  return request({
    url: '/app/appComment/' + id,
    method: 'get'
  })
}

// 新增文章评论
export function addAppComment(data) {
  return request({
    url: '/app/appComment',
    method: 'post',
    data: data
  })
}

// 修改文章评论
export function updateAppComment(data) {
  return request({
    url: '/app/appComment',
    method: 'put',
    data: data
  })
}

// 删除文章评论
export function delAppComment(id) {
  return request({
    url: '/app/appComment/' + id,
    method: 'delete'
  })
}
// 导出文章评论
export function exportAppComment(query) {
    return request({
        url: '/app/appComment/export',
        method: 'get',
        params: query
    })
}

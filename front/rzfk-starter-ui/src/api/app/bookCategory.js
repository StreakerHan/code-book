import request from '@/utils/request'

// 查询书籍分类列表
export function listBookCategory(query) {
  return request({
    url: '/app/bookCategory/list',
    method: 'get',
    params: query
  })
}

// 查询书籍分类详细
export function getBookCategory(id) {
  return request({
    url: '/app/bookCategory/' + id,
    method: 'get'
  })
}

// 新增书籍分类
export function addBookCategory(data) {
  return request({
    url: '/app/bookCategory',
    method: 'post',
    data: data
  })
}

// 修改书籍分类
export function updateBookCategory(data) {
  return request({
    url: '/app/bookCategory',
    method: 'put',
    data: data
  })
}

// 删除书籍分类
export function delBookCategory(id) {
  return request({
    url: '/app/bookCategory/' + id,
    method: 'delete'
  })
}
// 导出书籍分类
export function exportBookCategory(query) {
    return request({
        url: '/app/bookCategory/export',
        method: 'get',
        params: query
    })
}

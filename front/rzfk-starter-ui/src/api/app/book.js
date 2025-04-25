import request from '@/utils/request'

// 查询书籍信息列表
export function listBook(query) {
  return request({
    url: '/app/book/list',
    method: 'get',
    params: query
  })
}

// 查询书籍信息详细
export function getBook(id) {
  return request({
    url: '/app/book/' + id,
    method: 'get'
  })
}

// 新增书籍信息
export function addBook(data) {
  return request({
    url: '/app/book',
    method: 'post',
    data: data
  })
}

// 修改书籍信息
export function updateBook(data) {
  return request({
    url: '/app/book',
    method: 'put',
    data: data
  })
}

// 删除书籍信息
export function delBook(id) {
  return request({
    url: '/app/book/' + id,
    method: 'delete'
  })
}
// 导出书籍信息
export function exportBook(query) {
    return request({
        url: '/app/book/export',
        method: 'get',
        params: query
    })
}

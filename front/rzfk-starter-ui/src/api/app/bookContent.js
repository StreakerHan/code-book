import request from '@/utils/request'

// 查询书籍内容列表
export function listBookContent(query) {
  return request({
    url: '/app/bookContent/list',
    method: 'get',
    params: query
  })
}

// 查询书籍内容详细
export function getBookContent(id) {
  return request({
    url: '/app/bookContent/' + id,
    method: 'get'
  })
}

// 新增书籍内容
export function addBookContent(data) {
  return request({
    url: '/app/bookContent',
    method: 'post',
    data: data
  })
}

// 修改书籍内容
export function updateBookContent(data) {
  return request({
    url: '/app/bookContent',
    method: 'put',
    data: data
  })
}

// 删除书籍内容
export function delBookContent(id) {
  return request({
    url: '/app/bookContent/' + id,
    method: 'delete'
  })
}
// 导出书籍内容
export function exportBookContent(query) {
    return request({
        url: '/app/bookContent/export',
        method: 'get',
        params: query
    })
}

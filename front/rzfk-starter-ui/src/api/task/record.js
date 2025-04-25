import request from '@/utils/request'

// 查询案件进展列表
export function listRecord(query) {
  return request({
    url: '/task/record/list',
    method: 'get',
    params: query
  })
}

// 查询案件进展详细
export function getRecord(id) {
  return request({
    url: '/task/record/' + id,
    method: 'get'
  })
}

// 新增案件进展
export function addRecord(data) {
  return request({
    url: '/task/record',
    method: 'post',
    data: data
  })
}

// 修改案件进展
export function updateRecord(data) {
  return request({
    url: '/task/record',
    method: 'put',
    data: data
  })
}

// 删除案件进展
export function delRecord(id) {
  return request({
    url: '/task/record/' + id,
    method: 'delete'
  })
}

// 导出案件进展
export function exportRecord(query) {
  return request({
    url: '/task/record/export',
    method: 'get',
    params: query
  })
}
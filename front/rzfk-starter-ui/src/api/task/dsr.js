import request from '@/utils/request'

// 查询案件当事人列表
export function listDsr(query) {
  return request({
    url: '/task/dsr/list',
    method: 'get',
    params: query
  })
}

// 查询案件当事人详细
export function getDsr(id) {
  return request({
    url: '/task/dsr/' + id,
    method: 'get'
  })
}

// 新增案件当事人
export function addDsr(data) {
  return request({
    url: '/task/dsr',
    method: 'post',
    data: data
  })
}

// 修改案件当事人
export function updateDsr(data) {
  return request({
    url: '/task/dsr',
    method: 'put',
    data: data
  })
}

// 删除案件当事人
export function delDsr(id) {
  return request({
    url: '/task/dsr/' + id,
    method: 'delete'
  })
}

// 导出案件当事人
export function exportDsr(query) {
  return request({
    url: '/task/dsr/export',
    method: 'get',
    params: query
  })
}
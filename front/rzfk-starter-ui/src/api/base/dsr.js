import request from '@/utils/request'

// 查询当事人列表
export function listDsr(query) {
  return request({
    url: '/base/dsr/list',
    method: 'get',
    params: query
  })
}

// 查询当事人详细
export function getDsr(id) {
  return request({
    url: '/base/dsr/' + id,
    method: 'get'
  })
}

// 新增当事人
export function addDsr(data) {
  return request({
    url: '/base/dsr',
    method: 'post',
    data: data
  })
}

// 修改当事人
export function updateDsr(data) {
  return request({
    url: '/base/dsr',
    method: 'put',
    data: data
  })
}

// 删除当事人
export function delDsr(id) {
  return request({
    url: '/base/dsr/' + id,
    method: 'delete'
  })
}

// 导出当事人
export function exportDsr(query) {
  return request({
    url: '/base/dsr/export',
    method: 'get',
    params: query
  })
}
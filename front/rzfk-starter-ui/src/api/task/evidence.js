import request from '@/utils/request'

// 查询案件证据列表
export function listEvidence(query) {
  return request({
    url: '/task/evidence/list',
    method: 'get',
    params: query
  })
}

// 查询案件证据详细
export function getEvidence(id) {
  return request({
    url: '/task/evidence/' + id,
    method: 'get'
  })
}

// 新增案件证据
export function addEvidence(data) {
  return request({
    url: '/task/evidence',
    method: 'post',
    data: data
  })
}

// 修改案件证据
export function updateEvidence(data) {
  return request({
    url: '/task/evidence',
    method: 'put',
    data: data
  })
}

// 删除案件证据
export function delEvidence(id) {
  return request({
    url: '/task/evidence/' + id,
    method: 'delete'
  })
}

// 导出案件证据
export function exportEvidence(query) {
  return request({
    url: '/task/evidence/export',
    method: 'get',
    params: query
  })
}
import request from '@/utils/request'

// 查询无人机战法列表
export function listTactics(query) {
  return request({
    url: '/base/tactics/list',
    method: 'get',
    params: query
  })
}

// 查询无人机战法详细
export function getTactics(id) {
  return request({
    url: '/base/tactics/' + id,
    method: 'get'
  })
}

// 新增无人机战法
export function addTactics(data) {
  return request({
    url: '/base/tactics',
    method: 'post',
    data: data
  })
}

// 修改无人机战法
export function updateTactics(data) {
  return request({
    url: '/base/tactics',
    method: 'put',
    data: data
  })
}

// 删除无人机战法
export function delTactics(id) {
  return request({
    url: '/base/tactics/' + id,
    method: 'delete'
  })
}

// 导出无人机战法
export function exportTactics(query) {
  return request({
    url: '/base/tactics/export',
    method: 'get',
    params: query
  })
}
import request from '@/utils/request'

// 查询案件列列表
export function listEvent(query) {
  return request({
    url: '/task/event/list',
    method: 'get',
    params: query
  })
}

// 查询案件列详细
export function getEvent(id) {
  return request({
    url: '/task/event/' + id,
    method: 'get'
  })
}
// 查询案件相关详细
export function getEventInfo(par) {
  return request({
    url: '/task/event/getEventInfo',
    method: 'get',
    params:par
  })
}

// 新增案件列
export function addEvent(data) {
  return request({
    url: '/task/event/saveEvent',
    method: 'post',
    data: data
  })
}

// 修改案件列
export function updateEvent(data) {
  return request({
    url: '/task/event',
    method: 'put',
    data: data
  })
}

// 删除案件列
export function delEvent(id) {
  return request({
    url: '/task/event/' + id,
    method: 'delete'
  })
}

// 导出案件列
export function exportEvent(query) {
  return request({
    url: '/task/event/export',
    method: 'get',
    params: query
  })
}
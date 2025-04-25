import request from '@/utils/request'

// 查询任务管理列表
export function listManage(query) {
  return request({
    url: '/task/manage/list',
    method: 'get',
    params: query
  })
}

// 查询任务管理详细
export function getManage(id) {
  return request({
    url: '/task/manage/' + id,
    method: 'get'
  })
}

// 新增任务管理
export function addManage(data) {
  return request({
    url: '/task/manage/saveManage',
    method: 'post',
    data: data
  })
}

// 修改任务管理
export function updateManage(data) {
  return request({
    url: '/task/manage',
    method: 'put',
    data: data
  })
}

// 删除任务管理
export function delManage(id) {
  return request({
    url: '/task/manage/' + id,
    method: 'delete'
  })
}

// 导出任务管理
export function exportManage(query) {
  return request({
    url: '/task/manage/export',
    method: 'get',
    params: query
  })
}
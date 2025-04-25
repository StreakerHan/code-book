import request from '@/utils/request'

// 查询app用户列表
export function listAppUser(query) {
  return request({
    url: '/app/appUser/list',
    method: 'get',
    params: query
  })
}

// 查询app用户详细
export function getAppUser(id) {
  return request({
    url: '/app/appUser/' + id,
    method: 'get'
  })
}

// 新增app用户
export function addAppUser(data) {
  return request({
    url: '/app/appUser',
    method: 'post',
    data: data
  })
}

// 修改app用户
export function updateAppUser(data) {
  return request({
    url: '/app/appUser',
    method: 'put',
    data: data
  })
}

// 删除app用户
export function delAppUser(id) {
  return request({
    url: '/app/appUser/' + id,
    method: 'delete'
  })
}
// 导出app用户
export function exportAppUser(query) {
    return request({
        url: '/app/appUser/export',
        method: 'get',
        params: query
    })
}

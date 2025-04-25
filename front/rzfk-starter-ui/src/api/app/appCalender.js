import request from '@/utils/request'

// 查询赛季赛程信息列表
export function listAppCalender(query) {
  return request({
    url: '/app/appCalender/list',
    method: 'get',
    params: query
  })
}

// 查询赛季赛程信息详细
export function getAppCalender(id) {
  return request({
    url: '/app/appCalender/' + id,
    method: 'get'
  })
}

// 新增赛季赛程信息
export function addAppCalender(data) {
  return request({
    url: '/app/appCalender',
    method: 'post',
    data: data
  })
}

// 修改赛季赛程信息
export function updateAppCalender(data) {
  return request({
    url: '/app/appCalender',
    method: 'put',
    data: data
  })
}

// 删除赛季赛程信息
export function delAppCalender(id) {
  return request({
    url: '/app/appCalender/' + id,
    method: 'delete'
  })
}
// 导出赛季赛程信息
export function exportAppCalender(query) {
    return request({
        url: '/app/appCalender/export',
        method: 'get',
        params: query
    })
}

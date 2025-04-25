import request from '@/utils/request'

// 查询短信发送记录列表
export function listAppSmsRecord(query) {
  return request({
    url: '/app/appSmsRecord/list',
    method: 'get',
    params: query
  })
}

// 查询短信发送记录详细
export function getAppSmsRecord(id) {
  return request({
    url: '/app/appSmsRecord/' + id,
    method: 'get'
  })
}

// 新增短信发送记录
export function addAppSmsRecord(data) {
  return request({
    url: '/app/appSmsRecord',
    method: 'post',
    data: data
  })
}

// 修改短信发送记录
export function updateAppSmsRecord(data) {
  return request({
    url: '/app/appSmsRecord',
    method: 'put',
    data: data
  })
}

// 删除短信发送记录
export function delAppSmsRecord(id) {
  return request({
    url: '/app/appSmsRecord/' + id,
    method: 'delete'
  })
}
// 导出短信发送记录
export function exportAppSmsRecord(query) {
    return request({
        url: '/app/appSmsRecord/export',
        method: 'get',
        params: query
    })
}

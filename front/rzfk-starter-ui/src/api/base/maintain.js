import request from '@/utils/request'

// 查询维护；包含无人机、推拉流、文达通推拉流等,字段不要乱删减，cjd后台和公安后台都用同一张列表
export function listMaintain(query) {
  return request({
    url: '/base/maintain/list',
    method: 'get',
    params: query
  })
}

// 查询维护；包含无人机、推拉流、文达通推拉流等,字段不要乱删减，cjd后台和公安后台都用同一张详细
export function getMaintain(id) {
  return request({
    url: '/base/maintain/' + id,
    method: 'get'
  })
}

// 新增维护；包含无人机、推拉流、文达通推拉流等,字段不要乱删减，cjd后台和公安后台都用同一张
export function addMaintain(data) {
  return request({
    url: '/base/maintain',
    method: 'post',
    data: data
  })
}

// 修改维护；包含无人机、推拉流、文达通推拉流等,字段不要乱删减，cjd后台和公安后台都用同一张
export function updateMaintain(data) {
  return request({
    url: '/base/maintain',
    method: 'put',
    data: data
  })
}

// 删除维护；包含无人机、推拉流、文达通推拉流等,字段不要乱删减，cjd后台和公安后台都用同一张
export function delMaintain(id) {
  return request({
    url: '/base/maintain/' + id,
    method: 'delete'
  })
}

// 导出维护；包含无人机、推拉流、文达通推拉流等,字段不要乱删减，cjd后台和公安后台都用同一张
export function exportMaintain(query) {
  return request({
    url: '/base/maintain/export',
    method: 'get',
    params: query
  })
}
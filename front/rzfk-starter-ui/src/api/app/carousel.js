import request from '@/utils/request'

// 查询轮播内容设置列表
export function listCarousel(query) {
  return request({
    url: '/app/appCarousel/list',
    method: 'get',
    params: query
  })
}

// 查询轮播内容设置详细
export function getCarousel(id) {
  return request({
    url: '/app/appCarousel/' + id,
    method: 'get'
  })
}

// 新增轮播内容设置
export function addCarousel(data) {
  return request({
    url: '/app/appCarousel',
    method: 'post',
    data: data
  })
}

// 修改轮播内容设置
export function updateCarousel(data) {
  return request({
    url: '/app/appCarousel',
    method: 'put',
    data: data
  })
}

// 删除轮播内容设置
export function delCarousel(id) {
  return request({
    url: '/app/appCarousel/' + id,
    method: 'delete'
  })
}
// 导出轮播内容设置
export function exportCarousel(query) {
    return request({
        url: '/app/appCarousel/export',
        method: 'get',
        params: query
    })
}

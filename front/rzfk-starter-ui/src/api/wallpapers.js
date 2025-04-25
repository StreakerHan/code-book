import request from '@/utils/request'

// 查询壁纸列表
export function listWallpapers(query) {
  return request({
    url: '/wallpaper/wallpapers/list',
    method: 'get',
    params: query
  })
}

// 查询壁纸详细
export function getWallpapers(id) {
  return request({
    url: '/wallpaper/wallpapers/' + id,
    method: 'get'
  })
}

// 新增壁纸
export function addWallpapers(data) {
  return request({
    url: '/wallpaper/wallpapers',
    method: 'post',
    data: data
  })
}

// 修改壁纸
export function updateWallpapers(data) {
  return request({
    url: '/wallpaper/wallpapers',
    method: 'put',
    data: data
  })
}

// 删除壁纸
export function delWallpapers(id) {
  return request({
    url: '/wallpaper/wallpapers/' + id,
    method: 'delete'
  })
}
// 导出壁纸
export function exportWallpapers(query) {
    return request({
        url: '/wallpaper/wallpapers/export',
        method: 'get',
        params: query
    })
}

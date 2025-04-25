import request from '@/utils/request'

// 查询壁纸主题列表
export function listWallpapersTopic(query) {
  return request({
    url: '/wallpaper/wallpapersTopic/list',
    method: 'get',
    params: query
  })
}

// 查询壁纸主题详细
export function getWallpapersTopic(id) {
  return request({
    url: '/wallpaper/wallpapersTopic/' + id,
    method: 'get'
  })
}

// 新增壁纸主题
export function addWallpapersTopic(data) {
  return request({
    url: '/wallpaper/wallpapersTopic',
    method: 'post',
    data: data
  })
}

// 修改壁纸主题
export function updateWallpapersTopic(data) {
  return request({
    url: '/wallpaper/wallpapersTopic',
    method: 'put',
    data: data
  })
}

// 删除壁纸主题
export function delWallpapersTopic(id) {
  return request({
    url: '/wallpaper/wallpapersTopic/' + id,
    method: 'delete'
  })
}
// 导出壁纸主题
export function exportWallpapersTopic(query) {
    return request({
        url: '/wallpaper/wallpapersTopic/export',
        method: 'get',
        params: query
    })
}

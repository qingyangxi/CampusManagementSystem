import request from '@/utils/request'

export function page(name,stuId,education,classId,page,pageSize) {
  return request({
    url: '/student?name='+name+'&stuId='+stuId+'&education='+education+'&classId='+classId+'&page='+page+'&pageSize='+pageSize,
    method: 'get'
  })
}

export function findAll() {
  return request({
    url: '/student',
    method: 'get'
  })
}

export function add(emp) {
  return request({
    url: '/student',
    method: 'post',
    data: emp
  })
}

export function update(dept) {
  return request({
    url: '/student',
    method: 'put',
    data: dept
  })
}

export function findAllCls() {
  return request({
    url: '/class',
    method: 'get'
  })
}

export function deleteById(id) {
  return request({
    url: '/student/'+id,
    method: 'delete',
  })
}


export function selectById(id) {
  return request({
    url: '/student/'+id,
    method: 'get',
  })
}

export function  selectByIdCls(id) {
  return request({
    url: '/class/'+id,
    method: 'get',
  })
}

export function dealPoints(id, point) {
  return request({
    url: '/student/point/'+id,
    method: 'put',
    data: point
  })
}

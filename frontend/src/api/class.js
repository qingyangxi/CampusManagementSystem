import request from '@/utils/request'

export function page(name,begin,end,page,pageSize) {
  return request({
    url: '/class?name='+name+'&begin='+begin+'&end='+end+'&page='+page+'&pageSize='+pageSize,
    method: 'get'
  })
}

export function findAll() {
  return request({
    url: '/class',
    method: 'get'
  })
}

export function add(cls) {
  return request({
    url: '/class',
    method: 'post',
    data: cls
  })
}

export function update(cls) {
  return request({
    url: '/class',
    method: 'put',
    data: cls
  })
}

export function deleteById(id) {
  return request({
    url: '/class/' + id,
    method: 'delete'
  })
}

export function selectById(id) {
  return request({
    url: '/class/' + id,
    method: 'get'
  })
}


export function findAllEmp() {
  return request({
    url: '/emps',
    method: 'get'
  })
}

export function selectByIdEmp(id) {
  return request({
    url: '/emps/'+id,
    method: 'get',
  })
}

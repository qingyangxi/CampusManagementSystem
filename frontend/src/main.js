import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'
import { getToken } from '@/utils/auth' // 引入获取 token 的方法

import '@/icons' // icon
// import '@/permission'

if (process.env.NODE_ENV === 'production') {
  const { mockXHR } = require('../mock')
  mockXHR()
}

const whiteList = ['/login', '/register']

router.beforeEach((to, from, next) => {
  const token = getToken() // 获取 token
  if (!token && !whiteList.includes(to.path)) {
    // 如果没有 token 且访问的不是白名单中的页面，则强制跳转到登录页面
    next('/login')
  } else {
    next() // 允许访问
  }
})

Vue.use(ElementUI)
Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})

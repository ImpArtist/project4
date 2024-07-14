import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/welcome',
    name: 'Welcome',
    component: () => import(/* webpackChunkName: "about" */ '../pages/welcome.vue')
  },
  {
    path: '/menus',
    name: 'menus',
    component: () => import(/* webpackChunkName: "about" */ '../pages/menu-page.vue')
  },
  {
    path: '/data-search',
    name: 'data-search',
    component: () => import(/* webpackChunkName: "about" */ '../pages/data-search.vue')
  },
  {
    path: '/chart-page',
    name: 'chart-page',
    component: () => import(/* webpackChunkName: "about" */ '../pages/chart-page.vue')
  },
  {
    path: '/database/manage',
    name: 'database-manage',
    component: () => import(/* webpackChunkName: "about" */ '../pages/database-manage.vue')
  },
  {
    path: '/api-use-statistics',
    name: 'api-use-statistics',
    component: () => import(/* webpackChunkName: "about" */ '../pages/api-use-statistics.vue')
  },
  {
    path: '/api-detail',
    name: 'api-detail',
    component: () => import(/* webpackChunkName: "about" */ '../pages/api-detail.vue')
  },
  {
    path: '/api-create',
    name: 'api-create',
    component: () => import(/* webpackChunkName: "about" */ '../pages/api-create.vue')
  },
  {
    path: '/database/table/manage',
    name: 'database-table-manage',
    component: () => import(/* webpackChunkName: "about" */ '../pages/database-table-manage.vue')
  },
  {
    path: '/database/table/index/manage/:TableName',
    name: 'database-table-manage-index',
    props: true, // 开启props传参
    component: () => import(/* webpackChunkName: "about" */ '../pages/database-table-index-manage.vue')
  },
  {
    path: '/charts',
    name: 'charts',
    component: () => import(/* webpackChunkName: "about" */ '../views/charts.vue')
  },
  {
    path: '/chart_test',
    name: 'chart_test',
    component: () => import(/* webpackChunkName: "about" */ '../views/charts_test.vue')
  },
  {
    path: '/ability-prediction',
    name: 'ability-prediction',
    component: () => import(/* webpackChunkName: "about" */ '../pages/ability-prediction.vue')
  },

  {
    path: '/',
    name: 'Manager',
    component: () => import('../views/manager.vue'),
    redirect: '/home',  // 重定向到主页
    children: [
      { path: 'introduction', name: '介绍',component: () => import('../views/introduction') },
    ]
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

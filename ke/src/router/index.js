import Vue from 'vue'
import Router from 'vue-router'
import main from '@/views/main.vue'
import seat from '@/views/seat.vue'
import curriculums from '@/views/curriculums.vue'
import platformPage from '@/views/platformPage.vue'
import login from '@/components/common/login.vue'
import notFound from '@/components/common/404.vue'
// () => import('@/views/main.vue')

Vue.use(Router)

export default new Router({
  routes: [
    {
      path:'/',
      name:'main',
      component : main
    },
    {
      path:'/seat',
      name:'seat',
      component : seat
    },
    {
      path:'/curriculums',
      name:'curriculums',
      component : curriculums
    },
    {
      path:'/platformPage',
      name:'platformPage',
      component : platformPage
    },
    {
      path:'/login',
      name:'login',
      component : login
    },{
      path: "/404",
      name: "notFound",
      component: notFound
    }, {
      path: "*", // 此处需特别注意置于最底部
      redirect: "/404"
    }

  ]
})

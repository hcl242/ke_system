import Vue from 'vue'
import Router from 'vue-router'
import main from '@/views/main.vue'
import seat from '@/views/seat.vue'
import curriculums from '@/views/curriculums.vue'
import platformPage from '@/views/platformPage.vue'
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
    }
  ]
})

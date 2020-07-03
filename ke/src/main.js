// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import API from '@/util/common/ApiUtil'
import Cookie from '@/util/common/CookieUtil'
import Fetch from '@/util/common/Fetch'
import Storage from '@/util/common/Storage'
import Wss from '@/util/common/websocket-connection'
import store from '@/store/store'
import Toastr from '@/assets/toastr/toastr.min'
import '@/assets/toastr/toastr.min.css'
import $ from 'jquery'
// import "@/assets/other/css/main/gbstyle.css";
// import "@/assets/other/css/main/reset.css";

Toastr.options = {
        closeButton: true,                   //关闭按钮
        debug: true,                         //debug模式
        "progressBar": true,                  //是否显示进度条
        positionClass: "toast-top-right",     //位置
        onclick: null,                        //点击事件
        showDuration: "300",                  //显示的动画时间
        hideDuration: "1000",                 //消失的动画时间
        timeOut: "3000",                      //展现时间
        extendedTimeOut: "1000",              //加长展示时间
        showEasing: "swing",                  //显示时的动画缓冲方式
        hideEasing: "linear",                 //消失时的动画缓冲方式
        showMethod: "fadeIn",                 //显示时的动画方式
        hideMethod: "fadeOut"                 //消失时的动画方式
    }

Vue.use(ElementUI)

Vue.config.productionTip = false

let protoType = Vue.prototype

protoType.util = {
  API,
  Cookie,
  Fetch,
  Toastr,
  Local : Storage.Local,
  Session : Storage.Session,
  Wss
}

router.beforeEach((to,form,next)=>{
  next();
})

import head from '@/components/common/BC_Head'
// import foot from '/src/components/common/BC_Foot.vue'

Vue.component('bcHead',head)
// Vue.component('bcFoot',foot)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})

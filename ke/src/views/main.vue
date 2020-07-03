<template>
  <div id="main" class="login-div" style="margin: 6.25rem auto;">
    <!-- <img src="http://q1.qlogo.cn/g?b=qq&nk=945794520&s=100" /> -->
          <div class="logo" :style="'background-image: url(http://q1.qlogo.cn/g?b=qq&nk='+(username==''?'1':username)+'&s=100);'"></div>
          <div class="title">腾讯课堂辅助系统</div>
          <div class="sub-title">Beta 0.0.8</div>
          <div class="sub-title">请使用qq登录,本系统本地运行,密码数据直接请求腾讯服务器,无后台,请放心使用</div>
          <div class="fields">
            <div class="username"><svg fill="#fff" viewBox="0 0 1024 1024"><path class="path1" d="M896 307.2h-819.2c-42.347 0-76.8 34.453-76.8 76.8v460.8c0 42.349 34.453 76.8 76.8 76.8h819.2c42.349 0 76.8-34.451 76.8-76.8v-460.8c0-42.347-34.451-76.8-76.8-76.8zM896 358.4c1.514 0 2.99 0.158 4.434 0.411l-385.632 257.090c-14.862 9.907-41.938 9.907-56.802 0l-385.634-257.090c1.443-0.253 2.92-0.411 4.434-0.411h819.2zM896 870.4h-819.2c-14.115 0-25.6-11.485-25.6-25.6v-438.566l378.4 252.267c15.925 10.618 36.363 15.925 56.8 15.925s40.877-5.307 56.802-15.925l378.398-252.267v438.566c0 14.115-11.485 25.6-25.6 25.6z"></path></svg><input type="username" v-model="username" class="user-input" placeholder="Username" /></div>
            <div class="password"><svg fill="#fff" viewBox="0 0 1024 1024"><path class="path1" d="M742.4 409.6h-25.6v-76.8c0-127.043-103.357-230.4-230.4-230.4s-230.4 103.357-230.4 230.4v76.8h-25.6c-42.347 0-76.8 34.453-76.8 76.8v409.6c0 42.347 34.453 76.8 76.8 76.8h512c42.347 0 76.8-34.453 76.8-76.8v-409.6c0-42.347-34.453-76.8-76.8-76.8zM307.2 332.8c0-98.811 80.389-179.2 179.2-179.2s179.2 80.389 179.2 179.2v76.8h-358.4v-76.8zM768 896c0 14.115-11.485 25.6-25.6 25.6h-512c-14.115 0-25.6-11.485-25.6-25.6v-409.6c0-14.115 11.485-25.6 25.6-25.6h512c14.115 0 25.6 11.485 25.6 25.6v409.6z"></path></svg><input type="password" v-model="password" class="pass-input" placeholder="Password" /></div>
          </div>
          <button class="signin-button" @click="login">登录</button>
          <div class="link"><a href="#" @click.prevent="readLocal" style="font-size: 1.5rem;">读取上一次输入</a></div>
  </div>
</template>

<script>


  export default {
    name: 'main',
    data() {
      return {
        username:"",
        password:"",
        loginMessage:""
      }
    },
    methods:{
      login(){
        var vm = this;
        this.util.Toastr.warning("2s后程序将进入自动登录操作,在成功登录之前,请勿进行任何操作!!!!!");
        setTimeout(function(){
          vm.toCurriculums();
        },2000);
      },
      toCurriculums(){
        if(!(typeof this.username == "undefined"||this.username==""||typeof this.password == "undefined"||this.password=="")){
           this.writeLocal();
        }
        this.$router.push({
          name:"curriculums",
          params:{
            username:this.username,
            password:this.password
          }
        })
      },
      init(){
        this.util.Fetch.post(this.util.API.CHECKLOGIN).then(res=>{
          this.loginMessage = res;
          console.log(this.loginMessage)
          if(this.loginMessage.loginStatus==0){
            var data = JSON.parse(this.loginMessage.data);
            if(data.retcode==0){
              //登录成功
              this.$router.push({
                name:"curriculums",
                params:{
                  loginMessage:this.loginMessage
                }
              })
              return;
            }
          }else if(this.loginMessage.loginStatus==1){
            //账号密码错误
            this.util.Toastr.warning(this.loginMessage.msg);
          }else if(this.loginMessage.loginStatus==2){
            //出现异常
            this.util.Toastr.warning(this.loginMessage.msg);
          }else{
            //没有
          }
        })
        let msg = this.$route.params.msg;
        let msgStauts = this.$route.params.msg;
        if(!(typeof msg == "undefined"||msg=="")){
          if(msgStauts=="0"){
            this.util.Toastr.success(msg);
          }else if(msgStauts=="1"){
            this.util.Toastr.warning(msg);
          }else if(msgStauts=="2"){
            this.util.Toastr.error(msg);
          }else if(msgStauts=="2"){
            this.util.Toastr.info(msg);
          }else{
             this.util.Toastr.warning(msg);
          }
        }
      },
      readLocal(){
        this.util.Fetch.post(this.util.API.LOGINCACHE).then(res=>{
          if(res!=null){
             this.username = res.username;
             this.password = res.password;
             return;
          }
        })
        if(this.util.Local.getItem("username")!=null){
          this.username = this.util.Local.getItem("username");
        }
        if(this.util.Local.getItem("password")!=null){
          this.password = this.util.Local.getItem("password");
        }
      },
      writeLocal(){
        this.util.Local.setItem("password",this.password);
        this.util.Local.setItem("username",this.username);
      }
    },
    created(){

    },
    mounted(){
      this.init();
    },
    watch:{

    }
  }
</script>

<style scoped>
  @import '../assets/css/login-style.css';
  #main{
    /* background-color: dimgray; */
  }
  .gbcont1 ul li{
    margin-bottom: 10px;
  }
  #toHeight{
	 /* height: 10240px; */
  }
</style>

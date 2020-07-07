<template>
  <div>
    <div v-if="loading" class="loader">
    		<div class="domino">
    			<div></div>
    			<div></div>
    			<div></div>
    			<div></div>
    		</div>
    </div>
  </div>
</template>

<script>
  function getChromeVersion() {
      var arr = navigator.userAgent.split(' ');
      var chromeVersion = '';
      for(var i=0;i < arr.length;i++){
          if(/chrome/i.test(arr[i]))
          chromeVersion = arr[i]
      }
      if(chromeVersion){
          return Number(chromeVersion.split('/')[1].split('.')[0]);
      } else {
          return false;
      }
  }

  export default{
    name:'login',
    data(){
      return {
        loading:true,
        username:"",
        password:""
      }
    },
    methods:{
      init(){
        if( this.$store.state.loginMessage == null||
            typeof this.$store.state.loginMessage == "undefined"||
            this.$store.state.loginMessage==""||
            this.$store.state.loginMessage.loginStatus!=0
        ){
          //不存在登录信息
          if(typeof this.$route.params.username == "undefined"||this.$route.params.username==""||typeof this.$route.params.password == "undefined"||this.$route.params.password==""){
             this.$router.push({
               name:"main",
               params:{
                 msg:"username或password不能为空!",
                 msgStatus:"1"//0 succ,1 warn,2 err,3 info
               }
             })
          }else{
            this.username = this.$route.params.username;
            this.password = this.$route.params.password;
            this.login();
          }
        }else{
          console.log(this.$store.state.loginMessage)
          //登录信息已存在
          var data = JSON.parse(this.$store.state.loginMessage.data);
          if(data.retcode!=0){
            //username 不合格
            console.log("账号或密码不合格!")
            this.$router.push({
              name:"main",
              params:{
                msg:"账号或密码错误!",
                msgStatus:"2"//0 succ,1 warn,2 err,3 info
              }
            });
            return;
          }
          this.loading = false;
          this.curriculums = data;
        }
      },
      login(){
        var version = getChromeVersion();
        console.log(version);
        if(version < 70) {
            alert('您使用的谷歌浏览器版本过低，为了更好地体验请将浏览器升级到最新版本！');
        }
        this.util.Fetch.post(this.util.API.CURRICULUMS,{
          username:this.username,
          password:this.password,
          version
        }).then(res => {
          if(res.retcode!=0){
            //username 不合格
            console.log("账号或密码不合格!")
            this.$router.push({
              name:"main",
              params:{
                msg:"账号或密码错误!",
                msgStatus:"2"//0 succ,1 warn,2 err,3 info
              }
            });
            return;
          }
          console.log("username通过!");
          this.$store.state.loginMessage = res;
          this.loading = false;
          this.$router.push({
            name:"platformPage"
          });
          return;
        })
      }
    },
    mounted(){
      this.init();
    }
  }
</script>

<style scoped="scoped">
  @import '../../assets/css/loading.css';
</style>

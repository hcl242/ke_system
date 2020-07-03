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


    <div v-else>
        <div class="CuContainer" v-for="(v,i) in curriculums.result.map_list" :key="i">
          <div class="alert alert-info categoryTitle">{{v.industry3_name}}</div>
          <div class="card"  v-for="(item,index) in v.map_courses" :key="item.room_id" @click="pushById(item.room_id)">
            <div class="content">
              <h2>{{item.cname}}</h2>
              <h3>{{item.room_id}}</h3>
              <h4>{{item.agency_info.name}}:<div v-html="item.agency_info.summay"></div></h4>
              <a href="#" @click.prevent="pushById(item.room_id)">进入教室</a>
            </div>
          </div>
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
		name: 'curriculums',
		data(){
			return{
				curriculums:"",
				username:"",
				loading:true
			}
		},
		methods:{
      fetchCurriculums(){
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
          this.loading = false;
          this.curriculums = res;
        })
      },
      init(){
        if(this.$route.params.loginMessage == null||typeof this.$route.params.loginMessage == "undefined"||this.$route.params.loginMessage==""){
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
            this.fetchCurriculums();
          }
        }else{
          var data = JSON.parse(this.$route.params.loginMessage.data);
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
      pushById(roomId){
        this.$router.push({
          name:"seat",
          params:{
            roomId:roomId
          }
        })
      }
		},
		mounted() {
      this.init();
		}
	}
</script>

<style scoped="scoped">
  @import '../assets/css/curriculums-style.css';
  @import '../assets/css/loading.css';
</style>

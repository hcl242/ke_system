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
      <div class="CuContainer">
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
  </div>

</template>

<script>
	export default{
		name: 'curriculums',
		data(){
			return{
				curriculums:"",
				loading:true
			}
		},
		methods:{
      init(){
        if(!(this.$store.state.loginMessage == null||typeof this.$store.state.loginMessage == "undefined"||this.$store.state.loginMessage=="")){
          var data = JSON.parse(this.$store.state.loginMessage.data);
          if(data.retcode!=0){
            //账号密码 不合格
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

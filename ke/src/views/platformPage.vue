<template>
  <div id="platformPage">
    <div class="shou_zhu" style="background: url(../../static/img/platformPage/san.png);height: 100%;width: 100%;position: absolute;" >
    		<div class="container">
    			<!-- <div align="center">
    				<h2><span> 课堂辅助 </span></h2>
    				<div class="xian1"></div>
    				<div class="xian2"></div>
    			</div> -->
    			<div class="col-sm-4" align="left"  @click="to('curriculums')">
    				<div class="thumbnail" align="center">
    					<div class="col-xs-3 col-lg-12">
    						<h3>实时监控平台</h3>
    						<div class="yuan">
    							<img src="../../static/img/platformPage/tuc_1.png" />
    						</div>
    					</div>
    					<div class="col-xs-9 col-lg-12">
    						<h4></h4>
    						<ul class="list-inline" v-for="(v,i) in curriculums.map_list" :key="i" v-if="i<2">
    							<li v-for="(item,j) in v.map_courses" :key="j" v-if="j<4"><a href="#">{{item.cname}}</a></li>
    						</ul>

    					</div>
    				</div>
    			</div>
    			<div class="col-sm-4" align="center">
    				<div class="thumbnail" align="center">
    					<div class="col-xs-3 col-lg-12">
    						<h3>数据统计平台</h3>
    						<div class="yuan">
    							<img src="../../static/img/platformPage/tuc_2.png" />
    						</div>
    					</div>
    					<div class="col-xs-9 col-lg-12">
    						<h4></h4>
    						<ul class="list-inline">
    							<li><a href="#">在线数据</a></li>
    							<li><a href="#">消息数据</a></li>
                  <li><a href="#">人数数据</a></li>
                  <li><a href="#">统计报表</a></li>
                  <li><a href="#">在线时间报表</a></li>
                  <li><a href="#">消息数量报表</a></li>
    						</ul>
    					</div>
    				</div>
    			</div>
    			<div class="col-sm-4" align="right">
    				<div class="thumbnail" align="center">
    					<div class="col-xs-3 col-lg-12">
    						<h3>后台设置平台</h3>
    						<div class="yuan">
    							<img src="../../static/img/platformPage/tuc_3.png" />
    						</div>
    					</div>
    					<div class="col-xs-9 col-lg-12">
    						<h4></h4>
    						<ul class="list-inline">
    							<li><a href="#">教室成员设置</a></li>
                  <li><a href="#">其它设置</a></li>
                  <li><a href="#">关于</a></li>
                  <li><a href="#">联系</a></li>
    						</ul>
    					</div>
    				</div>
    			</div>
    		</div>
    	</div>
  </div>
</template>

<script>
  export default{
    name: 'platformPage',
    data(){
      return {
        curriculums:""
      }
    },
    methods:{
      to(name){
        this.$router.push({
          name
        })
      },
      init(){
        if( this.$store.state.loginMessage == null||
            typeof this.$store.state.loginMessage == "undefined"||
            this.$store.state.loginMessage==""||
            this.$store.state.loginMessage.loginStatus!=0){
          console.log(this.$store.state.loginMessage)
          this.to("main");
        }else{
          this.curriculums = JSON.parse(this.$store.state.loginMessage.data).result;
        }
      }
    },
    mounted() {
      this.init();
    }
  }
</script>

<style scoped="scoped">
  @import '../assets/css/platformPage-style.css';
</style>

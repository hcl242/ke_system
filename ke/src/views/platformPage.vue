<template>
  <div id="platformPage">
    <div class="shou_zhu" style="background: url(../../static/img/platformPage/san.png);" >
    		<div class="container">
    			<!-- <div align="center">
    				<h2><span> 课堂辅助 </span></h2>
    				<div class="xian1"></div>
    				<div class="xian2"></div>
    			</div> -->
    			<div class="col-lg-4" align="left"  @click="to('curriculums')">
    				<div class="thumbnail" align="center">
    					<div class="col-xs-3 col-lg-12">
    						<h3>实时监控平台</h3>
    						<div class="yuan">
    							<img src="../../static/img/platformPage/tuc_1.png" />
    						</div>
    					</div>
    					<div class="col-xs-9 col-lg-12">
    						<h4>中国财经•政法教育资源共享平台</h4>
    						<ul class="list-inline">
    							<li><a href="#">学位论文库</a></li>
    							<li><a href="#">特色资源库</a></li>
    							<li><a href="#">机构知识库</a></li>
    							<li><a href="#">商业数据库</a></li>
    							<li><a href="#">财经博导库</a></li>
    							<li><a href="">外文期刊库</a></li>
    						</ul>
    					</div>
    				</div>
    			</div>
    			<div class="col-lg-4" align="center">
    				<div class="thumbnail" align="center">
    					<div class="col-xs-3 col-lg-12">
    						<h3>数据统计平台</h3>
    						<div class="yuan">
    							<img src="../../static/img/platformPage/tuc_2.png" />
    						</div>
    					</div>
    					<div class="col-xs-9 col-lg-12">
    						<h4>中国财经教育E-MOOC联盟</h4>
    						<ul class="list-inline">
    							<li><a href="#">在线数据</a></li>
    							<li><a href="#">消息数据</a></li>
    						</ul>
    					</div>
    				</div>
    			</div>
    			<div class="col-lg-4" align="right">
    				<div class="thumbnail" align="center">
    					<div class="col-xs-3 col-lg-12">
    						<h3>后台设置平台</h3>
    						<div class="yuan">
    							<img src="../../static/img/platformPage/tuc_3.png" />
    						</div>
    					</div>
    					<div class="col-xs-9 col-lg-12">
    						<h4>中国经管实验教学案例平台</h4>
    						<ul class="list-inline">
    							<li><a href="#">中国经管实验教学案例库</a></li>
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

      }
    },
    methods:{
      to(name){
        this.$router.push({
          name
        })
      }
    }
  }
</script>

<style scoped="scoped">
  @import '../assets/css/platformPage-style.css';
</style>

<template>
  <div id="404" style="background: url('../../../static/img/404/6.jpg') no-repeat left top;height: 100%;width: 100%;position: absolute;">
    <div class="text">
      <h1>404</h1>
    	<h2>Uh, Ohh</h2>
      <h3>对不起，我们找不到你要找的东西，因为这里太黑了</h3>
    </div>
    <div @click="$router.push({name:'main'})" class="torch"></div>
  </div>
</template>

<script>
  import '../../assets/js/404.js'
  export default{
    name:'notFound',
    data(){
      return{

      }
    },
    methods:{

    }
  }
</script>

<style scoped="scoped">
  @import '../../assets/css/404.css';
</style>

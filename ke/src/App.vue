<template>
  <div id="app">
    <router-view  />
    <footer style="position: fixed;bottom: 0px;margin: 0 auto;text-align: center;width: 100%;z-index: -100;color: lightgray;">免费使用,请勿商用</footer>
  </div>
</template>

<script>
export default {
  name: 'App',
  data(){
	  return {

	  }
  },
  methods:{

  }
}
</script>

<style>

#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  /* margin-top: 60px; */
}
body{
	margin: 0;
	padding: 0;
	font-family: "montserrat";
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-image: linear-gradient(125deg,#FFC0CB,#ADD8E6,#AFEEEE,#FAFAD2,#D2B48C,#F4A460,#FF0000);
	background-size: 800%;
	animation: bganimation 128s infinite;
}
@keyframes bganimation {
  0%{
    background-position: 0% 50%;
  }
  50%{
    background-position: 100% 50%;
  }
  100%{
    background-position: 0% 50%;
  }
}

</style>

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
      <div v-if="!noneStudents">
        <div style="margin: 50px auto auto;width: 50%;"><el-tag type="info" style="font-size: 18px;margin-bottom: 10px;">{{noneMenbers?'无人':'在线'}}</el-tag><el-progress :text-inside="true" :stroke-width="24" :percentage="onlinePerc()" :status="onlinePerc()==100?'success':(onlinePerc()>=80?'warning':'exception')"></el-progress></div>
        <input type="hidden" v-model="members" /><br />
        <div id="app-cover">
          <div class="row" v-for="(student,index) in studentsOnStatus" v-if="((index+1)%5==0)||(studentsOnStatus.length-1==index)">
            <div class="toggle-button-cover" v-for="(student,index) in studentsOnStatus.slice(((studentsOnStatus.length-1!=index)?index-4:(index-(studentsOnStatus.length%5==0?4:studentsOnStatus.length%5-1))),(index+1))" :key="index">
              <div class="button-cover">
              <div class="lay-name"><span class="badge badge-secondary">{{student.id}}-{{student.name}}</span></div>
              <!-- {{student.uint32_login_type}} -->
              <div class="onlineStatus">
                <img id="onlineStatusImg" :title="student.title" :src="'static/img/onlineStatus/status_'+(student.uint32_login_type<0||student.uint32_login_type>5?'6':student.uint32_login_type)+'.png'" />
              </div>
                <div class="button b2" id="button-11">
                  <input type="checkbox" class="checkbox" v-model="!student.status" />
                  <div class="knobs">
                    <span></span>
                  </div>
                  <div class="layer"></div>
                </div>
              </div>
            </div>

          </div>
        </div>
      </div>
      <div v-else>
        <div class="jumbotron">
          <h3>当前教室没有添加学生名单!请先添加!</h3>
          <p><a class="btn btn-primary btn-lg" href="#" @click.prevent="$router.push({'name':'main'})" role="button">返回主页</a></p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  //解析消息
  let parseReceice = function( receice ){
    // alert(receice.match(/42["cgi",(\S*)]/)[1]);
    let result = JSON.parse(receice.match(/^42((\S|\s)*)/)[1]);
    return result;
  }
  export default{
    data(){
      return {
        intervalList:[],
        sendMsg:"",
        members:"",
        msgs:[],
        msgTemp:"",
        roomId:"",
        students:"",
        studentsOnStatus:"",
        noneMenbers:false,
        noneStudents:false,
        loading:true,
        cachex510:0
      }
    },
    methods:{
      init(){
        console.time("init");
        if(typeof(this.$route.params.roomId) == "undefined"){
           this.$router.push({
             name:"curriculums"
           })
        }
        this.roomId = this.$route.params.roomId;
        this.fetchStudents();
        this.util.Wss.connect(this.roomId);
        this.util.Wss.listen(evt=>{
            console.time("listen");
            // console.log("消息接收:"+evt.data);
            let result = parseReceice(evt.data);
            let flag = false;
            if(result[0]=="cgi"){
              if(result[1].cmd=="0x6ff_0x510"){
                flag = true;
                if(result[1].data==null||typeof(result[1].data)==undefined){
                  if(this.cachex510>=5){
                    this.noneMenbers = true;
                    this.members = null;
                  }
                  //进入待无人状态，连续5次进来就设为无人状态
                  this.cachex510++;
                  return;
                }
                //清除待无人状态
                this.cachex510 = 0;
                this.noneMenbers = false;
                this.members = result[1].data.msg_subcmd0x1_rsp_memberpage.rpt_msg_role_info;
              }
            }else if(result[0]=="push"){
              if(result[1].cmd=="0x3a4"){
                flag = true;
                this.msgTemp = result;
              }
            }
            if(!flag){
              // console.log("其它:"+result);
            }
            console.timeEnd("listen");
        })
        console.timeEnd("init");
      },
      sendData(){
        this.util.Wss.send(`42["cgi",{"data":{"msg_body":{"msg_rich_text":{"msg_attr":{"uint32_char_set":0,"uint32_color":0,"uint32_effect":0,"uint32_pitch_and_family":0,"uint32_time":1588918463},"rpt_msg_elems":[{"msg_text":{"bytes_str":"`+this.sendMsg+`"},"uint32_elem_type":1},{"msg_add_info":{"str_nick_name":"32-黄传龙"},"uint32_elem_type":18}]}},"msg_content_head":{"uint32_div_seq":1588918463,"uint32_pkg_index":0,"uint32_pkg_num":1},"msg_routing_head":{"msg_edu":{"uint32_course_id":102344000}},"str_remark":"@SELF-1588918463931","uint32_label":0,"uint32_msg_rand":102344000,"uint32_msg_seq":1588918463,"uint32_uid_type":0,"uint32_role":0,"uint32_msg_type":0,"str_reply_msg_id":"","uint32_explain_type":0},"app_id":"tencentedu","room_id":102344000,"mod_id":"webclass","request_id":"0cf33603-135b-497a-b421-1fd1a6d809cd","seq":529,"cmd":"0x3a4"}]`);
      },
      refreshMem(){
        this.util.Wss.flushMembers();
      },
      fetchStudents(){
        console.time("fetchStudents");
        this.util.Fetch.get(this.util.API.STUDENTS+this.roomId).then(res=>{
            if(res==""){
              //没有学生名单
              this.noneStudents = true;
            }
            this.students = res;
            console.timeEnd("fetchStudents");
        })
      },
      checkOnline(){
        console.time("checkOnline");
        if(this.noneMenbers){
          this.studentsOnStatus = this.studentsOnStatus.map(item=>{
            item.status = false;
            return item;
          })
          return;
        }
        if(this.students=="")return;
        for(var student in this.studentsOnStatus){
          var flag = false;
          this.studentsOnStatus[student].uint32_login_type = 0;
          this.studentsOnStatus[student].title = "离线";
          for(var member of this.members){
            flag = false;
            //符合
            if(member.str_nick_name.indexOf(this.studentsOnStatus[student].name)!=-1){
              //脏名字检测
              var dirtyName = false;
              for(var studentDirty in this.studentsOnStatus){
                if(student!=studentDirty){
                  if(member.str_nick_name.indexOf(this.studentsOnStatus[studentDirty].name)!=-1){
                    //为脏名字
                    dirtyName = true;
                  }
                }
              }
              //在线
              if(!dirtyName){
                flag = true;
                this.studentsOnStatus[student].uint32_login_type = member.uint32_login_type;
                if(member.uint32_login_type<0||member.uint32_login_type>5){
                  this.studentsOnStatus[student].title = "未知";
                }else if(member.uint32_login_type==0){
                  this.studentsOnStatus[student].title = "离线";
                }else if(member.uint32_login_type==1){
                  this.studentsOnStatus[student].title = "安卓客户端";
                }else if(member.uint32_login_type==2){
                  this.studentsOnStatus[student].title = "ios客户端";
                }else if(member.uint32_login_type==3){
                  this.studentsOnStatus[student].title = "pc浏览器";
                }else if(member.uint32_login_type==4){
                  this.studentsOnStatus[student].title = "pc客户端";
                }else if(member.uint32_login_type==5){
                  this.studentsOnStatus[student].title = "安卓浏览器";
                }else{
                  this.studentsOnStatus[student].title = "未知";
                }
                //break要放里面
                break;
              }
              //uint32_login_type
            }
          }
          this.studentsOnStatus[student].status = flag;
        }
        console.timeEnd("checkOnline");
      },
      onlinePerc(){
        if(this.studentsOnStatus=="")return 0;
        return Math.floor((this.studentsOnStatus.filter(item=>item.status).length/this.studentsOnStatus.length)*100);
      }
    },
    created(){
      this.init();
    },
    mounted(){
      // this.init();
      let vm = this;
      setTimeout(function(){
        vm.refreshMem();
        vm.loading = false;
      },1000);
      var id = setInterval(function(){
        vm.refreshMem();
      },5000);
      this.intervalList.push(id);
    },
    destroyed() {
      this.intervalList.forEach(item=>clearInterval(item));
      this.util.Wss.close();
    },
    watch: {
      msgTemp(newValue, oldValue) {
        this.msgs.push(newValue[1]);
      },
      members(newValue, oldValue) {
        //str_nick_name
        console.log("members update");
        this.checkOnline();
      },
      students(newValue, oldValue){
        console.time("students");
        if(newValue=="")return;
        this.studentsOnStatus = new Array();
        for(var student of newValue){
          student.status = false;
          student.uint32_login_type=0;
          student.title= "离线";
          this.studentsOnStatus.push(student);
        }
        console.timeEnd("students");
      }
    }
  }

</script>

<style scoped>
  @import '../assets/css/seat-style.css';
  @import '../assets/css/loading.css';
  #canvas-container{
    text-align: center;
  }
</style>

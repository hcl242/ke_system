const base = `wss://ke.qq.com/impush/socket.io/?roomId={0}&appId=tencentedu&version=2&type=&role=&EIO=3&transport=websocket`
//var str = "js实现用{two}自符串替换占位符{two} {three}  {one} ".format({one: "I",two: "LOVE",three: "YOU"});
//var str2 = "js实现用{1}自符串替换占位符{1} {2}  {0} ".format("I","LOVE","YOU");
String.prototype.format = function() {
 if(arguments.length == 0) return this;
 var param = arguments[0];
 var s = this;
 if(typeof(param) == 'object') {
  for(var key in param)
   s = s.replace(new RegExp("\\{" + key + "\\}", "g"), param[key]);
  return s;
 } else {
  for(var i = 0; i < arguments.length; i++)
   s = s.replace(new RegExp("\\{" + i + "\\}", "g"), arguments[i]);
  return s;
 }
}

let wss = null;
let room_id = null;

export default{
  connect(roomId){
    wss = new WebSocket(base.format(roomId));
    room_id = roomId;
    // ws 打开
    wss.onopen = function (evt) {
        console.log("链接打开中...");
    	wss.send(`42["imconnect",{"cmd":"register","appId":"tencentedu","roomId":`+roomId+`,"seq":0,"modId":"webclass"}]`);
    };
  },
  listen(onmessage){
    if(wss==null)return;
      // ws 消息监听
      wss.onmessage = onmessage;

  },
  close(){
    if(wss==null)return;
    // ws 关闭
    wss.onclose = function (evt) {
        console.log("链接关闭中...");
    };
  },
  send(data){
    if(wss==null)return;
    wss.send(data);
  },
  flushMembers(listener){
    if(room_id==null)return;
    wss.send(`42["cgi",{"data":{"uint32_sub_cmd":1,"msg_subcmd0x1_req_memberpage":{"str_course_abs_id":"{0}","uint32_page_operation":0,"uint32_page_num":1,"uint32_need_special_user":1,"uint32_per_page_count":399,"uint32_version":1}},"app_id":"tencentedu","room_id":{0},"mod_id":"webclass","request_id":"6e293b00-dd16-4ae1-90b9-400ce1846b2c","seq":169,"cmd":"0x6ff_0x510"}]`.format(room_id));
  }
}

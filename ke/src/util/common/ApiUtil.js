// const port = window.location.port
const port = 8088
const baseUrl = 'http://localhost:'+port+'/api/'
// const resourceUrl = 'http://localhost:8080/simpleapistation/'
// const baseUrl = 'http://192.168.65.5:8080/simpleapistation/api/'
// const resourceUrl = 'http://192.168.65.5:8080/simpleapistation/'
export default {
  CURRICULUMS : baseUrl + "curriculums",
  STUDENTS: baseUrl + "students/",
  CHECKLOGIN: baseUrl + "checkLogin",
  LOGINCACHE: baseUrl + "getLoginCache"
}

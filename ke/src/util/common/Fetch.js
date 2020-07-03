import axios from 'axios'

axios.defaults.withCredentials = true

axios.interceptors.request.use((req)=>{
  //success
  return req;
},(req)=>{
  //error
  return req;
})

axios.interceptors.response.use((req)=>{
  //success
  return req.data;
},(req)=>{
  //error
  return req.data;
})

export default {
  get(url,params){
    return axios({
      url,
      method:"get",
      params,
      headers:{
        // 'Content-Type':'application/x-www-form-urlencoded'
      }
    })
  },
  post(url,data){
    return axios({
      url,
      method:"post",
      data,
      headers:{
        'Content-Type':'application/json'
      }
    })
  }
}

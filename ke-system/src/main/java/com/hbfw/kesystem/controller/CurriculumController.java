package com.hbfw.kesystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbfw.kesystem.bean.*;
import com.hbfw.kesystem.comments.ObjectAsFileUtils;
import com.hbfw.kesystem.comments.RequestUtils;
import com.hbfw.kesystem.comments.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CurriculumController {
    //学生默认存放地址
    @Value("${config.studentsBasePath}")
    String studentsBasePath;

    //驱动包默认存放地址
    @Value("${config.driverBasePath}")
    String driverPath;

    //上次登录信息保存地址
    @Value("${config.loginCachePath}")
    String preLoginDataPath;

    //校验的图片下载地址
    @Value("${config.crawlerBasePath}")
    String crawlerBasePath;

    //浏览器软件启动端口
    @Value("${server.port.webDriver}")
    String webDriverPort;

    //浏览器软件程序路径
    @Value("${config.binaryExeName}")
    String binaryExeName;

    //程序启动端口
    @Value("${server.port}")
    String port;

    //json处理工具
    private ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/api/curriculums", headers = "Accept=application/json")
    @ResponseBody
    public String CurriculumsJsonData(HttpServletRequest req,
                                      @RequestBody LoginClient client,
                                      HttpSession session
                                     ) throws Exception {
        System.out.println("chrome版本:"+client.getVersion());
        if(!(StringUtils.isEmpty(client.getUsername())||StringUtils.isEmpty(client.getPassword()))) {
            //保存上次登录对象
            ObjectAsFileUtils.writeObj(preLoginDataPath,client);
            //启动selenium 进行自动登录
            SeleniumResult seleniumResult = SeleniumUtils.runSelenium(new DefaultBasePath(null,driverPath,null,crawlerBasePath,webDriverPort,binaryExeName),client);
            //获取到登录的token信息
            LoginToken     loginToken = seleniumResult.getToken();
            //获取到WebDriver驱动以进行后面的页面转跳
            WebDriver      driver = seleniumResult.getDriver();
            //初始url
            String         url = "https://ke.qq.com/cgi-bin/user/user_center/get_plan_list?page=1&count=10&bkn={0}&t=0.6431";
            //设置session超时时间
            session.setMaxInactiveInterval(24*60*60);
            //token校验
            if("0".equals(loginToken.getBkn())){
                //设置session
                session.setAttribute("loginMessage",new LoginMessage(1,"登录错误,请重试!",null));
                //页面转跳
                driver.get("http://localhost:"+port);
                //登录失败
                return "{'retcode': 100021,'msg': null}";
            }
            //登录成功 计算url 并 根据loginToken的cookie和bkn进行数据获取
            url         = MessageFormat.format(url, loginToken.getBkn());
            String json = RequestUtils.runRequest(url,loginToken.getCookie());
            session.setAttribute("loginMessage",new LoginMessage(0,"登录成功!",json));
            driver.get("http://localhost:"+port);
//          driver.get("http://localhost:"+8888);
            return json;
        }
        System.out.println("不能登录,参数错误!");
        return "{'retcode': 100021,'msg': null}";
    }

    @RequestMapping(value = "/api/checkLogin")
    @ResponseBody
    public LoginMessage checkLogin(HttpSession session){
        Object loginMessage = session.getAttribute("loginMessage");
        if(loginMessage==null){
            return new LoginMessage(3,null,null);
        }
        return (LoginMessage)loginMessage;
    }

    @RequestMapping(value = "/api/getLoginCache")
    @ResponseBody
    public LoginClient getLoginCache(HttpSession session) throws Exception {
        //读取上次登录信息
        Object obj = ObjectAsFileUtils.readObj(preLoginDataPath);
        if(obj==null)return null;
        return (LoginClient) obj;
    }

    @RequestMapping(value = "/api/students/{roomId}", headers = "Accept=application/json")
    @ResponseBody
    public String StudentsJsonData(@PathVariable(value = "roomId",required = true) String roomId) throws IOException {
        File file = new File(studentsBasePath+"/" + roomId + ".txt");
        if(!file.exists()){
            System.out.println("不存在:"+roomId+".txt");
            return "";
        }
        InputStream inputStream = new FileInputStream(file);//获取输入流

//        FileInputStream inputStream = new FileInputStream("main/java/students/102344000.txt");
        //字节流转字符流
        ArrayList<Map> students = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//读取字符流
        String str = null;
        //将读取到的字符流赋值给buffer，readLine为读取一行，当前行为null时，表示已经读完
        while((str = bufferedReader.readLine()) != null) {
            String[] split = str.split(":");
            if(split.length==2){
                map.put("id",split[0]);
                map.put("name",split[1]);
                students.add(map);
                map = new HashMap<>();
            }

        }
        //关闭bufferReader和输入流
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        inputStream = null;//清空输入流
        return mapper.writeValueAsString(students);
    }
}

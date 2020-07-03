package com.hbfw.kesystem.comments;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class RequestUtils {
	//访问服务端API，三个参数，分别是API地址，请求方式，数据/参数
    //Params请求传递Map，Data请求传递JSON字符串
    public static String runRequest(String url,String cookie){
        StringBuffer buffer = new StringBuffer();//用于存储返回数据
        try {
            URL Url = new URL(url);//创建URL对象
            //对请求方式为GET和空值进行处理
			/*
			 * if (method == "GET"){ url = new URL(requestUrl+"?"+urlencode(info));//拼接URL }
			 * if (method != "GET" && method != "POST") { return new HashMap<String,
			 * Object>();//只处理GET和POST请求，其他请求不做处理 }
			 */
            System.out.println(url);
            System.out.println(cookie);
            //配置连接属性
            HttpURLConnection httpUrlConn = (HttpURLConnection)Url.openConnection();//创建连接对象
            httpUrlConn.setDoInput(true);// 从HttpUrlConntion读入，默认true
            httpUrlConn.setDoOutput(true);// post求情向httpUrlConntion读出，默认flase
            httpUrlConn.setUseCaches(false);// post请求不使用缓存
            httpUrlConn.setRequestMethod("GET");// 请求方式，默认GET
            httpUrlConn.setRequestProperty("Referer","https://ke.qq.com/user/index/index.html");// 传输json格式
            //Cookie: tdw_first_visited=1; pgv_info=ssid=s8482257668; ts_last=ke.qq.com/; pgv_pvid=8167728482; ts_uid=7451829751; Hm_lvt_0c196c536f609d373a16d246a117fd44=1588929037; _pathcode=0.8793993692468434; tdw_auin_data=-; tdw_data_flowid=; tdw_data_testid=; iswebp=1; ke_login_type=1; pgv_pvi=491581440; pgv_si=s3363993600; miniapp_qrcode_id=d63de9c6fc4a4a44aced7641bd18f1c5; _qpsvr_localtk=0.752515893239595; ptui_loginuin=1216096951; uin=o1216096951; skey=@JmL8ZzEFK; RK=rlx0nkoCZg; ptcz=dcd512d31958111be2093df342a5a1feff91ff408856662ad53afea4404933f4; luin=o1216096951; lskey=000100005d9ccab7bedf143ba31f41a4f33faf43131d18d7818dd375017b4c8ae428f40fb20ec9a6e9c2e977; p_uin=o1216096951; pt4_token=MbMOLUt4CABWdUQrtgY*bZjL7ys*Xa88NRZl-9KjC-w_; p_skey=VhHfj2mhDLehXmuW6n6ue*N19DORKOoSupa65YGgwKU_; p_luin=o1216096951; p_lskey=00040000c844550aa0c8928855af6e7e4b5c32d92ec79f7ea02ce3ce8530fe5f6d47c56bb1a6f7be96a07f48; Hm_lpvt_0c196c536f609d373a16d246a117fd44=1588929053; tdw_data={"ver4":"127.0.0.1","ver5":"","ver6":"","refer":"127.0.0.1","from_channel":"","path":"aB-0.8793993692468434","auin":"-","uin":"1216096951","real_uin":"1216096951"}; tdw_data_new_2={"auin":"-","sourcetype":"","sourcefrom":"","ver9":1216096951,"uin":1216096951,"visitor_id":"17916524585482363"}
            //tdw_first_visited=1; pgv_info=ssid=s8482257668; ts_last=ke.qq.com/; pgv_pvid=8167728482; ts_uid=7451829751; Hm_lvt_0c196c536f609d373a16d246a117fd44=1588929037; _pathcode=0.8793993692468434; tdw_auin_data=-; tdw_data_testid=; tdw_data_flowid=; iswebp=1; ke_login_type=1; pgv_si=s3363993600; pgv_pvi=491581440; miniapp_qrcode_id=d63de9c6fc4a4a44aced7641bd18f1c5; _qpsvr_localtk=0.752515893239595; ptui_loginuin=1216096951; uin=o1216096951; skey=@JmL8ZzEFK; RK=rlx0nkoCZg; ptcz=dcd512d31958111be2093df342a5a1feff91ff408856662ad53afea4404933f4; luin=o1216096951; lskey=000100005d9ccab7bedf143ba31f41a4f33faf43131d18d7818dd375017b4c8ae428f40fb20ec9a6e9c2e977; p_uin=o1216096951; pt4_token=MbMOLUt4CABWdUQrtgY*bZjL7ys*Xa88NRZl-9KjC-w_; p_skey=VhHfj2mhDLehXmuW6n6ue*N19DORKOoSupa65YGgwKU_; p_luin=o1216096951; p_lskey=00040000c844550aa0c8928855af6e7e4b5c32d92ec79f7ea02ce3ce8530fe5f6d47c56bb1a6f7be96a07f48; Hm_lpvt_0c196c536f609d373a16d246a117fd44=1588929053; tdw_data={"ver4":"127.0.0.1","ver5":"","ver6":"","refer":"127.0.0.1","from_channel":"","path":"aB-0.8793993692468434","auin":"-","uin":"1216096951","real_uin":"1216096951"}; tdw_data_new_2={"auin":"-","sourcetype":"","sourcefrom":"","ver9":"1216096951","uin":"1216096951","visitor_id":"17916524585482363","ver10":"","url_page":"","url_module":"","url_position":""}
            //RK=Ssx0iEozfg; ptcz=a46540830c570a5dedb7b3929400212c03322213f70cd1eab71ee350061e1108; pgv_pvid=9167931494; tvfe_boss_uuid=8998dd6481451f3f; pgv_pvi=4037709824; o_cookie=1216096951; pac_uid=1_1216096951; ied_qq=o1216096951; ts_uid=3980992190; localInterest=[2032]; gr_user_id=0dce32df-e74c-4290-8085-2bbb18073f6f; grwng_uid=cf2366f4-0ffa-4dd8-95dd-b95e69700d7e; qb_guid=2dd4b3e9a9075555f7ebb18d377988cb; qb_qua=PR=PC&CO=WBK&QV=3&PL=WIN&PB=GE&PPVN=10.5.0.3863&COVC=047000&CHID=45094&RL=2560*1440&MO=QB&VE=GA&BIT=64&OS=10.0.18363; Q-H5-GUID=2dd4b3e9a9075555f7ebb18d377988cb; ptui_loginuin=1216096951; luin=o1216096951; pgv_info=ssid=s7304826411; tdw_auin_data=-; ke_login_type=1; tdw_data_testid=; tdw_data_flowid=; tdw_first_visited=1; ts_refer=www.baidu.com/link; Hm_lvt_0c196c536f609d373a16d246a117fd44=1587432268,1588762429,1588903160,1588910018; tdw_data_sessionid=15889100177291245485983; iswebp=1; pgv_si=s8641832960; _qpsvr_localtk=0.40133693312603214; _pathcode=0.9689042036476871; uin=o1216096951; skey=@hqJMiCXQH; miniapp_qrcode_id=8503e1922ff340e48606cb9294c18832; lskey=00010000e926521b346423784999fe8cdbe2665b2353602753ab2e5825bb791c228e6e702a32eb6e93899310; p_uin=o1216096951; pt4_token=vbdNifsaTqTDJSe04dEgZbsynmS1ga5QYqrIWm0gSMs_; p_skey=MluFGFlGM2VfHAYFGQV30gkNcQWy6PS1qONRWSC6tss_; p_luin=o1216096951; p_lskey=00040000ed5d8c2ae21e80dcadd2ecae635ed8607de37cb7d9799f7704b89059fd318e356f5b45d9e0413a6c; ts_last=ke.qq.com/course/2241172; Hm_lpvt_0c196c536f609d373a16d246a117fd44=1588922488; tdw_data={"ver4":"www.baidu.com","ver5":"","ver6":"","refer":"www.baidu.com","from_channel":"","path":"aB-0.9689042036476871","auin":"-","uin":"1216096951","real_uin":"1216096951"}; tdw_data_new_2={"auin":"-","sourcetype":"","sourcefrom":"","ver9":"1216096951","uin":"1216096951","visitor_id":"22652030018584846","ver10":"","url_page":"personalcenter_orders","url_module":"","url_position":""}
            //RK=Ssx0iEozfg; ptcz=a46540830c570a5dedb7b3929400212c03322213f70cd1eab71ee350061e1108; pgv_pvid=9167931494; tvfe_boss_uuid=8998dd6481451f3f; pgv_pvi=4037709824; o_cookie=1216096951; pac_uid=1_1216096951; ied_qq=o1216096951; ts_uid=3980992190; localInterest=[2032]; gr_user_id=0dce32df-e74c-4290-8085-2bbb18073f6f; grwng_uid=cf2366f4-0ffa-4dd8-95dd-b95e69700d7e; qb_guid=2dd4b3e9a9075555f7ebb18d377988cb; qb_qua=PR=PC&CO=WBK&QV=3&PL=WIN&PB=GE&PPVN=10.5.0.3863&COVC=047000&CHID=45094&RL=2560*1440&MO=QB&VE=GA&BIT=64&OS=10.0.18363; Q-H5-GUID=2dd4b3e9a9075555f7ebb18d377988cb; ptui_loginuin=1216096951; luin=o1216096951; pgv_info=ssid=s7304826411; tdw_auin_data=-; ke_login_type=1; tdw_data_testid=; tdw_data_flowid=; tdw_first_visited=1; ts_refer=www.baidu.com/link; Hm_lvt_0c196c536f609d373a16d246a117fd44=1587432268,1588762429,1588903160,1588910018; tdw_data_sessionid=15889100177291245485983; iswebp=1; pgv_si=s8641832960; _qpsvr_localtk=0.40133693312603214; _pathcode=0.9689042036476871; uin=o1216096951; skey=@hqJMiCXQH; miniapp_qrcode_id=8503e1922ff340e48606cb9294c18832; lskey=00010000e926521b346423784999fe8cdbe2665b2353602753ab2e5825bb791c228e6e702a32eb6e93899310; p_uin=o1216096951; pt4_token=vbdNifsaTqTDJSe04dEgZbsynmS1ga5QYqrIWm0gSMs_; p_skey=MluFGFlGM2VfHAYFGQV30gkNcQWy6PS1qONRWSC6tss_; p_luin=o1216096951; p_lskey=00040000ed5d8c2ae21e80dcadd2ecae635ed8607de37cb7d9799f7704b89059fd318e356f5b45d9e0413a6c; ts_last=ke.qq.com/course/2241172; Hm_lpvt_0c196c536f609d373a16d246a117fd44=1588922488; tdw_data={"ver4":"www.baidu.com","ver5":"","ver6":"","refer":"www.baidu.com","from_channel":"","path":"aB-0.9689042036476871","auin":"-","uin":"1216096951","real_uin":"1216096951"}; tdw_data_new_2={"auin":"-","sourcetype":"","sourcefrom":"","ver9":"1216096951","uin":"1216096951","visitor_id":"22652030018584846","ver10":"","url_page":"personalcenter_orders","url_module":"","url_position":""}
            httpUrlConn.setRequestProperty("Cookie",cookie);// 接收类型json
            //httpUrlConn.setRequestProperty("accept","*/*")//暴力方法设置接受所有类型，防止出现415
            httpUrlConn.connect();//开始连接，配置信息必须在连接前设置完毕
            int statusCode = httpUrlConn.getResponseCode();//获取状态码
            if(statusCode == 200){
                InputStream inputStream = httpUrlConn.getInputStream();//获取输入流
                //字节流转字符流
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//读取字符流
                String str = null;
                //将读取到的字符流赋值给buffer，readLine为读取一行，当前行为null时，表示已经读完
                while((str = bufferedReader.readLine()) != null) {
                    buffer.append(str);
                }
                //关闭bufferReader和输入流
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                inputStream = null;//清空输入流
                httpUrlConn.disconnect();//断开连接
            	
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    //GET请求URL处理
    public static String urlencode(Object params){
        if(params == null){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Map<String,Object> map = (Map)params;
        for (Map.Entry i : map.entrySet()) {
            try {sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String str = sb.toString();
        if(str.length() != 0){
            str = str.substring(0,str.length()-1);
        }
        return str;
    }

    public static String urlFormat(String url,String name) throws Exception {
    	//32-%E9%BB%84%E4%BC%A0%E9%BE%99
    	SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    	Date date = new Date();
    	String time = format.format(date);
    	String data = name +" "+ time;
    	System.out.println("\t设置名字:\t"+data);
    	data = URLEncoder.encode(data, "utf-8");
    	return MessageFormat.format(url, data);
    }
    

}
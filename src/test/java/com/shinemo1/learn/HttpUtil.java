package com.shinemo1.learn;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class HttpUtil {
    public static Map<String,String> cookies=new HashMap<>();
    public  static String getRequest(String url , Map<String,String> params){
        //获取所有键值对
        Set<String> keys=params.keySet();
        //拼接url
        int mark=1;
        for (String key:keys){
            if (mark==1){
                url=url+"?"+key+"="+params.get(key);
            }else {
                url=url+"&"+key+"="+params.get(key);
            }
            mark++;
        }

        String result="";
        try {
            HttpGet httpGet=new HttpGet(url);
            HttpClient client = HttpClients.createDefault();
            addCookieInRequestHeaderBeforeRequest(httpGet);

            HttpResponse response=client.execute(httpGet);
            getAndStoreCookiesFromResponseHeaders(response);

            result=EntityUtils.toString(response.getEntity());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void addCookieInRequestHeaderBeforeRequest(HttpRequest request) {
       String cookie= cookies.get("Cookie");
       if(cookie!=null){
          request.addHeader("Cookie", cookie);
       }

    }

    private static void getAndStoreCookiesFromResponseHeaders(HttpResponse response) {
        //从响应头中获取Set-Cookie
        Header setCookieHeader=response.getFirstHeader("Set-Cookie");
        //如果不为空，取出响应头的值
        if(setCookieHeader!=null){
            String cookiePairsString=setCookieHeader.getValue();
            if(cookiePairsString !=null){
                cookies.put("Cookie",cookiePairsString);
//                String[] cookiePairs=cookiePairsString.split(";");
//                if(cookiePairs!=null){
//                    for(String cookiePair:cookiePairs){
//                        cookies.put("G_AUTH_TOKEN", cookiePair);
//                    }
//                }
            }

        }
    }

    public static String postRequest(String url,Map<String,String> params){
        HttpPost httpPost=new HttpPost(url);
        //UrlEncodedFormEntity构造函数只接受List<? extends NameValuePair>为参数，所以不能使用map
        List<BasicNameValuePair> paramters=new ArrayList<>();
        //获取所有键值对
        Set<String> keys=params.keySet();
        //通过循环将参数保存到集合中
        for (String key : keys) {
            String value=params.get(key);
            paramters.add(new BasicNameValuePair(key, value));
        }
        String result="";
        try {
            //UrlEncodedFormEntity这个类是用来把输入数据编码成合适的内容
            //两个键值对，被UrlEncodedFormEntity实例编码后变为如下内容：param1=value1¶m2=value2
            httpPost.setEntity(new UrlEncodedFormEntity(paramters, "UTF-8"));
            HttpClient client =HttpClients.createDefault();
            addCookieInRequestHeaderBeforeRequest(httpPost);

            HttpResponse response=client.execute(httpPost);
            getAndStoreCookiesFromResponseHeaders(response);

            //从响应对象中获取响应实体
            HttpEntity entity=response.getEntity();
            result=EntityUtils.toString(entity);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  result;

    }

    public static String doService(String url,String type,Map<String,String> params){
        String result="";
        if ("post".equalsIgnoreCase(type)){
            result=HttpUtil.postRequest(url, params);
        }else if("get".equalsIgnoreCase(type)){
            result=HttpUtil.getRequest(url, params);
        }
        return result;
    }
}

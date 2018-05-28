package com.wxy.dg.common.util.wx;

import net.sf.json.JSONObject;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by wxy on 2017/1/4.
 */
public class GetWxConfig {

    public static String accesstoken_url = WeixinConstants.ACCESSTOKEN_URL;
    public static String ticket_url = WeixinConstants.TICKET_URL;

    public static String getAccessToken(){
        String token = null;
        accesstoken_url = accesstoken_url.replace("APPID", WeixinConstants.APPID);
        accesstoken_url = accesstoken_url.replace("SECRET", WeixinConstants.APP_SECRECT);
        JSONObject jsonObject = HttpClientUtil.httpsRequest(accesstoken_url, "GET", null);
        if (null != jsonObject) {
            try {
                token = jsonObject.getString("access_token");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return token;
    }

    public static String getTicket(String accessToken){
        String jsapi_ticket = null;
        ticket_url = ticket_url.replace("ACCESS_TOKEN", accessToken);
        JSONObject jsonObject = HttpClientUtil.httpsRequest(ticket_url, "GET", null);
        if (null != jsonObject) {
            try {
                jsapi_ticket = jsonObject.getString("ticket");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jsapi_ticket;
    }

    public static Map<String,String> sign(String ticket,String url) {

        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16).toLowerCase();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String string1;
        String signature = "";
        // 注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + ticket + "&noncestr=" + nonce_str
                + "&timestamp=" + timestamp + "&url=" + url;
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("appid",WeixinConstants.APPID);
        ret.put("jsapi_ticket", ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        return ret;
    }

    public static String urlEnodeUTF8(String str){
        String result = str;
        try {
            result = URLEncoder.encode(str,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }




}

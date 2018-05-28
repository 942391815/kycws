package com.wxy.dg.common.util.wx;

/**
 * Created by wxy on 2017/1/15.
 */
public class WeixinConstants {
    public final static String APPID = "wx7cb5bf236c992e1d";//服务号的应用号
    public final static String APP_SECRECT = "aedb06164ddc7f9ec37d398ea29254a8";//服务号的应用密码
    public final static String MCHID = "1413190902";
    public final static String notify_url = "http://idaigan.com/dg-ai/mobile/notifyUrl";
    public final static String trade_type = "JSAPI";
    public final static String REDIRECT_URI = "http://idaigan.com/dg-ai/mobile/getAuthToken";
    public final static String TOKEN = "weixinCourse";//服务号的配置token
    public final static String StringSCOPE = "snsapi_userinfo";
    public final static String API_KEY = "GMINGMEWQ5lH4XlB5qxBr8V5MBciXID1";//API密钥
    public final static String signType = "MD5";//API密钥
    public final static String baseUrl = "http://idaigan.com/dg-ai";



    /**
     * 微信基础接口地址
     */
    //普通access_token接口
    public final static String ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";
    //ticket临时票据接口
    public final static String TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
    //统一下单
    public final static String UNIFIEDORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
}

package com.wxy.dg.modules.controller;

import com.wxy.dg.common.base.BaseAction;
import com.wxy.dg.common.constant.Constant;
import com.wxy.dg.common.enums.SubInfoEnum;
import com.wxy.dg.common.model.*;
import com.wxy.dg.common.service.*;
import com.wxy.dg.common.util.Page;
import com.wxy.dg.common.util.wx.*;
import org.apache.commons.lang3.StringUtils;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by test on 2016/12/18.
 */

@Controller
public class MobileHandleAction extends BaseAction {

    @Autowired
    private SubInfoService subInfoService;
    @Autowired
    private BackInfoService backInfoService;
    @Autowired
    private UserTaskService userTaskService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ConsumerService consumerService;
    @Autowired
    private GetAuthToken getAuthToken;
    @Autowired
    private GetWxUserInfo getWxUserInfo;
    @Autowired
    private WeixinPayCommonUtil weixinPayCommonUtil;

    /**
     * 网页授权获取AuthToken
     */
    @RequestMapping("/mobile/getAuthToken")
    @ResponseBody
    public Consumer getAuthToken(HttpServletRequest req, HttpServletResponse resp, String code)
            throws ServletException, IOException {
        Consumer consumer = null;
        AuthToken authToken = getAuthToken.getAuthToken(code);
        if (authToken != null) {
            consumer = getWxUserInfo.getUserInfo(authToken.getAccess_token(), authToken.getOpenid());
            if (consumer != null) {
                Consumer query = new Consumer();
                query.setOpenId(consumer.getOpenId());
                List<Consumer> result = consumerService.findByCondition(query);
                if (result != null && result.size() > 0) {
                    consumer.setUid(Long.parseLong(result.get(0).getCode()));
                    consumerService.update(consumer);
                } else {
                    consumerService.save(consumer);
                }
            }
        }
        return consumer;
    }

    /**
     * JSSDK-获取sign
     *
     * @param request
     * @param response
     * @param url      当前页面的url
     */
    @RequestMapping("/mobile/getSign")
    @ResponseBody
    public Map<String, String> getSign(HttpServletRequest request, HttpServletResponse response, @RequestParam String url) {
        ServletContext context = request.getSession().getServletContext();
        String accessToken = (String) context.getAttribute("accessToken");
        if (accessToken == null) {
            accessToken = GetWxConfig.getAccessToken();
            context.setAttribute("accessToken", accessToken);
            System.out.println("-------accessToken 超时，重新获取--------" + accessToken);
        }
        String jsapiTicket = (String) context.getAttribute("jsapiTicket");
        if (jsapiTicket == null) {
            jsapiTicket = GetWxConfig.getTicket(accessToken);
            context.setAttribute("jsapiTicket", jsapiTicket);
            System.out.println("-------jsapiTicket 超时，重新获取--------" + jsapiTicket);
        }
        Map<String, String> ret = GetWxConfig.sign(jsapiTicket, url);
        return ret;
    }


    /**
     * 用户发表文章
     *
     * @param request
     * @param response
     * @param map
     * @return openid, orderId, orderType必传
     * http://localhost:9001/mobile/task/insert?openid=1&orderId=259&subType=1&url=wwww.oschaina.net
     * 如果是企业用户  增加处理时间  handleDay=20
     * http://localhost:9001/mobile/task/insert?openid=1&orderId=259&subType=2&name=ccccdddddddd&businessType=%E4%B8%8D%E7%9F%A5%E9%81%93&describe=csdn&handleDay=20
     * http://localhost:9001/mobile/task/insert?openid=1&orderId=259&subType=2&name=cccc&businessType=eeeeeeeeeee&describe=csdn&handleDay=20
     */
    @RequestMapping("/mobile/task/insert")
    @ResponseBody
    public ResponseModel insert(HttpServletRequest request, HttpServletResponse response, @RequestParam Map map) {
        ResponseModel rm = new ResponseModel();
        try {
            SubInfoEnum subInfoEnum = userTaskService.submitTask(map);
            rm.setResult(subInfoEnum.getCode());
            rm.setMessage("invoke success");
        } catch (Exception e) {
            e.printStackTrace();
            rm.setStatus(Constant.RESPONSE_FAIL);
            rm.setMessage(e.getMessage());
        }
        return rm;
    }

    /**
     * 获取用户提交的文章列表
     *
     * @param request
     * @param response
     * @param map
     * @return 根据openID获取用户提交的文章列表
     * 根据subType 和openId
     * /mobile/consumer/list?subType=1&openId=123123
     */
    @RequestMapping("/mobile/consumer/list")
    @ResponseBody
    public ResponseModel getUserList(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, String> map) {
        ResponseModel rm = new ResponseModel();
        int start = 0;
        int limit = 15;
        if (map.get("curPage") != null) {
            start = Integer.parseInt(map.get("curPage")) - 1;
        }
        try {
            SubInfo subInfo = new SubInfo();
            subInfo.setOpenid(map.get("openid"));
            subInfo.setSortColumns("sub_time desc ");
            if (map.get("subType") != null) {

                subInfo.setSubType(Integer.parseInt(map.get("subType")));
            }
            List<SubInfo> result = subInfoService.findByCondition(subInfo, start * limit, limit);

            Page<SubInfo> page = new Page<SubInfo>();
            Integer totalCount = subInfoService.getTotalCount(subInfo);

            page.setCount(totalCount);
            page.setList(result);
            rm.setResult(page);
            rm.setMessage("invoke success");
        } catch (Exception e) {
            e.printStackTrace();
            rm.setStatus(Constant.RESPONSE_FAIL);
            rm.setMessage(e.getMessage());
        }
        return rm;
    }

    /**
     * 订单列表
     *
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping("/mobile/orderList")
    @ResponseBody
    public List<Order> orderList(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, String> map) {

        Order order = new Order();
        order.setYn(1);
        order.setSortColumns("update_time desc");
        List<Order> list = orderService.findByCondition(order);
        return list;
    }

    /**
     * 用户购买下单
     *
     * @param request
     * @param response
     * @param map
     * @return 用户购买订单
     * 根据orderId 和openId
     * /mobile/purchaseOrder?openId=1&orderId=12123
     * /mobile/purchaseOrder?openId=123123&total_fee=12123
     */
    @RequestMapping("/mobile/purchaseOrder")
    @ResponseBody
    public ResponseModel purchaseOrder(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, String> map) {
        String orderId = map.get("orderId");
        String openId = map.get("openId");
        ResponseModel responseModel = new ResponseModel();
        if (orderId == null || openId == null) {
            responseModel.setStatus(500);
            responseModel.setMessage("orderId,openId 不能为空!");
            return responseModel;
        }
        try {
            responseModel.setStatus(200);
            orderService.purchaseOrder(openId, orderId);
            responseModel.setMessage("invoke success !");
        } catch (Exception e) {
            e.printStackTrace();
            responseModel.setMessage(e.getMessage());
            responseModel.setStatus(500);
        }
//        if (orderId == null || openId == null) {
//            responseModel.setStatus(500);
//            responseModel.setMessage("orderId,openId 不能为空!");
//            return responseModel;
//        }
//        try {
//            responseModel.setStatus(200);
//            orderService.purchaseOrder(openId, orderId);
//            responseModel.setMessage("invoke success !");
//        } catch (Exception e) {
//            e.printStackTrace();
//            responseModel.setMessage(e.getMessage());
//            responseModel.setStatus(500);
//        }
        return responseModel;
    }

    @RequestMapping("/mobile/getBackInfoList")
    @ResponseBody
    public ResponseModel getBackInfoList(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, String> map) {
        String subId = map.get("subId");
        BackInfo backinfo = new BackInfo();
        ResponseModel responseModel = new ResponseModel();
        if (subId == null) {
            responseModel.setStatus(500);
            responseModel.setMessage("subId 不能为空!");
            return responseModel;
        }
        try {
            responseModel.setStatus(200);
            backinfo.setSubId(Integer.parseInt(subId));
            responseModel.setResult(backInfoService.findByCondition(backinfo));
            responseModel.setMessage("invoke success !");
        } catch (Exception e) {
            e.printStackTrace();
            responseModel.setMessage(e.getMessage());
            responseModel.setStatus(500);
        }
        return responseModel;
    }

    @RequestMapping("/mobile/toPay")
    @ResponseBody
    public Map<String, Object> toPay(HttpServletRequest request, HttpServletResponse response, @RequestParam String openid,@RequestParam String money){
        String orderId = WeixinPayCommonUtil.genOrderNo();

        String totalFeeStr = money;
        Float totalFee = 0.0f;

        if(StringUtils.isNotBlank(totalFeeStr)){
            totalFee = new Float(totalFeeStr);
        }

        //获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
        //随机数
        String nonce_str = WeixinPayCommonUtil.createNoncestr(16);
        //商品描述
        String body = orderId;
        //商户订单号
        String out_trade_no = orderId;
        //订单生成的机器 IP
        String spbill_create_ip = "114.55.239.137";
        //总金额
        Integer total_fee = Math.round(totalFee*100);
        //这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
        String notify_url = WeixinConstants.notify_url;

        SortedMap<String, Object> packageParams = new TreeMap<String, Object>();
        packageParams.put("appid", WeixinConstants.APPID);
        packageParams.put("mch_id", WeixinConstants.MCHID);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", body);
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("total_fee", total_fee+"");
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", WeixinConstants.notify_url);
        packageParams.put("trade_type", WeixinConstants.trade_type);
        packageParams.put("openid", openid);

        //签名
        String sign = WeixinPayCommonUtil.createSign("UTF-8", packageParams);
        packageParams.put("sign", sign);

        //参数封装成XML
        String requestXML = WeixinPayCommonUtil.getRequestXml(packageParams);

        //请求下单
        String result = WeixinPayCommonUtil.httpsRequest(WeixinConstants.UNIFIEDORDER, "POST", requestXML);

        SortedMap<String, Object> map = new TreeMap<String, Object>();
        try {
            map.put("appId", WeixinConstants.APPID);
            map.put("timeStamp", Long.toString(new Date().getTime()));
            map.put("nonceStr", WeixinPayCommonUtil.createNoncestr(16));
            map.put("package", "prepay_id="+WeixinPayCommonUtil.doXMLParse(result).get("prepay_id"));
            map.put("signType", WeixinConstants.signType);
            String finalsign = WeixinPayCommonUtil.createSign("UTF-8", map);
            map.put("paySign", finalsign);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 微信异步回调，不会跳转页面
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/mobile/notifyUrl")
    public String weixinReceive(HttpServletRequest request,HttpServletResponse response, Model model){
        String xml_review_result = WeixinPayCommonUtil.getXmlRequest(request);
        System.out.println("微信支付结果:"+xml_review_result);
        Map resultMap = null;
        try {
            resultMap = WeixinPayCommonUtil.doXMLParse(xml_review_result);
            System.out.println("resultMap:"+resultMap);
            if (!WeixinPayCommonUtil.isTenpaySign(resultMap)){
                return "fail";
            }else{
                System.out.println("===============付款成功==============");
                return "success";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

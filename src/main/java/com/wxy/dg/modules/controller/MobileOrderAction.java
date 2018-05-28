package com.wxy.dg.modules.controller;

import com.wxy.dg.common.model.ResponseModel;
import com.wxy.dg.common.model.SubInfo;
import com.wxy.dg.common.service.OrderService;
import com.wxy.dg.common.service.SubInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by test on 2017/2/26.
 */

@Controller
@RequestMapping("/mobile")
public class MobileOrderAction {

    @Autowired
    private OrderService orderService;
    @Autowired
    private SubInfoService subInfoService;

    /**
     * http://localhost:8080/mobile/getUserOrders/list?openId=ooIO7wfiRhp9vQ7q2BEssFQIKlu4
     * 根据用户的Openid查找用户购买的套餐的类别
     *
     * @param request
     * @param response
     * @param map
     * @return
     */

    @RequestMapping("/getUserOrders/list")
    @ResponseBody
    public ResponseModel purchaseOrder(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, String> map) {
        ResponseModel rm = new ResponseModel();
        rm.setStatus(200);
        rm.setMessage("invoke success !");
        String openId = map.get("openId");
        if (openId == null) {
            rm.setStatus(500);
            rm.setMessage("open id is null");
        }
        try {
            rm.setResult(orderService.findOrderByOpenId(openId));
        } catch (Exception e) {
            rm.setStatus(500);
            rm.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return rm;
    }

    /**
     * http://localhost:8080/mobile/getSubinfo/list?openId=ooIO7wfiRhp9vQ7q2BEssFQIKlu4&orderId=259
     * 根据用户的openid 和订单id查询用户提交的信息
     *
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping("/getSubinfo/list")
    @ResponseBody
    public ResponseModel getSubinfoList(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, String> map) {
        ResponseModel rm = new ResponseModel();
        rm.setStatus(200);
        rm.setMessage("invoke success !");
        SubInfo subInfo = new SubInfo();

        String openId = map.get("openId");
        if (openId == null) {
            rm.setStatus(500);
            rm.setMessage("open id is null");
        }
        subInfo.setOpenid(openId);
        String orderId = map.get("orderId");
        if (orderId == null) {
            rm.setStatus(500);
            rm.setMessage("order id is null");
        }

        subInfo.setOrderId(Integer.parseInt(orderId));
        try {
            rm.setResult(subInfoService.findByCondition(subInfo));
        } catch (Exception e) {
            rm.setStatus(500);
            rm.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return rm;
    }
}

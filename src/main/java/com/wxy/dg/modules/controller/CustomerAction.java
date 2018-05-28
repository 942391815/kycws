package com.wxy.dg.modules.controller;

import com.wxy.dg.common.base.BaseAction;
import com.wxy.dg.common.model.Consumer;
import com.wxy.dg.common.model.ResponseModel;
import com.wxy.dg.common.service.ConsumerService;
import com.wxy.dg.common.service.SmsCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by test on 2016/12/3.
 */

@Controller
@RequestMapping("/consumer")
public class CustomerAction extends BaseAction {
    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private SmsCodeService smsCodeService;

    @Value("${maxSmsNumber}")
    private int maxSmsNum;

    /**
     * 获取根据ID获取用户详情
     *
     * @param pk
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public Consumer getDtail(@RequestParam Long pk) {
        return consumerService.findByPK(pk);
    }

    //    http://localhost:8080/consumer/sendCode?mobile=xxx
    @RequestMapping("/sendCode")
    @ResponseBody
    public ResponseModel sendCode(@RequestParam Map<String, String> param) {
        ResponseModel model = new ResponseModel();
        model.setStatus(200);
        String mobile = param.get("mobile");
        if (mobile == null) {
            model.setStatus(500);
            model.setMessage("mobile is null");
        } else {
            int smsCountByPhone = smsCodeService.getSmsCountByPhone(mobile);
            if (smsCountByPhone >= maxSmsNum) {
                model.setStatus(501);
                model.setMessage("当天发送短信次数已经用完!");
            } else {
                smsCodeService.sendCode(mobile);
                model.setMessage("invoke ok ");
            }
        }
        return model;
    }

    /**
     * {smsCode:"",openId:"",name:"",mob:""}
     *
     * @param param
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public ResponseModel register(@RequestParam Map<String, String> param) {

        ResponseModel model = new ResponseModel();
        try {
            consumerService.register(param);
            model.setStatus(200);
            model.setMessage("invoke ok ");
        } catch (Exception e) {
            model.setStatus(500);
            e.printStackTrace();
            model.setMessage("invoke error ");
        }
        return model;
    }

}

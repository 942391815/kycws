package com.wxy.dg.modules.controller;

import com.wxy.dg.common.model.BackInfo;
import com.wxy.dg.common.model.ResponseModel;
import com.wxy.dg.common.model.SubInfo;
import com.wxy.dg.common.service.SubInfoService;
import com.wxy.dg.common.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * Created by test on 2017/1/21.
 */
@Controller
@RequestMapping("/subinfo")
public class SubinfoAction {

    @Autowired
    private SubInfoService subInfoService;


    @RequestMapping(value = "/updateSubinfo")
    @ResponseBody
    public ResponseModel toBackInfo(HttpServletResponse response, Model model, @RequestParam Map<String, String> param) {
        ResponseModel responseModel = new ResponseModel();
        String subId = param.get("subId");
        String name = param.get("articleName");
        SubInfo subInfo = new SubInfo();
        try {
            String decode = URLDecoder.decode(name, "UTF-8");
            responseModel.setStatus(200);
            subInfo.setSid(Long.parseLong(subId));
            subInfo.setName(decode);
            subInfoService.update(subInfo);
        } catch (Exception e) {
            responseModel.setStatus(500);
            responseModel.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return responseModel;
    }
}

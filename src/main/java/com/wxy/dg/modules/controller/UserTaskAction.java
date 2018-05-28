package com.wxy.dg.modules.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wxy.dg.common.base.BaseAction;
import com.wxy.dg.common.constant.Constant;
import com.wxy.dg.common.enums.BackInfoStateEnum;
import com.wxy.dg.common.enums.SubInfoEnum;
import com.wxy.dg.common.enums.SubInfoStateEnum;
import com.wxy.dg.common.model.BackInfo;
import com.wxy.dg.common.model.ResponseModel;
import com.wxy.dg.common.model.SubInfo;
import com.wxy.dg.common.service.BackInfoService;
import com.wxy.dg.common.service.SubInfoService;
import com.wxy.dg.common.service.UserTaskService;
import com.wxy.dg.common.util.Page;
import com.wxy.dg.common.util.UserUtils;
import com.wxy.dg.modules.model.Notice;
import com.wxy.dg.modules.security.SystemAuthorizingRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by test on 2016/12/3.
 */
@Controller
public class UserTaskAction extends BaseAction {
    @Autowired
    private SubInfoService subInfoService;
    @Autowired
    private BackInfoService backInfoService;

    @RequestMapping(value = "/attendance")
    public String list(@RequestParam Map<String, String> paramMap, HttpServletRequest request,
                       HttpServletResponse response, Model model) {
        int start = 0;
        int limit = 15;
        if (paramMap.get("d-148916-p") != null) {
            start = Integer.parseInt(paramMap.get("d-148916-p")) - 1;
        }

        String viewName = "";
        String subType = paramMap.get("subType");
        if (subType == null) {
            subType = "1";
        }
        viewName = getViews(viewName, subType);
        SubInfo subInfo = new SubInfo();
        subInfo.setState(0);
        subInfo.setSubType(Integer.parseInt(subType));
        subInfo.setSortColumns(" next_handle_time desc ");
        Page<SubInfo> page = new Page<SubInfo>();
        Integer totalCount = subInfoService.getTotalCount(subInfo);
        List<SubInfo> result = subInfoService.findByCondition(subInfo, start * limit, limit);
        page.setCount(totalCount);
        page.setList(result);

        model.addAttribute("page", page);
        model.addAttribute("path", "attendance");

        return "modules/" + viewName;
    }
    @ResponseBody
    @RequestMapping(value = "/claim")
    public ResponseModel update(@RequestParam Map<String, String> map, HttpServletRequest request,
                                HttpServletResponse response, Model model) {
        ResponseModel rm = new ResponseModel();
        try {
            List<SubInfo> subInfoList = JSONArray.parseArray(map.get("ids"), SubInfo.class);
            subInfoService.updateByState(subInfoList);
            rm.setStatus(Constant.RESPONSE_SUCCESS);
            rm.setMessage("invoke success");

        } catch (Exception e) {
            rm.setStatus(Constant.RESPONSE_FAIL);
            rm.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return rm;
    }

    @RequestMapping(value = "/mywork")
    public String myWorklist(@RequestParam Map<String, String> paramMap, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();
        int start = 0;
        int limit = 15;
        if (paramMap.get("d-148916-p")!=null) {
            start = Integer.parseInt(paramMap.get("d-148916-p")) - 1;
        }
        SubInfo subInfo = new SubInfo();
        subInfo.setState(SubInfoStateEnum.CLAIM_TASK.getCode());
        subInfo.setUserId(principal.getId());

        String viewName = "";
        String subType = paramMap.get("subType");
        if (subType == null) {
            subType = "1";
        }
        viewName = getViews(viewName, subType);

        Page<SubInfo> page = new Page<SubInfo>();
        subInfo.setSubType(Integer.parseInt(subType));
        Integer totalCount = subInfoService.getTotalCount(subInfo);
        List<SubInfo> result = subInfoService.findByCondition(subInfo, start * limit, limit);
        page.setCount(totalCount);
        page.setList(result);
        model.addAttribute("page", page);
        model.addAttribute("path", "mywork");
        return "modules/"+viewName;
    }


    @RequestMapping(value = "/myInHandWork")
    public String myInHandWork(@RequestParam Map<String, String> paramMap, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();
        int start = 0;
        int limit = 15;
        if (paramMap.get("d-148916-p")!=null) {
            start = Integer.parseInt(paramMap.get("d-148916-p")) - 1;
        }
        String viewName = "";
        String subType = paramMap.get("subType");
        if (subType == null) {
            subType = "1";
        }
        viewName = getViews(viewName, subType);
        BackInfo backInfo = new BackInfo();
        backInfo.setUserId(new Integer(principal.getId()));
        backInfo.setState(BackInfoStateEnum.TEMPORARY_TASK.getCode());
        backInfo.setSubType(Integer.parseInt(subType));
        backInfo.setSortColumns(" update_time desc ");

        Page<BackInfo> page = new Page<BackInfo>();
        Integer totalCount = backInfoService.getTotalCount(backInfo);
        List<BackInfo> result = backInfoService.findByCondition(backInfo, start * limit, limit);
        page.setCount(totalCount);
        page.setList(result);
        model.addAttribute("page", page);
        model.addAttribute("subType", subType);

        return "modules/myInHandWork";
    }


    @RequestMapping(value = "/mySubmitWork")
    public String mySubmitWork(@RequestParam Map<String, String> paramMap, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();
        int start = 0;
        int limit = 15;
        if (paramMap.size() != 0&&paramMap.get("d-148916-p")!=null) {
            start = Integer.parseInt(paramMap.get("d-148916-p")) - 1;
        }
        BackInfo backInfo = new BackInfo();
        backInfo.setUserId(new Integer(principal.getId()));
        backInfo.setState(BackInfoStateEnum.SUBMIT_TASK.getCode());
        backInfo.setSortColumns(" submit_time desc ");

        String viewName = "";
        String subType = paramMap.get("subType");
        if (subType == null) {
            subType = "1";
        }

        Page<BackInfo> page = new Page<BackInfo>();
        Integer totalCount = backInfoService.getTotalCount(backInfo);
        List<BackInfo> result = backInfoService.findByCondition(backInfo, start * limit, limit);
        page.setCount(totalCount);
        page.setList(result);
        model.addAttribute("page", page);
        model.addAttribute("subType", subType);
        return "modules/mySubmitWork";
    }

    private String getViews(String viewName, String subType) {
        switch (subType) {
            case "1":
                viewName="attendanceList";
                break;
            case "2":
                viewName="attendanceEnterpriseList";
                break;
            case "3":
                viewName="attendanceBrandList";
                break;
        }
        return viewName;
    }

}

package com.wxy.dg.modules.controller;

import com.alibaba.fastjson.JSONObject;
import com.wxy.dg.common.base.BaseAction;
import com.wxy.dg.common.enums.BackInfoStateEnum;
import com.wxy.dg.common.enums.BackInfoTaskEnum;
import com.wxy.dg.common.enums.SubInfoStateEnum;
import com.wxy.dg.common.model.BackInfo;
import com.wxy.dg.common.model.Order;
import com.wxy.dg.common.model.ResponseModel;
import com.wxy.dg.common.model.SubInfo;
import com.wxy.dg.common.service.BackInfoService;
import com.wxy.dg.common.service.OrderService;
import com.wxy.dg.common.service.SubInfoService;
import com.wxy.dg.common.util.DateUtils;
import com.wxy.dg.common.util.Page;
import com.wxy.dg.common.util.UserUtils;
import com.wxy.dg.modules.security.SystemAuthorizingRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by test on 2016/12/3.
 */
@Controller
@RequestMapping("/backInfo")
public class BackInfoAction extends BaseAction {
    @Autowired
    private BackInfoService backInfoService;
    @Autowired
    private SubInfoService subInfoService;

    @RequestMapping(value = "/toBackInfo")
    public String toBackInfo(HttpServletResponse response, Model model, @RequestParam Map<String, String> param) {

        String subId = param.get("subId");
        BackInfo backInfo = new BackInfo();
        BackInfo queryDto = new BackInfo();
        if (!StringUtils.isEmpty(subId)) {
            backInfo.setSubId(Integer.parseInt(subId));
            queryDto.setSubId(Integer.parseInt(subId));
        }

        SubInfo subInfo = subInfoService.findByPK(Long.parseLong(subId));
        Integer round = subInfo.getRound();
        if (round != null) {
            queryDto.setRound(round - 1);
        }
        backInfo.setRound(round);
        List<BackInfo> currentBackInfo = backInfoService.findByCondition(backInfo);
        if (currentBackInfo != null && currentBackInfo.size() > 0) {
            backInfo = currentBackInfo.get(0);
        }
        List<BackInfo> backInfos = backInfoService.findByCondition(queryDto);
        Integer count = backInfoService.getTotalCount(queryDto);
        Page<BackInfo> page = new Page<BackInfo>();
        page.setList(backInfos);
        page.setCount(count);

        model.addAttribute("page", page);
        model.addAttribute("backInfo", backInfo);
        model.addAttribute("subInfo", subInfo);
        model.addAttribute("subType", subInfo.getSubType());
        /**
         * // TODO: 2017/1/8  入口 
         */
//        return "modules/backInfoInsertNew";
        return "modules/backInfoInsert";
    }

    @RequestMapping(value = "/insert")
    public String insert(HttpServletResponse response, Model model, @RequestParam Map<String, String> param) {

        String requestJson = JSONObject.toJSONString(param);
        BackInfo backInfo = JSONObject.parseObject(requestJson, BackInfo.class);
        SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();

        backInfo.setUserId(principal.getId());

        SubInfo subInfo = subInfoService.findByPK(backInfo.getSubId());

        if (backInfo.getState() == 0) {
            backInfo.setUpdateTime(DateUtils.getDateTime());
            backInfo.setState(BackInfoStateEnum.TEMPORARY_TASK.getCode());
            subInfo.setState(SubInfoStateEnum.TODO_INFO.getCode());
        } else {
            backInfo.setSubmitTime(DateUtils.getDateTime());
            backInfo.setState(BackInfoStateEnum.SUBMIT_TASK.getCode());
            subInfo.setState(SubInfoStateEnum.PROCESSED.getCode());
            if (backInfo.getBid() == null) {
                backInfo.setUpdateTime(DateUtils.getDateTime());
            }
        }
        if (subInfo != null) {
            backInfo.setRound(subInfo.getRound());
        }
        if (backInfo.getBid() != null) {

            backInfoService.update(backInfo);
        } else {
            backInfoService.save(backInfo);
        }

        subInfoService.update(subInfo);
        model.addAttribute("backInfo", backInfo);
        if ("0".equals(backInfo.getState())) {
            return "redirect:/myInHandWork";
        } else if ("0".equals(backInfo.getState())) {
            return "redirect:/mySubmitWork";
        } else {
            return "redirect:/mywork";
        }
    }

    @RequestMapping(value = "/detail")
    public String detail(HttpServletResponse response, Model model, @RequestParam Map<String, String> param) {

        String bid = param.get("bid");
        BackInfo backInfo = backInfoService.findByPK(Long.parseLong(bid));
        Integer subId = backInfo.getSubId();
        SubInfo subInfo = subInfoService.findByPK(Long.parseLong(subId + ""));
        model.addAttribute("backInfo", backInfo);
        model.addAttribute("subInfo", subInfo);

        BackInfo queryDto = new BackInfo();
        queryDto.setSubId(Integer.parseInt(subInfo.getSid() + ""));
        queryDto.setSortColumns(" submit_time desc ");
        queryDto.setState(BackInfoStateEnum.COMPLETE_TASK.getCode());

        List<BackInfo> historyList = backInfoService.findByCondition(queryDto);
        Page<BackInfo> page = new Page<BackInfo>();
        page.setCount(historyList.size());
        page.setList(historyList);

        model.addAttribute("page", page);
        model.addAttribute("subType", param.get("subType") == null ? "1" : param.get("subType"));

        String from = param.get("from");
        if (from != null) {
            model.addAttribute("from", from);
        }

        return "modules/backInfoInsert";
    }


    @RequestMapping(value = "saveBackInfo")
    @ResponseBody
    public ResponseModel saveBackInfo(@RequestParam Map<String, String> paramMap, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {
        ResponseModel rm = new ResponseModel();
        String requestJson = JSONObject.toJSONString(paramMap);
        BackInfo backInfo = JSONObject.parseObject(requestJson, BackInfo.class);
        SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();
        backInfo.setUserId(principal.getId());
        backInfo.setUpdateTime(DateUtils.getDateTime());
        backInfo.setState(BackInfoStateEnum.TEMPORARY_TASK.getCode());
        backInfo.setSubmitTime(DateUtils.getDateTime());
        if (backInfo.getBid() == null) {
            backInfo.setUpdateTime(DateUtils.getDateTime());
        }
        if (backInfo.getBid() != null) {
            backInfoService.update(backInfo);
        } else {
            backInfoService.save(backInfo);
        }
        rm.setResult(backInfo.getBid());
        return rm;
    }
    @RequestMapping(value = "findbyPk")
    @ResponseBody
    public ResponseModel findbyPk(@RequestParam Map<String, String> paramMap, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {
        ResponseModel rm = new ResponseModel();
        BackInfo backInfo = backInfoService.findByPK(Long.parseLong(paramMap.get("pk")));
        rm.setResult(backInfo);
        return rm;
    }


    @RequestMapping(value = "/subDetail")
    public String subDetail(HttpServletResponse response, Model model, @RequestParam Map<String, String> param) {

        String bid = param.get("bid");
        BackInfo backInfo = backInfoService.findByPK(Long.parseLong(bid));
        Integer subId = backInfo.getSubId();
        SubInfo subInfo = subInfoService.findByPK(Long.parseLong(subId + ""));

        model.addAttribute("backInfo", backInfo);
        model.addAttribute("subInfo", subInfo);
        return "modules/backInfoSubmitDetail";
    }


    @RequestMapping(value = "/toAudit")
    public String toAudit(HttpServletResponse response, Model model, @RequestParam Map<String, String> param) {

        ResponseModel rm = new ResponseModel();
        int start = 0;
        int limit = 15;
        if (param.get("curPage") != null) {
            start = Integer.parseInt(param.get("curPage")) - 1;
        }

        BackInfo backInfo = new BackInfo();
        backInfo.setState(BackInfoStateEnum.SUBMIT_TASK.getCode());
        backInfo.setSortColumns(" submit_time desc ");
        String subType = param.get("subType") == null ? "1" : param.get("subType");
        backInfo.setSubType(Integer.parseInt(subType));
        List<BackInfo> backInfoList = backInfoService.findByCondition(backInfo, start * limit, limit);
        Integer totalCount = backInfoService.getTotalCount(backInfo);
        Page<BackInfo> page = new Page<BackInfo>();
        page.setCount(totalCount);
        page.setList(backInfoList);
        model.addAttribute("page", page);
        model.addAttribute("subType", subType);

        return "modules/backInfoAduit";
    }

    @RequestMapping(value = "/auditResult")
    public String auditResult(HttpServletResponse response, Model model, @RequestParam Map<String, String> param) {
        String bid = param.get("bid");
        String state = param.get("state");
        BackInfo backInfo = new BackInfo();
        backInfo.setBid(Long.parseLong(bid));
        backInfo.setState(Integer.parseInt(state));

        backInfoService.update(backInfo);
        return "redirect:/backInfo/toAudit";
    }
}

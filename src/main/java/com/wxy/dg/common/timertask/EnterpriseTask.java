//package com.wxy.dg.common.timertask;
//
//import com.wxy.dg.common.enums.BackInfoStateEnum;
//import com.wxy.dg.common.enums.RoundEnum;
//import com.wxy.dg.common.enums.SubInfoStateEnum;
//import com.wxy.dg.common.enums.SubInfoTypeEnum;
//import com.wxy.dg.common.model.BackInfo;
//import com.wxy.dg.common.model.SubInfo;
//import com.wxy.dg.common.service.BackInfoService;
//import com.wxy.dg.common.service.SmsCodeService;
//import com.wxy.dg.common.service.SubInfoService;
//import com.wxy.dg.common.util.DateUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by micheal on 2017/1/2.
// */
//
///**
// * 提交类型为企业的处理时间
// */
//
//@Service
//public class EnterpriseTask {
//    @Autowired
//    private SubInfoService subInfoService;
//    @Autowired
//    private BackInfoService backInfoService;
//    @Autowired
//    private SmsCodeService smsCodeService;
//
//    public void execute() {
//        String currntDate = DateUtils.getDate();
//        //需要发送的任务
//        List<SubInfo> subInfoList = subInfoService.getScheduleSubInfoByDate(currntDate);
//        String dateNextMonth = DateUtils.getDateNextMonth(currntDate);
//        BackInfo backInfo = new BackInfo();
//        for (SubInfo each : subInfoList) {
//            backInfo.setRound(each.getRound());
//            backInfo.setSubId(Integer.parseInt(each.getSid() + ""));
//            List<Map<String, String>> sendMessageTask = backInfoService.getSendMessageTask(backInfo);
//            for (Map<String, String> eachMap : sendMessageTask) {
//                smsCodeService.sendBackInfoDetail(eachMap);
//            }
//            backInfo.setState(BackInfoStateEnum.COMPLETE_TASK.getCode());
//
//            subInfoService.initNextRound(each.getSid() + "",dateNextMonth);
//        }
//    }
//}

//package com.wxy.dg.common.timertask;
//
//import com.wxy.dg.common.enums.RoundEnum;
//import com.wxy.dg.common.enums.SubInfoStateEnum;
//import com.wxy.dg.common.model.BackInfo;
//import com.wxy.dg.common.model.SubInfo;
//import com.wxy.dg.common.service.BackInfoService;
//import com.wxy.dg.common.service.SmsCodeService;
//import com.wxy.dg.common.service.SubInfoService;
//import com.wxy.dg.common.util.DateUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by test on 2016/12/18.
// * <p/>
// * 三天四次的定时任务，第一次
// */
//
//public class Day3Of4SequenceFirst {
//    @Autowired
//    private SubInfoService subInfoService;
//    @Autowired
//    private BackInfoService backInfoService;
//    @Autowired
//    private SmsCodeService smsCodeService;
//
//    public void execute() {
//        /**
//         * 查找昨天用户 20点之前提交的
//         */
//        SubInfo subInfo = new SubInfo();
//        subInfo.setSubTimeEnd(DateUtils.getLastEightTime(1));
//        subInfo.setRound(1);//第一轮处理
//        subInfo.setState(SubInfoStateEnum.PROCESSED.getCode());
//        subInfo.setSubType(1);
//
//        //需要发送的任务
//        List<SubInfo> subInfoList = subInfoService.findByCondition(subInfo);
//        String nextHandleTime = DateUtils.setDateAndHour(0, 17);
//        BackInfo backInfo = new BackInfo();
//        for (SubInfo each : subInfoList) {
//            backInfo.setRound(RoundEnum.FIRST.getCode());
//            backInfo.setSubId(Integer.parseInt(each.getSid() + ""));
//
//            List<Map<String, String>> sendMessageTask = backInfoService.getSendMessageTask(backInfo);
//            for (Map<String, String> eachMap : sendMessageTask) {
//                smsCodeService.sendBackInfoDetail(eachMap);
//            }
//            subInfoService.initNextRound(each.getSid() + "",nextHandleTime);
//        }
//    }
//}

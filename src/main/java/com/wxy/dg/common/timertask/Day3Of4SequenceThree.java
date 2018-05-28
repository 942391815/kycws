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
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by test on 2016/12/18.
// * 三天四次的定时任务，第二次
// * 下午五点
// */
//// TODO: 2016/12/18  放开定时任务24小时限制，只要定时任务处理，下一轮就可以开始
//
//public class Day3Of4SequenceThree {
//    @Autowired
//    private SubInfoService subInfoService;
//    @Autowired
//    private BackInfoService backInfoService;
//    @Autowired
//    private SmsCodeService smsCodeService;
//
//    public void execute(){
//        /**
//         * 查找昨天用户 20点之前提交的
//         */
//        SubInfo subInfo = new SubInfo();
//        subInfo.setSubTimeEnd(DateUtils.getLastEightTime(2));
//        subInfo.setRound(3);//第三轮处理
//        subInfo.setState(SubInfoStateEnum.PROCESSED.getCode());
//        subInfo.setSubType(1);
//        //需要发送的任务
//        List<SubInfo> subInfoList = subInfoService.findByCondition(subInfo);
//        String nextHandleTime = DateUtils.setDateAndHour(1, 18);
//
//        BackInfo backInfo = new BackInfo();
//        for (SubInfo each:subInfoList){
//            backInfo.setRound(RoundEnum.THIRD.getCode());
//            backInfo.setSubId(Integer.parseInt(each.getSid()+""));
//
//            List<Map<String, String>> sendMessageTask = backInfoService.getSendMessageTask(backInfo);
//
//            for (Map<String, String> eachMap : sendMessageTask) {
//                smsCodeService.sendBackInfoDetail(eachMap);
//            }
//            subInfoService.initNextRound(each.getSid()+"",nextHandleTime);
//        }
//
//    }
//}

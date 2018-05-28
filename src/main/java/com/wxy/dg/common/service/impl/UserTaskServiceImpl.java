package com.wxy.dg.common.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wxy.dg.common.constant.Constant;
import com.wxy.dg.common.enums.RoundEnum;
import com.wxy.dg.common.enums.SubInfoEnum;
import com.wxy.dg.common.enums.SubInfoTypeEnum;
import com.wxy.dg.common.model.Consumer;
import com.wxy.dg.common.model.ConsumerOrder;
import com.wxy.dg.common.model.SubInfo;
import com.wxy.dg.common.service.ConsumerOrderService;
import com.wxy.dg.common.service.ConsumerService;
import com.wxy.dg.common.service.SubInfoService;
import com.wxy.dg.common.service.UserTaskService;
import com.wxy.dg.common.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by test on 2016/12/3.
 */

@Service
@Transactional(readOnly = true)
public class UserTaskServiceImpl implements UserTaskService {

    @Autowired
    private SubInfoService subInfoService;
    @Autowired
    private ConsumerService consumerService;
    @Autowired
    private ConsumerOrderService consumerOrderService;
    @Autowired
    private EnterpriseService enterpriseService;


    /**
     * 用户提交文章
     * 此方法需要事务  consumer表中数量减去1
     * subinfo 表中需要插入一条记录 根据微信ID关联
     *
     * @param map
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Override
    public SubInfoEnum submitTask(Map<String, String> map) {

        String requestJson = JSONObject.toJSONString(map);
        try {
            requestJson = URLDecoder.decode(requestJson, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        SubInfo subInfo = JSONObject.parseObject(requestJson, SubInfo.class);
        if (subInfo.getSubType() == null) {

            //提交类型为空，提交失败
            return SubInfoEnum.ERROR;
        }

        SubInfoTypeEnum subInfoTypeEnum = SubInfoTypeEnum.getSubInfoEnumByCode(subInfo.getSubType());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date subTime = new Date();
        subInfo.setSubTime(sdf.format(subTime).toString());

        switch (subInfoTypeEnum) {

            //自媒体
            case SELF_MEDIA:
                subInfo.setNextHandleTime(subInfoService.getHandleTimeBySubTime(subTime));
                break;

            //企业
            case ENTERPRISE:
                if(!isQualifiedEnterpriseSubmitTime(subInfo.getHandleTime())){
                    return SubInfoEnum.EXCEED_MAX;
                }
                String handleDay = map.get("handleDay");
                String handleTime = DateUtils.getDayOfMonth(Integer.parseInt(handleDay));
                subInfo.setHandleTime(handleTime);
                subInfo.setNextHandleTime(DateUtils.getDateNextMonth(handleTime));
                break;

            //品牌
            case BRAND:
                break;
        }


        String openId = map.get("openid");
        if (!StringUtils.isEmpty(openId)) {
            Consumer consumer = new Consumer();
            consumer.setOpenId(openId);
            List<Consumer> list = consumerService.findByCondition(consumer);
            if (list != null && list.size() > 0) {
                consumer = list.get(0);
                //查询用户购买的套餐
                Integer orderId = subInfo.getOrderId();
                if (orderId == null) {
                    return SubInfoEnum.UNKNOW_PACKAGE;
                }
                ConsumerOrder consumerOrder = new ConsumerOrder();
                consumerOrder.setOrderId(orderId);
                consumerOrder.setOpenId(openId);

                List<ConsumerOrder> consumerOrderList = consumerOrderService.findByCondition(consumerOrder);
                if (consumerOrderList != null && consumerOrderList.size() > 0) {
                    consumerOrder = consumerOrderList.get(0);
                }
                if (consumerOrder.getCount() < 1) {
                    return SubInfoEnum.INSUFFICIENT;
                }
                subInfo.setOpenid(openId);
                subInfo.setRound(RoundEnum.FIRST.getCode());

                subInfoService.save(subInfo);
                consumerService.update(consumer);

                consumerOrder.setCount(consumerOrder.getCount() - 1);

                consumerOrderService.update(consumerOrder);

                return SubInfoEnum.SUCCESS;
            }
        } else {
            return SubInfoEnum.NOT_EXIST;
        }
        return SubInfoEnum.NOT_EXIST;
    }

    @Override
    public boolean isQualifiedEnterpriseSubmitTime(String date) {

        if(enterpriseService.getSubInfoCountByHandleTime(date)<Constant.MAX_ENTERPRISE_EACHDAY){
            return true;
        }
        return false;
    }
}

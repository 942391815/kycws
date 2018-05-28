package com.wxy.dg.modules.controller;

import com.alibaba.fastjson.JSONObject;
import com.wxy.dg.common.base.BaseAction;
import com.wxy.dg.common.model.Order;
import com.wxy.dg.common.service.OrderService;
import com.wxy.dg.common.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by test on 2016/12/3.
 */

//// TODO: 2016/12/17 用户可以购买多个套餐 
@Controller
@RequestMapping("/order")
public class OrderListAction extends BaseAction{
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/list")
    public String OrderList(@RequestParam Map<String, String> paramMap,HttpServletResponse response,Model model) {

        int start = 0;
        int limit = 15;
        if(paramMap.size()!=0){
            start = Integer.parseInt(paramMap.get("d-148916-p"))-1;
        }

        Order order = new Order();
        order.setYn(1);
        order.setSortColumns("update_time desc");
        List<Order> list = orderService.findByCondition(order,start,limit);
        Integer totalCount = orderService.getTotalCount(order);
        Page<Order> result = new Page<Order>();
        result.setCount(totalCount);
        result.setList(list);
        model.addAttribute("page",result);
        return "modules/order";
    }
    @RequestMapping(value = "/detail")
    public String OrderDetail(HttpServletResponse response,Model model,@RequestParam String id) {
        if(!StringUtils.isEmpty(id)&&!"".equals(id)){
            Order order = orderService.findByPK(Long.parseLong(id));
            model.addAttribute("order",order);
        }else{
            model.addAttribute("order",new Order());
            return "modules/orderInsert";
        }
        return "modules/orderManage";
    }
    @RequestMapping(value = "/update")
    public String update(HttpServletResponse response,Model model, @RequestParam  Map param) {
        String jsonString = JSONObject.toJSONString(param);
        Order order = JSONObject.parseObject(jsonString, Order.class);
        orderService.update(order);
        return "redirect:/order/list";
    }
    @RequestMapping(value = "/deleteOrder")
    public String delete(HttpServletResponse response,Model model, @RequestParam Long id) {
        orderService.delete(id);
        return "redirect:/order/list";
    }

    @RequestMapping(value = "/insert")
    public String insert(HttpServletResponse response,Model model, @RequestParam Map<String,String> param) {
        String jsonString = JSONObject.toJSONString(param);
        Order order = JSONObject.parseObject(jsonString, Order.class);
        orderService.save(order);
        return "redirect:/order/list";
    }
}

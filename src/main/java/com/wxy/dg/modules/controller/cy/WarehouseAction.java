package com.wxy.dg.modules.controller.cy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Micheal on 2018/6/9.
 */
@Controller
@RequestMapping(value="/warehouse")
public class WarehouseAction {

    @RequestMapping(value = "/index")
    public String index(HttpServletResponse response, Model model, @RequestParam Map<String, String> param) {
        return "/modules/cw/warehouse";
    }
}

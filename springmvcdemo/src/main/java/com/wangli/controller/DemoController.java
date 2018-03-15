package com.wangli.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller()
public class DemoController {
    @RequestMapping("demo")
    public ModelAndView toDemoPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("demo");
        return mv;
    }

}

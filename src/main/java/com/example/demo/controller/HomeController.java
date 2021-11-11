package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public ModelAndView home(HttpSession session)
    {
        ModelAndView mv = new ModelAndView("home");
        String anant = "ANANT";
        mv.addObject("namer", anant);
        String aryan = "ARYAN";
        mv.addObject("name", aryan);
        return mv;
    }
}

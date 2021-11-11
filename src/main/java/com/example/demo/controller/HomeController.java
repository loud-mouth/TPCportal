package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    public boolean logged_in(HttpSession session)
    {
        if(session.getAttribute("student") == null && session.getAttribute("company") == null)
        {
            return false;
        }
        return true;
    }

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public ModelAndView home(HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if(logged_in(session))
        {
            mv.addObject("error", "You are already logged in. Redirecting to dashboard...\n");
            mv.setViewName("dashboard");
            return mv;
        }
        mv.setViewName("home");
        String anant = "ANANT";
        mv.addObject("namer", anant);
        String aryan = "ARYAN";
        mv.addObject("name", aryan);
        return mv;
    }
}

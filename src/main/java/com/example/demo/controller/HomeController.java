package com.example.demo.controller;

import com.example.demo.dao.JobProfileRepository;
import com.example.demo.models.Company;
import com.example.demo.models.JobProfile;
import com.example.demo.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    JobProfileRepository jobprofilerepo;

    @Autowired
    LoginModule loginmodule;

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public ModelAndView home(HttpSession session)
    {
        ModelAndView mv = loginmodule.confirm_login_as(session, "notLoggedIn");
        if(!mv.isEmpty())
        {
            mv.addObject("error", "You are already logged in. Redirecting to dashboard...\n");
            return mv;
        }
        mv = loginmodule.redirect("home", session);
        return mv;
    }
}

package com.example.demo.controller;

import com.example.demo.dao.JobProfileRepository;
import com.example.demo.dao.newsletterRepository;
import com.example.demo.models.Company;
import com.example.demo.models.JobProfile;
import com.example.demo.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.example.demo.models.test;
import javax.servlet.http.HttpSession;
import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


@Transactional
@Controller
public class HomeController {

    @Autowired
    JobProfileRepository jobprofilerepo;

    @Autowired
    LoginModule loginmodule;

    @Autowired
    newsletterRepository newsletterrepo;


    @Autowired
    private JavaMailSender javaMailSender;

    @PostMapping("/addEmail")
    public ModelAndView addEmail(@RequestParam("emailId") String emailId, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if(session.getAttribute("student") != null)
        {
            mv = loginmodule.redirect("student", session);
        }
        else if(session.getAttribute("company") != null)
        {
            mv = loginmodule.redirect("company", session);
        }
        else
        {
            mv = loginmodule.redirect("home", session);
        }

        try{
            int done = newsletterrepo.addEmailId(emailId);
            assert(done != -1);
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(emailId);
            System.out.println("sending mail to " + emailId);
            msg.setSubject("Congratulations! -from Team TPCportal @ IIT BHU");
            msg.setText("You have been added to our mailing list!");
            javaMailSender.send(msg);

        }
        catch (Exception e)
        {
            mv.addObject("error", "BAD KEY : Invalid Email Address / Address already in database");

        }
        return mv;
    }


    @RequestMapping(value = {"/home", "/"}, method = RequestMethod.GET)
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

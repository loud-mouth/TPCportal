package com.example.demo.controller;

import com.example.demo.dao.InterviewerRepository;
import com.example.demo.dao.JobProfileRepository;
import com.example.demo.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.example.demo.models.test;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.naming.CompositeName;
import javax.servlet.http.HttpSession;

import com.example.demo.dao.CompanyRepository;

import java.sql.Date;
import java.util.List;

@Transactional
@Controller
public class CompanyController {

    @Autowired
    CompanyRepository companyrepo;

    @Autowired
    JobProfileRepository jobprofilerepo;

    @Autowired
    InterviewerRepository interviewerrepo;

    @Autowired
    LoginModule loginmodule;


    @PostMapping("/company/addInterviewer")
    public ModelAndView addInterviewerPost(@ModelAttribute("Interviewer") Interviewer interviewer,  HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        test tester = new test();
        if(!tester.validatePhoneNumber(interviewer.getPhoneNumber()))
        {

            Company company = (Company)session.getAttribute("company");
            mv.addObject("error", "Invalid Phone Number");
            mv.addObject("companyId", company.getCompanyId());
            mv.addObject("company", company);
            mv.setViewName("addInterviewer");
            return mv;
        }

        System.out.println(interviewer.getName() + " || " + interviewer.getCompanyId() + " || " + interviewer.getPhoneNumber());
        String name = interviewer.getName();

        if(!tester.validateName(name))
        {
            System.out.println("REDIRECTING __ BAD KEY");
            mv.addObject("error", "Invalid Name");
            Company company = (Company)session.getAttribute("company");
            mv.addObject("companyId", company.getCompanyId());
            mv.addObject("company", company);

            mv.setViewName("addInterviewer");
            return mv;
        }

        interviewer = interviewerrepo.saveInterviewer(interviewer);
        mv = loginmodule.redirect("company", session);
        if(interviewer.getInterviewerId() == -1)
        {
            mv.addObject("error", "Interviewer Details Could Not be Added.");
        }
        else
        {
            mv.addObject("message", "Interviewer Details Added Successfully");
        }
        return mv;
    }

    @GetMapping("/company/addInterviewer")
    public ModelAndView addInterviewerGet(HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        mv = loginmodule.confirm_login_as(session, "company");
        if(!mv.isEmpty())
        {
            return mv;
        }

        Company company = (Company)session.getAttribute("company");
        mv.addObject("companyId", company.getCompanyId());
        mv.addObject("company", company);
        mv.setViewName("addInterviewer");
        return mv;
    }

    @PostMapping("/company/logout")
    public ModelAndView Logoutprocess(HttpSession session)
    {
        ModelAndView mv = loginmodule.confirm_login_as(session, "company");
        if(!mv.isEmpty())
        {
            return mv;
        }
        mv = loginmodule.redirect("home", session);
        mv.addObject("message", "Logged Out Successfully.");
        return mv;
    }

    //VALIDATE EMAIL ID
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    @PostMapping("/company/login")
    public ModelAndView Loginprocess(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session
    )
    {

        ModelAndView mv = new ModelAndView();
        Company maybe = new Company();
        maybe = companyrepo.getCompanyByEmailId(username);

        if(!isValidEmailAddress(username))
        {
            mv = loginmodule.redirect("home", session);
            mv.addObject("error", "BAD KEY : Company Email Id is not valid");
            return mv;
        }

        if(maybe.getCompanyId() == -1)
        {
            System.out.println("LALALALA\n");
            mv.setViewName("home");
            mv.addObject("error", "The entered Company Email-Id does not exist in the database.\n");
            return mv;
        }

        if(!maybe.getPassword().equals(password))
        {
            System.out.println(maybe.getPassword() + " || " + password + " || " + "BAD PASSWORD\n");
            mv.setViewName("home");
            mv.addObject("error", "The password is incorrect");
            return mv;
        }

        session.setAttribute("company", maybe);
        mv = loginmodule.redirect("company", session);
        return mv;
    }

    @GetMapping("/company/register")
    public ModelAndView companyRegister(HttpSession session)
    {
        ModelAndView mv = loginmodule.confirm_login_as(session, "notLoggedIn");
        if(!mv.isEmpty())
        {
            return mv;
        }
        mv.setViewName("companyRegister");
        return mv;
    }

    @PostMapping("/company/register")
    public ModelAndView doCompanyRegister(
            @RequestParam("name") String name,
            @RequestParam("repcontact") String repcontact,
            @RequestParam("emailid") String emailid,
            @RequestParam("password") String password,
            @RequestParam("headoffice") String headoffice,
            @RequestParam("website") String website,
            HttpSession session
    )
    {

        test tester = new test();

        ModelAndView mv = new ModelAndView();

        if(!isValidEmailAddress(emailid))
        {
            mv.setViewName("companyRegister");
            mv.addObject("error", "BAD KEY : Company Email is not valid");
            return mv;
        }

        if(!tester.validatePhoneNumber(repcontact))
        {
            mv.setViewName("companyRegister");
            mv.addObject("error", "BAD KEY : Phone number is not valid");
            return mv;
        }

        if(!tester.validateURL(website))
        {
            mv.setViewName("companyRegister");
            mv.addObject("error", "BAD KEY : URL is not valid");
            return mv;
        }

        Company company = new Company();
        company.setName(name);
        company.setRepContact(repcontact);
        company.setPassword(password);
        company.setEmailId(emailid);
        company.setWebsite(website);
        company.setHeadOffice(headoffice);

        Company savedCopy = new Company();
        savedCopy = companyrepo.saveCompany(company);

        if(savedCopy.getCompanyId() == -1)
        {
            mv.addObject("error", "Details filled incorrectly\n");
            mv.setViewName("companyRegister");
            return mv;
        }

        mv.addObject("message", "You have been successfully registered");
        mv.setViewName("home");
        return mv;
    }

}

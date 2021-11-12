package com.example.demo.controller;

import com.example.demo.dao.InterviewerRepository;
import com.example.demo.dao.JobProfileRepository;
import com.example.demo.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.CompositeName;
import javax.servlet.http.HttpSession;

import com.example.demo.dao.CompanyRepository;

import java.sql.Date;
import java.util.List;

@Controller
public class CompanyController {

    @Autowired
    CompanyRepository companyrepo;

    @Autowired
    JobProfileRepository jobprofilerepo;

    @Autowired
    InterviewerRepository interviewerrepo;

    public boolean logged_in(HttpSession session)
    {
        if(session.getAttribute("student") == null && session.getAttribute("company") == null)
        {
            return false;
        }
        return true;
    }

    @PostMapping("/company/addInterviewer")
    public ModelAndView addInterviewerPost(@ModelAttribute("Interviewer") Interviewer interviewer,  HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        System.out.println(interviewer.getName() + " || " + interviewer.getCompanyId() + " || " + interviewer.getPhoneNumber());
        interviewer = interviewerrepo.saveInterviewer(interviewer);
        if(interviewer.getInterviewerId() == -1)
        {
            mv.addObject("error", "Interviewer Details Could Not be Added.");
        }
        else
        {
            mv.addObject("message", "Interviewer Details Added Successfully");
        }
        Company company= (Company)session.getAttribute("company");
        List<JobProfile> activejobs = jobprofilerepo.getJobProfilesByCompanyId(company.getCompanyId());
        mv.addObject("activejobs", activejobs);
        mv.setViewName("dashboard-company");
        return mv;
    }

    @GetMapping("/company/addInterviewer")
    public ModelAndView addInterviewerGet(HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if(!logged_in(session))
        {
            mv.setViewName("home");
            return mv;
        }
        else if(session.getAttribute("student") != null)
        {
            Student student = (Student)session.getAttribute("student");
            mv.addObject("student", student);
            List<JobProfile> availablejobs = jobprofilerepo.getJobProfilesAvailableToStudent(student);
            mv.addObject("availablejobs", availablejobs);
            mv.setViewName("dashboard");
            return mv;
        }

        Company company = (Company)session.getAttribute("company");
        mv.addObject("companyId", company.getCompanyId());
        mv.setViewName("addInterviewer");
        return mv;
    }

    @PostMapping("/company/logout")
    public ModelAndView Logoutprocess(HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if(!logged_in(session))
        {
            mv.setViewName("home");
            return mv;
        }
        else if(session.getAttribute("student") != null)
        {
            Student student = (Student)session.getAttribute("student");
            mv.addObject("student", student);
            List<JobProfile> availablejobs = jobprofilerepo.getJobProfilesAvailableToStudent(student);
            mv.addObject("availablejobs", availablejobs);
            mv.setViewName("dashboard");
            return mv;
        }
        session.removeAttribute("company");
        session.invalidate();
        mv.addObject("message", "Logged Out Successfully.");
        mv.setViewName("home");
        return mv;
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
        mv.addObject("company", maybe);
        List<JobProfile> activejobs = jobprofilerepo.getJobProfilesByCompanyId(maybe.getCompanyId());
        System.out.println("Number of active jobs for this company are " + activejobs.size());
        mv.addObject("activejobs", activejobs);
        mv.setViewName("dashboard-company");
        return mv;
    }

    @GetMapping("/company/register")
    public ModelAndView companyRegister(HttpSession session)
    {
        ModelAndView mv = new ModelAndView();

        if(logged_in(session) == true) {
            mv.addObject("error", "You are already logged in. Log out before registering.");
            if(session.getAttribute("student") != null)
            {
                Student student = (Student)session.getAttribute("student");
                mv.addObject("student", student);
                List<JobProfile> availablejobs = jobprofilerepo.getJobProfilesAvailableToStudent(student);
                mv.addObject("availablejobs", availablejobs);
                mv.setViewName("dashboard");
            }
            else
            {
                Company company= (Company)session.getAttribute("company");
                List<JobProfile> activejobs = jobprofilerepo.getJobProfilesByCompanyId(company.getCompanyId());
                mv.addObject("activejobs", activejobs);
                mv.setViewName("dashboard-company");
            }

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
        ModelAndView mv = new ModelAndView();
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

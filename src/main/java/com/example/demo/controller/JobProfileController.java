package com.example.demo.controller;

import com.example.demo.dao.JobProfileRepository;
import com.example.demo.models.Guardian;
import com.example.demo.models.JobProfile;
import com.example.demo.models.Student;
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

import com.example.demo.models.Company;
import com.example.demo.dao.CompanyRepository;

import java.sql.Date;

@Controller
public class JobProfileController {
    public boolean logged_in_as_company(HttpSession session)
    {
        return session.getAttribute("student") == null && session.getAttribute("company") != null;
    }

    @Autowired
    JobProfileRepository jobprofilerepo;

    @GetMapping("/company/makeJobProfile")
    public ModelAndView makeJobProfile(HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if(!logged_in_as_company(session))
        {
            mv.addObject("error", "You are not authorised to access that page.");
            mv.setViewName("home");
            return mv;
        }
        mv.setViewName("makeJobProfile");
        Company company = new Company();
        company = (Company)(session.getAttribute("company"));
        mv.addObject("companyId", company.getCompanyId());
        return mv;
    }

    @PostMapping("/company/makeJobProfile")
    public ModelAndView JobProfileRegister(
            @ModelAttribute("JobProfile") JobProfile jobProfile,
            HttpSession session
    )
    {
        ModelAndView mv = new ModelAndView();
        JobProfile savedCopy = jobprofilerepo.saveJobProfile(jobProfile);
        if(savedCopy.getCompanyId() == -1)
        {
            mv.addObject("error", "incorrect details were provided");
            mv.setViewName("makeJobProfile");
            return mv;
        }
        mv.addObject("message", "Job Profile for " + savedCopy.getPosition() + " has been successfully added!");
        mv.setViewName("dashboard-company");
        return mv;
    }
}

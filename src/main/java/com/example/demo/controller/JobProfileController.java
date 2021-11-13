package com.example.demo.controller;

import com.example.demo.dao.JobProfileRepository;
import com.example.demo.dao.PptRepository;
import com.example.demo.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.CompositeName;
import javax.servlet.http.HttpSession;

import com.example.demo.models.Company;
import com.example.demo.dao.CompanyRepository;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

@Controller
public class JobProfileController {

    @Autowired
    JobProfileRepository jobprofilerepo;

    @Autowired
    LoginModule loginmodule;

    @Autowired
    PptRepository pptrepo;

//    @Autowired


    @PostMapping("/company/shortlist/{id}")
    public ModelAndView showShortlist(@PathVariable("id") int id, HttpSession session)
    {
        System.out.println("Accessing job profile with id = " + id);
        JobProfile jobProfile = jobprofilerepo.getJobProfilesByJobProfileId(id);
        ModelAndView mv = new ModelAndView();
//        Shortlist shortList = shortlistrepo.getShortListByJobProfile(jobProfile);
        mv.addObject("jobProfile", jobProfile);
//        mv.addObject("shortlist", shortlist);
        mv.setViewName("shortlist");
        return mv;
    }

    @GetMapping("/company/makeJobProfile")
    public ModelAndView makeJobProfile(HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        ModelAndView checkCreds = loginmodule.confirm_login_as(session, "company");
        if(!checkCreds.isEmpty())
        {
            return checkCreds;
        }

        mv.setViewName("makeJobProfile");
        Company company = new Company();
        company = (Company)(session.getAttribute("company"));
        mv.addObject("companyId", company.getCompanyId());
        return mv;
    }

    private static final String SEARCH_TYPES = "searchTypes";

    @ModelAttribute()
    public void initValues(Model model) {
        model.addAttribute(SEARCH_TYPES, Arrays.asList("cse", "bme"));
    }

    @PostMapping("/company/makeJobProfile")
    public ModelAndView JobProfileRegister(
            @ModelAttribute("JobProfile") JobProfile jobProfile,
            @ModelAttribute("PPT") PPT ppt,
            @RequestParam List<String> tickedBranches,
            HttpSession session
    )
    {
        for(String field : tickedBranches)
        {
            System.out.println("batman" + field);
        }
        ModelAndView mv = new ModelAndView();
        JobProfile savedCopy = jobprofilerepo.saveJobProfile(jobProfile);
        System.out.println("New Job Profile created with id "+savedCopy.getJobProfileId()+" and position "+savedCopy.getPosition());
        if(savedCopy.getCompanyId() == -1)
        {
            mv.addObject("error", "incorrect details were provided");
            mv.setViewName("makeJobProfile");
            return mv;
        }
        ppt.setJobProfileId(savedCopy.getJobProfileId());
        PPT savedPPT = pptrepo.savePPT(ppt);
        if(savedPPT.getJobProfileId() == -1)
        {
            mv.addObject("error", "incorrect details were provided");
            mv.setViewName("makeJobProfile");
            return mv;
        }
        mv = loginmodule.redirect("company", session);
        mv.addObject("message", "Job Profile for " + savedCopy.getPosition() + " has been successfully added!");
        return mv;
    }
}

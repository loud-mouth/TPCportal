package com.example.demo.controller;

import com.example.demo.dao.*;
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

import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
public class JobProfileController {

    @Autowired
    JobProfileRepository jobprofilerepo;

    @Autowired
    LoginModule loginmodule;

    @Autowired
    PptRepository pptrepo;

    @Autowired
    EligibleBranchesRepository eligiblebranchesrepo;

    @Autowired
    C1Repository c1repo;

    @Autowired
    ShortlistRepository shortlistrepo;

    @Autowired
    CodingTestRepository codingtestrepo;

    @Autowired
    InterviewerRepository interviewerrepo;

    private static final String SEARCH_TYPES = "searchTypes";


    @PostMapping("/company/newInterviewRound/{id}")
    public ModelAndView createInterviewRound(@PathVariable("id") int id, HttpSession session)
    {
        System.out.println("Creating new interview round....");
        ModelAndView mv = new ModelAndView();
        JobProfile jobProfile = jobprofilerepo.getJobProfilesByJobProfileId(id);
        mv.addObject("jobProfile", jobProfile);
        List<C1> c1 = c1repo.getC1(jobProfile);
        mv.addObject("c1", c1);
        Company company = (Company)(session.getAttribute("company"));
        List<Interviewer> interviewers = interviewerrepo.getAll(company);
        mv.addObject("interviewers", interviewers);
        mv.setViewName("makeInterviewRound");
        return mv;
    }

    @PostMapping("/company/newCodingRound/{id}")
    public ModelAndView createCodingRound(@PathVariable("id") int id, HttpSession session)
    {
        System.out.println("Let's create a Round");
        System.out.println("Creating new coding round....");
        ModelAndView mv = new ModelAndView();
        JobProfile jobProfile = jobprofilerepo.getJobProfilesByJobProfileId(id);
        mv.addObject("jobProfile", jobProfile);
        mv.setViewName("makeCodingRound");
        return mv;
    }

    @PostMapping("/company/saveInterviewRound")
    public ModelAndView saveInterviewRound(@ModelAttribute("Interviewer") Interviewer interviewer, int target, HttpSession session)
    {
        System.out.println("saving new interview round....");
        ModelAndView mv = new ModelAndView();
        mv = loginmodule.redirect("company", session);
//        JobProfile jobProfile = jobprofilerepo.getJobProfilesByJobProfileId();
//        List<C1> c1 = c1repo.getC1(jobProfile); //current shortlist
//        //student whose marks are >= will be inserted into new shortlist entry
//        System.out.println("DEATH....");
//        jobprofilerepo.increaseRound(jobProfile);
//        session.setAttribute(jobprofilerepo.getJobProfilesByJobProfileId(jobProfile.getJobProfileId()));
//        int saved = codingtestrepo.saveCodingTest(codingTest);
//        if(saved == -1)
//        {
//            mv.addObject("error", "Round Could Not Be Scheduled");
//        }
//        else
//        {
//            mv.addObject("message", "Round Scheduled Successfully");
//            for(C1 entry : c1)
//            {
//                if(entry.getShortlist().getScore() >= target)
//                {
//                    Shortlist shortlist = new Shortlist();
//                    shortlist.setJobProfileId(entry.getShortlist().getJobProfileId());
//                    shortlist.setRoundNumber(entry.getShortlist().getRoundNumber()+1);
//                    shortlist.setStudentId(entry.getShortlist().getStudentId());
//                    shortlist.setResumeLink(entry.getShortlist().getResumeLink());
//                    shortlist.setScore(0);
//                    shortlistrepo.saveShortlist(shortlist);
//                }
//            }
//            System.out.println("Successfully updated shortlists");
//        }

        return mv;
    }

    @PostMapping("/company/saveCodingRound")
    public ModelAndView saveCodingRound(@ModelAttribute("CodingTest") CodingTest codingTest, int target, HttpSession session)
    {
        System.out.println("saving new coding round....");
        ModelAndView mv = new ModelAndView();
        mv = loginmodule.redirect("company", session);
        JobProfile jobProfile = jobprofilerepo.getJobProfilesByJobProfileId(codingTest.getJobProfileId());
        List<C1> c1 = c1repo.getC1(jobProfile); //current shortlist
        //student whose marks are >= will be inserted into new shortlist entry
        System.out.println("DEATH....");
        jobprofilerepo.increaseRound(jobProfile);
//        session.setAttribute(jobprofilerepo.getJobProfilesByJobProfileId(jobProfile.getJobProfileId()));

        int saved = codingtestrepo.saveCodingTest(codingTest);
        if(saved == -1)
        {
            mv.addObject("error", "Round Could Not Be Scheduled");
        }
        else
        {
            mv.addObject("message", "Round Scheduled Successfully");
            for(C1 entry : c1)
            {
                if(entry.getShortlist().getScore() >= target)
                {
                    Shortlist shortlist = new Shortlist();
                    shortlist.setJobProfileId(entry.getShortlist().getJobProfileId());
                    shortlist.setRoundNumber(entry.getShortlist().getRoundNumber()+1);
                    shortlist.setStudentId(entry.getShortlist().getStudentId());
                    shortlist.setResumeLink(entry.getShortlist().getResumeLink());
                    shortlist.setScore(0);
                    shortlistrepo.saveShortlist(shortlist);
                }
            }
            System.out.println("Successfully updated shortlists");
        }

        return mv;
    }

    @PostMapping("/company/shortlist/{id}")
    public ModelAndView showShortlist(@PathVariable("id") int id, HttpSession session)
    {
        System.out.println("Accessing job profile with id = " + id);
        JobProfile jobProfile = jobprofilerepo.getJobProfilesByJobProfileId(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("jobProfile", jobProfile);
        List<C1> c1 = c1repo.getC1(jobProfile);
        mv.addObject("c1", c1);
        session.setAttribute("intoTheForm", c1);
        if(c1 != null)
        {
            System.out.println("Students found = " + c1.size());
        }
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




    @ModelAttribute()
    public void initValues(Model model) {
        model.addAttribute(SEARCH_TYPES, Arrays.asList("Chemical Engineering and Technology", "Civil Engineering", "Computer Science and Engineering",
                "Electrical Engineering", "Electronics Engineering", "Mechanical Engineering", "Metallurgical Engineering",
                "Mining Engineering", "Pharmaceutical Engineering & Technology", "Industrial Chemistry",
                "Mathematics and Computing", "Applied Physics", "Biochemical Engineering", "Biomedical Engineering",
                "Materials Science and Technology"));
    }

    @PostMapping("/company/makeJobProfile")
    public ModelAndView JobProfileRegister(
            @ModelAttribute("JobProfile") JobProfile jobProfile,
            @ModelAttribute("PPT") PPT ppt,
            @RequestParam List<String> tickedBranches,
            HttpSession session
    )
    {
        HashMap<String, String> branchCode = new HashMap<String, String>();
        branchCode.put("Chemical Engineering and Technology", "che");
        branchCode.put("Civil Engineering", "civ");
        branchCode.put("Computer Science and Engineering", "cse");
        branchCode.put("Electrical Engineering", "ee");
        branchCode.put("Electronics Engineering", "ece");
        branchCode.put("Mechanical Engineering", "mec");
        branchCode.put("Metallurgical Engineering", "met");
        branchCode.put("Mining Engineering", "min");
        branchCode.put("Pharmaceutical Engineering & Technology", "phe");
        branchCode.put("Industrial Chemistry", "apc");
        branchCode.put("Mathematics and Computing", "mat");
        branchCode.put("Applied Physics", "app");
        branchCode.put("Biochemical Engineering", "bce");
        branchCode.put("Biomedical Engineering", "bme");
        branchCode.put("Materials Science and Technology", "mst");



        ModelAndView mv = new ModelAndView();
        int savedCopy = jobprofilerepo.saveJobProfile(jobProfile);
        System.out.println("New Job Profile created with id "+jobProfile.getJobProfileId()+" and position "+jobProfile.getPosition());
        if(savedCopy == -1)
        {
            mv.addObject("error", "incorrect details were provided");
            mv.setViewName("makeJobProfile");
            return mv;
        }
        jobProfile.setJobProfileId(savedCopy);
        for(String field : tickedBranches)
        {
            System.out.println("available for field " + field);
            int savedBranch = eligiblebranchesrepo.saveEligibility(branchCode.get(field), jobProfile);
        }
        ppt.setJobProfileId(savedCopy);
        PPT savedPPT = pptrepo.savePPT(ppt);
        if(savedPPT.getJobProfileId() == -1)
        {
            mv.addObject("error", "incorrect details were provided");
            mv.setViewName("makeJobProfile");
            return mv;
        }
        mv = loginmodule.redirect("company", session);
        mv.addObject("message", "Job Profile for " + jobProfile.getPosition() + " has been successfully added!");
        return mv;
    }
}

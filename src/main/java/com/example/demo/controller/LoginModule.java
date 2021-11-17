package com.example.demo.controller;

import com.example.demo.dao.*;
import com.example.demo.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Component
public class LoginModule {

    @Autowired(required = true)
    JobProfileRepository jobprofilerepo;

    @Autowired
    S1Repository s1repo;

    @Autowired
    C1Repository c1repo;

    @Autowired
    S2Repository s2repo;

    @Autowired
    S3Repository s3repo;

    public ModelAndView redirect(String key, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if(Objects.equals(key, "student"))
        {
            Student student = (Student)session.getAttribute("student");
            mv.addObject("studentName", student.getName());
            mv.addObject("student", student);
            List<JobProfile> availablejobs = jobprofilerepo.getJobProfilesAvailableToStudent(student);
            List<S1> s1 = s1repo.getS1(student);
            List<S1> s2 = s1repo.getS2(student);

            mv.addObject("s1", s1);
            mv.addObject("s2", s2);
            mv.addObject("availablejobs", availablejobs);

            List<S2> allActiveCodingTests = s2repo.getActiveCodingTests(student);
            mv.addObject("allActiveCodingTests", allActiveCodingTests);

            List<S3> allActiveInterviewTests = s3repo.getActiveInterviewTests(student);
            mv.addObject("allActiveInterviewTests", allActiveInterviewTests);

            System.out.println("Number of Active Coding Tests "+allActiveCodingTests.size());

            mv.setViewName("dashboard");

        }
        else if(Objects.equals(key, "company"))
        {
            Company company= (Company)session.getAttribute("company");
            List<JobProfile> activejobs = jobprofilerepo.getJobProfilesByCompanyId(company.getCompanyId());
            mv.addObject("activejobs", activejobs);
            mv.addObject("company", company);
            mv.setViewName("dashboard-company");
        }
        else
        {
            mv.setViewName("home");
            session.removeAttribute("student");
            session.removeAttribute("company");
            session.invalidate();
        }
        return mv;
    }

    public ModelAndView confirm_login_as(HttpSession session, String key)
    {
        ModelAndView mv = new ModelAndView();
        if(session.getAttribute("student") != null)
        {
            System.out.println("student is active");
        }
        if((Objects.equals(key, "student") && session.getAttribute("student") != null && session.getAttribute("company") == null)
            || (Objects.equals(key, "company") && session.getAttribute("student") == null && session.getAttribute("company") != null)
                || (Objects.equals(key, "notLoggedIn") && session.getAttribute("student") == null && session.getAttribute("company") == null))
        {
            System.out.println("Credentials confirmed");
        }
        else if(session.getAttribute("student") == null && session.getAttribute("company") == null && !Objects.equals(key, "notLoggedIn"))
        {
            mv = redirect("notLoggedIn", session);
            mv.addObject("error", "Please Log in first to access this page.");
        }
        else if(Objects.equals(key, "notLoggedIn") && session.getAttribute("student") != null)
        {
            mv = redirect("student", session);
            mv.addObject("error", "You are already logged in. Log out first to access this page.");
        }
        else if(Objects.equals(key, "notLoggedIn") && session.getAttribute("company") != null)
        {
            mv = redirect("company", session);
            mv.addObject("error", "You are already logged in. Log out first to access this page.");
        }
        else
        {
            if(Objects.equals(key, "student"))
            {
                mv = redirect("company", session);
            }
            else
            {
                mv = redirect("student", session);
            }
            mv.addObject("error", "You are not authorised to view this page");
        }
        if(mv.isEmpty())
        {
            System.out.println("AAAAAAAAAAAAA");
        }
        return mv;
    }
}

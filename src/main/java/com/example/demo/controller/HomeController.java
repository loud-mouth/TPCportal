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

            if(session.getAttribute("student") != null){
                Student student = (Student)session.getAttribute("student");
                mv.addObject("student", student);
                List<JobProfile> availablejobs = jobprofilerepo.getJobProfilesAvailableToStudent(student);
                mv.addObject("availablejobs", availablejobs);
                mv.setViewName("dashboard");
            }
             else{
                 Company company= (Company)session.getAttribute("company");
                    List<JobProfile> activejobs = jobprofilerepo.getJobProfilesByCompanyId(company.getCompanyId());
                    mv.addObject("activejobs", activejobs);
                    mv.setViewName("dashboard-company");
            }

            return mv;
        }
        mv.setViewName("home");
        return mv;
    }
}

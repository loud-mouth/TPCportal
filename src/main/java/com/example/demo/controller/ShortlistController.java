package com.example.demo.controller;

import com.example.demo.dao.JobProfileRepository;
import com.example.demo.dao.ShortlistRepository;
import com.example.demo.models.Company;
import com.example.demo.models.JobProfile;
import com.example.demo.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Transactional
@Controller
public class ShortlistController {

    @Autowired
    ShortlistRepository shortlistrepo;

    @Autowired
    LoginModule loginmodule;

    public boolean isURL(String url) {
        try {
            (new java.net.URL(url)).openStream().close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @PostMapping("/student/apply/{id}")
    public ModelAndView addResume(@PathVariable("id") int id,
                                  @ModelAttribute("Shortlist") Shortlist shortlist,
                                  HttpSession session)
    {
        System.out.println(shortlist.getStudentId());
        System.out.println(shortlist.getRoundNumber());
        System.out.println(shortlist.getResumeLink());
        System.out.println(shortlist.getJobProfileId());

        ModelAndView mv = new ModelAndView();

        String rlink = shortlist.getResumeLink();
        System.out.println(rlink+"  testing");
        if(!isURL(rlink))
        {
            mv = loginmodule.redirect("student", session);
            mv.addObject("error", "BAD KEY : Not a working URL");
            return mv;
        }

        shortlist.setJobProfileId(id);
        shortlistrepo.saveShortlist(shortlist);

        mv = loginmodule.redirect("student", session);
        if(shortlist.getStudentId() == -1)
        {
            mv.addObject("error", "Error while applying");
        }
        else
        {
            mv.addObject("message", "Successfully Applied");
        }
        return mv;
    }

    @PostMapping("/company/changescore")
    public ModelAndView addResume(
                                  HttpServletRequest request,
                                  HttpSession session)
    {

        List<C1> c1 = (List<C1>)session.getAttribute("intoTheForm");
        session.removeAttribute("intoTheForm");
        ModelAndView mv = new ModelAndView();
//

        for(C1 entry : c1) {
            Shortlist shortlist = entry.getShortlist();
            String str = "score" + entry.getShortlist().getStudentId() + "--" + entry.getShortlist().getRoundNumber() + "--" + entry.getShortlist().getJobProfileId();
            shortlist.setScore(Integer.parseInt(request.getParameter(str)));
            shortlistrepo.updateShortlist(shortlist);
            if (shortlist.getStudentId() == -1) {
                System.out.println("COULD NOT SAVE SHORTLIST");
            }
        }

        mv = loginmodule.redirect("company", session);

        return mv;
    }


}

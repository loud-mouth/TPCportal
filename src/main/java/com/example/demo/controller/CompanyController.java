package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.CompositeName;
import javax.servlet.http.HttpSession;

import com.example.demo.models.Company;
import com.example.demo.dao.CompanyRepository;


@Controller
public class CompanyController {

    @Autowired
    CompanyRepository companyrepo;

    @PostMapping("/company/login")
    public ModelAndView Loginprocess(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session
    )
    {

        ModelAndView mv = new ModelAndView();
        Company student = new Company();
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
        mv.addObject("companyName", maybe.getName());
        mv.addObject("company", maybe);
        mv.setViewName("dashboard-company");

        return mv;
    }
}

package com.example.demo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

import com.example.demo.Student;
import com.example.demo.dao.StudentRepository;


@Controller
public class StudentController {

    @Qualifier("studentRepositoryImpl")
    @Autowired
    StudentRepository studentrepo;

    @PostMapping("/student/login")
    public ModelAndView Loginprocess(
                                        @RequestParam("username") String username,
                                        @RequestParam("password") String password,
                                        HttpSession session
                                )
    {

        ModelAndView mv = new ModelAndView();
        Student student = new Student();
        Student maybe = new Student();
        maybe = studentrepo.getStudentByStudentId(Integer.parseInt(username));

        if(maybe.getStudentId() == -1)
        {
            System.out.println("LALALALA\n");
            mv.setViewName("home");
            mv.addObject("error", "The entered Student-Id does not exist in the database.\n");
            return mv;
        }

        if(!maybe.getPassword().equals(password))
        {
            System.out.println(maybe.getPassword() + " || " + password + " || " + "BAD PASSWORD\n");
            mv.setViewName("home");
            mv.addObject("error", "The password is incorrect");
            return mv;
        }

        session.setAttribute("student", maybe);
        mv.addObject("studentName", maybe.getName());
        mv.addObject("student", student);
        mv.setViewName("dashboard");

        return mv;
    }


    @PostMapping("/blabla")
    public ModelAndView newPage(
            @RequestParam("username") String username,
            HttpSession session
    )
    {
        ModelAndView mv = new ModelAndView();
        mv.addObject("username", username);
        mv.setViewName("newpage");
        return mv;
    }

}

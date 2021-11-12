package com.example.demo.controller;



import com.example.demo.dao.GuardianRepository;
import com.example.demo.models.Guardian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

import com.example.demo.models.Student;
import com.example.demo.dao.StudentRepository;

import java.sql.Date;


@Controller
public class StudentController {

    @Qualifier("studentRepositoryImpl")
    @Autowired
    StudentRepository studentrepo;

    @Autowired
    GuardianRepository guardianrepo;


    public boolean logged_in(HttpSession session)
    {
        if(session.getAttribute("student") == null && session.getAttribute("company") == null)
        {
            return false;
        }
        return true;
    }

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
        System.out.println("INTEGER "+username);
        if(maybe.getStudentId() == -1)
        {
            System.out.println("Incorrect creds\n");
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

    @GetMapping("/student/register")
    public ModelAndView studentRegister(HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if(logged_in(session) == true) {
            mv.addObject("error", "You are already logged in. Log out before registering.");
            if(session.getAttribute("student") != null)
            {
                mv.setViewName("dashboard");
            }
            else
            {
                mv.setViewName("dashboard-company");
            }

            return mv;
        }

        mv.setViewName("studentRegister");
        return mv;
    }

    @PostMapping("/student/register")
    public ModelAndView doStudentRegister(
            @RequestParam("studentid") int studentId,
            @RequestParam("name") String name,
            @RequestParam("emailid") String emailid,
            @RequestParam("password") String password,
            @RequestParam("address") String address,
            @RequestParam("dob") Date dob,
            @RequestParam("phonenumber") String phonenumber,
            @RequestParam("guardianname") String guardianname,
            @RequestParam("guardianphone") String guardianphone,
            @RequestParam("academicsession") int academicsession,
            @RequestParam("branch") String branch,
            @RequestParam("course") String course,
            @RequestParam("cgpa") float cgpa,
            @RequestParam("sittingfor") String sittingfor,
            HttpSession session
    )
    {
        System.out.println("dob " + dob);
        ModelAndView mv = new ModelAndView();


        Student student = new Student();
        student.setStudentId(studentId);
        student.setName(name);
        student.setEmail(emailid);
        student.setPassword(password);
        student.setAddress(address);
        student.setDob(dob);
        student.setBranch(branch);
        student.setCourse(course);
        student.setSittingFor(sittingfor);
        student.setTPRid(100);
        student.setCgpa(cgpa);
        student.setPhoneNumber(phonenumber);
        student.setAcademicSession(academicsession);

        System.out.println("dob2 " + student.getDob());

        Student savedCopy = new Student();
        savedCopy = studentrepo.saveStudent(student);

        if(savedCopy.getStudentId() == -1)
        {
            mv.addObject("error", "Details filled incorrectly\n");
            mv.setViewName("studentRegister");
            return mv;
        }

        Guardian guardian = new Guardian();
        guardian.setName(guardianname);
        guardian.setPhoneNumber(guardianphone);
        guardian.setStudentId(savedCopy.getStudentId());
        Guardian savedGuard = new Guardian();
        savedGuard = guardianrepo.saveGuardian(guardian);

        if(savedGuard.getStudentId() == -1)
        {
            mv.addObject("error", "Details filled incorrectly\n");
            mv.setViewName("studentRegister");
            return mv;
        }

        mv.addObject("message", "You have been successfully registered");
        mv.setViewName("home");
        return mv;
    }



}

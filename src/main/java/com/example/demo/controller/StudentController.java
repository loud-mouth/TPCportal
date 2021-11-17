package com.example.demo.controller;



import com.example.demo.dao.GuardianRepository;
import com.example.demo.dao.JobProfileRepository;
import com.example.demo.models.Company;
import com.example.demo.models.Guardian;
import com.example.demo.models.JobProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpSession;

import com.example.demo.models.Student;
import com.example.demo.dao.StudentRepository;

import com.example.demo.controller.LoginModule;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


@Transactional
@Controller
public class StudentController {

    @Qualifier("studentRepositoryImpl")
    @Autowired
    StudentRepository studentrepo;

    @Autowired
    GuardianRepository guardianrepo;

    @Autowired
    JobProfileRepository jobprofilerepo;

    @Autowired
    LoginModule loginmodule;


    @PostMapping("/student/logout")
    public ModelAndView Logoutprocess(HttpSession session)
    {
        ModelAndView mv = loginmodule.confirm_login_as(session, "student");
        if(!mv.isEmpty())
        {
            return mv;
        }
        mv = loginmodule.redirect("home", session);
        mv.addObject("message", "Logged Out Successfully.");
        return mv;
    }

    @PostMapping("/student/login")
    public ModelAndView Loginprocess(
                                        @RequestParam("username") String username,
                                        @RequestParam("password") String password,
                                        HttpSession session
                                )
    {

        //VALIDATE ROLL NUMBER
        if(username.length()!=8)
        {
            ModelAndView mv = loginmodule.redirect("home", session);
            mv.addObject("error", "BAD KEY : Student Id is not 8-digits");
            return mv;
        }
        try {
            int us = Integer.parseInt(username);
            assert (us > 0);
        }
        catch (Exception e)
        {
            ModelAndView mv = loginmodule.redirect("home", session);
            mv.addObject("error", "BAD KEY : Student Id is not valid");
            return mv;
        }


        Student student = new Student();
        Student maybe = new Student();
        System.out.println("At login");
        maybe = studentrepo.getStudentByStudentId(Integer.parseInt(username));
        System.out.println("INTEGER "+username);
        if(maybe.getStudentId() == -1)
        {
            System.out.println("Incorrect creds\n");
            ModelAndView mv = loginmodule.redirect("home", session);
            mv.addObject("error", "The entered Student-Id does not exist in the database.\n");
            return mv;
        }

        if(!maybe.getPassword().equals(password))
        {
            System.out.println(maybe.getPassword() + " || " + password + " || " + "BAD PASSWORD\n");
            ModelAndView mv = loginmodule.redirect("home", session);
            mv.addObject("error", "The password is incorrect");
            return mv;
        }

        session.setAttribute("student", maybe);
        ModelAndView mv = loginmodule.redirect("student", session);
        return mv;
    }

    @GetMapping("/student/register")
    public ModelAndView studentRegister(HttpSession session)
    {
        ModelAndView checkCreds = loginmodule.confirm_login_as(session, "notLoggedIn");
        if(!checkCreds.isEmpty()) {
            return checkCreds;
        }

        ModelAndView mv = new ModelAndView();
        mv.setViewName("studentRegister");
        return mv;
    }

    @Autowired
    private JavaMailSender javaMailSender;

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    public boolean validateName(String name)
    {
        boolean flag = true;
        for(int i=0; i<name.length(); ++i)
        {
            char c = name.charAt(i);
            if(!Character.isLetter(c) && !(c==' ') && !(c == '.'))
            {
                return false;
            }
            if(i==0 && !Character.isLetter(c))
            {
                return false;
            }
        }
        return flag;
    }

    public boolean validateStudentId(String username)
    {
        if(username.length()!=8)
        {
            return false;
        }
        try {
            int us = Integer.parseInt(username);
            assert (us > 0);
        }
        catch (Exception e)
        {
            return false;
        }

        return true;
    }

    public boolean validateAcademicSession(int year)
    {
        try{
            assert(year >= 2020);
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }

    public boolean validatePhoneNumber(String number)
    {
        for(int i=0; i<number.length(); ++i)
        {
            char c = number.charAt(i);
            if(!Character.isDigit(c) && c!='+' && c!=' ' && c!='-' && c!='(' && c!=')')
            {
                return false;
            }
        }
        return true;
    }

    @PostMapping("/student/register")
    public ModelAndView doStudentRegister(
            @RequestParam("studentid") String studentId,
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
        String username = (studentId);

        if(!validateStudentId(username) || username.isEmpty())
        {
            mv.setViewName("studentRegister");
            mv.addObject("error", "BAD KEY : Student Id is not valid");
            return mv;
        }

        if(!validateName(name)|| name.isEmpty())
        {
            mv.setViewName("studentRegister");
            mv.addObject("error", "BAD KEY : Student Name is not valid");
            return mv;
        }
        if(!validateName(guardianname) || guardianname.isEmpty())
        {
            mv.setViewName("studentRegister");
            mv.addObject("error", "BAD KEY : Guardian Name is not valid");
            return mv;
        }

        if(!isValidEmailAddress(emailid) || emailid.isEmpty())
        {
            mv.setViewName("studentRegister");
            mv.addObject("error", "BAD KEY : Email ID is not valid");
            return mv;
        }

        if(!validateAcademicSession((academicsession)))
        {
            mv.setViewName("studentRegister");
            mv.addObject("error", "BAD KEY : Academic session is not valid");
            return mv;
        }


        if(!validatePhoneNumber(phonenumber) || phonenumber.isEmpty())
        {
            mv.setViewName("studentRegister");
            mv.addObject("error", "BAD KEY : Phone Number is not valid");
            return mv;
        }

        if(!validatePhoneNumber(guardianphone) || guardianphone.isEmpty())
        {
            mv.setViewName("studentRegister");
            mv.addObject("error", "BAD KEY : Guardian Phone Number is not valid");
            return mv;
        }

        HashMap<String, Integer> tprcode = new HashMap<String, Integer>();
        tprcode.put("cse+btech", 100);
        tprcode.put("cse+idd", 200);
        tprcode.put("ee+btech", 101);
        tprcode.put("ee+idd", 201);
        tprcode.put("ece+btech", 102);
        tprcode.put("ece+idd", 202);
        tprcode.put("mat", 103);
        tprcode.put("mat", 203);

        Student already = studentrepo.getStudentByStudentId(Integer.parseInt(studentId));
        if(already.getStudentId() != -1)
        {
            mv.addObject("error", "Details filled incorrectly. Student with same student Id already exist\n");
            mv.setViewName("studentRegister");
            return mv;
        }

        Student student = new Student();
        student.setStudentId(Integer.parseInt(studentId));
        student.setName(name);
        student.setEmail(emailid);
        student.setPassword(password);
        student.setAddress(address);
        student.setDob(dob);
        student.setBranch(branch);
        student.setCourse(course);
        student.setSittingFor(sittingfor);
        Integer tprid = tprcode.get(branch+course);
        if(tprid == null)
        {
            tprid = 404;
        }
        student.setTPRid(tprid);
        student.setCgpa(cgpa);
        student.setPhoneNumber(phonenumber);
        student.setAcademicSession(academicsession);

        System.out.println("dob2 " + student.getDob());

        Student savedCopy = new Student();
        savedCopy = studentrepo.saveStudent(student);

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(emailid);
        System.out.println("sending registration mail to " + emailid);
        msg.setSubject("Thank You! -from Team TPCportal @ IIT BHU");
        msg.setText("Thank You for registering to our portal! We hope you have a wonderful time with lots of great opportunities knocking at your door.\n\n-With regards,\nThe TPC Team,\nIIT Varanasi(BHU)");
        javaMailSender.send(msg);

        if(savedCopy.getStudentId() == -1)
        {
            mv.addObject("error", "Details filled incorrectly. Student with same student Id might already exist\n");
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

        mv = loginmodule.redirect("home", session);
        mv.addObject("message", "You have been successfully registered");
        return mv;
    }



}

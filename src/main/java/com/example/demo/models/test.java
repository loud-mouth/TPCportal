package com.example.demo.models;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class test {
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            System.out.println("Checking email");
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

    public boolean validateURL(String url) {
        try {
            (new java.net.URL(url)).openStream().close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}

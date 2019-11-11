package com.internship.controller;

import com.internship.model.User;
import com.internship.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
    @Autowired
    private IUserService service;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping("/login")
    public String Login(){
        return "loginPage/login";
    }

    @RequestMapping("/logerror")
    public String LogErr(Model m){
        m.addAttribute("error","Incorrect login or password or registration Error, please try again");
        return "loginPage/login";
    }

    @RequestMapping("/logreg")
    public String LogRegistration(Model m){
        m.addAttribute("error","Successful registration, please enter your login and password");
        return "loginPage/login";
    }

    @RequestMapping("/logout")
    public String LogOut(){
        return "loginPage/login";
    }

    @RequestMapping("/accessDenied")
    public String accessDenied(){
        return "loginPage/accessDenied";
    }

    @RequestMapping("/registration")
    public String Registration(Model m){
        m.addAttribute("command", new User());
        return "loginPage/registration";
    }

    @RequestMapping(value="/save",method = RequestMethod.POST)
    public String save(@ModelAttribute("user") User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if(service.add(user) != null)
            return "redirect:logreg";
        else
            return "redirect:logerror";
    }

}

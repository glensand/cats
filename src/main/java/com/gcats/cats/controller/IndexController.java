package com.gcats.cats.controller;

import com.gcats.cats.model.User;
import com.gcats.cats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {


    @Autowired
    private UserService userService;

    private ModelAndView getModelWithUser(){
        return userService.getModelWithUser();
    }

    @RequestMapping(value= {"/home", "/"}, method = RequestMethod.GET)
    public String home(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByLogin(auth.getName());

        if(user.getRole().equals("ADMIN"))
            return "redirect:/admin/users";
        else
            return "redirect:/lessons";
    }
}


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

import javax.validation.Valid;

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

    @RequestMapping(value= {"/user/private"}, method = RequestMethod.GET)
    public ModelAndView privateArea(){

        ModelAndView modelAndView = getModelWithUser();
        modelAndView.setViewName("user/privateArea");
        return modelAndView;
    }

    @RequestMapping(value= {"/user/edit"}, method = RequestMethod.POST)
    public String edit(User user){

        User user1 = userService.findUserById(user.getId());
        user1.setLogin(user.getLogin());
        user1.setName(user.getName());
        user1.setLastName(user.getLastName());
        userService.saveUser(user1);
        return "redirect:/user/private";
    }
}


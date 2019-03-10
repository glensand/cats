package com.gcats.cats.controller;

import com.gcats.cats.model.Lesson;
import com.gcats.cats.model.User;
import com.gcats.cats.service.LessonService;
import com.gcats.cats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

//comment just for slack github's commits redirection test

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    //some copy and paste, cause spring autowire doesnt work with static classes,
    // methods and stuff like that
    //TODO: create class to be produce model and view with user
    private ModelAndView getModelWithUser(){

        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("user", user);

        return modelAndView;
    }

    @RequestMapping(value= {"/home", "/"}, method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = getModelWithUser();
        modelAndView.setViewName("/home");
        return modelAndView;
    }

    @RequestMapping(value= {"/admin/user/new"}, method = RequestMethod.GET)
    public ModelAndView newUser(){
        ModelAndView modelAndView = getModelWithUser();
        modelAndView.addObject("newUser", new User());
        modelAndView.setViewName("admin/adduser");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/user/new", method = RequestMethod.POST)
    public ModelAndView createNewUser(@RequestParam String role, @Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = getModelWithUser();
        if (bindingResult.hasErrors())
            modelAndView.setViewName("admin/adduser");
        else {
            user.setRole(role);
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been saved successfully");
            modelAndView.addObject("newUser", new User());
            modelAndView.setViewName("admin/adduser");
        }
        return modelAndView;
    }
}

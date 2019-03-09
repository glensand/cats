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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private UserService userService;

    private ModelAndView getModelWithUser(){

        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("user", user);

        return modelAndView;
    }

    @RequestMapping(value="/curator/lesson/new", method = RequestMethod.GET)
    public ModelAndView addLesson(){
        ModelAndView modelAndView = getModelWithUser();
        Lesson lesson = new Lesson();
        modelAndView.addObject("lesson", lesson);
        modelAndView.setViewName("curator/addlesson");
        return modelAndView;
    }

    @RequestMapping(value = "/curator/lesson/new", method = RequestMethod.POST)
    public ModelAndView createNewLesson(@Valid Lesson lesson, BindingResult bindingResult) {
        ModelAndView modelAndView = getModelWithUser();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("curator/addlesson");
        } else {

            System.out.println(lesson.getName());
            lessonService.saveLesson(lesson);
            modelAndView.addObject("successMessage", "Lesson has been saved successfully");
            System.out.println(lesson.getId());
            modelAndView.addObject("lesson", new Lesson());
            modelAndView.setViewName("curator/addlesson");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/lessons", method = RequestMethod.GET)
    public ModelAndView showLessons() {
        ModelAndView modelAndView = getModelWithUser();
        modelAndView.addObject("lessons", lessonService.listAllLessons());
        modelAndView.setViewName("lessons");
        return modelAndView;
    }

    @RequestMapping(value = "lesson/{id}", method = RequestMethod.GET)
    public ModelAndView showLesson(@PathVariable Integer id){
        ModelAndView modelAndView = getModelWithUser();
        modelAndView.addObject("lesson", lessonService.findLessonById(id));
        modelAndView.setViewName("lessonshow");
        return modelAndView;
    }

    @RequestMapping(value = "curator/lesson/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable Integer id){
        ModelAndView modelAndView = getModelWithUser();
        modelAndView.addObject("lesson", lessonService.findLessonById(id));
        modelAndView.setViewName("curator/addlesson");
        return modelAndView;
    }
}

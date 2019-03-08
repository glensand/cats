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

    @RequestMapping(value="/admin/lesson/new", method = RequestMethod.GET)
    public ModelAndView addlesson(){
        ModelAndView modelAndView = new ModelAndView();
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Lesson lesson = new Lesson();
        modelAndView.addObject("lesson", lesson);
        //modelAndView.addObject("adminMessage","Content Available Only for Users with Admin role");
        modelAndView.setViewName("admin/addlesson");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/lesson/new", method = RequestMethod.POST)
    public ModelAndView createNewLesson(@Valid Lesson lesson, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("admin/addlesson");
        } else {

            System.out.println(lesson.getName());
            lessonService.saveLesson(lesson);
            modelAndView.addObject("successMessage", "Lesson has been saved successfully");
            //Lesson newLesson = new Lesson(lesson);
            System.out.println(lesson.getId());
            modelAndView.addObject("lesson", new Lesson());
            modelAndView.setViewName("admin/addlesson");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/lessons", method = RequestMethod.GET)
    public ModelAndView showLessons() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("lessons", lessonService.listAllLessons());
        modelAndView.setViewName("lessons");
        return modelAndView;
    }

    @RequestMapping(value = "lesson/{id}", method = RequestMethod.GET)
    public ModelAndView showLesson(@PathVariable Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("lesson", lessonService.findLessonById(id));
        modelAndView.setViewName("lessonshow");
        return modelAndView;
    }

    @RequestMapping(value = "admin/lesson/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("lesson", lessonService.findLessonById(id));
        modelAndView.setViewName("admin/addlesson");
        return modelAndView;
    }
}

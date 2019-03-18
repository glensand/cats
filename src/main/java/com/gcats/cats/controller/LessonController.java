package com.gcats.cats.controller;

import com.gcats.cats.model.Comment;
import com.gcats.cats.model.Lesson;
import com.gcats.cats.model.User;
import com.gcats.cats.service.CommentService;
import com.gcats.cats.service.LessonService;
import com.gcats.cats.service.UserService;
import com.gcats.cats.utils.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LessonController {

    @Autowired
    PdfGenerator pdfGeneratorUtil;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    private ModelAndView getModelWithUser(){
        return userService.getModelWithUser();
    }

    @RequestMapping(value="/curator/lesson/new", method = RequestMethod.GET)
    public ModelAndView addLesson(){
        ModelAndView modelAndView = getModelWithUser();
        Lesson lesson = new Lesson();
        modelAndView.addObject("lesson", lesson);
        modelAndView.setViewName("lesson/new");
        return modelAndView;
    }

    @RequestMapping(value = "/curator/lesson/new", method = RequestMethod.POST)
    public ModelAndView createNewLesson(@Valid Lesson lesson, BindingResult bindingResult) {
        ModelAndView modelAndView = getModelWithUser();
        modelAndView.setViewName("lesson/new");
        if (!bindingResult.hasErrors()) {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            lesson.setAuthor(auth.getName());
            System.out.println(lesson.getName());
            lessonService.saveLesson(lesson);
            modelAndView.addObject("successMessage", "Lesson has been saved successfully");
            System.out.println(lesson.getId());
            modelAndView.addObject("lesson", new Lesson());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/lessons", method = RequestMethod.GET)
    public ModelAndView showLessons() {
        ModelAndView modelAndView = getModelWithUser();
        modelAndView.addObject("lessons", lessonService.listAllLessons());
        modelAndView.setViewName("lesson/lessons");
        return modelAndView;
    }

    @RequestMapping(value = "lesson/{id}", method = RequestMethod.GET)
    public ModelAndView showLesson(@PathVariable Integer id){
        ModelAndView modelAndView = getModelWithUser();
        modelAndView.addObject("lesson", lessonService.findLessonById(id));
        modelAndView.addObject("comment", new Comment());
        modelAndView.addObject("comments", commentService.listByLessonId(id));
        modelAndView.setViewName("lesson/show");
        return modelAndView;
    }


    @RequestMapping(value = "/lesson/comment/new/{id}", method = RequestMethod.POST)
    public String addComment(@PathVariable Integer id, @Valid Comment comment, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            comment.setLessonId(id);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            comment.setUserId(user.getId());
            comment.setUserName(user.getName());
            commentService.saveComment(comment);
        }
        return "redirect:/lesson/" + id;
    }

    @RequestMapping(value = "curator/lesson/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable Integer id){
        ModelAndView modelAndView = getModelWithUser();
        modelAndView.addObject("lesson", lessonService.findLessonById(id));
        modelAndView.setViewName("lesson/new");
        return modelAndView;
    }

    //TODO: add real lesson html to pdf processing, with data base info usage
    //TODO: modify PdfGenerator, to be able to pass Lists{Iterable} as argument
    @RequestMapping("/pdf/{fileName:.+}")
    public void downloadPDFResource( HttpServletRequest request,
                                     HttpServletResponse response,
                                     @PathVariable("fileName") String fileName) {

        try{
            Map<String,String> data = new HashMap<String,String>();
            Lesson lesson = lessonService.findLessonById(Integer.parseInt(fileName));
            data.put("name", lesson.getName());
            data.put("goal", lesson.getGoal());
            data.put("text", lesson.getText());
            data.put("author", lesson.getAuthor());
            pdfGeneratorUtil.createPdf("lessonToPdf", fileName, data);
        }
        catch (java.lang.Exception e){
            System.out.println("handled");
        }

        String dataDirectory = request.getServletContext().getRealPath("/pdf/");
        System.out.println(dataDirectory);
        final String fileNameDot = fileName + ".pdf";
        Path file = Paths.get(dataDirectory, fileNameDot);
        if (Files.exists(file)) {
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment; filename=" + fileNameDot);
            try {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        //return "redirect:/lesson/" + fileName;
    }
}

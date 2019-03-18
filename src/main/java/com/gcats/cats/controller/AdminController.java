package com.gcats.cats.controller;

import com.gcats.cats.model.User;
import com.gcats.cats.service.UserService;
import com.gcats.cats.service.storage.FileSystemStorageService;
import com.gcats.cats.utils.CsvReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Vector;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    private final FileSystemStorageService storageService;

    @Autowired
    public AdminController(FileSystemStorageService storageService) {
        this.storageService = storageService;
    }

    private ModelAndView getModelWithUser(){
        return userService.getModelWithUser();
    }

    @RequestMapping(value= {"/admin/users"}, method = RequestMethod.GET)
    public ModelAndView users(){
        ModelAndView modelAndView = getModelWithUser();
        modelAndView.addObject("users", userService.listAllUsers());
        modelAndView.setViewName("admin/users");
        return modelAndView;
    }

    @RequestMapping(value= {"/admin/user/new"}, method = RequestMethod.GET)
    public ModelAndView newUser(){
        ModelAndView modelAndView = getModelWithUser();
        //modelAndView.addObject("newUser", new User());
        modelAndView.setViewName("admin/adduser");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/upload", method = RequestMethod.POST)
    public ModelAndView handleFileUpload(@RequestParam("file") MultipartFile file,
                                         RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        CsvReader csvReader = new CsvReader();
        try{
            Vector<User> users = csvReader.read(storageService.loadAsResource(file.getOriginalFilename()).getFile());
            for(User user:users){
                User userExists = userService.findUserByEmail(user.getEmail());
                if(userExists==null)
                    userService.saveUser(user);
            }
        }
        catch (java.lang.Exception e){
            System.out.println(e.toString());
        }

        ModelAndView modelAndView = getModelWithUser();
        //modelAndView.addObject("newUser", new User());
        modelAndView.setViewName("admin/adduser");
        modelAndView.addObject("successMessage",
                "Users has been saved successfully");
        return modelAndView;
    }

}

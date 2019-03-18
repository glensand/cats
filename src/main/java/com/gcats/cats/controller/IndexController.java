package com.gcats.cats.controller;

import au.com.bytecode.opencsv.CSVReader;
import com.gcats.cats.service.storage.FileSystemStorageService;
import com.gcats.cats.utils.CsvReader;
import com.gcats.cats.model.User;
import com.gcats.cats.service.UserService;
import com.gcats.cats.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import javax.validation.Valid;
import java.util.Vector;


@Controller
public class IndexController {


    @Autowired
    private UserService userService;

    private ModelAndView getModelWithUser(){
        return userService.getModelWithUser();
    }

    @RequestMapping(value= {"/home", "/"}, method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = getModelWithUser();
        modelAndView.setViewName("/home");
        return modelAndView;
    }
}


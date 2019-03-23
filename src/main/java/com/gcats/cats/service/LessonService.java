package com.gcats.cats.service;

import com.gcats.cats.model.Lesson;
import com.gcats.cats.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service("lessonService")
public class LessonService  {

    private LessonRepository lessonRepository;
//
//    @Autowired
//    private SessionFactory sessionFactory;

    @Autowired
    public LessonService(LessonRepository lessonRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.lessonRepository = lessonRepository;
    }

    public void update(Lesson object) {
        saveLesson(object);
    }

    public Lesson findLessonByName(String name) {
        return lessonRepository.findByName(name);
    }

    public Lesson findLessonById(int id) {
        return lessonRepository.findById(id);
    }

    public Lesson saveLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public Iterable<Lesson> listAllLessons() {
        return lessonRepository.findAll();
    }


}
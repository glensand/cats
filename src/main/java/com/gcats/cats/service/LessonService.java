package com.gcats.cats.service;

import com.gcats.cats.model.Lesson;
import com.gcats.cats.repository.LessonRepository;
import com.gcats.cats.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(object);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }

    public Lesson findLessonByName(String name) {
        return lessonRepository.findByName(name);
    }

    public Lesson findLessonById(int id) {
        return lessonRepository.findById(id);
    }

    public Lesson saveLesson(Lesson lesson) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(lesson);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
        return lesson;
    }

    public Iterable<Lesson> listAllLessons() {
        return lessonRepository.findAll();
    }


}
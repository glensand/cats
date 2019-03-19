package com.gcats.cats.repository;

import com.gcats.cats.model.Lesson;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("lessonRepository")
public interface LessonRepository  extends JpaRepository<Lesson, Long> {

    Lesson findByName(String name);
    Lesson findById(int id);

}

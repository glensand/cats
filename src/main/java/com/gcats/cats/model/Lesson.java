package com.gcats.cats.model;

import com.gcats.cats.model.LessonsAttributes.AuthorsNotes;
import com.gcats.cats.model.LessonsAttributes.Estimates;
import com.gcats.cats.model.LessonsAttributes.ReflectionPrompts;
import com.gcats.cats.model.LessonsAttributes.TeacherTask;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@ToString(exclude = {"comments", "resources"})
@Table(name = "lesson")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lesson_id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @Column(name = "goal")
    private String goal;

    @Column(name = "time_interval")
    private String timeInterval;

    @Column(name = "background")
    private String background;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Resource> resources;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TeacherTask> teacherTasks;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Estimates> estimates;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ReflectionPrompts> reflectionPrompts;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AuthorsNotes> authorsNotes;
}

package com.gcats.cats.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lesson")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lesson_id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "goal")
    private String goal;

    @OneToMany
    private Set<Resource> resources;

    @OneToMany
    private Set<Comment> comments;

    @Column(name = "time_interval")
    private int timeInterval;

    @Column(name = "background")
    private String background;
}

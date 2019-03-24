package com.gcats.cats.model.LessonsAttributes;

import com.gcats.cats.model.Lesson;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "authors_notes")
public class AuthorsNotes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "note_id")
    private int id;

    @Column(name = "note")
    private String note;
}

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
@Table(name = "estimates")
public class Estimates{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "estimate_id")
    private int id;

    @Column(name = "estimate")
    private String estimate;
}

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
@Table(name = "reflection_prompts")
public class ReflectionPrompts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "r_prompt_id")
    private int id;

    @Column(name = "r_prompt")
    private String rPrompt;
}

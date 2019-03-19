package com.gcats.cats.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;
    @Column(name = "login")
    @NotEmpty(message = "*Please provide an login")
    private String login;
    @Column(name = "password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;
    @Column(name = "name")
    //@NotEmpty(message = "*Please provide your name")
    private String name;
    @Column(name = "last_name")
    //@NotEmpty(message = "*Please provide your last name")
    private String lastName;
    @Column(name = "role")
    private String role;
    @Column(name = "active")
    private int active;
}

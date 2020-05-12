package com.demo1.applesson1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String password;

    private String name_surname;
    private int age;
    private String sex;
    private int lvl;
    private String mail;
    private String stripeCustomerId;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_courses_buy",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses;



//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
//    private List<Course> createdCourses;



    @JsonIgnore
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Role> roles;


    @Override
    public String toString(){
        return "id="+ id + "username="+username +"password="+ password +
                "name_surname=" + name_surname + "age=" + age + "sex=" + sex + "lvl="+lvl;
    }
}


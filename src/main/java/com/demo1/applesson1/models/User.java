package com.demo1.applesson1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
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
    private String photoURL;
    private String mail;
    private String statusMailActivate;

    private String stripeCustomerId;
    private String tokenStripe;
    private String stripeCardId;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_courses_buy",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses;


    @JsonIgnore
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Role> roles;


    @Override
    public String toString() {
        return "id=" + id + "username=" + username + "password=" + password +
                "name_surname=" + name_surname + "age=" + age + "sex=" + sex + "lvl=" + lvl
                + "stripeCustomerId" + stripeCustomerId + "tokenStripe" + tokenStripe + "stripeCardId" + stripeCardId
                + "photoURL=" + photoURL + "statusMailActivate="+ statusMailActivate;
    }
}


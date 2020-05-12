package com.demo1.applesson1.models;


import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String title;

    private String description;

    private Integer price;

    private String genre;

    private String linkOnVideo;

    private String publicKey;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_courses_buy",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;


    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Override
    public String toString() {
        return "id=" + id + "title=" + title + "description=" + description + "price=" + price
                + "genre=" + genre + "linkOnVideo=" + linkOnVideo;
    }

}

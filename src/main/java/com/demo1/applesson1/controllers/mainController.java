package com.demo1.applesson1.controllers;

import com.demo1.applesson1.dbServices.UserDAO;
import com.demo1.applesson1.models.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
public class mainController {
    private UserDAO userService;
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home() {
        return ("home");
    }


    @GetMapping("/users")
    public List<User> getUsers() {
        List<User> allUsers = userService.findAll();
        return allUsers;
    }

    @GetMapping("/test")
    public String test(){
        System.out.println("test");
        return ("test");
    }



    //Lesson1

//    @GetMapping("/save")
////    /save?name=Oleh&age=23&surname=Kosylo
//    @RequestParam String name, @RequestParam String age  /// you can change it on object of DB, if that obj have field of requestParam
//    public User saveGet(User user) {
//        System.out.println(user);
//        return user;
//    }

    @PostMapping("/save")
    public User savePost(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return user;
    }

    @GetMapping("users/{id}")
    public User getUser(@PathVariable int id) {
        User optionalUser = userService.findById(id).orElseThrow(() -> new RuntimeException("Error 500"));
        return optionalUser;
    }

    @GetMapping("users/delete")
    public void deleteUser(@RequestParam Integer userId) {
        userService.deleteById(userId);
    }


    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) {
        String path = System.getProperty("user.home") + File.separator + "images" + File.separator + file.getOriginalFilename();
        try {
            file.transferTo(new File(path));
        } catch (IOException e) {
            return "Error";
        }
        return "ok";
    }

}

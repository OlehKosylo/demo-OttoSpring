package com.demo1.applesson1.controllers;

import com.demo1.applesson1.dto.LoginRequest;
import com.demo1.applesson1.dto.UserRequest;
import com.demo1.applesson1.dto.UserResponse;
import com.demo1.applesson1.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public HttpEntity<List<UserResponse>> getUsers() {

        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping
    public HttpEntity<UserResponse> getUser(@RequestParam Integer id) {

        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam Integer id) {

        userService.delete(id);

        new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/upload")
    public HttpEntity<String> upload(@RequestParam MultipartFile file) {

       return new ResponseEntity<>(userService.uploadAvatar(file), HttpStatus.OK);
    }

}

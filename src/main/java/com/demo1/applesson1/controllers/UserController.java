package com.demo1.applesson1.controllers;

import com.demo1.applesson1.dto.EditUserInfoRequest;
import com.demo1.applesson1.dto.UserResponse;
import com.demo1.applesson1.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @GetMapping("/userInfoForMainPage")
    public HttpEntity<UserResponse> getUserInfoForMainPage(@RequestParam Integer userId) {

        return new ResponseEntity<>(userService.getUserInfoForMainPage(userId), HttpStatus.OK);
    }

    @GetMapping("/userInfoForProfilePage")
    public HttpEntity<UserResponse> getUserInfoForProfilePage(@RequestParam Integer userId) {

        return new ResponseEntity<>(userService.getUserInfoForProfilePage(userId), HttpStatus.OK);
    }


    @PostMapping("/userEditInfo")
    public HttpEntity<UserResponse> editUserInfo(@RequestBody EditUserInfoRequest editUserInfoResponse) {

        System.out.println(editUserInfoResponse);
        return new ResponseEntity<>(userService.editInfoUser(editUserInfoResponse), HttpStatus.OK);
    }

//    @PostMapping("/upload")
//    public HttpEntity<String> upload(@RequestParam MultipartFile file) {
//
//        return new ResponseEntity<>(userService.uploadAvatar(file,1), HttpStatus.OK);
//    }


//    @GetMapping("/all")
//    public HttpEntity<List<UserResponse>> getUsers() {
//
//        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
//    }

//    @PostMapping("/updateUserName")
//    public void updateUserName(@RequestParam Integer id, @RequestParam String newName){
//        userService.updateUserName(id,newName);
//    }

//    @DeleteMapping("/delete")
//    public void delete(@RequestParam Integer id) {
//
//        userService.delete(id);
//
//        new ResponseEntity<>(HttpStatus.OK);
//



}

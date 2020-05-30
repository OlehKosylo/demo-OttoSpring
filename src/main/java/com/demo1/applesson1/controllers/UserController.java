package com.demo1.applesson1.controllers;

import com.demo1.applesson1.dto.Request.UserRequest;
import com.demo1.applesson1.dto.Response.UserResponse;
import com.demo1.applesson1.services.UserService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public HttpEntity<UserResponse> getUserInfoForProfilePage(@RequestParam Integer userId) throws StripeException {

        return new ResponseEntity<>(userService.getUserInfoForProfilePage(userId), HttpStatus.OK);
    }


    @PostMapping("/userEditInfo")
    public HttpEntity<UserResponse> editUserInfo(@RequestBody UserRequest editUserInfoResponse) {

        return new ResponseEntity<>(userService.editInfoUser(editUserInfoResponse), HttpStatus.OK);
    }

    @PostMapping("/userEditPhoto")
    public HttpEntity<UserResponse> editUserPhoto(@RequestBody UserRequest editUserInfoResponse) {

        return new ResponseEntity<>(userService.editUserPhoto(editUserInfoResponse), HttpStatus.OK);
    }

}

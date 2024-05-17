/*
   ScaleX  Backend Developer Assignment
   Overview:
   This file is part of the ScaleX project.

   Author: Priyanshu Lodha

   These are the Api's that need to be called for the desired result described in assignment.

   Date: 17-MAY-2024

    /auth/signup -  To add user to the application.

    /auth/login -  To login user to the application provided with auth token and refresh token.

    /auth/refresh -  To refresh the token for the user.

    Thank You!!!
*/

package com.example.scalexassignment.controller;

import com.example.scalexassignment.dto.ReqRes;
import com.example.scalexassignment.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<ReqRes> signUp(@RequestBody ReqRes signUpRequest){
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }
    @PostMapping("/login")
    public ResponseEntity<ReqRes> signIn(@RequestBody ReqRes signInRequest){
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }
    @PostMapping("/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes refreshTokenRequest){
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }
}

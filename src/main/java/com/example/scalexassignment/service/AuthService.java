package com.example.scalexassignment.service;

import com.example.scalexassignment.dto.ReqRes;
import com.example.scalexassignment.entity.User;
import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    JWTUtils jwtUtils;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    Integer userCount=0;
    public ReqRes signUp(ReqRes registrationRequest){
        ReqRes resp=new ReqRes();
            User user=new User();
            user.setId(++userCount);
            user.setUserName(registrationRequest.getName());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setRole(registrationRequest.getRole());
            int result=customUserDetailService.addUser(user);
            if(result==1){
                resp.setMessage("User saved Successfully");
                resp.setUser(user);
                resp.setStatusCode(200);
            }else {
                resp.setMessage("User already exist");
                resp.setStatusCode(500);
            }

        return resp;
    }
    public ReqRes signIn(ReqRes signInRequest){
        ReqRes resp=new ReqRes();
        UserDetails userExists=customUserDetailService.findByUserName(signInRequest.getName());
        if(userExists==null){
            resp.setStatusCode(500);
            resp.setMessage("User not exist!!!");
            return resp;
        }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getName(),signInRequest.getPassword()));
            var user=customUserDetailService.findByUserName(signInRequest.getName());
            if(user==null){
                resp.setMessage("User not found");
                resp.setStatusCode(500);
                return resp;
            }
            var jwt=jwtUtils.generateToken(user);
            var refreshToken=jwtUtils.generateRefreshToken(new HashMap<>(),user);
            resp.setStatusCode(200);
            resp.setToken(jwt);
            resp.setRefreshToken(refreshToken);
            resp.setExpirationTime("24Hr");
            resp.setMessage("Successfully Signed In");
            return resp;
    }
    public ReqRes refreshToken(ReqRes refreshTokenRequest){
        ReqRes resp=new ReqRes();
        String name=jwtUtils.extractUserName(refreshTokenRequest.getToken());
        var user=customUserDetailService.findByUserName(refreshTokenRequest.getName());
        if(user==null){
            resp.setMessage("User not found");
            resp.setStatusCode(500);
            return resp;
        }
        if(jwtUtils.isTokenValid(refreshTokenRequest.getToken(),user)){
            var jwt=jwtUtils.generateToken(user);
            resp.setStatusCode(200);
            resp.setToken(jwt);
            resp.setRefreshToken(refreshTokenRequest.getToken());
            resp.setExpirationTime("24Hr");
            resp.setMessage("Successfully Refreshed Token");
        }
        return resp;
    }
}

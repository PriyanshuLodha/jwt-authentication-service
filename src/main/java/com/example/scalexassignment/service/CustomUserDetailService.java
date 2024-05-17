package com.example.scalexassignment.service;

import com.example.scalexassignment.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUserName(username);
    }
    List<User> userList=new ArrayList<>();

    public UserDetails findByUserName(String userName){
        for(User user:userList){
            if(user.getUsername().equals(userName)){
                return user;
            }
        }
        return null;
    }
    public Integer addUser(User user)
    {
        Integer flag=1;
        for(User u:userList){
            if(u.getUsername().equals(user.getUsername())){
                flag=0;
            }
        }
        userList.add(user);
        for(User u:userList){
            System.out.println(user);
        }
        return flag;
    }
}

/*
   ScaleX  Backend Developer Assignment

   Overview:
   This file is part of the ScaleX project.

   Author: Priyanshu Lodha

   These are the Api's that need to be called for the desired result described in assignment.

   Date: 17-MAY-2024

    /public/home -  To get the books according to user roles.

    /public/addBook - To add books to the csv file. Access for ADMIN users only.

    /public/deleteBook - To delete books from the csv file. Access for ADMIN users only.

   Thank You!!!
*/


package com.example.scalexassignment.controller;

import com.example.scalexassignment.dto.AddBookDTO;
import com.example.scalexassignment.dto.DeleteBookDTO;
import com.example.scalexassignment.dto.ReqRes;
import com.example.scalexassignment.entity.Book;
import com.example.scalexassignment.service.BookService;
import com.example.scalexassignment.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/public")
public class BookController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    CustomUserDetailService customUserDetailService;
    @Autowired
    BookService bookService;
    @PostMapping("/home")
    public List<Book> viewBooks(@RequestBody ReqRes request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getName(),request.getPassword()));
        UserDetails user=customUserDetailService.findByUserName(request.getName());
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        boolean isAdmin = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));

        boolean isUser = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("USER"));
        if(isAdmin){
            return bookService.readCSV("ADMIN");
        }
        if(isUser){
            return bookService.readCSV("USER");
        }else {
            return null;
        }
    }
    @PostMapping("/addBook")
    public String addBook(@RequestBody AddBookDTO addBookDTO){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(addBookDTO.getName(),addBookDTO.getPassword()));
        UserDetails user=customUserDetailService.findByUserName(addBookDTO.getName());
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        boolean isAdmin = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));

        boolean isUser = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("USER"));
        if(isAdmin){
            bookService.addBook(addBookDTO);
            return bookService.addBook(addBookDTO);
        }
        else {
            return "You don't have permission to add book talk to admins please!!!";
        }
    }
    @DeleteMapping("/deleteBook")
    public String addBook(@RequestBody DeleteBookDTO deleteBookDTO){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(deleteBookDTO.getName(),deleteBookDTO.getPassword()));
        UserDetails user=customUserDetailService.findByUserName(deleteBookDTO.getName());
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        boolean isAdmin = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));

        boolean isUser = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("USER"));
        if(isAdmin){
            return bookService.deleteBook(deleteBookDTO.getBookName());
        }
        else {
            return "You don't have permission to add book talk to admins please!!!";
        }
    }
}

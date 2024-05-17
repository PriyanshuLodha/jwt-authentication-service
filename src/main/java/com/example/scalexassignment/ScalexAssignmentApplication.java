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

    /auth/signup -  To add user to the application.

    /auth/login -  To login user to the application provided with auth token and refresh token.

    /auth/refresh -  To refresh the token for the user.

   Thank You!!!
*/
package com.example.scalexassignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScalexAssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScalexAssignmentApplication.class, args);
	}

}

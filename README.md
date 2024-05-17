# scalex-assginment-backend-developer

# Library Management System

This project is a simple library management system with authentication and authorization features built using Node.js and Express.

## Features

- **User Authentication**: Users can log in with their credentials and receive a JWT token for authorization.
- **Role-based Access Control**: There are two types of users: Admin and Regular. Each user type has different access permissions.
- **View Books**: Regular users can view a list of books available in the library. Admin users can view all books.
- **Add Book**: Only Admin users can add books to the library.
- **Delete Book**: Only Admin users can delete books from the library.

## port: 8081

Developer -

Priyanshu Lodha
priyanshulodha181@gmail.com
+91-9672696274

## Endpoints

- `/login`: POST endpoint for user authentication. Returns a JWT token upon successful login.
- `/home`: GET endpoint to view a list of books based on the user type.
- `/addBook`: POST endpoint to add a new book to the library.
- `/deleteBook`: POST endpoint to delete a book from the library.

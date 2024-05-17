package com.example.scalexassignment.service;

import com.example.scalexassignment.dto.AddBookDTO;
import com.example.scalexassignment.entity.Book;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    public List<Book> readCSV(String role) {
        List<Book> books = new ArrayList<>();
        List<Book> userRecords=readUserCSV();
        List<Book> adminRecords=readAdminCSV();
        if(role.equals("ADMIN")){
            books.addAll(userRecords);
            books.addAll(adminRecords);
        }
        if(role.equals("USER")){

            books.addAll(userRecords);
        }
        return books;
    }
    private List<Book> readAdminCSV() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Priyanshu\\OneDrive\\Desktop\\Training\\scalex-assignment\\src\\main\\java\\com\\example\\scalexassignment\\records\\adminUser.csv"))) {
            String line;
            // Skip header line
            br.readLine();
            while ((line = br.readLine()) != null) {
                // Split the line by comma delimiter
                String[] parts = line.split(",");
                // Check if parts has enough elements
                if (parts.length >= 3) {
                    // Parse the parts and create a Book object
                    Book book = new Book(parts[0], parts[1], parts[2]); // trim to remove leading/trailing whitespace
                    books.add(book);
                } else {
                    // Handle invalid line format
                    System.err.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }
        return books;
    }
    private List<Book> readUserCSV() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Priyanshu\\OneDrive\\Desktop\\Training\\scalex-assignment\\src\\main\\java\\com\\example\\scalexassignment\\records\\regularUser.csv"))) {
            String line;
            // Skip header line
            br.readLine();
            while ((line = br.readLine()) != null) {
                // Split the line by comma delimiter
                String[] parts = line.split(",");
                // Check if parts has enough elements
                if (parts.length >= 3) {
                    // Parse the parts and create a Book object
                    Book book = new Book(parts[0], parts[1], parts[2]); // trim to remove leading/trailing whitespace
                    books.add(book);
                } else {
                    // Handle invalid line format
                    System.err.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }
        return books;
    }
    public String addBook(AddBookDTO addBookDTO){
        String bookName = addBookDTO.getBookName();
        String author = addBookDTO.getAuthor();
        int publicationYear = addBookDTO.getPublicationYear();

        if (StringUtils.isBlank(bookName) || StringUtils.isBlank(author)) {
            return "Book name and author cannot be empty.";
        }

        if (publicationYear < 1000 || publicationYear > LocalDate.now().getYear()) {
            return "Invalid publication year.";
        }

        // Add book to regularUser.csv
        addBookToRegularUserCSV(bookName, author, publicationYear);


        return "Book added successfully.";
    }
    public void addBookToRegularUserCSV(String bookName, String author, int publicationYear) {
        // Create a new book entry
        Book book = new Book(bookName, author, Integer.toString(publicationYear));

        // Specify the file path
        String filePath = "C:\\Users\\Priyanshu\\OneDrive\\Desktop\\Training\\scalex-assignment\\src\\main\\java\\com\\example\\scalexassignment\\records\\regularUser.csv";

        // Write the book entry to the regularUser.csv file
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(toCSVString(bookName, author, Integer.toString(publicationYear)));
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }
    }

    public String toCSVString(String name,String author,String publicationYear) {
        return String.format("%s,%s,%s", name, author, publicationYear);
    }
    public String deleteBook(String bookName) {
        if (StringUtils.isBlank(bookName)) {
            return "Book name cannot be empty.";
        }

        String filePath = "C:\\Users\\Priyanshu\\OneDrive\\Desktop\\Training\\scalex-assignment\\src\\main\\java\\com\\example\\scalexassignment\\records\\regularUser.csv";
        File inputFile = new File(filePath);
        File tempFile = new File("tempRegularUser.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {

            String line;
            boolean found = false;
            writer.println("Book Name,Author,Publication Year"); // Write header
            reader.readLine(); // Skip header in the original file

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String currentBookName = parts[0].trim();
                    System.out.println("Comparing: '" + currentBookName + "' with '" + bookName + "'");
                    if (currentBookName.equalsIgnoreCase(bookName.trim())) {
                        found = true;
                        continue; // Skip this line to delete the book
                    }
                } else {
                    System.err.println("Invalid line format: " + line);
                }
                writer.println(line);
            }

            if (!found) {
                return "Book not found.";
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "Error processing the file.";
        }

        if (!inputFile.delete()) {
            return "Could not delete the original file.";
        }

        if (!tempFile.renameTo(inputFile)) {
            return "Could not rename the temporary file.";
        }

        return "Book deleted successfully.";
    }

}

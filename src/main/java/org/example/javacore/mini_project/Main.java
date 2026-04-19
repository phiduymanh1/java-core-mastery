package org.example.javacore.mini_project;

import org.example.javacore.mini_project.model.Book;
import org.example.javacore.mini_project.model.Library;
import org.example.javacore.mini_project.model.Person;
import org.example.javacore.mini_project.model.Student;
import org.example.javacore.mini_project.model.Teacher;
import org.example.javacore.mini_project.service.LibraryService;

public class Main {
    public static void main(String[] args) {

        Library library = new Library();
        LibraryService service = new LibraryService();

        Book b1 = new Book(1, "Java Core");
        Book b2 = new Book(2, "OOP Design");

        library.addBook(b1);
        library.addBook(b2);

        Person student = new Student(1, "Manh");
        Person teacher = new Teacher(2, "An");

        service.borrowBook(student, b1);
        service.borrowBook(student, b2);
        service.borrowBook(student, new Book(3, "Extra")); // vượt limit

        service.borrowBook(teacher, b2);
    }
}

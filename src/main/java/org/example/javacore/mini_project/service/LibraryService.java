
package org.example.javacore.mini_project.service;

import org.example.javacore.mini_project.model.Book;
import org.example.javacore.mini_project.model.Person;

public class LibraryService {

    public void borrowBook(Person person, Book book) {

        if (book.isBorrowed()) {
            System.out.println("Book already borrowed!");
            return;
        }

        if (person.getBorrowedCount() >= person.getMaxBooks()) {
            System.out.println(person.getName() + " reached max limit!");
            return;
        }

        book.borrow();
        person.borrowBook(book);

        System.out.println(person.getName() + " borrowed " + book.getTitle());
    }

    public void returnBook(Person person, Book book) {
        book.giveBack();
        person.returnBook(book);

        System.out.println(person.getName() + " returned " + book.getTitle());
    }
}
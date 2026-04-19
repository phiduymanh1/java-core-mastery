package org.example.javacore.mini_project.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Person {
    protected int id;
    protected String name;
    protected List<Book> borrowedBooks = new ArrayList<>();

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public abstract int getMaxBooks();

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    public int getBorrowedCount() {
        return borrowedBooks.size();
    }

    public String getName() {
        return name;
    }
}

package org.example.javacore.mini_project.model;

public class Student extends Person {

    public Student(int id, String name) {
        super(id, name);
    }

    @Override
    public int getMaxBooks() {
        return 3;
    }
}

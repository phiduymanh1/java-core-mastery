package org.example.javacore.mini_project.model;

public class Teacher extends Person {

    public Teacher(int id, String name) {
        super(id, name);
    }

    @Override
    public int getMaxBooks() {
        return 5;
    }
}

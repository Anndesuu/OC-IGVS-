package com.example.instructorsgradeviewsystem;

public class Item {
    String name;
    String student_num;

    public Item(String name, String student_num) {
        this.name = name;
        this.student_num = student_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudent_num() {
        return student_num;
    }

    public void setStudent_num(String student_num) {
        this.student_num = student_num;
    }
}

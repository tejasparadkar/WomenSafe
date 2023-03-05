package com.example.BSafe;

public class ModelClass {
    String name, number;

    public ModelClass(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "ModelClass{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}

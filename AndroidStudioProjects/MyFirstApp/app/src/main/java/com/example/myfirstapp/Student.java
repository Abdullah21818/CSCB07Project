package com.example.myfirstapp;


public class Student {
    int id;
    String name;

    public Student(){

    }

    @Override
    public String toString(){
        return "Student{"+
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Student(int id, String name){
        this.id=id;
        this.name = name;

    }

    //Need setters and getters

    //CAN BE DONE AUTOMATICALLY
    /*
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    */



    public void setId(int id) {this.id=id;}

    public void setName(String name) {this.name = name;}

    public int getId() {return id;}

    public String getName(){ return name;}

}

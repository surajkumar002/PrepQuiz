package com.codewithharry.prepquiz;

public class Quiz {
    private String name;

    private String time;

    public Quiz(){

    }

    public Quiz(String name, String time){
        this.name = name;
        this.time = time;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}

package com.codewithharry.prepquiz;

public class User {
    private String name;
    private boolean teacher;
    private String code;

    public User(){

    }

    public User(String name,boolean teacher,String code){
        this.name = name;
        this.teacher = teacher;
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeacher(boolean teacher) {
        this.teacher = teacher;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public boolean getTeacher() {
        return teacher;
    }

    public String getCode() {
        return code;
    }
}

package com.codewithharry.prepquiz;

import java.io.Serializable;

public class Question implements Serializable {

    public String question;
    public String option_a;
    public String option_b;
    public String option_c;
    public String option_d;
    public String correct_answer;

    public Question(){ }

    public void use(){

    }

    Question(String q,String a,String b,String c,String d,String c_a){
        question = q;
        option_a = a;
        option_b = b;
        option_c = c;
        option_d = d;
        correct_answer = c_a;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public String getOption_a() {
        return option_a;
    }

    public void setOption_a(String option_a) {
        this.option_a = option_a;
    }


    public String getOption_b() {
        return option_b;
    }

    public void setOption_b(String option_b) {
        this.option_b= option_b;
    }



    public String getOption_c() {
        return option_c;
    }

    public void setOption_c(String option_c) {
        this.option_c = option_c;
    }



    public String getOption_d() {
        return option_d;
    }

    public void setOption_d(String option_d) {
        this.option_d = option_d;
    }



    public String getCorrect_answer() { return correct_answer; }

    public void setCorrect_answer(String correct_answer) { this.correct_answer = correct_answer; }
}

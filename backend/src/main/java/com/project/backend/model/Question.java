package com.project.backend.model;

import java.util.*;

public class Question {
    String question;
    String answer;
    int marks;
    ArrayList<String> options;

    public Question(String question, String answer, int marks, ArrayList<String> options) {
        this.question = question;
        this.answer = answer;
        this.marks = marks;
        this.options = options;
    }

    int evaluateQuestion(String markedAnswer) {
        if(markedAnswer == this.answer) {
            return marks;
        }
        return 0;
    }

    String getQuestion() {
        return this.question;
    }

    String getAnswer() {
        return this.answer;
    }

    int getMarks() {
        return this.marks;
    }

    ArrayList<String> getOptions() {
        return this.options;
    }
}

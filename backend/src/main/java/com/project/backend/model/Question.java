package com.project.backend.model;

import java.util.*;

public class Question {
    String questionId;
    String question;
    String answer;
    int marks;
    ArrayList<String> options;

    public Question(String questionId, String question, String answer, int marks, ArrayList<String> options) {
        this.questionId = questionId;
        this.question = question;
        this.answer = answer;
        this.marks = marks;
        this.options = options;
    }

    public Question(String question, String answer, int marks, ArrayList<String> options) {
        this.question = question;
        this.answer = answer;
        this.marks = marks;
        this.options = options;
    }

    public int evaluateQuestion(String markedAnswer) {
        if(markedAnswer == this.answer) {
            return marks;
        }
        return 0;
    }

    public String getQuestionId() {
        return this.questionId;
    }

    public String getQuestion() {
        return this.question;
    }

    public String getAnswer() {
        return this.answer;
    }

    public int getMarks() {
        return this.marks;
    }

    public ArrayList<String> getOptions() {
        return this.options;
    }
}

package com.project.backend.controller;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.backend.model.*;


@Controller
public class TeacherController {

    TestDatabaseModel testDatabaseModel = null;

    TeacherController() {
        testDatabaseModel = new TestDatabaseModel();
    }

    @GetMapping(path = "/teacherDashboard")
    String viewTeacherDashboard(@RequestParam String teacherId, Model model) {
        model.addAttribute("teacherId", teacherId);
        if(testDatabaseModel.checkTeacherId(teacherId) == false) {
            return "invalidTeacher";
        }
        return "teacherDashboard";
    }

    @GetMapping(path = "/uploadTest")
    String uploadTest(@RequestParam String teacherId, Model model) {
        if(testDatabaseModel.checkTeacherId(teacherId) == false) {
            return "invalidTeacher";
        }
        return "uploadTest";
    }

    @SuppressWarnings("unchecked")
    @PostMapping(path = "/submitTest")
    String submitTest(@RequestBody Map<String, Object> body, Model model) {
        System.out.println(body);

        ArrayList<Question> questions = new ArrayList<Question>();
        ArrayList<String> question = (ArrayList<String>)body.get("questions");
        ArrayList<String> answers = (ArrayList<String>)body.get("answers");
        ArrayList<ArrayList<String>> options = (ArrayList<ArrayList<String>>)body.get("options");
        
        for(int i = 0; i < question.size(); i++) {
            questions.add(new Question(question.get(i), answers.get(i), 4, options.get(i)));
        }
        
        Test test = new Test(
            "T69",
            (String)body.get("subject"),
            (String)body.get("difficulty"),
            (String)body.get("startDateTime"),
            (String)body.get("endDateTime"),
            (String)body.get("description"),
            false,
            questions
        );

        int oldQuestionCount = testDatabaseModel.storeQuestions(questions);
        testDatabaseModel.storeTest(oldQuestionCount, test);

        return "redirect:" + "/teacherDashboard";
    }
}
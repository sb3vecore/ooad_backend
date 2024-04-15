package com.project.backend.controller;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.backend.model.*;

@Controller
public class StudentController {

    TestDatabaseModel testDatabaseModel = null;

    StudentController() {
        testDatabaseModel = new TestDatabaseModel();
    }

    @GetMapping(path = "/studentDashboard")
    String viewStudentDashboard(@RequestParam String SRN, Model model) {
        Student student = new Student();
        if(student.checkStudentSRN(SRN) == false) {
            return "invalidStudent";
        }
        ArrayList<Test> tests = testDatabaseModel.retrieveAllTests();
        model.addAttribute("tests", tests);
        return "studentDashboard";
    }

}

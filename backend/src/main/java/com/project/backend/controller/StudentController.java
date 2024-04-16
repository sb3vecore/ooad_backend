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
public class StudentController {

    TestDatabaseModel testDatabaseModel = null;
    StudentDatabaseModel studentDatabaseModel = null;

    StudentController() {
        testDatabaseModel = new TestDatabaseModel();
        studentDatabaseModel = new StudentDatabaseModel();
    }

    @GetMapping(path = "/studentDashboard")
    String viewStudentDashboard(@RequestParam String SRN, Model model) {
        Student student = new Student();
        model.addAttribute("SRN", SRN);
        if(student.checkStudentSRN(SRN) == false) {
            return "invalidStudent";
        }
        ArrayList<Test> tests = testDatabaseModel.retrieveAllValidTests(SRN);
        model.addAttribute("tests", tests);
        return "studentDashboard";
    }

    @GetMapping(path = "/takeTest")
    String takeTest(@RequestParam String SRN, @RequestParam String testId, Model model) {
        Student student = new Student();
        if(student.checkStudentSRN(SRN) == false) {
            return "invalidStudent";
        }
        Test test = testDatabaseModel.getTestDetails(testId);
        model.addAttribute("test", test);
        return "takeTest";
    }

    @SuppressWarnings("unchecked")
    @PostMapping(path = "/submitStudentTest")
    String submitStudentTest(@RequestBody Map<String, Object> body, Model model) {
        System.out.println(body);
        
        Result result = new Result((String)body.get("SRN"), (String)body.get("testId"), (Map<String, String>)body.get("markedOptions"));
        studentDatabaseModel.storeStudentResponses(result);
        studentDatabaseModel.calculateStudentTotal(result);
        studentDatabaseModel.storeTestResult(result);
        return String.format("redirect:/studentDashboard?SRN=%s", body.get("SRN"));
    }

    @GetMapping(path = "/viewPreviousTests")
    String viewPreviousTests(@RequestParam String SRN, Model model) {
        PrevTests prevTests = new PrevTests();
        List<Map<String, Object>> tests = prevTests.retrieveAllTests(SRN);
        model.addAttribute("tests", tests);
        return "viewPreviousTests";
    }

    @GetMapping(path = "/viewTestResult")
    String viewTestResult(@RequestParam String SRN, @RequestParam String testId, Model model) {
        model.addAttribute("testResult", this.studentDatabaseModel.getTestResult(SRN, testId));
        return "showTestResult";
    }

    @GetMapping(path = "/reviewTest")
    public String reviewTest(@RequestParam String SRN, @RequestParam String testId, Model model) {
        List<Map<String, Object>> testDetails = studentDatabaseModel.getTestDetails(SRN, testId);
        model.addAttribute("testDetails", testDetails);
        return "reviewTest";
    }

}
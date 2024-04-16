package com.project.backend.controller;

import com.project.backend.model.Admin;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class AdminController {

    @GetMapping("/adminDashboard")
    public String viewPendingTests(Model model, @RequestParam String adminId, HttpSession session) { // Use @PathVariable for path variables
        model.addAttribute("adminId", adminId);

        Admin admin = (Admin) session.getAttribute("admin");
        // System.out.println(admin.getAdminId());
        System.out.println("Admin Dashboard for: " + admin.getAdminId());


        ArrayList<String[]> pendingTestData = admin.getPendingTestData();
        ArrayList<String[]> acceptedTestData = admin.getAcceptedTestData();

        model.addAttribute("pendingTestList", pendingTestData.subList(1, pendingTestData.size()));
        model.addAttribute("acceptedTestList", acceptedTestData.subList(1, acceptedTestData.size()));
        return "adminDashboard";
    }

    @GetMapping("/updateTestForm")
    public String updateForm(@RequestParam String testId, Model model) {
        model.addAttribute("testId", testId);
        return "updateTestForm";
    }

    @PostMapping("/updateTest")
    public String updateTest(@RequestParam String testId, @RequestParam String difficulty,
            @RequestParam String startDateTime, @RequestParam String endDateTime, HttpSession session) {

        Admin admin = (Admin) session.getAttribute("admin");
        System.out.println(admin.getAdminId());
        admin.updateTest(testId, difficulty, startDateTime, endDateTime);
        String redirectUrl = UriComponentsBuilder.fromPath("/adminDashboard")
                .queryParam("adminId", admin.getAdminId())
                .toUriString();

        return "redirect:" + redirectUrl;
    }

    @PostMapping("/acceptTest")
    public String acceptTest(@RequestParam String testId, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");
        admin.acceptTest(testId);

        String redirectUrl = UriComponentsBuilder.fromPath("/adminDashboard")
                .queryParam("adminId", admin.getAdminId())
                .toUriString();

        return "redirect:" + redirectUrl;
    }
}

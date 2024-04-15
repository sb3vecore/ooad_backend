package com.project.backend.controller;

import com.project.backend.model.Admin;

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
    public String viewPendingTests(Model model, @RequestParam String adminId) { // Use @PathVariable for path variables
        System.out.println("Admin Dashboard for: " + adminId);
        model.addAttribute("adminId", adminId);

        Admin a = new Admin();
        a.setAdminId(adminId);
        ArrayList<String[]> pendingTestData = a.getPendingTestData();
        ArrayList<String[]> acceptedTestData = a.getAcceptedTestData();

        model.addAttribute("pendingTestList", pendingTestData.subList(1, pendingTestData.size()));
        model.addAttribute("acceptedTestList", acceptedTestData.subList(1, acceptedTestData.size()));
        return "adminDashboard";
    }

    @GetMapping("/updateTestForm")
    public String updateForm(@RequestParam String testId, Model model) {
        model.addAttribute("testId", testId);
        System.out.println("heeeeeeeee");
        return "updateTestForm";
    }

    @PostMapping("/updateTest")
    public String updateTest(@RequestParam String testId, @RequestParam String difficulty,
            @RequestParam String startDateTime, @RequestParam String endDateTime) {

        Admin a2 = new Admin();
        a2.setAdminId("AD001");
        a2.updateTest(testId, difficulty, startDateTime, endDateTime);
        String redirectUrl = UriComponentsBuilder.fromPath("/adminDashboard")
                .queryParam("adminId", a2.getAdminId())
                .toUriString();

        // Redirect to the constructed URL
        return "redirect:" + redirectUrl;
    }

    @PostMapping("/acceptTest")
    public String acceptTest(@RequestParam String testId) {
        Admin a2 = new Admin();
        a2.setAdminId("AD001");
        a2.acceptTest(testId);
        String redirectUrl = UriComponentsBuilder.fromPath("/adminDashboard")
                .queryParam("adminId", a2.getAdminId())
                .toUriString();

        // Redirect to the constructed URL
        return "redirect:" + redirectUrl;
    }
    

}

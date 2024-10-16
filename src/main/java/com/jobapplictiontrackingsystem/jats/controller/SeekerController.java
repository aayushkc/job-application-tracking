package com.jobapplictiontrackingsystem.jats.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jobapplictiontrackingsystem.jats.entity.JobApplication;
import com.jobapplictiontrackingsystem.jats.entity.Jobs;
import com.jobapplictiontrackingsystem.jats.entity.User;
import com.jobapplictiontrackingsystem.jats.services.JobService;
import com.jobapplictiontrackingsystem.jats.services.impl.JobApplicationServiceImpl;

@Controller
@RequestMapping("/seeker")
public class SeekerController {

    @Autowired
    private JobService jobService;

    @Autowired
    private JobApplicationServiceImpl jobApplicationService;

    // View available jobs
    @GetMapping("/jobs")
    public String viewJobs(Model model) {
        List<Jobs> jobs = jobService.getAllJobs();
        model.addAttribute("jobs", jobs);
        return "seeker/jobs";
    }

    // Apply for a job
    @PostMapping("/apply/{jobId}")
    public String applyForJob(@PathVariable Long jobId, @AuthenticationPrincipal User user) {
        Optional<Jobs> job = jobService.getJobById(jobId);
        if (job.isPresent()) {
            jobApplicationService.applyForJob(job.get(), user);
        }
        return "redirect:/seeker/applied-jobs";
    }

    // View applied jobs and status
    @GetMapping("/applied-jobs")
    public String viewAppliedJobs(@AuthenticationPrincipal User user, Model model) {
        List<JobApplication> applications = jobApplicationService.getApplicationsForSeeker(user);
        model.addAttribute("applications", applications);
        return "seeker/applied-jobs";
    }
}

package com.jobapplictiontrackingsystem.jats.controller;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jobapplictiontrackingsystem.jats.entity.JobApplication;
import com.jobapplictiontrackingsystem.jats.entity.JobApplication.ApplicationStatus;
import com.jobapplictiontrackingsystem.jats.entity.Jobs;
import com.jobapplictiontrackingsystem.jats.entity.User;
import com.jobapplictiontrackingsystem.jats.services.impl.JobApplicationServiceImpl;
import com.jobapplictiontrackingsystem.jats.services.impl.JobServiceImpl;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/recruiter")
public class RecruiterController {

    @Autowired
    JobServiceImpl jobService;

    @Autowired
    private JobApplicationServiceImpl jobApplicationService;

    @GetMapping
    public String recruiterHome(@AuthenticationPrincipal User recruiter,Model model){
        List<Jobs> jobs = jobService.getJobsForRecruiter(recruiter);
        model.addAttribute("jobs",jobs);
        return "recruiter/index";
    }

    @GetMapping("/job/{id}")
    public String getJobById(@PathVariable Long id, Model model) {
        Optional<Jobs> job = jobService.getJobById(id);
        if (job.isPresent()) {
            model.addAttribute("job", job.get()); 
        } else {
            model.addAttribute("job", new Jobs()); 
            model.addAttribute("error", "Job not found");
        }
        return "recruiter/viewJob";
    }

    @GetMapping("/add-job")
    public String createJobForm(Model model) {
        model.addAttribute("job", new Jobs());
        return "recruiter/addJob";
    }

    @PostMapping("/add-job")
    public String createJob(@Valid @ModelAttribute("job") Jobs job, BindingResult result,@AuthenticationPrincipal User recruiter, Model model) {
        if(result.hasErrors()){
            model.addAttribute("job", job);
            return "recruiter/addJob";
        }
        job.setCompany(recruiter.getUserName());
        job.setRecruiter(recruiter);
        jobService.createJob(job);
        return "redirect:/recruiter";
    }

    @GetMapping("/edit/{id}")
    public String editJobForm(@PathVariable Long id, Model model) {
        Optional<Jobs> job= jobService.getJobById(id);
        if (job.isPresent()) {
            model.addAttribute("job", job.get()); 
        } else {
            model.addAttribute("job", null); 
        }
        return "recruiter/editJob";
    }

    @PostMapping("/{id}")
    public String updateJob(@PathVariable Long id,@AuthenticationPrincipal User recruiter, @ModelAttribute Jobs job) {
        job.setRecruiter(recruiter);
        job.setCompany(recruiter.getUserName());
        jobService.updateJob(id, job);
        return "redirect:/recruiter";
    }

    @GetMapping("/delete/{id}")
    public String deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return "redirect:/recruiter";
    }
    
    @GetMapping("/job/{jobId}/applications")
    public String viewJobApplications(@PathVariable Long jobId, Model model) {
        Optional<Jobs> job = jobService.getJobById(jobId);
        if (job.isPresent()) {
            List<JobApplication> applications = jobApplicationService.getApplicationsForJob(job.get());
            model.addAttribute("applications", applications);
        }
        return "recruiter/job-applications";
    }

    // Update job application status
    @PostMapping("/application/{applicationId}/status")
    public String updateApplicationStatus(@PathVariable Long applicationId, @RequestParam ApplicationStatus status) {
        jobApplicationService.updateApplicationStatus(applicationId, status);
        return "redirect:/recruiter";
    }
}

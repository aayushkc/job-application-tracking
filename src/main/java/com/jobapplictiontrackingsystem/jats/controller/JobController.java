package com.jobapplictiontrackingsystem.jats.controller;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.jobapplictiontrackingsystem.jats.entity.Jobs;
import com.jobapplictiontrackingsystem.jats.services.JobService;

@Controller
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping
    public String getAllJobs(Model model) {
        model.addAttribute("jobs", jobService.getAllJobs());
        return "jobs/list";
    }

    @GetMapping("/{id}")
    public String getJobById(@PathVariable Long id, Model model) {
        Optional<Jobs> job = jobService.getJobById(id);
        if (job.isPresent()) {
            model.addAttribute("job", job.get()); 
        } else {
            model.addAttribute("job", new Jobs()); 
            model.addAttribute("error", "Job not found");
        }
        return "jobs/viewJob";
    }

    @GetMapping("/add-job")
    public String createJobForm(Model model) {
        model.addAttribute("job", new Jobs());
        return "jobs/addJob";
    }

    @PostMapping
    public String createJob(@ModelAttribute Jobs job) {
        jobService.createJob(job);
        return "redirect:/jobs";
    }

    @GetMapping("/edit/{id}")
    public String editJobForm(@PathVariable Long id, Model model) {
        Optional<Jobs> job= jobService.getJobById(id);
        if (job.isPresent()) {
            model.addAttribute("job", job.get()); 
        } else {
            model.addAttribute("job", null); 
        }
        return "jobs/editJob";
    }

    @PostMapping("/{id}")
    public String updateJob(@PathVariable Long id, @ModelAttribute Jobs job) {
        jobService.updateJob(id, job);
        return "redirect:/jobs";
    }

    @GetMapping("/delete/{id}")
    public String deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return "redirect:/jobs";
    }
}

package com.jobapplictiontrackingsystem.jats.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobapplictiontrackingsystem.jats.entity.JobApplication;
import com.jobapplictiontrackingsystem.jats.entity.Jobs;
import com.jobapplictiontrackingsystem.jats.entity.User;
import com.jobapplictiontrackingsystem.jats.entity.JobApplication.ApplicationStatus;
import com.jobapplictiontrackingsystem.jats.repo.JobApplicationRepository;

@Service
public class JobApplicationServiceImpl {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    public List<JobApplication> getApplicationsForSeeker(User seeker) {
        return jobApplicationRepository.findBySeeker(seeker);
    }

    public List<JobApplication> getApplicationsForJob(Jobs job) {
        return jobApplicationRepository.findByJob(job);
    }

    public void applyForJob(Jobs job, User seeker) {
        JobApplication application = new JobApplication();
        application.setJob(job);
        application.setSeeker(seeker);
        jobApplicationRepository.save(application);
    }

    public void updateApplicationStatus(Long applicationId, ApplicationStatus status) {
        JobApplication application = jobApplicationRepository.findById(applicationId).orElse(null);
        if (application != null) {
            application.setStatus(status);
            jobApplicationRepository.save(application);
        }
    }
}

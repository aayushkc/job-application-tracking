package com.jobapplictiontrackingsystem.jats.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobapplictiontrackingsystem.jats.entity.Jobs;
import com.jobapplictiontrackingsystem.jats.entity.User;
import com.jobapplictiontrackingsystem.jats.repo.JobRepository;
import com.jobapplictiontrackingsystem.jats.services.JobService;

@Service
public class JobServiceImpl implements JobService{

    @Autowired
    JobRepository jobRepo;

    public List<Jobs> getJobsForRecruiter(User recruiter) {
        return jobRepo.findByRecruiter(recruiter);
    }

    @Override
    public List<Jobs> getAllJobs() {
        return jobRepo.findAll();
    }

    @Override
    public Optional<Jobs> getJobById(Long id) {

        return jobRepo.findById(id);
    }

    @Override
    public Jobs updateJob(Long id,Jobs job) {
        return jobRepo.save(job);
    }

    @Override
    public Jobs createJob(Jobs job) {
       return jobRepo.save(job);
    }

    @Override
    public void deleteJob(Long id) {
        jobRepo.deleteById(id);
    }
    
}

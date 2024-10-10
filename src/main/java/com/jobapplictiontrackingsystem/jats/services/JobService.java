package com.jobapplictiontrackingsystem.jats.services;

import java.util.List;
import java.util.Optional;

import com.jobapplictiontrackingsystem.jats.entity.Jobs;


public interface JobService {
    List<Jobs> getAllJobs();

    Optional<Jobs> getJobById(Long id);
    Jobs updateJob(Long id,Jobs job);
    Jobs createJob(Jobs job);
    void deleteJob(Long id);

}

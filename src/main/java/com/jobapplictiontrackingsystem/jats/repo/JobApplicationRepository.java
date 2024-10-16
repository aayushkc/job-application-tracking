package com.jobapplictiontrackingsystem.jats.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobapplictiontrackingsystem.jats.entity.JobApplication;
import com.jobapplictiontrackingsystem.jats.entity.Jobs;
import com.jobapplictiontrackingsystem.jats.entity.User;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findBySeeker(User seeker);
    List<JobApplication> findByJob(Jobs job);
}


package com.jobapplictiontrackingsystem.jats.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobapplictiontrackingsystem.jats.entity.Jobs;
import com.jobapplictiontrackingsystem.jats.entity.User;

@Repository
public interface JobRepository extends JpaRepository<Jobs, Long> {
     List<Jobs> findByRecruiter(User recruiter);
}

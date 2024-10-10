package com.jobapplictiontrackingsystem.jats.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobapplictiontrackingsystem.jats.entity.Jobs;

@Repository
public interface JobRepository extends JpaRepository<Jobs, Long> {
    
}

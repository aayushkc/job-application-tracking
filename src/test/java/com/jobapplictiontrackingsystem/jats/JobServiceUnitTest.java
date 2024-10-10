package com.jobapplictiontrackingsystem.jats;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.jobapplictiontrackingsystem.jats.entity.Jobs;
import com.jobapplictiontrackingsystem.jats.repo.JobRepository;
import com.jobapplictiontrackingsystem.jats.services.impl.JobServiceImpl;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness =  Strictness.LENIENT)
public class JobServiceUnitTest {

    @Mock
    JobRepository jobRepo;

    @InjectMocks
    JobServiceImpl jobServiceImpl;

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        
        List<Jobs> jobList = new ArrayList<>();
        Jobs job1 = new Jobs();
        job1.setTitle("Software Developer");
        jobList.add(job1);
        Mockito.when(jobRepo.save(any(Jobs.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.when(jobRepo.findAll()).thenReturn(jobList);
        Mockito.when(jobRepo.findById(1L)).thenReturn(Optional.of(job1));
        Mockito.when(jobRepo.findById(99L)).thenReturn(Optional.empty());  // for failure case
        Mockito.doNothing().when(jobRepo).deleteById(any());
    }

    // Success and Failure Test Cases for Create

    @Test
    void testCreateJobSuccess() {
        Jobs job1 = new Jobs();
        job1.setTitle("Data Scientist");
        job1.setDescription("Analyze data for insights.");
        job1.setCompany("tech solution");
        job1.setSalary(1234L);

        Jobs createdJob = jobServiceImpl.createJob(job1);
        assertEquals("Data Scientist", createdJob.getTitle());
        assertThatNoException();
    }

    @Test
    void testCreateJobFailureValidation() {
        Jobs job1 = new Jobs();
        job1.setTitle(null);  // Invalid title for testing failure case
        job1.setDescription("Analyze data for insights.");

        Set<ConstraintViolation<Jobs>> violations = validator.validate(job1);
        assertTrue(!violations.isEmpty());
    }

    // Success and Failure Test Cases for Fetching All Jobs

    @Test
    void testGetAllJobsSuccess() {
        List<Jobs> jobList = jobServiceImpl.getAllJobs();
        assertEquals(1, jobList.size());
        assertEquals("Software Developer", jobList.get(0).getTitle());
    }

    @Test
    void testGetAllJobsEmptyList() {
        Mockito.when(jobRepo.findAll()).thenReturn(new ArrayList<>());  // Simulating no jobs
        List<Jobs> jobList = jobServiceImpl.getAllJobs();
        assertTrue(jobList.isEmpty());
    }

    // Success and Failure Test Cases for Fetching a Job by ID

    @Test
    void testGetJobByIdSuccess() {
        Optional<Jobs> job = jobServiceImpl.getJobById(1L);
        assertTrue(job.isPresent());
        assertEquals("Software Developer", job.get().getTitle());
    }

    @Test
    void testGetJobByIdFailureNotFound() {
        Optional<Jobs> job = jobServiceImpl.getJobById(99L);  // Simulating job not found
        assertTrue(job.isEmpty());
    }

    // Success and Failure Test Cases for Updating a Job

    @Test
    void testUpdateJobSuccess() {
        Jobs job = new Jobs();
        job.setTitle("Updated Title");
        job.setDescription("Updated Description");

        Jobs updatedJob = jobServiceImpl.updateJob(1L, job);
        assertEquals("Updated Title", updatedJob.getTitle());
    }

    @Test
    void testUpdateJobFailureInvalidData() {
        Jobs job = new Jobs();
        job.setTitle(null);  // Invalid title for testing failure case

        Set<ConstraintViolation<Jobs>> violations = validator.validate(job);
        assertTrue(!violations.isEmpty());
    }

    // Success and Failure Test Cases for Deleting a Job

    @Test
    void testDeleteJobSuccess() {
        jobServiceImpl.deleteJob(1L);
        assertThatNoException();
    }

    @Test
    void testDeleteJobFailureJobNotFound() {
        Mockito.doThrow(new RuntimeException("Job not found")).when(jobRepo).deleteById(99L);  // Simulating deletion failure
        assertThrows(RuntimeException.class, () -> jobServiceImpl.deleteJob(99L));
    }
}

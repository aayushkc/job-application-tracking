package com.jobapplictiontrackingsystem.jats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.jobapplictiontrackingsystem.jats.entity.Jobs;
import com.jobapplictiontrackingsystem.jats.repo.JobRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = JatsApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class JobControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JobRepository repo;
     @BeforeEach
    public void setUp() {
        Jobs job1 = new Jobs();
        job1.setTitle("Software Developer");
        job1.setDescription("Develop software solutions.");
        job1.setCompany("Tech Corp");
        repo.save(job1);
    }

    @AfterEach
    public void tearDown() {
        repo.deleteAll();
    }

     @Test
    @WithMockUser(username = "admin", roles = { "RECRUITER" })
    void testGetJobByIdSuccess() throws Exception {
        // Fetch a job that was added during setup
        Jobs job = repo.findAll().get(0);

        // Perform GET request for the job by ID
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/recruiter/job/" + job.getId()))
                .andReturn();

        // Assert the response status and content
        assertEquals(200, result.getResponse().getStatus());
        Jobs fetchedJob = (Jobs) result.getModelAndView().getModel().get("job");
        assertNotNull(fetchedJob); // Ensure that the job is not null
        assertEquals("Software Developer", fetchedJob.getTitle()); // Validate title
        assertEquals("Tech Corp", fetchedJob.getCompany()); // Validate company
    }

    @Test
    @WithMockUser(username = "admin", roles = { "RECRUITER" })
    void testGetJobByIdFailureNotFound() throws Exception {
        
        // Attempt to fetch a job that doesn't exist
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/recruiter/job/99"))
                .andReturn();

        assertEquals(200, result.getResponse().getStatus());
        Object job = result.getModelAndView().getModel().get("error");
       
        assertEquals("Job not found", job);
        
    }

    
}

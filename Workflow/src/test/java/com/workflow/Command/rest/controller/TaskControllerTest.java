package com.workflow.Command.rest.controller;
import com.oga.workflow.command.rest.controller.TaskController;
import com.oga.workflow.command.rest.entities.Review;
import com.oga.workflow.command.rest.request.ProcessRequest;
import com.oga.workflow.command.rest.services.StartMessage;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskControllerTest {

    private MockMvc mockMvc;


    @Mock
    private StartMessage startMessage;

    private TaskController taskController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        taskController = new TaskController(startMessage);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    public void testStartProcess() throws Exception {
        // Arrange
        ProcessRequest processRequest = new ProcessRequest();
        processRequest.setProcessKey("BookReview-process");
        processRequest.setBookId("123");
        Review review = new Review();
        processRequest.setReview(review);

        ProcessInstance processInstance = mock(ProcessInstance.class);
        when(processInstance.getId()).thenReturn("BookReview-process");

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks/start")
                        .content("{\"processKey\": \"BookReview-process\", \"bookId\": 123, \"review\": {}}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("BookReview-process"));
    }



}

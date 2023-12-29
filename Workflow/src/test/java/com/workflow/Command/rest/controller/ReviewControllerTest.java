package com.workflow.Command.rest.controller;

import com.oga.cqrsref.infrastructure.CommandDispatcher;
import com.oga.workflow.command.commands.UpdateReviewCommand;
import com.oga.workflow.command.rest.controller.ReviewController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class ReviewControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CommandDispatcher commandDispatcher;

    private ReviewController reviewController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        reviewController = new ReviewController(commandDispatcher);
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
    }

/*    @Test
    public void testCreateReview() throws Exception {
        // Arrange
        CreateReviewCommand createReviewCommand = new CreateReviewCommand();
        when(commandDispatcher.send(createReviewCommand)).thenReturn(1);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/reviews/create")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(1));
    }*/

    @Test
    public void testUpdateReview() throws Exception {
        // Arrange
        UpdateReviewCommand updateReviewCommand = new UpdateReviewCommand();

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/reviews/update")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }
}

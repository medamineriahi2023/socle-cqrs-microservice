package com.workflow.Command.rest.controller;

import com.oga.cqrsref.infrastructure.CommandDispatcher;
import com.oga.workflow.command.rest.controller.BookController;
import org.junit.jupiter.api.Test;


import com.oga.workflow.command.commands.CreateBookCommand;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

class BookControllerTest {

    @Mock
    private CommandDispatcher commandDispatcher;

    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookController = new BookController(commandDispatcher);
    }

    @Test
    void testCreateBook() {
        // Arrange
        CreateBookCommand createBookCommand = new CreateBookCommand("123", "Book Title", "Author");

        // Mock the commandDispatcher to do nothing when dispatching the command
        doNothing().when(commandDispatcher).send(any(CreateBookCommand.class));

        // Act
        ResponseEntity<?> responseEntity = bookController.createBook(createBookCommand);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
    }

}

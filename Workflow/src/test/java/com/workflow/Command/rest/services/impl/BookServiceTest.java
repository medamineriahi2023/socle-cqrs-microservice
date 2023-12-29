package com.workflow.Command.rest.services.impl;


import com.oga.workflow.command.rest.entities.Book;
import com.oga.workflow.command.rest.entities.Review;
import com.oga.workflow.command.rest.repository.BookRepository;
import com.oga.workflow.command.rest.repository.ReviewRepository;
import com.oga.workflow.command.rest.services.impl.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class BookServiceTest {

    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        bookService = new BookService(bookRepository, reviewRepository);
    }

    @Test
    public void testCreateBook() {
        // Arrange
        Book book = new Book();
        book.setTitle("Book Title");
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Act
        Book createdBook = bookService.createBook(book);

        // Assert
        assertNotNull(createdBook);
        assertEquals(book.getTitle(), createdBook.getTitle());
    }

    @Test
    public void testGetBookById() {
        // Arrange
        int bookId = 1;
        Book book = new Book();
        book.setId(bookId);
        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));

        // Act
        Book retrievedBook = bookService.getBookById(bookId);

        // Assert
        assertNotNull(retrievedBook);
        assertEquals(bookId, retrievedBook.getId());
    }

    @Test
    public void testCreateReview() {
        // Arrange
        int bookId = 1;
        Book book = new Book();
        book.setId(bookId);
        Review review = new Review();
        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        // Act
        Review createdReview = bookService.createReview(bookId, review);

        // Assert
        assertNotNull(createdReview);
        assertEquals(book, createdReview.getBook());
    }

    @Test
    public void testPublishReview() {
        // Arrange
        int bookId = 1;
        int reviewId = 1;
        Book book = new Book();
        book.setId(bookId);
        Review review = new Review();
        review.setId(reviewId);
        review.setBook(book);
        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));
        when(reviewRepository.findById(anyInt())).thenReturn(Optional.of(review));

        // Act
        bookService.publishReview(bookId, reviewId);

        // Assert
        assertTrue(review.isPublished());
    }
}

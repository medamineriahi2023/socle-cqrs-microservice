package com.oga.workflow.command.rest.services.impl;

import com.oga.workflow.command.rest.entities.Book;
import com.oga.workflow.command.rest.entities.Review;
import com.oga.workflow.command.rest.repository.BookRepository;
import com.oga.workflow.command.rest.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implémentation des services liés aux entités Book et Review.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public BookService(BookRepository bookRepository, ReviewRepository reviewRepository) {
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
    }

    /**
     * Crée un nouveau livre.
     *
     * @param book L'objet Book contenant les détails du livre.
     * @return Le livre créé.
     */
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    /**
     * Récupère un livre par son identifiant.
     *
     * @param id L'identifiant du livre.
     * @return Le livre correspondant à l'identifiant, ou null s'il n'existe pas.
     */
    public Book getBookById(int id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.orElse(null);
    }

    /**
     * Crée une nouvelle critique pour un livre donné.
     *
     * @param bookId L'identifiant du livre.
     * @param review L'objet Review contenant les détails de la critique.
     * @return La critique créée, ou null si le livre n'existe pas.
     */
    public Review createReview(int bookId, Review review) {
        Book book = getBookById(bookId);
        if (book != null) {
            review.setBook(book);
            return reviewRepository.save(review);
        }
        return null;
    }

    /**
     * Publie une critique donnée pour un livre donné.
     *
     * @param bookId   L'identifiant du livre.
     * @param reviewId L'identifiant de la critique.
     */
    public void publishReview(int bookId, int reviewId) {
        Book book = getBookById(bookId);
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (book != null && review != null && review.getBook().equals(book)) {
            review.setPublished(true);
            reviewRepository.save(review);
        }
    }
}

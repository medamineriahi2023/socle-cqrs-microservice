package com.oga.workflow.command.handlers.impl;

import com.oga.cqrsref.commands.BaseCommand;
import com.oga.cqrsref.producer.EventProducer;
import com.oga.workflow.command.aggregate.BookAggregate;
import com.oga.workflow.command.aggregate.ReviewAggregate;
import com.oga.workflow.command.commands.CreateReviewCommand;
import com.oga.workflow.command.commands.UpdateReviewCommand;
import com.oga.workflow.command.events.impl.ReviewCreatedEvent;
import com.oga.workflow.command.events.impl.ReviewUpdatedEvent;
import com.oga.workflow.command.infrastructure.impl.ReviewEventHandler;
import com.oga.workflow.command.model.EventModel;
import com.oga.workflow.command.rest.entities.Book;
import com.oga.workflow.command.rest.entities.Review;
import com.oga.workflow.command.rest.repository.ReviewRepository;
import com.oga.workflow.command.rest.services.impl.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
@EnableJpaRepositories("com.oga.workflow.command.rest.repository")
@ComponentScan("com.oga.cqrsref.handlers")
public class ReviewCommandHandler implements ReviewCommandHandlerInterface {

    final ReviewRepository reviewRepository;
    final EventProducer eventProducer;

    final ReviewEventHandler reviewEventHandler;

    final BookService bookService;

    /**
     * Constructeur de la classe ReviewCommandHandler.
     *
     * @param reviewRepository   Repository utilisé pour interagir avec la base de données des critiques.
     * @param eventProducer      Producteur d'événements utilisé pour publier les événements liés aux critiques.
     * @param reviewEventHandler Gestionnaire d'événements des critiques utilisé pour traiter les événements liés aux critiques.
     * @param bookService        Service de gestion des livres utilisé pour obtenir les informations du livre lié à la critique.
     */
    @Autowired
    public ReviewCommandHandler(ReviewRepository reviewRepository, EventProducer eventProducer, ReviewEventHandler reviewEventHandler, BookService bookService) {
        this.reviewRepository = reviewRepository;
        this.eventProducer = eventProducer;
        this.reviewEventHandler = reviewEventHandler;
        this.bookService = bookService;
    }

    /**
     * Gère la commande spécifiée et renvoie un identifiant de critique.
     *
     * @param command Commande à traiter.
     * @return Identifiant de la critique.
     * @throws IllegalArgumentException si le type de commande n'est pas pris en charge.
     */
    @Override
    public Integer handle(BaseCommand command) {
        int reviewId = 0;
        if (command instanceof CreateReviewCommand) {
            reviewId = handleCreateReviewCommand((CreateReviewCommand) command);
        } else if (command instanceof UpdateReviewCommand) {
            handleUpdateReviewCommand((UpdateReviewCommand) command);
        } else {
            throw new IllegalArgumentException("Unsupported command type");
        }
        return reviewId;
    }

    /**
     * Traite la commande CreateReviewCommand spécifiée et renvoie l'identifiant de la critique créée.
     *
     * @param command Commande CreateReviewCommand à traiter.
     * @return Identifiant de la critique créée.
     */
    private int handleCreateReviewCommand(CreateReviewCommand command) {
        // Création d'un agrégat de critique à partir de la commande
        ReviewAggregate reviewAggregate = new ReviewAggregate(command);

        // Création d'une nouvelle critique à partir des informations de la commande
        Review review = new Review();
        if(command.getReviewer() == null || command.getReviewer().isEmpty()){
            return 0;
        }
        review.setReviewer(command.getReviewer());

        if(command.getContent() == null|| command.getContent().isEmpty() ){
            return 0;
        }
        review.setContent(command.getContent());
        if(command.isPublished() != false){
            return 0;
        }
        review.setPublished(command.isPublished());
        if (command.getBookId() == 0 ) {
            return 0;
        }

        // Récupération des informations du livre lié à la critique
        if(bookService.getBookById(command.getBookId()) == null){
            return 0 ;
        }
        Book book = bookService.getBookById(command.getBookId());
        review.setBook(book);

        // Sauvegarde de la critique dans le repository
        review = reviewRepository.save(review);

        // Création d'une map contenant la critique
        HashMap<String, Object> myMap = new HashMap<>();
        myMap.put("review", review);

        // Génération d'un identifiant unique pour l'événement
        var event = EventModel.builder()
                .id(reviewAggregate.getId())
                .aggregateIdentifier(reviewAggregate.getId())
                .aggregateType(BookAggregate.AGGREGATE_TYPE)
                .eventType(ReviewCreatedEvent.EVENT_TYPE)
                .eventData(myMap)
                .timeStamp(new Date())
                .build();

        // Sauvegarde de l'agrégat de critique dans le gestionnaire d'événements
        reviewEventHandler.save(reviewAggregate, ReviewCreatedEvent.EVENT_TYPE);

        ReviewCreatedEvent reviewCreatedEvent =new ReviewCreatedEvent(String.valueOf(command.getBookId()), command.getReviewer(),command.getContent()
                ,command.isPublished()
        );
        // Publication de l'événement ReviewCreatedEvent
        eventProducer.produce(ReviewCreatedEvent.EVENT_TYPE, reviewCreatedEvent);

        // Renvoie l'identifiant de la critique créée
        return review.getId();
    }

    /**
     * Traite la commande UpdateReviewCommand spécifiée.
     *
     * @param command Commande UpdateReviewCommand à traiter.
     */
    private void handleUpdateReviewCommand(UpdateReviewCommand command) {
        // Création d'un agrégat de critique à partir de la commande
        ReviewAggregate reviewAggregate = new ReviewAggregate(command);

        // Récupération de la critique à mettre à jour




        Review review = reviewRepository.findById(command.getReviewId()).get();

        // Mise à jour des informations de la critique si elles sont fournies dans la commande
        if (command.getContent() != null) {
            review.setContent(command.getContent());
        }
        review.setPublished(command.isPublished());

        // Sauvegarde de la critique mise à jour dans le repository
        review = reviewRepository.save(review);

        // Création d'une map contenant la critique
        HashMap<String, Object> myMap = new HashMap<>();
        myMap.put("review", review);

        // Mise à jour de l'agrégat de critique avec la critique mise à jour
        reviewAggregate.setReview(review);
        reviewAggregate.setBookId(review.getBook().getId());

        // Génération d'un identifiant unique pour l'événement
        var event = EventModel.builder()
                .id(reviewAggregate.getId())
                .aggregateIdentifier(reviewAggregate.getId())
                .aggregateType(BookAggregate.AGGREGATE_TYPE)
                .eventType(ReviewUpdatedEvent.EVENT_TYPE)
                .eventData(myMap)
                .timeStamp(new Date())
                .build();

        ReviewUpdatedEvent reviewUpdatedEvent = new ReviewUpdatedEvent(command.getReviewId()
        ,command.getContent(),command.isPublished());
        // Sauvegarde de l'agrégat de critique dans le gestionnaire d'événements
        reviewEventHandler.save(reviewAggregate, ReviewUpdatedEvent.EVENT_TYPE);

        // Publication de l'événement ReviewUpdatedEvent
        eventProducer.produce(ReviewUpdatedEvent.EVENT_TYPE, reviewUpdatedEvent);
    }
}

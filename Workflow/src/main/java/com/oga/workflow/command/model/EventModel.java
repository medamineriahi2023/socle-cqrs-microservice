package com.oga.workflow.command.model;

import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;


/**
 * Represents an event model in the system.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(collection = "eventStore")
public class EventModel extends BaseEvent {

    /**
     * The unique identifier of the event.
     */
    @Id
    private String id;

    /**
     * The timestamp of the event.
     */
    private Date timeStamp;

    /**
     * The identifier of the associated aggregate.
     */
    private String aggregateIdentifier;

    /**
     * The type of the associated aggregate.
     */
    private String aggregateType;

    /**
     * The type of the event.
     */
    private String eventType;

    /**
     * The data of the event.
     */
    private Map<String, Object> eventData;
}


package com.oga.cqrsref.handlers;


import com.oga.cqrsref.domain.AggregateRoot;
import org.springframework.stereotype.Service;

@Service
public interface EventSourcingHandler<T extends AggregateRoot>{

    void save(AggregateRoot aggregate);
    T getById(String id);
    void republishEvents();
}

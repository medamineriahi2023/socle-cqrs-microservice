package com.oga.cqrsref.infrastructure;

import com.oga.cqrsref.domain.BaseEntity;
import com.oga.cqrsref.exceptions.MultipleQueryHandlersException;
import com.oga.cqrsref.exceptions.NoQueryHandlerException;
import com.oga.cqrsref.queries.BaseQuery;
import com.oga.cqrsref.queries.QueryHandlerMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for dispatching queries to their respective handlers.
 */
@Service
public class IQueryDispatcher implements QueryDispatcher {
    private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();

    /**
     * Registers a query handler for a specific query type.
     *
     * @param type    the query type to register the handler for
     * @param handler the handler to register
     * @param <T>     the type of the query
     */
    @Override
    public <T extends BaseQuery,U extends BaseEntity> void registerHandler(Class<T> type, QueryHandlerMethod<T,U> handler) {
        var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);
    }

    /**
     * Sends a query to its registered handler.
     *
     * @param query the query to send
     * @param <U>   the type of the result of the query
     * @return the result of the query
     * @throws RuntimeException if no query handler was registered, or if more than one handler was registered
     */
    @Override
    public <U extends BaseEntity> List<U> send(BaseQuery query) {
        var handlers = routes.get(query.getClass());
        if (handlers == null || handlers.size() <= 0) {
            throw new NoQueryHandlerException();
        }
        if (handlers.size() > 1) {
            throw new MultipleQueryHandlersException();
        }

        return (List<U>) handlers.get(0).handle(query);

    }
    @Override
    public <U extends BaseEntity> Object sendObject(BaseQuery query) {
        var handlers = routes.get(query.getClass());
        if (handlers == null || handlers.size() <= 0) {
            throw new NoQueryHandlerException();
        }
        if (handlers.size() > 1) {
            throw new MultipleQueryHandlersException();
        }

        return  handlers.get(0).handle(query);

    }


}

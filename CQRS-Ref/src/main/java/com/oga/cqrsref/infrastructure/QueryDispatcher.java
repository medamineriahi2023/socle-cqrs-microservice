package com.oga.cqrsref.infrastructure;

import com.oga.cqrsref.domain.BaseEntity;
import com.oga.cqrsref.queries.BaseQuery;
import com.oga.cqrsref.queries.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery, U extends BaseEntity> void registerHandler(Class<T> type, QueryHandlerMethod<T,U> handler);
    <U extends BaseEntity> List<U> send(BaseQuery query);
    <U extends BaseEntity> Object sendObject(BaseQuery query);


}

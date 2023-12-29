package com.oga.cqrsref.queries;

import com.oga.cqrsref.domain.BaseEntity;

@FunctionalInterface
public interface QueryHandlerMethod<T extends BaseQuery , R extends BaseEntity> {

    Object handle(T query);
}



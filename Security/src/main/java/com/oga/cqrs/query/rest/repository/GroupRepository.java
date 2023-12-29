package com.oga.cqrs.query.rest.repository;

import com.oga.cqrs.query.rest.dto.Group;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;


@Primary
public interface GroupRepository extends MongoRepository<Group,String> {

}

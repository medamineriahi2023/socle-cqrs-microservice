package com.oga.cqrs.query.rest.repository;

import com.oga.cqrs.query.rest.dto.Role;
import com.oga.cqrs.query.rest.dto.User;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


@Primary
public interface UserRepository extends MongoRepository<User,String> {
    List<User> findByIdIn(List<String> ids);

}

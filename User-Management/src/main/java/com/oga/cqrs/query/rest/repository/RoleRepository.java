package com.oga.cqrs.query.rest.repository;

import com.oga.cqrs.query.rest.dto.Role;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


@Primary
public interface RoleRepository extends MongoRepository<Role,String> {
  List<Role> findByIdIn(List<String> ids);
}

package com.oga.cqrs.query.handlers.user;


import com.oga.cqrs.query.queries.user.FindUserById;
import com.oga.cqrs.query.rest.dto.*;
import com.oga.cqrs.query.rest.repository.*;
import com.oga.cqrsref.domain.BaseEntity;
import com.oga.cqrsref.queries.BaseQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



/**
 * Service class that handles queries related to the user entity.
 */
@Service
@EnableMongoRepositories("com.oga.cqrs.query.rest.repository")
public class UserQueryHandlers implements IUserQueryHandler {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RoleRepository roleRepository;

  /**
   * @param baseQuery the query to handle
   * @return the list of BaseEntity objects containing the user data
   * List<User> users , retrieves a list of User objects from the userRepository using the findAll method
   * UsersDetails userdetail represents the user with all affectation to read
   * for loop, iterates through the users to get its roles from the array of ids present in user entity
   * roleRepository.findByIdIn(u.getRolesids()) gives the list of roles related to a specific user
   * setters are used to populate userdetail
   * userdetails.add(userdetail) , each iteration a user detail is added to the list to show
   * List<BaseEntity> UsersList ,  new ArrayList is created to hold BaseEntity objects
   * userdetails.forEach(UsersList::add) iterates over each User object in the users list and adds them to the UsersList
   */
  @Override
  public List<BaseEntity> handle(BaseQuery baseQuery) {
    List<User> users = userRepository.findAll();
    UsersDetails userdetail=new UsersDetails();
    List<UsersDetails> userdetails=new ArrayList<>();
    List<Role> role;
    for (User u : users){
      userdetail.setUser(u);
      role=roleRepository.findByIdIn(u.getRolesids());
      userdetail.setRoles(role);
      userdetails.add(userdetail);
      userdetail=new UsersDetails();
    }
    List<BaseEntity> UsersList = new ArrayList<>();
    userdetails.forEach(UsersList::add);
    return UsersList;

  }

  /**
   * @param FindUserById the query to handle
   * @return The retrieved User object
   * List<User> users , retrieves a list of User objects from the userRepository using the findAll method
   * userRepository.findById(FindUserById.getId()) , queries the userRepository for a user with the ID specified in the FindUserById parameter
   * UsersDetails userdetail represents the user with all affectation to read
   * roleRepository.findByIdIn(u.get().getRolesids()) gives the list of roles related to a specific user
   * setters are used to populate userdetail
   */
  @Override
  public UsersDetails handle(FindUserById FindUserById) {

    Optional<User> u=userRepository.findById(FindUserById.getId());
    UsersDetails userdetail=new UsersDetails();
    List<Role> role;
    role=roleRepository.findByIdIn(u.get().getRolesids());
    userdetail.setUser(u.get());
    userdetail.setRoles(role);
    return userdetail;
  }



}

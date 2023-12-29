package com.oga.cqrs.query.handlers.role;

import com.oga.cqrs.query.queries.role.FindRoleById;
import com.oga.cqrs.query.rest.dto.Role;
import com.oga.cqrs.query.rest.repository.RoleRepository;
import com.oga.cqrsref.domain.BaseEntity;
import com.oga.cqrsref.queries.BaseQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class RoleQueryHandler implements IRoleQueryHandler {
  @Autowired
  private RoleRepository roleRepository;


  /**
   * @param baseQuery the query to handle
   * @return the list of BaseEntity objects containing the role data
   * List<Role> roles , retrieves a list of role objects from the roleRepository using the findAll method
   * List<BaseEntity> rolesList ,  new ArrayList is created to hold BaseEntity objects
   * roles.forEach(rolesList::add) iterates over each role object in the roles list and adds them to the rolesList
   */
  @Override
  public List<BaseEntity> handle(BaseQuery baseQuery) {
    List<Role> roles = roleRepository.findAll();
    List<BaseEntity> rolesList = new ArrayList<>();
    roles.forEach(rolesList::add);
    return rolesList;

  }
  /**
   * @param FindRoleById the query to handle
   * @return The retrieved role object
   * List<Role> role  , retrieves a list of role objects from the roleRepository using the findAll method
   * tmprole=roleRepository.findById(FindRoleById.getId()) , queries the roleRepository for a role with the ID specified in the FindRoleById parameter
   * tmprole.get() , retrieve the content of the Optional object
   * role.add(tmprole.get()) , The retrieved role  is extracted using tmprole.get() and added to the role list
   */
  @Override
  public Role handle(FindRoleById FindRoleById) {
    List<Role> role = new ArrayList<>();
    Optional<Role> tmprole=roleRepository.findById(FindRoleById.getId());
    role.add(tmprole.get());
    return tmprole.get();
  }

}

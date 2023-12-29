package com.oga.cqrs.query.handlers.group;


import com.oga.cqrs.query.queries.group.FindGroupById;
import com.oga.cqrs.query.rest.dto.Group;
import com.oga.cqrs.query.rest.repository.GroupRepository;
import com.oga.cqrsref.domain.BaseEntity;
import com.oga.cqrsref.queries.BaseQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Service class that handles queries related to the user entity.
 */
@Service
public class GroupQueryHandlers implements IGroupQueryHandler {

  @Autowired
  private GroupRepository groupRepository;

  /**
   * @param baseQuery the query to handle
   * @return the list of BaseEntity objects containing the group data
   * List<Group> groups , retrieves a list of group objects from the groupRepository using the findAll method
   * List<BaseEntity> groupList ,  new ArrayList is created to hold BaseEntity objects
   * groups.forEach(groupList::add) iterates over each group object in the groups list and adds them to the groupList
   */
  @Override
  public List<BaseEntity> handle(BaseQuery baseQuery) {
    List<Group> groups = groupRepository.findAll();
    List<BaseEntity> groupList = new ArrayList<>();
    groups.forEach(groupList::add);
    return groupList;

  }

  /**
   * @param FindGroupById the query to handle
   * @return The retrieved group object
   * List<Group> group  , retrieves a list of group objects from the groupRepository using the findAll method
   * groupRepository.findById(FindGroupById.getId()) , queries the groupRepository for a group with the ID specified in the FindUserById parameter
   * tmpgroup.get() , retrieve the content of the Optional object
   * group.add(tmpgroup.get()) , The retrieved group  is extracted using tmpgroup.get() and added to the group list
   */
  @Override
  public Group handle(FindGroupById FindGroupById) {
    List<Group> group = new ArrayList<>();
    Optional<Group> tmpgroup=groupRepository.findById(FindGroupById.getId());
    group.add(tmpgroup.get());
    return tmpgroup.get();
  }


}

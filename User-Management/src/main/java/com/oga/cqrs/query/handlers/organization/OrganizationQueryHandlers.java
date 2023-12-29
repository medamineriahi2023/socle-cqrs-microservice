package com.oga.cqrs.query.handlers.organization;


import com.oga.cqrs.query.queries.group.FindGroupById;
import com.oga.cqrs.query.rest.dto.Group;
import com.oga.cqrs.query.rest.dto.Organization;
import com.oga.cqrs.query.rest.repository.GroupRepository;
import com.oga.cqrs.query.rest.repository.OrganizationRepository;
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
public class OrganizationQueryHandlers implements IOrganizationQueryHandler {

  @Autowired
  private OrganizationRepository organizationRepository;

  @Override
  public List<BaseEntity> handle(BaseQuery baseQuery) {
    List<Organization> organizations = organizationRepository.findAll();
    List<BaseEntity> organizationsList = new ArrayList<>();
    organizations.forEach(organizationsList::add);
    return organizationsList;

  }



}

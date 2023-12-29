package com.oga.cqrs.query.rest.controller;

import com.oga.cqrs.command.rest.constant.Constant;
import com.oga.cqrs.query.queries.organizations.FindAllOrganizations;
import com.oga.cqrs.query.rest.dto.Organization;
import com.oga.cqrs.query.rest.response.QueryOrganizationResponse;
import com.oga.cqrsref.infrastructure.IQueryDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Logger;

@ComponentScan(basePackages="com.oga.cqrsref.controller")
@RestController
@RequestMapping(path = "/security/v1")
public class OrganizationQueryController {

  private final Logger logger = Logger.getLogger(OrganizationQueryController.class.getName());

  @Autowired
  private IQueryDispatcher queryDispatcher;

  @GetMapping("orgs")
  public ResponseEntity<QueryOrganizationResponse> getAllGroups() {
    List<Organization> organizations = queryDispatcher.send(new FindAllOrganizations());
    if (organizations == null ) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
    var response = QueryOrganizationResponse.builder()
        .organizations(organizations)

        .message(MessageFormat.format(Constant.GET_ALL_ORGS_SUCCESS, organizations.size()))
        .build();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}

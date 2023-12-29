package com.oga.cqrs.query.events.organization;

import com.oga.cqrs.command.events.organizations.OrganizationCreated;
import com.oga.cqrs.query.rest.dto.Organization;
import com.oga.cqrs.query.rest.repository.OrganizationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.oga.cqrs.command.rest.constant.Constant.NON_VALID_ID;

/**
 * An event handler for handling user events by saving the user to a MongoDB repository.
 */


@Service
@Slf4j
public class OrganizationEventHandler implements IOrganizationEventHandler {

  @Autowired
  private OrganizationRepository organizationRepository;

  /**
   * handles the busness logic for creating a user , called when  an event UserCreated occurs.
   * @param event the create user event to handle
   * user, a variable  that will be constructed from the event
   * Inside the try block:
   * The properties from the event.getUser() is used to build a User object using the builder pattern
   * Inside the catch block:
   * If there's a NumberFormatException , it throws an IllegalArgumentException with a message (NON_VALID_ID)
   * userRepository.save(user), persists the user in a database
   */
  @Override
  public void on(OrganizationCreated event) {
    Organization organization;

    try{
      organization = Organization.builder()
          .id(event.getOrganization().getId())
          .name(event.getOrganization().getName())
          .displayName(event.getOrganization().getDisplayName())
          .realm(event.getOrganization().getRealm())
          .url(event.getOrganization().getUrl())
          .build();
    }catch (NumberFormatException e) {
      throw new IllegalArgumentException(NON_VALID_ID,e);
    }

    organizationRepository.save(organization);
  }
}

package com.oga.cqrsref.constants;

public class Constants {

  public static final String BAD_REQUEST_ERROR_MESSAGE_PREFIX = "Creation failed with a bad request - ";
  public static final String NO_QUERY_HANDLER_REGISTERED = "No query handler was registered!";
  public static final String CANNOT_SEND_QUERY_TO_MORE_THAN_ONE_HANDLER = "Cannot send query to more than one handler!";
  public static final String COULD_NOT_RETRIEVE_EVENT_STREAM = "Could not retrieve event stream from the event store!";
  public static final String COMMAND_HANDLER_NOT_REGISTERED_ERROR = "No command handler was registered!";
  public static final String MULTIPLE_COMMAND_HANDLERS_ERROR = "Cannot send command to more than one handler!";
  public static final String CREATION_SUCCESS_MESSAGE = "creation request completed successfully!";
  public static final String CREATION_ERROR_MESSAGE = "Error occurred during creation.";
  public static final String UPDATE_SUCCESS_MESSAGE = "update request completed successfully!";
  public static final String UPDATE_ERROR_MESSAGE = "Error occurred during update.";
  public static final String DELETION_SUCCESS_MESSAGE = "deletion request completed successfully!";
  public static final String DELETION_ERROR_MESSAGE = "Error occurred during deletion.";
  public static final String GET_ALL_SUCCESS_MESSAGE = "Get all request successfully returned {0}!";
  public static final String GET_ALL_FAILURE_MESSAGE = "Failed to complete get all request!";
  public static final String FIND_BY_SUCCESS_MESSAGE_FORMAT = "%s retrieval by %s request completed successfully!";
  public static final String FIND_BY_ERROR_MESSAGE_FORMAT = "Error occurred during %s retrieval by %s.";
  public static final String INVALID_ID_ERROR_MESSAGE = "Invalid ID";
  public static final String ENTITY_NOT_FOUND_ERROR_MESSAGE_FORMAT = "%s not found with ID: %s";
  public static final String ENTITY_MOVE_SUCCESS_MESSAGE_FORMAT = "%s moved successfully!";
  public static final String ENTITY_MOVE_ERROR_MESSAGE_FORMAT = "Error occurred during move!";
  public static final String COUNT_SUCCESS_MESSAGE_FORMAT = "Number of %s found: %d";



}

package com.oga.subscription.command.rest.constant;

public class Constant {
    private Constant() {
    }

    public static final String SUCCESS_MESSAGE = "Creation request completed successfully!";
    public static final String ERROR_MESSAGE_PREFIX = "Error while processing request to create a new object for id - ";
    public static final String BAD_REQUEST_ERROR_MESSAGE_PREFIX = "Creation failed with a bad request - ";
    public static final String NO_QUERY_HANDLER_REGISTERED = "No query handler was registered!";
    public static final String CANNOT_SEND_QUERY_TO_MORE_THAN_ONE_HANDLER = "Cannot send query to more than one handler!";
    public static final String COULD_NOT_RETRIEVE_EVENT_STREAM = "Could not retrieve event stream from the event store!";
    public static final String COMMAND_HANDLER_NOT_REGISTERED_ERROR = "No command handler was registered!";
    public static final String MULTIPLE_COMMAND_HANDLERS_ERROR = "Cannot send command to more than one handler!";
    public static final String GET_ALL_SUCCESS = "Successfully returned {0} !";
    public static final String GET_ALL_FAILURE = "Failed to complete get all request!";

}

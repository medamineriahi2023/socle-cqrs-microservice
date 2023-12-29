package com.oga.cqrsref.controller;

import com.oga.cqrsref.Response.BaseResponse;
import com.oga.cqrsref.constants.*;
import com.oga.cqrsref.exceptions.MultipleQueryHandlersException;
import com.oga.cqrsref.exceptions.NoQueryHandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class CustomControllerAdvice {
  private static final Logger logger = LoggerFactory.getLogger(CustomControllerAdvice.class);

  @ResponseStatus(HttpStatus.OK)
  @ExceptionHandler(Exception.class)
  @ResponseBody
  public ResponseEntity<String> handleException(Exception ex) {
    return ResponseEntity.status(HttpStatus.OK)
        .body("An unexpected error occurred: " + ex.getMessage());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(RuntimeException.class)
  @ResponseBody
  public ResponseEntity<String> handleBadRequestException(RuntimeException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body("Bad request: " + ex.getMessage());
  }

  @ExceptionHandler(NoQueryHandlerException.class)
  public ResponseEntity<BaseResponse> handleNoQueryHandlerException(NoQueryHandlerException ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new BaseResponse(ex.getMessage()));
  }
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  public ResponseEntity<String> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body("Bad request: " + errors);
  }
  @ExceptionHandler(MultipleQueryHandlersException.class)
  public ResponseEntity<BaseResponse> handleMultipleQueryHandlersException(MultipleQueryHandlersException ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new BaseResponse(ex.getMessage()));
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<BaseResponse> handleIllegalStateException(IllegalStateException ex) {
    logger.warn(MessageFormat.format(Constants.BAD_REQUEST_ERROR_MESSAGE_PREFIX, ex.toString()));
    return new ResponseEntity<>(new BaseResponse(ex.toString()), HttpStatus.BAD_REQUEST);
  }



  @ModelAttribute("SUCCESS_MESSAGE")
  public String getSuccessMessage() {
    return "creation request completed successfully!";
  }

  @ModelAttribute("ERROR_MESSAGE_PREFIX")
  public String getErrorMessagePrefix() {
    return "Error while processing request to create a new entity for id - ";
  }

  @ModelAttribute("BAD_REQUEST_ERROR_MESSAGE_PREFIX")
  public String getBadRequestErrorMessagePrefix() {
    return "Creation failed with a bad request - ";
  }

  @ModelAttribute("NO_QUERY_HANDLER_REGISTERED")
  public String getNoQueryHandlerRegisteredMessage() {
    return "No query handler was registered!";
  }

  @ModelAttribute("CANNOT_SEND_QUERY_TO_MORE_THAN_ONE_HANDLER")
  public String getCannotSendQueryToMoreThanOneHandlerMessage() {
    return "Cannot send query to more than one handler!";
  }

  @ModelAttribute("COULD_NOT_RETRIEVE_EVENT_STREAM")
  public String getCouldNotRetrieveEventStreamMessage() {
    return "Could not retrieve event stream from the event store!";
  }

  @ModelAttribute("COMMAND_HANDLER_NOT_REGISTERED_ERROR")
  public String getCommandHandlerNotRegisteredErrorMessage() {
    return "No command handler was registered!";
  }

  @ModelAttribute("MULTIPLE_COMMAND_HANDLERS_ERROR")
  public String getMultipleCommandHandlersErrorMessage() {
    return "Cannot send command to more than one handler!";
  }

  @ModelAttribute("GET_ALL_SUCCESS")
  public String getGetAllSuccessMessage() {
    return "Successfully returned {0} !";
  }

  @ModelAttribute("GET_ALL_FAILURE")
  public String getGetAllFailureMessage() {
    return "Failed to complete get all  request!";
  }
}

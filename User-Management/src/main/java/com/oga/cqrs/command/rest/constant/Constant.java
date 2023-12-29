package com.oga.cqrs.command.rest.constant;


import com.oga.cqrsref.constants.Constants;

public class Constant extends Constants {
  public static final String SUCCESS_MESSAGE_CREATION = " creation request for a user completed successfully!";
  public static final String SUCCESS_MESSAGE_CREATION_GROUP = " creation request for a group completed successfully!";


  public static final String SUCCESS_MESSAGE_CREATION_ORGANIZATION = " creation request for a organization completed successfully!";

  public static final String SUCCESS_MESSAGE_CREATION_ROLE = " creation request for a role completed successfully!";

  public static final String SUCCESS_MESSAGE_UPDATE_GROUP = " update request for a group completed successfully!";
  public static final String SUCCESS_MESSAGE_UPDATE_ROLE = " update request for a role completed successfully!";
  public static final String ROLES_AFFECTED = " roles are affected successfully for user ";
  public static final String UPDATE_PASSWORD="UPDATE_PASSWORD";
  public static final String VERIFY_EMAIL="VERIFY_EMAIL";
  public static final String SUCCESS_MESSAGE_UPDATE = " update request for a user completed successfully!";
  public static final String SUCCESS_MESSAGE_UPDATE_PASSWORD = " update password request for a user completed successfully!";
  public static final String GROUP_NOT_FOUND = " group does not exists";
  public static final String GROUP_EXISTS = " group  exists";

  public static final String LOGOUT = " Successfully logged out";
  public static final String PASSWORD_FORGOTTEN = " email sent to ";

  public static final String ERROR_MESSAGE_PREFIX = "Error while processing request to create a user for id - ";
  public static final String SUCCESS_MESSAGE_DELETE = " delete request for a user completed successfully!";

  public static final String SUCCESS_MESSAGE_DELETE_GROUP = " delete request for a group completed successfully!";
  public static final String SUCCESS_MESSAGE_DELETE_ROLE = " delete request for a role completed successfully!";
  public static final String GET_ALL_GROUPS_SUCCESS = "Successfully returned groups {0} !";
  public static final String GET_ALL_ORGS_SUCCESS = "Successfully returned organizations {0} !";
  public static final String GET_ALL_USERS_SUCCESS = "Successfully returned users {0} !";
  public static final String GET_ALL_ROLES_SUCCESS = "Successfully returned roles {0} !";
  public static final String GET_ALL_USERS_FAILURE = "Failed to complete get all users request!";

  public  static final String PHONE_NUMBER = "phone_number";
  public  static final String USER_ALREADY_EXISTS = "The user does already exist";
  public  static final String NON_VALID_ID = "Id is not valid";
  public static final String USER_NOT_FOUND = " user not found";
  public static final  String GET_USER_BY_ID_SUCCESS ="Successfully returned one user !";
  public static final  String GET_GROUP_BY_ID_SUCCESS ="Successfully returned one group !";
  public static final  String GET_ROLE_BY_ID_SUCCESS ="Successfully returned one role !";
  public static final  String ROLE_GROUP_ASSOCIATION ="Role cannot be deleted because it is associated with one or more group.";
  public static final  String ROLE_USER_ASSOCIATION ="Role cannot be deleted because it is associated with one or more user.";
  public static final  String GROUP_USER_ASSOCIATION ="Group cannot be deleted because it is associated with one or more user.";
  public static final  String TOKEN_GENERATION ="Token generated";



}

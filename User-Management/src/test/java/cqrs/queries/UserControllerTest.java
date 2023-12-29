package cqrs.queries;


import com.oga.cqrs.query.rest.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.*;

public class UserControllerTest {
  private Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

  private UserRepository userRepository;


  @BeforeEach
  void setUp() {

    userRepository = mock(UserRepository.class);

  }
  @Test
  void get_Allusers() {
    userRepository.findAll();
    verify(userRepository, times(1)).findAll();

  }
  @Test
  void get_Oneuser() {
    userRepository.findById("3a28be20-b9d8-43a2-ae8c-f45377d6c604");
    verify(userRepository, times(1)).findById("3a28be20-b9d8-43a2-ae8c-f45377d6c604");

  }
}

package cqrs.queries;

import com.oga.cqrs.query.rest.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.*;

public class RoleControllerTest {
  private Logger logger = LoggerFactory.getLogger(RoleControllerTest.class);

  private RoleRepository roleRepository;


  @BeforeEach
  void setUp() {

    roleRepository = mock(RoleRepository.class);

  }
  @Test
  void get_Allroles() {
    roleRepository.findAll();
    verify(roleRepository, times(1)).findAll();

  }
  @Test
  void get_oneroles() {
    roleRepository.findById("39928959-2535-486b-a15d-a32b505cdfbc");
    verify(roleRepository, times(1)).findById("39928959-2535-486b-a15d-a32b505cdfbc");

  }

}

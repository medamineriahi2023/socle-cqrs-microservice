package cqrs.queries;

import com.oga.cqrs.query.rest.repository.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.*;



public class GroupControllerTest {
  private Logger logger = LoggerFactory.getLogger(GroupControllerTest.class);

  private GroupRepository groupRepository;


  @BeforeEach
  void setUp() {

    groupRepository = mock(GroupRepository.class);

  }
  @Test
  void get_Allgroups() {
    groupRepository.findAll();
    verify(groupRepository, times(1)).findAll();

  }
  @Test
  void get_Onegroup() {
    groupRepository.findById("8e910d32-2723-41f2-8cc6-9b5416e8c7ab");
    verify(groupRepository, times(1)).findById("8e910d32-2723-41f2-8cc6-9b5416e8c7ab");

  }
}

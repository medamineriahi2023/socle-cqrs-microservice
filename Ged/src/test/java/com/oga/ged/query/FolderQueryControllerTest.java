package com.oga.ged.query;

import com.oga.ged.query.handlers.IQueryHandler;
import com.oga.ged.query.queries.FindFoldersByParentQuery;
import com.oga.ged.query.rest.dto.Folder;
import com.oga.ged.query.rest.repository.FolderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FolderQueryControllerTest {
    private Logger logger = LoggerFactory.getLogger(FolderQueryControllerTest.class);
    private IQueryHandler iQueryHandler ;
    private FolderRepository folderRepository;

    @BeforeEach
    void setUp() {
        folderRepository = mock(FolderRepository.class);
        IFolderQueryHandler = new IFolderQueryHandler(folderRepository);
    }

    @Test
    void handle_FindFoldersByParent_ReturnsMatchingFolders() {
        String parentIdToFind = "ID du parent à trouver";

        Folder matchingFolder1 = new Folder("1", parentIdToFind, "Folder 1", "Title 1", "Description 1", "NodeType 1");
        Folder nonMatchingFolder1 = new Folder("2", "Autre parent", "Folder 2", "Title 2", "Description 2", "NodeType 2");
        Folder matchingFolder2 = new Folder("3", parentIdToFind, "Folder 3", "Title 3", "Description 3", "NodeType 3");

        List<Folder> folders = Arrays.asList(matchingFolder1, matchingFolder2);

        FindFoldersByParent query = new FindFoldersByParent(parentIdToFind);

        when(folderRepository.findByParentId(parentIdToFind)).thenReturn(folders);

        List<Folder> result = IFolderQueryHandler.handle(query);

        assertEquals(2, result.size());
        assertTrue(result.contains(matchingFolder1));
        assertTrue(result.contains(matchingFolder2));
        assertFalse(result.contains(nonMatchingFolder1));

        logger.info("handle_FindFoldersByParent_ReturnsListOfFolders test passed successfully.");
        logger.info("Result: {}", result);

        verify(folderRepository, times(1)).findByParentId(parentIdToFind);
    }

}

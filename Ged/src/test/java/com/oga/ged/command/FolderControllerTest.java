package com.oga.ged.command;

import com.oga.cqrsref.handlers.EventSourcingHandler;
import com.oga.ged.command.commands.CreateFolderCommand;
import com.oga.ged.command.commands.DeleteFolderCommand;
import com.oga.ged.command.commands.UpdateFolderCommand;
import com.oga.ged.command.domain.FolderAggregate;
import com.oga.ged.command.handlers.IFolderCommandHandler;
import com.oga.ged.command.rest.controller.FolderCommandController;
import org.alfresco.core.handler.NodesApi;
import org.alfresco.core.model.Node;
import org.alfresco.core.model.NodeBodyCreate;
import org.alfresco.core.model.NodeBodyUpdate;
import org.alfresco.core.model.NodeEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


public class FolderControllerTest {
    private Logger logger = LoggerFactory.getLogger(FolderCommandController.class);
    private IFolderCommandHandler IFolderCommandHandler;
    private EventSourcingHandler<FolderAggregate> eventSourcingHandler;
    private NodesApi nodesApi;

    @BeforeEach
    void setUp() {
        nodesApi = mock(NodesApi.class);
        eventSourcingHandler = mock(EventSourcingHandler.class);
        IFolderCommandHandler = new IFolderCommandHandler(nodesApi,eventSourcingHandler);
    }

    @Test
    void handle_CreateFolderCommand() {
        CreateFolderCommand createFolderCommand =CreateFolderCommand.builder()
                .name("Nom du dossier")
                .title("Titre du dossier")
                .description("Description du dossier")
                .parentId("ID du parent")
                .nodeType("cm:folder").build();

        Map<String, Object> properties = new HashMap<>();
        properties.put("cm:title", createFolderCommand.getTitle());
        properties.put("cm:description", createFolderCommand.getDescription());

        NodeEntry nodeEntry = new NodeEntry();
        Node node=new Node();
        node.setId("newId");
        node.setName(createFolderCommand.getName());
        node.setProperties(properties);
        node.setNodeType(createFolderCommand.getNodeType());
        nodeEntry.setEntry(node);

        NodeBodyCreate nodeBodyCreate= new NodeBodyCreate();
        nodeBodyCreate.setName(createFolderCommand.getName());
        nodeBodyCreate.setProperties(properties);
        nodeBodyCreate.setNodeType(createFolderCommand.getNodeType());

        ResponseEntity<NodeEntry> successfulResponse = new ResponseEntity<>(nodeEntry, HttpStatus.OK);
        when(nodesApi.createNode(
                eq(createFolderCommand.getParentId()),
                eq(nodeBodyCreate),
                any(), any(), any(), any(), any()))
                .thenReturn(successfulResponse);

        IFolderCommandHandler.handle(createFolderCommand);

        verify(nodesApi, times(1)).createNode(
                eq(createFolderCommand.getParentId()),
                eq(nodeBodyCreate),
                any(), any(), any(), any(), any());
        verify(eventSourcingHandler, times(1)).save(any(FolderAggregate.class));

        logger.info("handle_CreateFolderCommand_Success test passed successfully.");
    }

    @Test
    void handle_UpdateFolderCommand() {
        UpdateFolderCommand updateFolderCommand = UpdateFolderCommand.builder()
                .id("ID du dossier")
                .name("Nouveau nom du dossier")
                .title("Nouveau titre du dossier")
                .description("Nouvelle description du dossier")
                .build();

        Map<String, Object> properties = new HashMap<>();
        properties.put("cm:title", updateFolderCommand.getTitle());
        properties.put("cm:description", updateFolderCommand.getDescription());

        NodeBodyUpdate nodeBodyUpdate = new NodeBodyUpdate();
        nodeBodyUpdate.setName(updateFolderCommand.getName());
        nodeBodyUpdate.setProperties(properties);

        ResponseEntity<NodeEntry> successfulResponse = new ResponseEntity<>(HttpStatus.OK);
        when(nodesApi.updateNode(
                eq(updateFolderCommand.getId()),
                eq(nodeBodyUpdate),
                any(), any()))
                .thenReturn(successfulResponse);

        IFolderCommandHandler.handle(updateFolderCommand);

        verify(nodesApi, times(1)).updateNode(
                eq(updateFolderCommand.getId()),
                eq(nodeBodyUpdate),
                any(), any());
        verify(eventSourcingHandler, times(1)).save(any(FolderAggregate.class));

        logger.info("handle_UpdateFolderCommand_Success test passed successfully.");
    }

    @Test
    void handle_DeleteFolderCommand() {
        DeleteFolderCommand deleteFolderCommand = DeleteFolderCommand.builder()
                .id("ID du dossier à supprimer")
                .build();

        ResponseEntity<Void> successfulResponse = new ResponseEntity<>(HttpStatus.OK);
        when(nodesApi.deleteNode(eq(deleteFolderCommand.getId()), eq(false)))
                .thenReturn(successfulResponse);

        IFolderCommandHandler.handle(deleteFolderCommand);

        verify(nodesApi, times(1)).deleteNode(eq(deleteFolderCommand.getId()), eq(false));
        verify(eventSourcingHandler, times(1)).save(any(FolderAggregate.class));

        logger.info("handle_DeleteFolderCommand_Success test passed successfully.");
    }

}
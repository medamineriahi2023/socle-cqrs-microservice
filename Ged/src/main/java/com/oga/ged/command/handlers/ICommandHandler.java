package com.oga.ged.command.handlers;

import com.oga.cqrsref.constants.Constants;
import com.oga.cqrsref.handlers.EventSourcingHandler;
import com.oga.ged.command.commands.*;
import com.oga.ged.command.domain.*;
import com.oga.ged.command.rest.utils.exceptions.CreationException;
import com.oga.ged.command.rest.utils.exceptions.DeletionException;
import com.oga.ged.command.rest.utils.exceptions.MoveException;
import com.oga.ged.command.rest.utils.exceptions.UpdateException;
import lombok.extern.slf4j.Slf4j;
import org.alfresco.core.handler.*;
import org.alfresco.core.model.*;
import org.apache.chemistry.opencmis.commons.impl.json.JSONObject;
import org.apache.chemistry.opencmis.commons.impl.json.JSONValue;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation of CommandHandler that handles folder commands.
 */
@Service
@ComponentScan("com.oga.cqrsref.handlers")
@Slf4j
public class ICommandHandler implements CommandHandler {
    private NodesApi nodesApi;
    private ContentApi contentApi;
    private PeopleApi peopleApi;
    private GroupsApi groupsApi;
    private SharedLinksApi sharedLinksApi;
    private final EventSourcingHandler<FolderAggregate> eventSourcingHandler;


    public ICommandHandler(NodesApi nodesApi, ContentApi contentApi, PeopleApi peopleApi, GroupsApi groupsApi, SharedLinksApi sharedLinksApi, EventSourcingHandler<FolderAggregate> eventSourcingHandler) {
        this.nodesApi = nodesApi;
        this.contentApi = contentApi;
        this.peopleApi = peopleApi;
        this.groupsApi = groupsApi;
        this.sharedLinksApi = sharedLinksApi;
        this.eventSourcingHandler = eventSourcingHandler;
    }

    /**
     * Gère la commande de création d'un dossier.
     *
     * @param createFolderCommand La commande de création d'un dossier à gérer.
     *                            Cette commande doit contenir les informations suivantes :
     *                            - parentId : L'identifiant du parent du dossier à créer.
     *                            - name : Le nom du dossier à créer.
     *                            - title : Le titre du dossier à créer (facultatif).
     *                            - description : La description du dossier à créer (facultatif).
     *                            - properties : Les propriétés du dossier à créer (facultatif).
     *                            - aspectNames : Les noms d'aspects du dossier à créer (facultatif).
     *                            - definition : La définition du dossier à créer (facultatif).
     *                            - relativePath : Le chemin relatif du dossier à créer (facultatif).
     *                            - association : L'association du dossier à créer (facultatif).
     *                            - secondaryChildren : Les sous-enfants du dossier à créer (facultatif).
     *                            - targets : Les cibles du dossier à créer (facultatif).
     *                            - permissionElements : Les éléments de permission du dossier à créer (facultatif).
     * @throws CreationException Si une erreur survient lors de la création du dossier.
     *                           Cette exception est lancée lorsque la création échoue avec un code de statut HTTP
     *                           différent de 2xx lors de l'appel à l'API externe pour créer le dossier.
     */
    public void handle(CreateFolderCommand createFolderCommand) throws CreationException {
        try {
            // Préparer les données pour la création du dossier
            NodeBodyCreate folderBody = new NodeBodyCreate();
            createFolderCommand.setNodeType("cm:folder");
            folderBody.setNodeType("cm:folder");
            folderBody.setName(createFolderCommand.getName());

            // Gérer les propriétés du dossier
            Object existingProperties = createFolderCommand.getProperties();
            if (existingProperties == null || existingProperties instanceof JSONObject && ((JSONObject) existingProperties).isEmpty()) {
                // Si les nouvelles propriétés ne sont pas fournies, utiliser les valeurs par défaut
                JSONObject properties = new JSONObject();
                properties.put("cm:title", createFolderCommand.getTitle());
                properties.put("cm:description", createFolderCommand.getDescription());
                folderBody.setProperties(properties);
                createFolderCommand.setProperties(properties);
            } else {
                // Si les nouvelles propriétés sont fournies, les ajouter aux propriétés existantes
                JSONObject properties;
                if (existingProperties instanceof JSONObject) {
                    properties = (JSONObject) existingProperties;
                } else {
                    String jsonStr = JSONValue.toJSONString(existingProperties);
                    properties = (JSONObject) JSONValue.parse(jsonStr);
                }
                properties.put("cm:title", createFolderCommand.getTitle());
                properties.put("cm:description", createFolderCommand.getDescription());
                folderBody.setProperties(properties);
                createFolderCommand.setProperties(properties);
            }

            // Gérer les noms d'aspects du dossier
            folderBody.setAspectNames(createFolderCommand.getAspectNames());

            // Gérer les autres paramètres du dossier
            folderBody.setDefinition(createFolderCommand.getDefinition());
            folderBody.setRelativePath(createFolderCommand.getRelativePath());
            folderBody.setAssociation(createFolderCommand.getAssociation());
            folderBody.setSecondaryChildren(createFolderCommand.getSecondaryChildren());
            folderBody.setTargets(createFolderCommand.getTargets());

            // Gérer les permissions du dossier
            List<PermissionElement> permissionElements = createFolderCommand.getPermissionElements();
            if (permissionElements != null && !permissionElements.isEmpty()) {
                PermissionsBody permissionsBody = new PermissionsBody();
                permissionsBody.setIsInheritanceEnabled(true);
                List<PermissionElement> updatedPermissions = permissionElements.stream()
                        .peek(permissionElement -> permissionElement.setAccessStatus(PermissionElement.AccessStatusEnum.ALLOWED))
                        .collect(Collectors.toList());
                permissionsBody.setLocallySet(updatedPermissions);
                folderBody.setPermissions(permissionsBody);
                createFolderCommand.setPermissions(permissionsBody);
            }

            // Appel à l'API externe pour créer le dossier
            ResponseEntity<NodeEntry> response = nodesApi.createNode(createFolderCommand.getParentId(), folderBody, null, null, null, null, null);

            // Vérifier si la création a réussi (code de statut 2xx)
            if (response.getStatusCode().is2xxSuccessful()) {
                NodeEntry createdNode = response.getBody();
                String nodeId = createdNode.getEntry().getId();
                createFolderCommand.setFolderId(nodeId);

                // Fusionner les noms d'aspects existants avec les nouveaux noms d'aspects (si existants)
                List<String> aspectNames = createFolderCommand.getAspectNames();
                List<String> responseAspectNames = createdNode.getEntry().getAspectNames();
                aspectNames = Stream.concat(aspectNames.stream(), responseAspectNames.stream())
                        .distinct()
                        .collect(Collectors.toList());
                createFolderCommand.setAspectNames(aspectNames);

                // Récupérer les détails du dossier créé
                createFolderCommand.setCreated(Date.from(createdNode.getEntry().getCreatedAt().toInstant()));
                createFolderCommand.setCreator(createdNode.getEntry().getCreatedByUser().getId());
                createFolderCommand.setModified(Date.from(createdNode.getEntry().getModifiedAt().toInstant()));
                createFolderCommand.setModifier(createdNode.getEntry().getModifiedByUser().getId());

                log.info("Dossier créé avec succès ! ID : " + nodeId);

                // Création d'un agrégat de dossier et sauvegarde de l'événement
                FolderAggregate folderAggregate = new FolderAggregate(createFolderCommand);
                eventSourcingHandler.save(folderAggregate);
            } else {
                // Si la création a échoué, lancer une exception personnalisée avec un message d'erreur explicatif
                throw new CreationException(Constants.CREATION_ERROR_MESSAGE + response.getStatusCodeValue());
            }
        } catch (CreationException e) {
            // Gérer l'exception et la relancer pour une propagation appropriée
            log.info(e.getMessage());
            throw e;
        }
    }


    /**
     * Gère la commande de mise à jour d'un dossier.
     *
     * @param updateFolderCommand La commande de mise à jour d'un dossier à gérer.
     *                            Cette commande doit contenir les informations suivantes :
     *                            - folderId : L'identifiant du dossier à mettre à jour.
     *                            - name : Le nouveau nom du dossier (facultatif).
     *                            - title : Le nouveau titre du dossier (facultatif).
     *                            - description : La nouvelle description du dossier (facultatif).
     *                            - properties : Les nouvelles propriétés du dossier (facultatif).
     *                            - aspectNames : Les nouveaux noms d'aspects du dossier (facultatif).
     *                            - permissionElements : Les nouveaux éléments de permission du dossier (facultatif).
     * @throws UpdateException Si une erreur survient lors de la mise à jour du dossier.
     *                         Cette exception est lancée lorsque la mise à jour échoue avec un code de statut HTTP
     *                         différent de 2xx lors de l'appel à l'API externe pour mettre à jour le dossier.
     */
    public void handle(UpdateFolderCommand updateFolderCommand) throws UpdateException {
        try {
            // Récupérer les propriétés actuelles du dossier depuis l'API externe
            ResponseEntity<NodeEntry> nodeResponse = nodesApi.getNode(updateFolderCommand.getFolderId(), null, null, null);

            // Vérifier si la récupération des propriétés a réussi (code de statut 200)
            if (nodeResponse.getStatusCode() == HttpStatus.OK) {
                NodeEntry nodeEntry = nodeResponse.getBody();
                Map<String, Object> nodeProperties = (Map<String, Object>) nodeEntry.getEntry().getProperties();

                // Vérifier si les propriétés du dossier contiennent les valeurs par défaut, et si oui, utiliser les valeurs actuelles
                // pour mettre à jour la commande de mise à jour (si les nouvelles valeurs ne sont pas fournies)
                if (nodeProperties.containsKey("cm:title") && updateFolderCommand.getTitle() == null) {
                    updateFolderCommand.setTitle((String) nodeProperties.get("cm:title"));
                }

                if (nodeProperties.containsKey("cm:description") && updateFolderCommand.getDescription() == null) {
                    updateFolderCommand.setDescription((String) nodeProperties.get("cm:description"));
                }
            }

            // Préparer les données pour l'appel à l'API externe pour mettre à jour le dossier
            NodeBodyUpdate nodeBodyUpdate = new NodeBodyUpdate();
            nodeBodyUpdate.setName(updateFolderCommand.getName());

            // Gérer les propriétés du dossier
            Object existingProperties = updateFolderCommand.getProperties();
            if (existingProperties == null || existingProperties instanceof JSONObject && ((JSONObject) existingProperties).isEmpty()) {
                // Si les nouvelles propriétés ne sont pas fournies, utiliser les valeurs par défaut
                JSONObject properties = new JSONObject();
                properties.put("cm:title", updateFolderCommand.getTitle());
                properties.put("cm:description", updateFolderCommand.getDescription());
                nodeBodyUpdate.setProperties(properties);
            } else {
                // Si les nouvelles propriétés sont fournies, les ajouter aux propriétés existantes
                JSONObject properties;
                if (existingProperties instanceof JSONObject) {
                    properties = (JSONObject) existingProperties;
                } else {
                    String jsonStr = JSONValue.toJSONString(existingProperties);
                    properties = (JSONObject) JSONValue.parse(jsonStr);
                }
                properties.put("cm:title", updateFolderCommand.getTitle());
                properties.put("cm:description", updateFolderCommand.getDescription());
                nodeBodyUpdate.setProperties(properties);
            }

            // Gérer les noms d'aspects du dossier
            nodeBodyUpdate.setAspectNames(updateFolderCommand.getAspectNames());

            // Gérer les permissions du dossier
            List<PermissionElement> permissionElements = updateFolderCommand.getPermissionElements();
            if (permissionElements != null && !permissionElements.isEmpty()) {
                PermissionsBody permissionsBody = new PermissionsBody();
                permissionsBody.setIsInheritanceEnabled(true);
                List<PermissionElement> updatedPermissions = permissionElements.stream()
                        .peek(permissionElement -> permissionElement.setAccessStatus(PermissionElement.AccessStatusEnum.ALLOWED))
                        .collect(Collectors.toList());
                permissionsBody.setLocallySet(updatedPermissions);
                nodeBodyUpdate.setPermissions(permissionsBody);
                updateFolderCommand.setPermissions(permissionsBody);
            }

            // Appel à l'API externe pour mettre à jour le dossier
            ResponseEntity<NodeEntry> response = nodesApi.updateNode(updateFolderCommand.getFolderId(), nodeBodyUpdate, null, null);

            // Vérifier si la mise à jour a réussi (code de statut 200)
            if (response.getStatusCode() == HttpStatus.OK) {
                NodeEntry updatedNode = response.getBody();
                updateFolderCommand.setName(updatedNode.getEntry().getName());
                updateFolderCommand.setAspectNames(updatedNode.getEntry().getAspectNames());
                updateFolderCommand.setProperties(updatedNode.getEntry().getProperties());
                updateFolderCommand.setModified(Date.from(updatedNode.getEntry().getModifiedAt().toInstant()));
                updateFolderCommand.setModifier(updatedNode.getEntry().getModifiedByUser().getId());
                log.info("Dossier mis à jour avec succès!");

                // Création d'un agrégat de dossier et sauvegarde de l'événement
                FolderAggregate folderAggregate = new FolderAggregate(updateFolderCommand);
                eventSourcingHandler.save(folderAggregate);
            } else {
                // Si la mise à jour a échoué, lancer une exception personnalisée avec un message d'erreur explicatif
                throw new UpdateException(Constants.UPDATE_ERROR_MESSAGE + response.getStatusCodeValue());
            }
        } catch (UpdateException e) {
            // Gérer l'exception et la relancer pour une propagation appropriée
            log.info(e.getMessage());
            throw e;
        }
    }


    /**
     * Gère la commande de suppression d'un dossier.
     *
     * @param deleteFolderCommand La commande de suppression d'un dossier à gérer.
     *                            Cette commande doit contenir les informations suivantes :
     *                            - folderId : L'identifiant du dossier à supprimer.
     * @throws DeletionException Si une erreur survient lors de la suppression du dossier.
     *                           Cette exception est lancée lorsque la suppression échoue avec un code de statut HTTP
     *                           différent de 2xx lors de l'appel à l'API externe pour supprimer le dossier.
     */
    public void handle(DeleteFolderCommand deleteFolderCommand) throws DeletionException {
        try {
            // Appel à l'API externe pour supprimer le dossier
            ResponseEntity<Void> response = nodesApi.deleteNode(deleteFolderCommand.getFolderId(), false);

            // Vérifier si la suppression a réussi (code de statut 2xx)
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Dossier supprimé avec succès!");

                // Création d'un agrégat de dossier et sauvegarde de l'événement
                FolderAggregate folderAggregate = new FolderAggregate(deleteFolderCommand);
                eventSourcingHandler.save(folderAggregate);
            } else {
                // Si la suppression a échoué, lancer une exception personnalisée avec un message d'erreur explicatif
                throw new DeletionException(Constants.DELETION_ERROR_MESSAGE + response.getStatusCodeValue());
            }
        } catch (DeletionException e) {
            // Gérer l'exception et la relancer pour une propagation appropriée
            log.info(e.getMessage());
            throw e;
        }
    }


    /**
     * Gère la commande de déplacement d'un dossier.
     *
     * @param moveFolderCommand La commande de déplacement d'un dossier à gérer.
     *                           Cette commande doit contenir les informations suivantes :
     *                           - folderId : L'identifiant du dossier à déplacer.
     *                           - targetParentId : L'identifiant du dossier cible où déplacer le dossier.
     * @throws MoveException Si une erreur survient lors du déplacement du dossier.
     *                        Cette exception est lancée lorsque le déplacement échoue avec un code de statut HTTP
     *                        différent de 2xx lors de l'appel à l'API externe pour déplacer le dossier.
     */
    public void handle(MoveFolderCommand moveFolderCommand) throws MoveException {
        try {
            // Préparer les données pour l'appel à l'API externe pour déplacer le dossier
            NodeBodyMove nodeBodyMove = new NodeBodyMove();
            nodeBodyMove.setTargetParentId(moveFolderCommand.getTargetParentId());

            // Appel à l'API externe pour déplacer le dossier
            ResponseEntity<NodeEntry> response = nodesApi.moveNode(
                    moveFolderCommand.getFolderId(),
                    nodeBodyMove,
                    null,
                    null
            );

            // Vérifier si le déplacement a réussi (code de statut 2xx)
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Déplacement du dossier effectué avec succès!");

                // Création d'un agrégat de dossier et sauvegarde de l'événement
                FolderAggregate folderAggregate = new FolderAggregate(moveFolderCommand);
                eventSourcingHandler.save(folderAggregate);
            } else {
                // Si le déplacement a échoué, lancer une exception personnalisée avec un message d'erreur explicatif
                throw new MoveException(Constants.ENTITY_MOVE_ERROR_MESSAGE_FORMAT + response.getStatusCodeValue());
            }
        } catch (MoveException e) {
            // Gérer l'exception et la relancer pour une propagation appropriée
            log.info(e.getMessage());
            throw e;
        }
    }


    /**
     * Gère la commande de création d'un fichier.
     *
     * @param createFileCommand La commande de création d'un fichier à gérer.
     *                           Cette commande doit contenir les informations suivantes :
     *                           - folderId : L'identifiant du dossier où créer le fichier.
     *                           - file : Le contenu du fichier à créer (peut être null).
     *                           - name : Le nom du fichier à créer.
     *                           - nodeType : Le type de nœud du fichier à créer (peut être null).
     *                           - renditions : Les rendus du fichier à créer (peut être null).
     *                           - overwrite : Indique si le fichier doit être écrasé s'il existe déjà (peut être null).
     *                           - majorVersion : Indique si la création du fichier doit être une version majeure (peut être null).
     *                           - comment : Le commentaire à inclure lors de la création du fichier (peut être null).
     *                           - autoRename : Indique si le fichier doit être renommé automatiquement en cas de conflit (peut être null).
     *                           - relativePath : Le chemin relatif du fichier (peut être null).
     *                           - versioningEnabled : Indique si la gestion de version est activée pour le fichier (peut être null).
     * @throws CreationException Si une erreur survient lors de la création du fichier.
     *                           Cette exception est lancée lorsque la création échoue avec un code de statut HTTP
     *                           différent de 2xx lors de l'appel à l'API externe pour créer le fichier.
     */
    public void handle(CreateFileCommand createFileCommand) throws CreationException {
        try {
            // Appel à l'API externe pour créer le fichier
            ResponseEntity<NodeEntry> response = contentApi.createNode(
                    createFileCommand.getFolderId(),
                    createFileCommand.getFile(),
                    createFileCommand.getName(),
                    createFileCommand.getNodeType(),
                    createFileCommand.getRenditions(),
                    createFileCommand.getOverwrite(),
                    createFileCommand.getMajorVersion(),
                    createFileCommand.getComment(),
                    createFileCommand.getAutoRename(),
                    createFileCommand.getRelativePath(),
                    createFileCommand.getVersioningEnabled(),
                    null,
                    null
            );

            // Vérifier si la création a réussi (code de statut 2xx)
            if (response.getStatusCode().is2xxSuccessful()) {
                NodeEntry nodeEntry = response.getBody();
                String documentId = nodeEntry.getEntry().getId();

                // Mettre à jour les champs de la commande avec les nouvelles valeurs retournées par l'API
                createFileCommand.setFileId(documentId);
                createFileCommand.setAspectNames(nodeEntry.getEntry().getAspectNames());
                createFileCommand.setProperties(nodeEntry.getEntry().getProperties());
                createFileCommand.setCreated(Date.from(nodeEntry.getEntry().getCreatedAt().toInstant()));
                createFileCommand.setCreator(nodeEntry.getEntry().getCreatedByUser().getId());
                createFileCommand.setModified(Date.from(nodeEntry.getEntry().getModifiedAt().toInstant()));
                createFileCommand.setModifier(nodeEntry.getEntry().getModifiedByUser().getId());
                createFileCommand.setContent(nodeEntry.getEntry().getContent());

                log.info("Document créé avec succès ! ID : " + documentId);

                // Création d'un agrégat de fichier et sauvegarde de l'événement
                FileAggregate fileAggregate = new FileAggregate(createFileCommand);
                eventSourcingHandler.save(fileAggregate);
            } else {
                // Si la création a échoué, lancer une exception personnalisée avec un message d'erreur explicatif
                throw new CreationException(Constants.CREATION_ERROR_MESSAGE + response.getStatusCodeValue());
            }
        } catch (CreationException e) {
            // Gérer l'exception et la relancer pour une propagation appropriée
            log.info(e.getMessage());
            throw e;
        }
    }


    /**
     * Gère la commande de mise à jour d'un fichier.
     *
     * @param updateFileCommand La commande de mise à jour d'un fichier à gérer.
     *                           Cette commande doit contenir les informations suivantes :
     *                           - fileId : L'identifiant du fichier à mettre à jour.
     *                           - name : Le nouveau nom du fichier (peut être null).
     *                           - title : Le nouveau titre du fichier (peut être null).
     *                           - description : La nouvelle description du fichier (peut être null).
     *                           - aspectNames : Les noms d'aspects associés au fichier (peut être null).
     *                           - properties : Les propriétés associées au fichier (peut être null).
     *                           - permissionElements : Les éléments de permission pour le fichier (peut être null).
     * @throws UpdateException Si une erreur survient lors de la mise à jour du fichier.
     *                           Cette exception est lancée lorsque la mise à jour échoue avec un code de statut HTTP
     *                           différent de 200 lors de l'appel à l'API externe pour mettre à jour le fichier.
     */
    public void handle(UpdateFileCommand updateFileCommand) throws UpdateException {
        try {
            // Obtenir les propriétés existantes du fichier depuis l'API externe
            ResponseEntity<NodeEntry> nodeResponse = nodesApi.getNode(updateFileCommand.getFileId(), null, null, null);

            // Vérifier si l'obtention des propriétés a réussi (code de statut HTTP 200)
            if (nodeResponse.getStatusCode() == HttpStatus.OK) {
                NodeEntry nodeEntry = nodeResponse.getBody();
                Map<String, Object> nodeProperties = (Map<String, Object>) nodeEntry.getEntry().getProperties();

                // Mettre à jour les champs de la commande avec les propriétés existantes si les nouveaux champs sont nuls
                if (nodeProperties.containsKey("cm:title") && updateFileCommand.getTitle() == null) {
                    updateFileCommand.setTitle((String) nodeProperties.get("cm:title"));
                }

                if (nodeProperties.containsKey("cm:description") && updateFileCommand.getDescription() == null) {
                    updateFileCommand.setDescription((String) nodeProperties.get("cm:description"));
                }
            }

            // Préparer les données pour l'appel à l'API externe pour mettre à jour le fichier
            NodeBodyUpdate nodeBodyUpdate = new NodeBodyUpdate();
            nodeBodyUpdate.setName(updateFileCommand.getName());
            Object existingProperties = updateFileCommand.getProperties();

            // Gérer les propriétés existantes ou vides pour les mettre à jour avec les nouvelles valeurs
            if (existingProperties == null || existingProperties instanceof JSONObject && ((JSONObject) existingProperties).isEmpty()) {
                JSONObject properties = new JSONObject();
                properties.put("cm:title", updateFileCommand.getTitle());
                properties.put("cm:description", updateFileCommand.getDescription());
                nodeBodyUpdate.setProperties(properties);
            } else {
                JSONObject properties;
                if (existingProperties instanceof JSONObject) {
                    properties = (JSONObject) existingProperties;
                } else {
                    String jsonStr = JSONValue.toJSONString(existingProperties);
                    properties = (JSONObject) JSONValue.parse(jsonStr);
                }
                properties.put("cm:title", updateFileCommand.getTitle());
                properties.put("cm:description", updateFileCommand.getDescription());
                nodeBodyUpdate.setProperties(properties);
            }

            nodeBodyUpdate.setAspectNames(updateFileCommand.getAspectNames());

            // Mettre à jour les permissions du fichier si elles sont fournies dans la commande
            List<PermissionElement> permissionElements = updateFileCommand.getPermissionElements();
            if (permissionElements != null && !permissionElements.isEmpty()) {
                PermissionsBody permissionsBody = new PermissionsBody();
                permissionsBody.setIsInheritanceEnabled(true);
                List<PermissionElement> updatedPermissions = permissionElements.stream()
                        .peek(permissionElement -> permissionElement.setAccessStatus(PermissionElement.AccessStatusEnum.ALLOWED))
                        .collect(Collectors.toList());
                permissionsBody.setLocallySet(updatedPermissions);
                nodeBodyUpdate.setPermissions(permissionsBody);
                updateFileCommand.setPermissions(permissionsBody);
            }

            // Appel à l'API externe pour mettre à jour le fichier
            ResponseEntity<NodeEntry> response = nodesApi.updateNode(updateFileCommand.getFileId(), nodeBodyUpdate, null, null);

            // Vérifier si la mise à jour a réussi (code de statut HTTP 200)
            if (response.getStatusCode() == HttpStatus.OK) {
                NodeEntry updatedNode = response.getBody();

                // Mettre à jour les champs de la commande avec les nouvelles valeurs de l'API
                updateFileCommand.setName(updatedNode.getEntry().getName());
                updateFileCommand.setAspectNames(updatedNode.getEntry().getAspectNames());
                updateFileCommand.setProperties(updatedNode.getEntry().getProperties());
                updateFileCommand.setModified(Date.from(updatedNode.getEntry().getModifiedAt().toInstant()));
                updateFileCommand.setModifier(updatedNode.getEntry().getModifiedByUser().getId());

                log.info("Document mis à jour avec succès !");

                // Création d'un agrégat de fichier et sauvegarde de l'événement
                FileAggregate fileAggregate = new FileAggregate(updateFileCommand);
                eventSourcingHandler.save(fileAggregate);
            } else {
                // Si la mise à jour a échoué, lancer une exception personnalisée avec un message d'erreur explicatif
                throw new UpdateException(Constants.UPDATE_ERROR_MESSAGE + response.getStatusCodeValue());
            }
        } catch (UpdateException e) {
            // Gérer l'exception et la relancer pour une propagation appropriée
            log.info(e.getMessage());
            throw e;
        }
    }


    /**
     * Gère la commande de suppression d'un fichier.
     *
     * @param deleteFileCommand La commande de suppression d'un fichier à gérer.
     *                           Cette commande doit contenir les informations suivantes :
     *                           - fileId : L'identifiant du fichier à supprimer.
     * @throws DeletionException Si une erreur survient lors de la suppression du fichier.
     *                           Cette exception est lancée lorsque la suppression échoue avec un code de statut HTTP
     *                           différent de 2xx lors de l'appel à l'API externe pour supprimer le fichier.
     */
    public void handle(DeleteFileCommand deleteFileCommand) throws DeletionException {
        try {
            // Appel à l'API externe pour supprimer le fichier
            ResponseEntity<Void> response = nodesApi.deleteNode(deleteFileCommand.getFileId(), false);

            // Vérifier si la suppression a réussi (code de statut 2xx)
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Document supprimé avec succès !");

                // Création d'un agrégat de fichier et sauvegarde de l'événement
                FileAggregate fileAggregate = new FileAggregate(deleteFileCommand);
                eventSourcingHandler.save(fileAggregate);
            } else {
                // Si la suppression a échoué, lancer une exception personnalisée avec un message d'erreur explicatif
                throw new DeletionException(Constants.DELETION_ERROR_MESSAGE + response.getStatusCodeValue());
            }
        } catch (DeletionException e) {
            // Gérer l'exception et la relancer pour une propagation appropriée
            log.info(e.getMessage());
            throw e;
        }
    }


    /**
     * Gère la commande de déplacement d'un fichier.
     *
     * @param moveFileCommand La commande de déplacement d'un fichier à gérer.
     *                        Cette commande doit contenir les informations suivantes :
     *                        - fileId : L'identifiant du fichier à déplacer.
     *                        - targetParentId : L'identifiant du répertoire cible où déplacer le fichier.
     * @throws MoveException Si une erreur survient lors du déplacement du fichier.
     *                        Cette exception est lancée lorsque le déplacement échoue avec un code de statut HTTP
     *                        différent de 2xx lors de l'appel à l'API externe pour déplacer le fichier.
     */
    public void handle(MoveFileCommand moveFileCommand) throws MoveException {
        try {
            // Préparer les données pour l'appel à l'API externe pour déplacer le fichier
            NodeBodyMove nodeBodyMove = new NodeBodyMove();
            nodeBodyMove.setTargetParentId(moveFileCommand.getTargetParentId());

            // Appel à l'API externe pour déplacer le fichier
            ResponseEntity<NodeEntry> response = nodesApi.moveNode(
                    moveFileCommand.getFileId(),
                    nodeBodyMove,
                    null,
                    null
            );

            // Vérifier si le déplacement a réussi (code de statut 2xx)
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Déplacement du document effectué avec succès !");

                // Création d'un agrégat de fichier et sauvegarde de l'événement
                FileAggregate fileAggregate = new FileAggregate(moveFileCommand);
                eventSourcingHandler.save(fileAggregate);
            } else {
                // Si le déplacement a échoué, lancer une exception personnalisée avec un message d'erreur explicatif
                throw new MoveException(Constants.ENTITY_MOVE_ERROR_MESSAGE_FORMAT + response.getStatusCodeValue());
            }
        } catch (MoveException e) {
            // Gérer l'exception et la relancer pour une propagation appropriée
            log.info(e.getMessage());
            throw e;
        }
    }


    /**
     * Gère la commande de création d'un utilisateur.
     *
     * @param createUserCommand La commande de création d'un utilisateur à gérer.
     *                           Cette commande doit contenir les informations suivantes :
     *                           - userId : L'identifiant de l'utilisateur à créer.
     *                           - password : Le mot de passe de l'utilisateur.
     *                           - firstName : Le prénom de l'utilisateur.
     *                           - lastName : Le nom de l'utilisateur.
     *                           - email : L'adresse email de l'utilisateur.
     *                           - description : La description de l'utilisateur (peut être null).
     *                           - skypeId : L'identifiant Skype de l'utilisateur (peut être null).
     *                           - googleId : L'identifiant Google de l'utilisateur (peut être null).
     *                           - instantMessageId : L'identifiant de messagerie instantanée de l'utilisateur (peut être null).
     *                           - jobTitle : Le titre de poste de l'utilisateur (peut être null).
     *                           - location : Le lieu de travail de l'utilisateur (peut être null).
     *                           - company : Le nom de l'entreprise de l'utilisateur (peut être null).
     *                           - mobile : Le numéro de téléphone mobile de l'utilisateur (peut être null).
     *                           - telephone : Le numéro de téléphone de l'utilisateur (peut être null).
     *                           - userStatus : Le statut de l'utilisateur (peut être null).
     *                           - aspectNames : Les noms d'aspects associés à l'utilisateur (peut être null).
     *                           - properties : Les propriétés associées à l'utilisateur (peuvent être null).
     * @throws CreationException Si une erreur survient lors de la création de l'utilisateur.
     *                           Cette exception est lancée lorsque la création échoue avec un code de statut HTTP
     *                           différent de 2xx lors de l'appel à l'API externe pour créer l'utilisateur.
     */
    public void handle(CreateUserCommand createUserCommand) throws CreationException {
        try {
            // Préparer les données pour l'appel à l'API externe pour créer l'utilisateur
            PersonBodyCreate personBodyCreate = new PersonBodyCreate();
            personBodyCreate.setId(createUserCommand.getUserId());
            personBodyCreate.setPassword(createUserCommand.getPassword());
            personBodyCreate.setFirstName(createUserCommand.getFirstName());
            personBodyCreate.setLastName(createUserCommand.getLastName());
            personBodyCreate.setEmail(createUserCommand.getEmail());
            personBodyCreate.setDescription(createUserCommand.getDescription());
            personBodyCreate.setSkypeId(createUserCommand.getSkypeId());
            personBodyCreate.setGoogleId(createUserCommand.getGoogleId());
            personBodyCreate.setInstantMessageId(createUserCommand.getInstantMessageId());
            personBodyCreate.setJobTitle(createUserCommand.getJobTitle());
            personBodyCreate.setLocation(createUserCommand.getLocation());
            personBodyCreate.setCompany(createUserCommand.getCompany());
            personBodyCreate.setMobile(createUserCommand.getMobile());
            personBodyCreate.setTelephone(createUserCommand.getTelephone());
            personBodyCreate.setUserStatus(createUserCommand.getUserStatus());
            personBodyCreate.setAspectNames(createUserCommand.getAspectNames());
            personBodyCreate.setProperties(createUserCommand.getProperties());

            // Appel à l'API externe pour créer l'utilisateur
            ResponseEntity<PersonEntry> response = peopleApi.createPerson(personBodyCreate, null);

            // Vérifier si la création a réussi (code de statut 2xx)
            if (response.getStatusCode().is2xxSuccessful()) {
                // Obtenir les informations de l'utilisateur créé depuis la réponse de l'API
                PersonEntry createdPerson = response.getBody();

                // Mettre à jour les champs de la commande avec les valeurs de l'utilisateur créé
                createUserCommand.setDisplayName(createdPerson.getEntry().getDisplayName());
                createUserCommand.setAvatarId(createdPerson.getEntry().getAvatarId());
                createUserCommand.setStatusUpdatedAt(createdPerson.getEntry().getStatusUpdatedAt());
                createUserCommand.setEnabled(createdPerson.getEntry().isEnabled());
                createUserCommand.setEmailNotificationsEnabled(createdPerson.getEntry().isEmailNotificationsEnabled());
                createUserCommand.setCapabilities(createdPerson.getEntry().getCapabilities());

                // Création d'un agrégat d'utilisateur et sauvegarde de l'événement
                UserAggregate userAggregate = new UserAggregate(createUserCommand);
                eventSourcingHandler.save(userAggregate);

                log.info("Utilisateur créé avec succès ! ID : " + createUserCommand.getUserId());
            } else {
                // Si la création a échoué, lancer une exception personnalisée avec un message d'erreur explicatif
                throw new CreationException(Constants.CREATION_ERROR_MESSAGE + response.getStatusCodeValue());
            }
        } catch (CreationException e) {
            // Gérer l'exception et la relancer pour une propagation appropriée
            log.info(e.getMessage());
            throw e;
        }
    }


    /**
     * Gère la commande de mise à jour d'un utilisateur.
     *
     * @param updateUserCommand La commande de mise à jour d'un utilisateur à gérer.
     *                           Cette commande doit contenir les informations suivantes :
     *                           - userId : L'identifiant de l'utilisateur à mettre à jour.
     *                           - oldPassword : Le mot de passe actuel de l'utilisateur (peut être null si non modifié).
     *                           - password : Le nouveau mot de passe de l'utilisateur (peut être null si non modifié).
     *                           - firstName : Le nouveau prénom de l'utilisateur (peut être null si non modifié).
     *                           - lastName : Le nouveau nom de l'utilisateur (peut être null si non modifié).
     *                           - email : Le nouvel email de l'utilisateur (peut être null si non modifié).
     *                           - description : La nouvelle description de l'utilisateur (peut être null si non modifiée).
     *                           - skypeId : Le nouvel identifiant Skype de l'utilisateur (peut être null si non modifié).
     *                           - googleId : Le nouvel identifiant Google de l'utilisateur (peut être null si non modifié).
     *                           - instantMessageId : Le nouvel identifiant de messagerie instantanée de l'utilisateur (peut être null si non modifié).
     *                           - jobTitle : Le nouveau titre de poste de l'utilisateur (peut être null si non modifié).
     *                           - location : Le nouveau lieu de travail de l'utilisateur (peut être null si non modifié).
     *                           - company : Le nouveau nom de l'entreprise de l'utilisateur (peut être null si non modifié).
     *                           - mobile : Le nouveau numéro de téléphone mobile de l'utilisateur (peut être null si non modifié).
     *                           - telephone : Le nouveau numéro de téléphone de l'utilisateur (peut être null si non modifié).
     *                           - userStatus : Le nouveau statut de l'utilisateur (peut être null si non modifié).
     *                           - enabled : Le nouvel état d'activation de l'utilisateur (peut être null si non modifié).
     *                           - emailNotificationsEnabled : Le nouvel état d'activation des notifications par email pour l'utilisateur (peut être null si non modifié).
     *                           - aspectNames : Les nouveaux noms d'aspects associés à l'utilisateur (peut être null si non modifiés).
     *                           - properties : Les nouvelles propriétés associées à l'utilisateur (peuvent être null si non modifiées).
     * @throws UpdateException Si une erreur survient lors de la mise à jour de l'utilisateur.
     *                           Cette exception est lancée lorsque la mise à jour échoue avec un code de statut HTTP
     *                           différent de 2xx lors de l'appel à l'API externe pour mettre à jour l'utilisateur.
     */
    public void handle(UpdateUserCommand updateUserCommand) throws UpdateException {
        try {
            // Récupérer les informations de l'utilisateur existant depuis l'API externe
            ResponseEntity<PersonEntry> existingUserResponse = peopleApi.getPerson(updateUserCommand.getUserId(), null);

            if (existingUserResponse.getStatusCode().is2xxSuccessful()) {
                // Obtenir les informations de l'utilisateur existant depuis la réponse de l'API
                PersonEntry existingUser = existingUserResponse.getBody();

                // Mettre à jour les champs de la commande avec les valeurs de l'utilisateur existant s'ils ne sont pas fournis dans la commande de mise à jour
                if (updateUserCommand.getFirstName() == null) {
                    updateUserCommand.setFirstName(existingUser.getEntry().getFirstName());
                }
                // (Répéter ce processus pour tous les autres champs à mettre à jour)

                // Préparer les données pour l'appel à l'API externe pour mettre à jour l'utilisateur
                PersonBodyUpdate personBodyUpdate = new PersonBodyUpdate();
                personBodyUpdate.setOldPassword(updateUserCommand.getOldPassword());
                personBodyUpdate.setPassword(updateUserCommand.getPassword());
                personBodyUpdate.setFirstName(updateUserCommand.getFirstName());
                // (Inclure tous les autres champs de mise à jour)

                // Appel à l'API externe pour mettre à jour l'utilisateur
                ResponseEntity<PersonEntry> response = peopleApi.updatePerson(updateUserCommand.getUserId(), personBodyUpdate, null);

                // Vérifier si la mise à jour a réussi (code de statut 2xx)
                if (response.getStatusCode().is2xxSuccessful()) {
                    // Obtenir les informations de l'utilisateur mis à jour depuis la réponse de l'API
                    PersonEntry updatedPerson = response.getBody();

                    // Création d'un agrégat d'utilisateur et sauvegarde de l'événement
                    UserAggregate userAggregate = new UserAggregate(updateUserCommand);
                    eventSourcingHandler.save(userAggregate);

                    log.info("Utilisateur mis à jour avec succès ! ID : " + updateUserCommand.getUserId());
                } else {
                    // Si la mise à jour a échoué, lancer une exception personnalisée avec un message d'erreur explicatif
                    throw new UpdateException(Constants.UPDATE_ERROR_MESSAGE + response.getStatusCodeValue());
                }
            }
        } catch (UpdateException e) {
            // Gérer l'exception et la relancer pour une propagation appropriée
            log.info(e.getMessage());
            throw e;
        }
    }


    /**
     * Gère la commande de création d'un groupe.
     *
     * @param createGroupCommand La commande de création d'un groupe à gérer.
     *                           Cette commande doit contenir les informations suivantes :
     *                           - groupId : L'identifiant du groupe à créer.
     *                           - displayName : Le nom d'affichage du groupe.
     *                           - parentIds : La liste des identifiants des groupes parents, s'il y en a (peut être null).
     * @throws CreationException Si une erreur survient lors de la création du groupe.
     *                           Cette exception est lancée lorsque la création échoue avec un code de statut HTTP
     *                           différent de 2xx lors de l'appel à l'API externe pour créer le groupe.
     */
    public void handle(CreateGroupCommand createGroupCommand) throws CreationException {
        try {
            // Préparer les données pour l'appel à l'API externe pour créer le groupe
            GroupBodyCreate groupBodyCreate = new GroupBodyCreate();
            groupBodyCreate.setId(createGroupCommand.getGroupId());
            groupBodyCreate.setDisplayName(createGroupCommand.getDisplayName());
            groupBodyCreate.setParentIds(createGroupCommand.getParentIds());

            // Appel à l'API externe pour créer le groupe
            ResponseEntity<GroupEntry> response = groupsApi.createGroup(groupBodyCreate, null, null);

            // Vérifier si la création a réussi (code de statut 2xx)
            if (response.getStatusCode().is2xxSuccessful()) {
                // Récupérer les informations du groupe créé depuis la réponse de l'API
                GroupEntry createdGroup = response.getBody();

                // Mettre à jour les informations de la commande avec celles du groupe créé
                createGroupCommand.setIsRoot(createdGroup.getEntry().isIsRoot());
                createGroupCommand.setZones(createdGroup.getEntry().getZones());

                // Création d'un agrégat de groupe et sauvegarde de l'événement
                GroupAggregate groupAggregate = new GroupAggregate(createGroupCommand);
                eventSourcingHandler.save(groupAggregate);

                log.info("Groupe créé avec succès ! ID : " + createdGroup.getEntry().getId());
            } else {
                // Si la création a échoué, lancer une exception personnalisée avec un message d'erreur explicatif
                throw new CreationException(Constants.CREATION_ERROR_MESSAGE + response.getStatusCodeValue());
            }
        } catch (CreationException e) {
            // Gérer l'exception et la relancer pour une propagation appropriée
            log.info(e.getMessage());
            throw e;
        }
    }


    /**
     * Gère la commande de suppression d'un groupe.
     *
     * @param deleteGroupCommand La commande de suppression d'un groupe à gérer.
     *                           Cette commande doit contenir les informations suivantes :
     *                           - groupId : L'identifiant du groupe à supprimer.
     * @throws DeletionException Si une erreur survient lors de la suppression du groupe.
     *                           Cette exception est lancée lorsque la suppression échoue avec un code de statut HTTP
     *                           différent de 2xx.
     */
    @Override
    public void handle(DeleteGroupCommand deleteGroupCommand) throws DeletionException {
        try {
            // Appel à l'API externe pour supprimer le groupe
            ResponseEntity<Void> response = groupsApi.deleteGroup(deleteGroupCommand.getGroupId(), false);

            // Vérifier si la suppression a réussi (code de statut 2xx)
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Groupe supprimé avec succès !");

                // Création d'un agrégat de groupe et sauvegarde de l'événement
                GroupAggregate groupAggregate = new GroupAggregate(deleteGroupCommand);
                eventSourcingHandler.save(groupAggregate);
            } else {
                // Si la suppression a échoué, lancer une exception personnalisée avec un message d'erreur explicatif
                throw new DeletionException(Constants.DELETION_ERROR_MESSAGE + response.getStatusCodeValue());
            }
        } catch (DeletionException e) {
            // Gérer l'exception et la relancer pour une propagation appropriée
            log.info(e.getMessage());
            throw e;
        }
    }

    /**
     * Gère la commande d'affectation d'une personne à un groupe.
     *
     * @param assignPersonToGroupCommand La commande d'affectation d'une personne à un groupe à gérer.
     *                                   Cette commande doit contenir les informations suivantes :
     *                                   - groupId : L'identifiant du groupe auquel affecter la personne.
     *                                   - personId : L'identifiant de la personne à affecter au groupe.
     * @throws CreationException Si une erreur survient lors de l'affectation de la personne au groupe.
     *                           Cette exception est lancée lorsque l'affectation échoue avec un code de statut HTTP
     *                           différent de 2xx lors de l'appel à l'API externe pour créer la relation personne-groupe.
     */
    @Override
    public void handle(AssignPersonToGroupCommand assignPersonToGroupCommand) throws CreationException {
        try {
            // Préparer les données pour l'appel à l'API externe pour créer la relation personne-groupe
            GroupMembershipBodyCreate groupMembershipBodyCreate = new GroupMembershipBodyCreate();
            groupMembershipBodyCreate.setId(assignPersonToGroupCommand.getPersonId());
            groupMembershipBodyCreate.setMemberType(GroupMembershipBodyCreate.MemberTypeEnum.PERSON);

            // Appel à l'API externe pour créer la relation personne-groupe
            ResponseEntity<GroupMemberEntry> response = groupsApi.createGroupMembership(
                    assignPersonToGroupCommand.getGroupId(),
                    groupMembershipBodyCreate,
                    null
            );

            // Vérifier si l'affectation a réussi (code de statut 2xx)
            if (response.getStatusCode().is2xxSuccessful()) {
                // Générer un nouvel identifiant unique pour la relation personne-groupe créée
                var id = UUID.randomUUID().toString();
                assignPersonToGroupCommand.setGroupMembershipId(id);

                // Création d'un agrégat de groupe et sauvegarde de l'événement
                GroupAggregate groupAggregate = new GroupAggregate(assignPersonToGroupCommand);
                eventSourcingHandler.save(groupAggregate);

                log.info("Personne affectée au groupe avec succès !");
            } else {
                // Si l'affectation a échoué, lancer une exception personnalisée avec un message d'erreur explicatif
                throw new CreationException(Constants.CREATION_ERROR_MESSAGE + response.getStatusCodeValue());
            }
        } catch (CreationException e) {
            // Gérer l'exception et la relancer pour une propagation appropriée
            log.info(e.getMessage());
            throw e;
        }
    }


    /**
     * Gère la commande de suppression d'une personne d'un groupe.
     *
     * @param removePersonFromGroupCommand La commande de suppression d'une personne d'un groupe à gérer.
     *                                     Cette commande doit contenir les informations suivantes :
     *                                     - groupId : L'identifiant du groupe à partir duquel supprimer la personne.
     *                                     - personId : L'identifiant de la personne à supprimer du groupe.
     * @throws DeletionException Si une erreur survient lors de la suppression de la personne du groupe.
     *                           Cette exception est lancée lorsque la suppression échoue avec un code de statut HTTP
     *                           différent de 2xx lors de l'appel à l'API externe pour supprimer la relation personne-groupe.
     */
    @Override
    public void handle(RemovePersonFromGroupCommand removePersonFromGroupCommand) throws DeletionException {
        try {
            // Appel à l'API externe pour supprimer la personne du groupe
            ResponseEntity<Void> response = groupsApi.deleteGroupMembership(
                    removePersonFromGroupCommand.getGroupId(),
                    removePersonFromGroupCommand.getPersonId()
            );

            // Vérifier si la suppression a réussi (code de statut 2xx)
            if (response.getStatusCode().is2xxSuccessful()) {
                // Création d'un agrégat de groupe et sauvegarde de l'événement
                GroupAggregate groupAggregate = new GroupAggregate(removePersonFromGroupCommand);
                eventSourcingHandler.save(groupAggregate);

                log.info("Affectation de la personne au groupe enlevée avec succès !");
            } else {
                // Si la suppression a échoué, lancer une exception personnalisée avec un message d'erreur explicatif
                throw new DeletionException(Constants.DELETION_ERROR_MESSAGE + response.getStatusCodeValue());
            }
        } catch (DeletionException e) {
            // Gérer l'exception et la relancer pour une propagation appropriée
            log.info(e.getMessage());
            throw e;
        }
    }

    /**
     * Gère la commande de création d'un lien partagé pour un fichier.
     *
     * @param createSharedLinkCommand La commande de création d'un lien partagé à gérer.
     *                                Cette commande doit contenir l'identifiant du fichier (nodeId) pour lequel
     *                                le lien partagé doit être créé.
     * @throws CreationException Si une erreur survient lors de la création du lien partagé.
     *                           Cette exception est lancée lorsque la création échoue avec un code de statut HTTP
     *                           différent de 2xx lors de l'appel à l'API externe pour créer le lien partagé.
     */
    public void handle(CreateSharedLinkCommand createSharedLinkCommand) throws CreationException {
        try {
            // Préparer les données pour la création du lien partagé
            SharedLinkBodyCreate sharedLinkBodyCreate = new SharedLinkBodyCreate();
            sharedLinkBodyCreate.setNodeId(createSharedLinkCommand.getFileId());

            // Appel à l'API externe pour créer le lien partagé
            ResponseEntity<SharedLinkEntry> response = sharedLinksApi.createSharedLink(sharedLinkBodyCreate, null, null);

            // Vérifier si la création a réussi (code de statut 2xx)
            if (response.getStatusCode().is2xxSuccessful()) {
                // Récupérer les informations du lien partagé créé
                SharedLinkEntry createdSharedLink = response.getBody();

                // Mettre à jour les propriétés de la commande CreateSharedLinkCommand avec les informations du lien partagé créé
                createSharedLinkCommand.setSharedLinkId(createdSharedLink.getEntry().getId());
                createSharedLinkCommand.setName(createdSharedLink.getEntry().getName());
                createSharedLinkCommand.setTitle(createdSharedLink.getEntry().getTitle());
                createSharedLinkCommand.setDescription(createdSharedLink.getEntry().getDescription());
                createSharedLinkCommand.setModifiedAt(Date.from(createdSharedLink.getEntry().getModifiedAt().toInstant()));
                createSharedLinkCommand.setModifiedByUser(createdSharedLink.getEntry().getModifiedByUser());
                createSharedLinkCommand.setSharedByUser(createdSharedLink.getEntry().getSharedByUser());
                createSharedLinkCommand.setContent(createdSharedLink.getEntry().getContent());
                createSharedLinkCommand.setAllowableOperations(createdSharedLink.getEntry().getAllowableOperations());
                createSharedLinkCommand.setAllowableOperationsOnTarget(createdSharedLink.getEntry().getAllowableOperationsOnTarget());
                createSharedLinkCommand.setIsFavorite(createdSharedLink.getEntry().isIsFavorite());
                createSharedLinkCommand.setProperties(createdSharedLink.getEntry().getProperties());
                createSharedLinkCommand.setAspectNames(createdSharedLink.getEntry().getAspectNames());
                createSharedLinkCommand.setPath(createdSharedLink.getEntry().getPath());

                // Création d'un agrégat de lien partagé et sauvegarde de l'événement
                SharedLinkAggregate sharedLinkAggregate = new SharedLinkAggregate(createSharedLinkCommand);
                eventSourcingHandler.save(sharedLinkAggregate);

                log.info("Lien partagé créé avec succès ! ID : " + createdSharedLink.getEntry().getId());
            } else {
                // Si la création a échoué, lancer une exception personnalisée avec un message d'erreur explicatif
                throw new CreationException(Constants.CREATION_ERROR_MESSAGE + response.getStatusCodeValue());
            }
        } catch (CreationException e) {
            // Gérer l'exception et la relancer pour une propagation appropriée
            log.info(e.getMessage());
            throw e;
        }
    }

    /**
     * Gère la commande de suppression d'un lien partagé.
     *
     * @param deleteSharedLinkCommand La commande de suppression d'un lien partagé à gérer.
     *                                Cette commande doit contenir l'identifiant du lien partagé (sharedLinkId) à supprimer.
     * @throws DeletionException Si une erreur survient lors de la suppression du lien partagé.
     *                          Cette exception est lancée lorsque la suppression échoue avec un code de statut HTTP
     *                          différent de 2xx lors de l'appel à l'API externe pour supprimer le lien partagé.
     */
    @Override
    public void handle(DeleteSharedLinkCommand deleteSharedLinkCommand) throws DeletionException {
        try {
            // Appel à l'API externe pour supprimer le lien partagé
            ResponseEntity<Void> response = sharedLinksApi.deleteSharedLink(deleteSharedLinkCommand.getSharedLinkId());

            // Vérifier si la suppression a réussi (code de statut 2xx)
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("lien partagé supprimé avec succès !");

                // Création d'un agrégat de lien partagé et sauvegarde de l'événement
                SharedLinkAggregate sharedLinkAggregate = new SharedLinkAggregate(deleteSharedLinkCommand);
                eventSourcingHandler.save(sharedLinkAggregate);
            } else {
                // Si la suppression a échoué, lancer une exception personnalisée avec un message d'erreur explicatif
                throw new DeletionException(Constants.DELETION_ERROR_MESSAGE + response.getStatusCodeValue());
            }
        } catch (DeletionException e) {
            // Gérer l'exception et la relancer pour une propagation appropriée
            log.info(e.getMessage());
            throw e;
        }
    }

}

package com.oga.ged.query.rest.repository;

import com.oga.ged.query.rest.dto.Folder;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Folder entities.
 */
@Primary
public interface FolderRepository extends MongoRepository<Folder,String> {
    List<Folder> findByParentId(String parentId);
    @Query(value = "{'_id' : ?0 }")
    Optional<Folder> findById(String id);



}

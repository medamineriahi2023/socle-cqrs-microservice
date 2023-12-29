package com.oga.ged.query.rest.repository;

import com.oga.ged.query.rest.dto.File;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Repository interface for managing File entities.
 */
@Primary
public interface FileRepository extends MongoRepository<File,String> {
    List<File> findByFolderId(String folderId);
    long countByFolderId(String folderId);


}

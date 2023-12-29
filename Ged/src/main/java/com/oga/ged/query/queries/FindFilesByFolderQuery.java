package com.oga.ged.query.queries;

import com.oga.cqrsref.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * query class for finding files by folder ID.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindFilesByFolderQuery extends BaseQuery {
    private String folderId;

}
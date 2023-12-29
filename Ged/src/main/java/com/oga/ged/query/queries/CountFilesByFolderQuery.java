package com.oga.ged.query.queries;

import com.oga.cqrsref.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * query class for counting files by folder.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountFilesByFolderQuery extends BaseQuery {
    private String folderId;
}

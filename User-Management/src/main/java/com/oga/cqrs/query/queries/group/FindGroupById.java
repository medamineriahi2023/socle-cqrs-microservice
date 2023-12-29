package com.oga.cqrs.query.queries.group;


import com.oga.cqrsref.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class FindGroupById extends BaseQuery {
  String id;

}

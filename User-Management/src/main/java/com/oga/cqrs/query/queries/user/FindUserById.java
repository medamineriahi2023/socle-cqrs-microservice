package com.oga.cqrs.query.queries.user;


import com.oga.cqrsref.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class FindUserById extends BaseQuery {
  String id;

}

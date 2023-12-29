package com.oga.workflow.command.rest.request;

import com.oga.workflow.command.rest.entities.Review;
import lombok.Data;

@Data
public class ProcessRequest {
    private String processKey;
    private String bookId ;
    private Review review;


}

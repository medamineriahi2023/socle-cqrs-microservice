package com.oga.workflow.command.rest.controller;

import com.oga.workflow.command.rest.entities.Review;
import com.oga.workflow.command.rest.services.StartMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workflow/tasks")
public class TaskController {


    private StartMessage startMessage;

    @Autowired
    public TaskController(StartMessage startMessage) {
        this.startMessage = startMessage;
    }

    @PostMapping("/start")
    public void startProcess(@RequestBody Review review) {

        startMessage.startPorcessByMessage(review);

    }
}

package be.brickbit.lpm.catering.controller;

import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.service.task.TaskService;
import be.brickbit.lpm.infrastructure.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("task")
public class TaskController extends AbstractController{
    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/{id}/start", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void startedTask(@PathVariable("id") Long id) {
        taskService.updateTaskWithStatus(id, OrderStatus.IN_PROGRESS, getCurrentUser());
    }

    @RequestMapping(value = "/{id}/complete", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void completeTask(@PathVariable("id") Long id) {
        taskService.updateTaskWithStatus(id, OrderStatus.READY, getCurrentUser());
    }
}

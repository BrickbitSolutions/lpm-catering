package be.brickbit.lpm.catering.controller;

import be.brickbit.lpm.catering.service.queue.IQueueService;
import be.brickbit.lpm.catering.service.queue.dto.QueueDto;
import be.brickbit.lpm.catering.service.queue.mapper.QueueDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/queue")
public class QueueController {
    @Autowired
    private IQueueService queueService;

    @Autowired
    private QueueDtoMapper queueDtoMapper;

    @RequestMapping(value = "/{queueName}", method = RequestMethod.GET, produces = MediaType
            .APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN', 'CATERING_CREW')")
    public List<QueueDto> getQueueTasks(@PathVariable String queueName) {
        return queueService.findAllTasks(queueName, queueDtoMapper);
    }
}

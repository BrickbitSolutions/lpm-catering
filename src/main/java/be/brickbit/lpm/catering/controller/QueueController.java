package be.brickbit.lpm.catering.controller;

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

import be.brickbit.lpm.catering.service.product.IProductService;
import be.brickbit.lpm.catering.service.queue.QueueService;
import be.brickbit.lpm.catering.service.queue.dto.QueueDto;
import be.brickbit.lpm.catering.service.queue.mapper.QueueDtoMapper;

@RestController
@RequestMapping("/queue")
public class QueueController {
    @Autowired
    private QueueService queueService;

    @Autowired
    private IProductService productService;

    @Autowired
    private QueueDtoMapper queueDtoMapper;

    @RequestMapping(value = "/{queueName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN', 'CATERING_CREW')")
    public List<QueueDto> getQueueTasks(@PathVariable String queueName) {
        return queueService.findAllTasks(queueName, queueDtoMapper);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN', 'CATERING_CREW')")
    @ResponseStatus(HttpStatus.OK)
    public List<String> findAllQueueNames() {
        return productService.findAllQueueNames();
    }
}

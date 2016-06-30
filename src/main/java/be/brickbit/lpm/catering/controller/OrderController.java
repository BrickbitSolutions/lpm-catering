package be.brickbit.lpm.catering.controller;

import java.util.List;

import javax.validation.Valid;

import be.brickbit.lpm.catering.domain.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import be.brickbit.lpm.catering.service.order.IOrderService;
import be.brickbit.lpm.catering.service.order.command.DirectOrderCommand;
import be.brickbit.lpm.catering.service.order.command.RemoteOrderCommand;
import be.brickbit.lpm.catering.service.order.dto.OrderDto;
import be.brickbit.lpm.catering.service.order.mapper.OrderDtoMapper;
import be.brickbit.lpm.catering.service.queue.IQueueService;
import be.brickbit.lpm.catering.service.queue.dto.QueueDto;
import be.brickbit.lpm.catering.service.queue.mapper.QueueDtoMapper;
import be.brickbit.lpm.infrastructure.AbstractController;

@RequestMapping("/order")
@RestController
public class OrderController extends AbstractController {
    @Autowired
    private IOrderService orderService;

    @Autowired
    private OrderDtoMapper orderDtoMapper;

    @Autowired
    private IQueueService queueService;

    @Autowired
    private QueueDtoMapper queueDtoMapper;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RequestMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getAllOrders(){
        return orderService.findAll(orderDtoMapper);
    }

    @RequestMapping(value = "/direct", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN', 'CATERING_CREW')")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto saveDirectOrder(@RequestBody @Valid DirectOrderCommand command){
        OrderDto order = orderService.placeDirectOrder(command, orderDtoMapper, getCurrentUser());

        queueService.queueAllTasksDirectOrder(order.getId(), queueDtoMapper).stream().forEach(this::pushToQueue);

        return order;
    }

    @RequestMapping(value = "/remote", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @PreAuthorize(value = "hasAnyRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto saveRemoteOrder(@RequestBody @Valid RemoteOrderCommand command){
        OrderDto order =  orderService.placeRemoteOrder(command, orderDtoMapper, getCurrentUser());

        queueService.queueAllTasks(order.getId(), queueDtoMapper).stream().forEach(this::pushToQueue);

        return order;
    }

    private void pushToQueue(QueueDto message){
        messagingTemplate.convertAndSend("/topic/kitchen.queue." + message.getQueueName(), message);
    }
}

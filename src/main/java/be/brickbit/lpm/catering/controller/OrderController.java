package be.brickbit.lpm.catering.controller;

import be.brickbit.lpm.catering.service.order.IOrderService;
import be.brickbit.lpm.catering.service.order.command.DirectOrderCommand;
import be.brickbit.lpm.catering.service.order.command.RemoteOrderCommand;
import be.brickbit.lpm.catering.service.order.dto.OrderDto;
import be.brickbit.lpm.catering.service.order.mapper.OrderDtoMapper;
import be.brickbit.lpm.catering.service.queue.QueueService;
import be.brickbit.lpm.catering.service.queue.dto.KitchenQueueDto;
import be.brickbit.lpm.catering.service.queue.mapper.KitchenQueueDtoMapper;
import be.brickbit.lpm.infrastructure.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/order")
public class OrderController extends AbstractController {
    @Autowired
    private IOrderService orderService;

    @Autowired
    private OrderDtoMapper orderDtoMapper;

    @Autowired
    private QueueService queueService;

    @Autowired
    private KitchenQueueDtoMapper mapper;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RequestMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'CATERING_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getAllOrders(){
        return orderService.findAll(orderDtoMapper);
    }

    @RequestMapping(value = "/direct", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'CATERING_ADMIN', 'CATERING_CREW')")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto saveDirectOrder(@RequestBody @Valid DirectOrderCommand command){
        OrderDto order = orderService.placeDirectOrder(command, orderDtoMapper, getCurrentUser());

        queueTasks(order);

        return order;
    }

    @RequestMapping(value = "/remote", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @PreAuthorize(value = "hasAnyRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto saveRemoteOrder(@RequestBody @Valid RemoteOrderCommand command){
        OrderDto order =  orderService.placeRemoteOrder(command, orderDtoMapper, getCurrentUser());

        queueTasks(order);

        return order;
    }

    private void queueTasks(OrderDto order) {
       queueService.queueTasks(order.getId(), mapper).stream().forEach(this::pushToQueue);
    }

    private void pushToQueue(KitchenQueueDto message){
        messagingTemplate.convertAndSend("/topic/kitchen/" + message.getQueueName(), message);
    }
}

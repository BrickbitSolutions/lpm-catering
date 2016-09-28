package be.brickbit.lpm.catering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.context.support.SecurityWebApplicationContextUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.service.order.IOrderService;
import be.brickbit.lpm.catering.service.order.command.DirectOrderCommand;
import be.brickbit.lpm.catering.service.order.command.RemoteOrderCommand;
import be.brickbit.lpm.catering.service.order.dto.OrderDto;
import be.brickbit.lpm.catering.service.order.mapper.OrderDtoMapper;
import be.brickbit.lpm.infrastructure.AbstractController;

@RequestMapping("/order")
@RestController
public class OrderController extends AbstractController {
    @Autowired
    private IOrderService orderService;

    @Autowired
    private OrderDtoMapper orderDtoMapper;

    @RequestMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getAllOrders() {
        return orderService.findAll(orderDtoMapper);
    }

    @RequestMapping(value = "/all/ready", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN', 'CATERING_CREW')")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> findAllReadyOrders() {
        return orderService.findOrderByStatus(OrderStatus.READY, orderDtoMapper);
    }

    @RequestMapping(value = "/{id}/process", method = RequestMethod.PUT)
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN', 'CATERING_CREW')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void processOrder(@PathVariable("id") Long id) {
        orderService.processOrder(id);
    }

    @RequestMapping(value = "/direct", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN', 'CATERING_CREW')")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto saveDirectOrder(@RequestBody @Valid DirectOrderCommand command) {
        return orderService.placeDirectOrder(command, orderDtoMapper, getCurrentUser());
    }

    @RequestMapping(value = "/remote", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @PreAuthorize(value = "hasAnyRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto saveRemoteOrder(@RequestBody @Valid RemoteOrderCommand command) {
        return orderService.placeRemoteOrder(command, orderDtoMapper, getCurrentUser());
    }
}

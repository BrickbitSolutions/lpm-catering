package be.brickbit.lpm.catering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.service.order.IOrderService;
import be.brickbit.lpm.catering.service.order.command.DirectOrderCommand;
import be.brickbit.lpm.catering.service.order.command.RemoteOrderCommand;
import be.brickbit.lpm.catering.service.order.dto.OrderDetailDto;
import be.brickbit.lpm.catering.service.order.mapper.OrderDetailDtoMapper;
import be.brickbit.lpm.infrastructure.AbstractController;

@RequestMapping("/order")
@RestController
public class OrderController extends AbstractController {
    @Autowired
    private IOrderService orderService;

    @Autowired
    private OrderDetailDtoMapper orderDetailDtoMapper;

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN', 'CATERING_CREW')")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDetailDto> getAllOrders(@RequestParam(value = "status", required = false) OrderStatus orderStatus) {
        if(orderStatus == null){
            return orderService.findAll(orderDetailDtoMapper);
        }else{
            return orderService.findOrderByStatus(orderStatus, orderDetailDtoMapper);
        }
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDetailDto> getOrderHistory() {
        return orderService.findByUserId(getCurrentUser().getId(), orderDetailDtoMapper);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public OrderDetailDto getOrderDetails(@PathVariable("id") Long id) {
        return orderService.findOne(id, orderDetailDtoMapper);
    }

    @RequestMapping(value = "/{id}/process", method = RequestMethod.PUT)
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN', 'CATERING_CREW')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void processOrder(@PathVariable("id") Long id) {
        orderService.processOrder(id);
    }

    @RequestMapping(value = "/{id}/notify", method = RequestMethod.PUT)
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN', 'CATERING_CREW')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void notifyReady(@PathVariable("id") Long id) {
        orderService.notifyReady(id);
    }

    @RequestMapping(value = "/{id}/process/reservation", method = RequestMethod.PUT)
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN', 'CATERING_CREW')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void processReservation(@PathVariable("id") Long id) {
        orderService.handleReservation(id);
    }

    @RequestMapping(value = "/direct", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN', 'CATERING_CREW')")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDetailDto saveDirectOrder(@RequestBody @Valid DirectOrderCommand command) {
        return orderService.placeDirectOrder(command, orderDetailDtoMapper, getCurrentUser());
    }

    @RequestMapping(value = "/remote", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @PreAuthorize(value = "hasAnyRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDetailDto saveRemoteOrder(@RequestBody @Valid RemoteOrderCommand command) {
        return orderService.placeRemoteOrder(command, orderDetailDtoMapper, getCurrentUser());
    }
}

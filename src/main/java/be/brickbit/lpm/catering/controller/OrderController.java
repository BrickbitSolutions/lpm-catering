package be.brickbit.lpm.catering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.service.order.IOrderService;
import be.brickbit.lpm.catering.service.order.command.CreateOrderCommand;
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
        if (orderStatus == null) {
            return orderService.findAll(orderDetailDtoMapper);
        } else {
            return orderService.findOrderByStatus(orderStatus, orderDetailDtoMapper);
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @PreAuthorize(value = "hasAnyRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDetailDto createOrder(@RequestBody @Valid CreateOrderCommand command, HttpServletRequest request) {
        if (command.getUserId() != null &&
                !(request.isUserInRole("ADMIN") ||
                request.isUserInRole("CATERING_ADMIN") ||
                request.isUserInRole("CATERING_CREW"))) {
            throw new AccessDeniedException("You are not authorized to create orders for other users");
        }

        return orderService.createOrder(command, orderDetailDtoMapper, getCurrentUser());
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

    @RequestMapping(value = "/{id}/process/reservation", method = RequestMethod.PUT)
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN', 'CATERING_CREW')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void processReservation(@PathVariable("id") Long id) {
        orderService.handleReservation(id);
    }
}

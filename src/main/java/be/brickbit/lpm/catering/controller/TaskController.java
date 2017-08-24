package be.brickbit.lpm.catering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import be.brickbit.lpm.catering.controller.dto.OrderDetailDto;
import be.brickbit.lpm.catering.controller.mapper.OrderDetailDtoMapper;
import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.service.order.OrderService;
import be.brickbit.lpm.catering.service.task.TaskService;
import be.brickbit.lpm.infrastructure.AbstractController;

@RestController
@RequestMapping("task")
public class TaskController extends AbstractController {
	@Autowired
	private TaskService taskService;

	@Autowired
	private OrderDetailDtoMapper orderDtoMapper;

	@Autowired
	private OrderService orderService;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@RequestMapping(value = "/{id}/start", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void startedTask(@PathVariable("id") Long id) {
		taskService.updateTaskWithStatus(id, OrderStatus.IN_PROGRESS, getCurrentUser());
	}

	@RequestMapping(value = "/{id}/complete", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void completeTask(@PathVariable("id") Long id) {
		taskService.updateTaskWithStatus(id, OrderStatus.READY, getCurrentUser());
		OrderDetailDto orderDetail = orderService.findByTaskId(id, orderDtoMapper);
		pushToTakeOutQueue(orderDetail);
	}

	private void pushToTakeOutQueue(OrderDetailDto message) {
		messagingTemplate.convertAndSend("/topic/zanzibar.queue", message);
	}
}

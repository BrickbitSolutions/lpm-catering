package be.brickbit.lpm.catering.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import be.brickbit.lpm.catering.domain.StockFlowType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import be.brickbit.lpm.catering.service.stockflow.IStockFlowService;
import be.brickbit.lpm.catering.service.stockflow.command.StockFlowCommand;
import be.brickbit.lpm.catering.service.stockflow.dto.StockFlowDto;
import be.brickbit.lpm.catering.service.stockflow.mapper.StockFlowDtoMapper;
import be.brickbit.lpm.infrastructure.AbstractController;

@RequestMapping("/stock/flow")
@RestController
public class StockFlowController extends AbstractController {

	@Autowired
	private IStockFlowService stockFlowService;

	@Autowired
	private StockFlowDtoMapper stockFlowDtoMapper;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN')")
	public List<StockFlowDto> getAll() {
		return stockFlowService.findAll(stockFlowDtoMapper);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public StockFlowDto save(@RequestBody @Valid StockFlowCommand command) {
		return stockFlowService.save(command, getCurrentUser().getId(), stockFlowDtoMapper);
	}

    @RequestMapping(value = "/type", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<StockFlowType> getAllTypes() {
        return Arrays.asList(StockFlowType.values());
    }
}

package be.brickbit.lpm.catering.controller;

import be.brickbit.lpm.catering.service.stockflow.IStockFlowService;
import be.brickbit.lpm.catering.service.stockflow.command.StockFlowCommand;
import be.brickbit.lpm.catering.service.stockflow.dto.StockFlowDto;
import be.brickbit.lpm.catering.service.stockflow.mapper.StockFlowDtoMapper;
import be.brickbit.lpm.core.domain.User;
import be.brickbit.lpm.infrastructure.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/stock/flow")
public class StockFlowController extends AbstractController{

    @Autowired
    private IStockFlowService stockFlowService;

    @Autowired
    private StockFlowDtoMapper stockFlowDtoMapper;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'CATERING_ADMIN')")
    public List<StockFlowDto> getAll(){
        return stockFlowService.findAll(stockFlowDtoMapper);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'CATERING_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public StockFlowDto save(@RequestBody @Valid StockFlowCommand command){
        return stockFlowService.save(command, getCurrentUser(), stockFlowDtoMapper);
    }

    @RequestMapping(value = "/{stockFlowId}/process", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'CATERING_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void processStockFlow(@PathVariable("stockFlowId") Long stockFlowId){
        stockFlowService.processStockFlow(stockFlowId);
    }
}

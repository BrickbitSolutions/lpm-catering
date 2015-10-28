package be.brickbit.lpm.catering.controller;

import be.brickbit.lpm.catering.service.stockcorrection.command.StockCorrectionCommand;
import be.brickbit.lpm.catering.service.stockcorrection.dto.StockCorrectionDto;
import be.brickbit.lpm.catering.service.stockflow.IStockFlowService;
import be.brickbit.lpm.catering.service.stockflow.command.StockFlowCommand;
import be.brickbit.lpm.catering.service.stockflow.dto.StockFlowDto;
import be.brickbit.lpm.catering.service.stockflow.mapper.StockFlowDtoMapper;
import be.brickbit.lpm.core.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/stock/flow")
public class StockFlowController {

    @Autowired
    private IStockFlowService stockFlowService;

    @Autowired
    private StockFlowDtoMapper stockFlowDtoMapper;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'CATERING_ADMIN')")
    public List<StockFlowDto> getAll(){
        return stockFlowService.findAll(stockFlowDtoMapper);
    }

    @RequestMapping(value = "/save")
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'CATERING_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public StockFlowDto save(@RequestBody @Valid StockFlowCommand command){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return stockFlowService.save(command, user, stockFlowDtoMapper);
    }
}

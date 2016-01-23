package be.brickbit.lpm.catering.controller;

import be.brickbit.lpm.catering.service.stockcorrection.IStockCorrectionService;
import be.brickbit.lpm.catering.service.stockcorrection.command.StockCorrectionCommand;
import be.brickbit.lpm.catering.service.stockcorrection.dto.StockCorrectionDto;
import be.brickbit.lpm.catering.service.stockcorrection.mapper.StockCorrectionDtoMapper;
import be.brickbit.lpm.core.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/stock/correction")
public class StockCorrectionController {

    @Autowired
    private IStockCorrectionService stockCorrectionService;

    @Autowired
    private StockCorrectionDtoMapper stockCorrectionDtoMapper;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'CATERING_ADMIN')")
    public List<StockCorrectionDto> getAllStockProducts(){
        return stockCorrectionService.findAll(stockCorrectionDtoMapper);
    }

    @RequestMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'CATERING_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public StockCorrectionDto saveNewStockProduct(@RequestBody @Valid StockCorrectionCommand command){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return stockCorrectionService.save(command, user, stockCorrectionDtoMapper);
    }
}

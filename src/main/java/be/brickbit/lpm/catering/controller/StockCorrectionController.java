package be.brickbit.lpm.catering.controller;

import be.brickbit.lpm.catering.service.stockcorrection.IStockCorrectionService;
import be.brickbit.lpm.catering.service.stockcorrection.command.NewStockCorrectionCommand;
import be.brickbit.lpm.catering.service.stockcorrection.dto.StockCorrectionDto;
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
@RequestMapping(value = "/stock/correction")
public class StockCorrectionController {

    @Autowired
    private IStockCorrectionService stockCorrectionService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'CATERING_ADMIN')")
    public List<StockCorrectionDto> getAllStockProducts(){
        return stockCorrectionService.findAll();
    }

    @RequestMapping(value = "/save")
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'CATERING_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveNewStockProduct(@RequestBody @Valid NewStockCorrectionCommand command){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        stockCorrectionService.saveStockCorrection(command, user);
    }
}

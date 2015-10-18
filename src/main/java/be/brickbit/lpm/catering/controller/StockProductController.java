package be.brickbit.lpm.catering.controller;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.service.stockproduct.IStockProductService;
import be.brickbit.lpm.catering.service.stockproduct.command.SaveStockProductCommand;
import be.brickbit.lpm.catering.service.stockproduct.dto.StockProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "stockproduct")
public class StockProductController {

    @Autowired
    private IStockProductService stockProductService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'CATERING_ADMIN')")
    public List<StockProductDto> getAllStockProducts(){
        return stockProductService.findAll();
    }

    @RequestMapping(value = "/all/{type}/{clearance}")
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'CATERING_ADMIN')")
    public List<StockProductDto> getAllStockProductsByTypeAndClearance(@PathVariable("type") ProductType type, @PathVariable("clearance") ClearanceType clearance){
        return stockProductService.findAllByTypeAndClearance(type, clearance);
    }

    @RequestMapping(value = "/save")
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'CATERING_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveNewStockProduct(@RequestBody @Valid SaveStockProductCommand command){
        stockProductService.saveNewStockProduct(command);
    }
}

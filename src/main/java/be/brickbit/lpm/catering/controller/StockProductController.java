package be.brickbit.lpm.catering.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.service.stockproduct.IStockProductService;
import be.brickbit.lpm.catering.service.stockproduct.command.StockProductCommand;
import be.brickbit.lpm.catering.service.stockproduct.dto.StockProductDto;
import be.brickbit.lpm.catering.service.stockproduct.mapper.StockProductDtoMapper;
import be.brickbit.lpm.infrastructure.AbstractController;

@RequestMapping(value = "stockproduct")
@RestController
public class StockProductController extends AbstractController{

    @Autowired
    private IStockProductService stockProductService;

    @Autowired
    private StockProductDtoMapper stockProductDtoMapper;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public StockProductDto getProductById(@PathVariable("id") Long productId) {
        return stockProductService.findOne(productId, stockProductDtoMapper);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'CATERING_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<StockProductDto> getAllStockProducts(){
        return stockProductService.findAll(stockProductDtoMapper);
    }

    @RequestMapping(value = "/all/{type}/{clearance}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'CATERING_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<StockProductDto> getAllStockProductsByTypeAndClearance(@PathVariable("type") ProductType type, @PathVariable("clearance") ClearanceType clearance){
        return stockProductService.findAllByTypeAndClearance(type, clearance, stockProductDtoMapper);
    }

    @RequestMapping(value = "/all/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<StockProductDto> getAllStockProductsByType(@PathVariable("type") ProductType type) {
        return stockProductService.findAllByType(type, stockProductDtoMapper);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'CATERING_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public StockProductDto saveNewStockProduct(@RequestBody @Valid StockProductCommand command){
        return stockProductService.save(command, stockProductDtoMapper);
    }

    @RequestMapping(value = "/types", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'CATERING_ADMIN'")
    public List<ProductType> getAllProductTypes(){
        return Arrays.asList(ProductType.values());
    }

    @RequestMapping(value = "/clearance", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'CATERING_ADMIN'")
    public List<ClearanceType> getAllClearanceTypes(){
        return Arrays.asList(ClearanceType.values());
    }
}

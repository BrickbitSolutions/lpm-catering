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

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.service.stockproduct.StockProductService;
import be.brickbit.lpm.catering.service.stockproduct.command.EditStockProductCommand;
import be.brickbit.lpm.catering.service.stockproduct.command.StockProductCommand;
import be.brickbit.lpm.catering.controller.dto.StockProductDto;
import be.brickbit.lpm.catering.controller.dto.StockProductDtoMapper;
import be.brickbit.lpm.infrastructure.AbstractController;

@RequestMapping(value = "stockproduct")
@RestController
public class StockProductController extends AbstractController {

    @Autowired
    private StockProductService stockProductService;

    @Autowired
    private StockProductDtoMapper stockProductDtoMapper;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public StockProductDto saveNewStockProduct(@RequestBody @Valid StockProductCommand command) {
        return stockProductService.save(command, stockProductDtoMapper);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public StockProductDto getProductById(@PathVariable("id") Long productId) {
        return stockProductService.findOne(productId, stockProductDtoMapper);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStockProduct(@PathVariable("id") Long id, @RequestBody @Valid EditStockProductCommand command) {
        stockProductService.updateStockProduct(id, command);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN')")
    public void deleteStockProduct(@PathVariable("id") Long id) {
        stockProductService.delete(id);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<StockProductDto> getAllStockProducts(
            @RequestParam(value = "type", required = false) ProductType type,
            @RequestParam(value = "clearance", required = false) ClearanceType clearance) {

        if(type != null && clearance != null){
            return stockProductService.findAllByTypeAndClearance(type, clearance, stockProductDtoMapper);
        }

        if(type != null){
            return stockProductService.findAllByType(type, stockProductDtoMapper);
        }

        return stockProductService.findAll(stockProductDtoMapper);
    }

    @RequestMapping(value = "/type", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN')")
    public List<ProductType> getAllProductTypes() {
        return Arrays.asList(ProductType.values());
    }

    @RequestMapping(value = "/clearance", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN')")
    public List<ClearanceType> getAllClearanceTypes() {
        return Arrays.asList(ClearanceType.values());
    }
}

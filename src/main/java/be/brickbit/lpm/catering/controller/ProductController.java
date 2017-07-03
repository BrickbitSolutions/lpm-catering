package be.brickbit.lpm.catering.controller;

import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.service.product.ProductService;
import be.brickbit.lpm.catering.service.product.command.CreateProductCommand;
import be.brickbit.lpm.catering.service.product.command.EditProductCommand;
import be.brickbit.lpm.catering.service.product.command.EditProductPreparationCommand;
import be.brickbit.lpm.catering.controller.dto.ProductDetailsDto;
import be.brickbit.lpm.catering.controller.dto.ProductDto;
import be.brickbit.lpm.catering.controller.mapper.ProductDetailsDtoMapper;
import be.brickbit.lpm.catering.controller.mapper.ProductDtoMapper;
import be.brickbit.lpm.infrastructure.AbstractController;
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

import javax.validation.Valid;
import java.util.List;

@RequestMapping("product")
@RestController
public class ProductController extends AbstractController{

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDtoMapper productDtoMapper;

    @Autowired
    private ProductDetailsDtoMapper productDetailsDtoMapper;

    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto saveNewProduct(@RequestBody @Valid CreateProductCommand someCreateProductCommand){
        return productService.save(someCreateProductCommand, productDtoMapper);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @PreAuthorize(value = "hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> getAllProducts(
            @RequestParam(value = "type", required = false) ProductType productType,
            @RequestParam(value = "enabled", required = false, defaultValue = "false") Boolean enabled,
            @RequestParam(value = "reservationOnly", required = false, defaultValue = "false") Boolean reservationOly
    ){
        if(productType == null){
            return productService.findAll(productDtoMapper);
        }else{
            return productService.findAllByType(productType, enabled, reservationOly, productDtoMapper);
        }
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ProductDto updateProduct(@PathVariable("id") Long id, @RequestBody @Valid EditProductCommand command) {
        return productService.updateProduct(id, command, productDtoMapper);
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN')")
    @RequestMapping(value = "/{id}/preparation", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ProductDto updateProductPreparation(@PathVariable("id") Long id, @RequestBody @Valid EditProductPreparationCommand command) {
        return productService.updateProductPreparation(id, command, productDtoMapper);
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN', 'CATERING_CREW')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProduct(@PathVariable("id") Long id) {
        return productService.findOne(id, productDtoMapper);
    }

    @PreAuthorize(value = "hasAnyRole('CATERING_ADMIN', 'ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable("id") Long id) {
        productService.delete(id);
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN')")
    @RequestMapping(value = "/{id}/available", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProductAvailability(@PathVariable("id") Long id) {
        productService.toggleAvailability(id);
    }

    @RequestMapping(value = "/{id}/receipt", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN', 'CATERING_CREW')")
    @ResponseStatus(HttpStatus.OK)
    public ProductDetailsDto getProductDetails(@PathVariable("id") Long id){
        return productService.findOne(id, productDetailsDtoMapper);
    }
}

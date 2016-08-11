package be.brickbit.lpm.catering.controller;

import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.service.product.IProductService;
import be.brickbit.lpm.catering.service.product.command.CreateProductCommand;
import be.brickbit.lpm.catering.service.product.dto.ProductDetailsDto;
import be.brickbit.lpm.catering.service.product.dto.ProductDto;
import be.brickbit.lpm.catering.service.product.mapper.ProductDetailsDtoMapper;
import be.brickbit.lpm.catering.service.product.mapper.ProductDtoMapper;
import be.brickbit.lpm.infrastructure.AbstractController;
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

import javax.validation.Valid;
import java.util.List;

@RequestMapping("product")
@RestController
public class ProductController extends AbstractController{

    @Autowired
    private IProductService productService;

    @Autowired
    private ProductDtoMapper productDtoMapper;

    @Autowired
    private ProductDetailsDtoMapper productDetailsDtoMapper;

    @RequestMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto saveNewProduct(@RequestBody @Valid CreateProductCommand someCreateProductCommand){
        return productService.save(someCreateProductCommand, productDtoMapper);
    }

    @RequestMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> getAllProducts(){
        return productService.findAll(productDtoMapper);
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN', 'CATERING_CREW')")
    @RequestMapping(value = "/all/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> getProductsByType(@PathVariable("type") ProductType productType) {
        return productService.findAllByType(productType, productDtoMapper);
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN', 'CATERING_CREW')")
    @RequestMapping(value = "/all/{type}/enabled", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> getEnabledProductsByType(@PathVariable("type") ProductType productType) {
        return productService.findAllEnabledByType(productType, productDtoMapper);
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

    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN', 'CATERING_CREW')")
    @RequestMapping(value = "/{id}/available", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable("id") Long id) {
        productService.toggleAvailability(id);
    }

    @RequestMapping(value = "/{id}/receipt", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public ProductDetailsDto getProductDetails(@PathVariable("id") Long id){
        return productService.findOne(id, productDetailsDtoMapper);
    }
}

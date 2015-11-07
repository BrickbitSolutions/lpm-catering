package be.brickbit.lpm.catering.controller;

import be.brickbit.lpm.catering.service.product.IProductService;
import be.brickbit.lpm.catering.service.product.command.ProductCommand;
import be.brickbit.lpm.catering.service.product.dto.ProductDetailsDto;
import be.brickbit.lpm.catering.service.product.dto.ProductDto;
import be.brickbit.lpm.catering.service.product.mapper.ProductDetailsDtoMapper;
import be.brickbit.lpm.catering.service.product.mapper.ProductDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private ProductDtoMapper productDtoMapper;

    @Autowired
    private ProductDetailsDtoMapper productDetailsDtoMapper;

    @RequestMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'CATERING_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto saveNewProduct(@RequestBody @Valid ProductCommand someProductCommand){
        return productService.save(someProductCommand, productDtoMapper);
    }

    @RequestMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'CATERING_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> getAllProducts(){
        return productService.findAll(productDtoMapper);
    }

    @RequestMapping(value = "/{id}/receipt", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'CATERING_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public ProductDetailsDto getProductDetails(@PathVariable("id") Long id){
        return productService.findOne(id, productDetailsDtoMapper);
    }
}

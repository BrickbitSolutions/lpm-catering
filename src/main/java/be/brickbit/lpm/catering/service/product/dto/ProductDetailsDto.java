package be.brickbit.lpm.catering.service.product.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class ProductDetailsDto {
    private String queueName;
    private String instructions;
    private Integer timerInMinutes;
    private List<ReceiptDto> productsToInclude;

    public ProductDetailsDto(String someQueueName, String someInstructions, Integer someTimerInMinutes,
                             List<ReceiptDto> someProductsToInclude) {
        queueName = someQueueName;
        instructions = someInstructions;
        timerInMinutes = someTimerInMinutes;
        productsToInclude = someProductsToInclude;
    }

    public ProductDetailsDto(List<ReceiptDto> someProductsToInclude) {
        productsToInclude = someProductsToInclude;
        instructions = "";
        queueName = "";
        timerInMinutes = 0;
    }
}

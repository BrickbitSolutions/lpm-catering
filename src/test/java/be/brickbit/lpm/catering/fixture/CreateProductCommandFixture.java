package be.brickbit.lpm.catering.fixture;

import static be.brickbit.lpm.catering.util.RandomValueUtil.*;

import java.util.Arrays;

import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.service.product.command.CreateProductCommand;

public class CreateProductCommandFixture {
    public static CreateProductCommand mutable() {
        return new CreateProductCommand(
                randomString(),
                randomDecimal(),
                ProductType.FOOD,
                Arrays.asList(ReceiptLineCommandFixtrue.mutable()),
                randomInt(),
                randomString(),
                randomString(50)
        );
    }
}

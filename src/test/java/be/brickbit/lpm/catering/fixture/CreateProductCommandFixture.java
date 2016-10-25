package be.brickbit.lpm.catering.fixture;

import com.google.common.collect.Lists;

import static be.brickbit.lpm.catering.util.RandomValueUtil.*;

import java.util.Arrays;

import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.service.product.command.CreateProductCommand;

public class CreateProductCommandFixture {
    public static CreateProductCommand mutable() {
        return new CreateProductCommand(
                randomString(),
                randomDecimal(1.0, 99.99),
                ProductType.FOOD,
                Arrays.asList(ReceiptLineCommandFixtrue.mutable()),
                Lists.newArrayList(),
                randomInt(),
                randomString(),
                randomString(50)
        );
    }
}

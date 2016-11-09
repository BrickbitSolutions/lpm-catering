package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.service.product.command.EditProductCommand;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomDecimal;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomString;

public class EditProductCommandFixture {

    public static EditProductCommand mutable() {
        return new EditProductCommand(
                randomString(),
                randomDecimal(),
                false
        );
    }
}

package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.service.product.command.EditProductPreparationCommand;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomInt;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomString;

public class EditProductPreparationCommandFixture {
    public static EditProductPreparationCommand mutable() {
        return new EditProductPreparationCommand(
                randomString(),
                randomInt(0, 300),
                randomString()
        );
    }
}

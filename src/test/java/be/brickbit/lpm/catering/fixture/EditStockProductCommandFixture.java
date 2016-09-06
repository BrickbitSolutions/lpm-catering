package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.service.stockproduct.command.EditStockProductCommand;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomString;

public class EditStockProductCommandFixture {
    public static EditStockProductCommand mutable() {
        return new EditStockProductCommand(
                randomString(),
                ClearanceType.PLUS_16,
                ProductType.DRINKS
        );
    }
}

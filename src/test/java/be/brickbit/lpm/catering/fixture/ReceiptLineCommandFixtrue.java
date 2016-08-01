package be.brickbit.lpm.catering.fixture;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomInt;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomLong;

import be.brickbit.lpm.catering.service.product.command.ReceiptLineCommand;

public class ReceiptLineCommandFixtrue {
	public static ReceiptLineCommand mutable() {
		return new ReceiptLineCommand(
				randomLong(),
				randomInt());
	}
}

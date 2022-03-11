package com.deltacapital.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class OfferProcessorTest {
    private OfferProcessor SUT;

    @BeforeEach
    public void init() {
        SUT = new OfferProcessor();
    }

    @Test
    public void whenOfferIsBuyOneGetFree_applyCorrectDiscount() throws Exception {
        //given

        //when
        ShoppingItem actualResponse = SUT.process(new ShoppingItemImpl("Apple",
                BigDecimal.valueOf(0.50)), 6, OfferType.BUY_ONE_GET_ONE_FREE);

        //then
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(BigDecimal.valueOf(-1.50), actualResponse.getPrice());
    }

    @Test
    public void whenOfferIsBuyTwoGetAnotherFree_applyCorrectDiscount() throws Exception {
        //given

        //when
        ShoppingItem actualResponse = SUT.process(new ShoppingItemImpl("Apple",
                BigDecimal.valueOf(0.50)), 8, OfferType.THREE_PRICE_TWO);

        //then
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(BigDecimal.valueOf(-1.00), actualResponse.getPrice());
    }

    @Test
    public void whenOfferIsInvalid_returnError() throws Exception {
        Assertions.assertThrows(OfferApplyException.class,
                () -> SUT.process(new ShoppingItemImpl("Apple", BigDecimal.valueOf(0.50)), 8, null));
    }

    @Test
    public void whenOfferIsValidButItemCountIsUnknown_returnError() {
        Assertions.assertThrows(OfferApplyException.class,
                () -> SUT.process(new ShoppingItemImpl("Apple", BigDecimal.valueOf(0.50)), 0, OfferType.BUY_ONE_GET_ONE_FREE));
    }
}
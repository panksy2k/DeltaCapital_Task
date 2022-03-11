package com.deltacapital.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingBasketTest {
    private ShoppingBasket SUT;

    @BeforeEach
    public void init() {
        SUT = new ShoppingBasket();
    }

    @Test
    public void whenAddingItemInCatalogue_addToBasketSuccessfully() throws Exception {
        //given

        //when
        boolean actualValue = SUT.addItem("Apple");

        //then
        assertTrue(actualValue);
    }

    @Test
    public void whenAddingItemNotInCatalogue_doNotAddToBasket() throws Exception {
        //given

        //when
        boolean actualValue = SUT.addItem("Orange");

        //then
        assertFalse(actualValue);
    }

    @Test
    public void whenCalculatingBasketCost_discountNotApplicable() throws Exception {
        //given
        SUT.addItem("Apple"); //0.35
        SUT.addItem("Apple");
        SUT.addItem("Banana"); //0.20
        SUT.addItem("Apple");
        SUT.addItem("Banana");
        SUT.addItem("Lime"); //0.15
        SUT.addItem("Lime");

        //when
        BigDecimal actualCost = SUT.getTotalCost();

        //then
        assertEquals(BigDecimal.valueOf(1.75D).setScale(2), actualCost);
    }

    @Test
    public void whenCalculatingBasketCost_discountApplies() throws Exception {
        //given
        SUT.addItem("Apple"); //0.35
        SUT.addItem("Apple");
        SUT.addItem("Apple");
        SUT.addItem("Banana"); //0.20
        SUT.addItem("Banana");
        SUT.addItem("Lime"); //0.15
        SUT.addItem("Lime");
        SUT.addItem("Lime");
        SUT.addItem("Melon"); //0.50
        SUT.addItem("Melon");
        SUT.addItem("Melon");
        SUT.addItem("Melon");
        SUT.addItem("Melon");

        //when
        BigDecimal actualCost = SUT.getTotalCost();

        //then
        assertEquals(BigDecimal.valueOf(3.25D).setScale(2), actualCost);
    }

    @Test
    public void whenBasketAlreadyCalculated_returnError() throws Exception {
        //given
        SUT.addItem("Apple"); //0.35

        SUT.addItem("Apple");
        SUT.addItem("Banana");
        SUT.addItem("Lime"); //0.15
        SUT.addItem("Melon"); //0.50
        SUT.getTotalCost();

        //when, then
        assertThrows(ShoppingBasketException.class, () -> SUT.getTotalCost());
    }
}
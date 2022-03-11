package com.deltacapital.task;

import java.math.BigDecimal;

public class OfferProcessor {
    public OfferProcessor() {
        super();
    }

    public ShoppingItem process(ShoppingItem anItem, int numberOfItems, OfferType ot) throws OfferApplyException {
        final OfferType discountType = ot;

        if(numberOfItems == 0) {
            throw new OfferApplyException("Number of items not known -- cannot apply discount");
        }

        if(anItem == null) {
            throw new OfferApplyException("Item on discount cannot be identified");
        }

        if(discountType == null) {
            throw new OfferApplyException("Offer type not found");
        }

        switch (discountType) {
            case BUY_ONE_GET_ONE_FREE: {
                int discountedItemsCount = numberOfItems / 2;
                return new ShoppingItemImpl(anItem.getName(), anItem.getPrice().multiply(BigDecimal.valueOf(-1 * discountedItemsCount)));
            }
            case THREE_PRICE_TWO: {
                int discountedItemsCount = numberOfItems / 3;
                return new ShoppingItemImpl(anItem.getName(), anItem.getPrice().multiply(BigDecimal.valueOf(-1 * discountedItemsCount)));
            }
        }

        return new ShoppingItemImpl(anItem.getName(), BigDecimal.ZERO);
    }
}

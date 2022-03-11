package com.deltacapital.task;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ShoppingBasket {
    private Map<String, Double> catalogue = new HashMap<>();
    private final Map<ShoppingItem, Integer> shoppingItems = new HashMap<>();

    private boolean isCheckedout;

    public ShoppingBasket() {
        initializeCatalogue();
        isCheckedout = false;
    }

    private void initializeCatalogue() {
        catalogue.computeIfAbsent("Apple", i -> 0.35D);
        catalogue.computeIfAbsent("Melon", i -> 0.50D);
        catalogue.computeIfAbsent("Lime", i -> 0.15D);
        catalogue.computeIfAbsent("Banana", i -> 0.20D);
    }

    public boolean addItem(String itemName) throws ShoppingBasketException {
        if(isCheckedout) {
            throw new ShoppingBasketException("Basket is checked out - cannot add more items - reset!");
        }

        if(!catalogue.containsKey(itemName)) {
            System.out.println("The item " + itemName + " is not present in the catalogue -- cannot add it to basket");
            return false;
        }

        ShoppingItem newItem = new ShoppingItemImpl(itemName, BigDecimal.valueOf(catalogue.get(itemName)));
        shoppingItems.compute(newItem, (item, count) -> count == null? 1 : count + 1);

        return true;
    }

    public BigDecimal getTotalCost() throws ShoppingBasketException, OfferApplyException {
        if(isCheckedout) {
            throw new ShoppingBasketException("Basket is checked out - reset!");
        }

        BigDecimal totalCost = BigDecimal.ZERO;

        OfferProcessor discountEngine = new OfferProcessor();

        for(ShoppingItem item : this.shoppingItems.keySet()) {
            totalCost = totalCost.add(item.getPrice().multiply(BigDecimal.valueOf(this.shoppingItems.get(item))));

            if(item.getName().equals("Melon")) {
                ShoppingItem discountedItem = discountEngine.process(item, this.shoppingItems.get(item), OfferType.BUY_ONE_GET_ONE_FREE);
                totalCost = totalCost.add(discountedItem.getPrice());
            } else if(item.getName().equals("Lime")) {
                ShoppingItem discountedItem = discountEngine.process(item, this.shoppingItems.get(item), OfferType.THREE_PRICE_TWO);
                totalCost = totalCost.add(discountedItem.getPrice());
            }
        }

        isCheckedout = true;
        return totalCost;
    }
}

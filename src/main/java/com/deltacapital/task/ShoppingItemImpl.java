package com.deltacapital.task;

import java.math.BigDecimal;
import java.util.Objects;

public class ShoppingItemImpl implements ShoppingItem {
    private String _name;
    private BigDecimal _price;

    public ShoppingItemImpl(String name, BigDecimal price) {
        if(name == null || name.isEmpty()) {
            throw new RuntimeException("Item should have a name");
        }

        _name = name;
        _price = price;
    }

    @Override
    public String getName() {
        return _name;
    }

    @Override
    public BigDecimal getPrice() {
        return _price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingItemImpl that = (ShoppingItemImpl) o;
        return _name.equals(that._name) && _price.equals(that._price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_name, _price);
    }
}

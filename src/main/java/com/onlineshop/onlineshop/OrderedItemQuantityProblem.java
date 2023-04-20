package com.onlineshop.onlineshop;

import entity.Item;

public class OrderedItemQuantityProblem {
    public OrderedItemQuantityProblem(Item item, int orderedQuantity) {
        this.itemName = item.getName();
        this.availableQuantity = item.getQuantity();
        this.orderedQuantity = orderedQuantity;
    }
    String itemName;
    int availableQuantity;
    int orderedQuantity;

    public String getItemName(){
        return itemName;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public int getOrderedQuantity(){
        return orderedQuantity;
    }
}

package com.silverbars.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.measure.Quantity;
import javax.measure.quantity.Mass;

final public class Order {
    private final UserId userId;
    private final Quantity<Mass> quantity;
    private final PricePerKg pricePerKg;
    private final Order.Type orderType;
    public Order(UserId userId, Quantity<Mass> quantity, PricePerKg pricePerKg, Order.Type orderType) {
        this.userId = userId;
        this.quantity = quantity;
        this.pricePerKg = pricePerKg;
        this.orderType = orderType;
    }

    public Quantity<Mass> quantity() {
        return quantity;
    }

    public PricePerKg pricePerKg() {
        return pricePerKg;
    }

    public Order.Type type() {
        return orderType;
    }

    @Override
    public String toString() {
        return "Order{" +
                "userId=" + userId +
                ", quantity=" + quantity +
                ", pricePerKg=" + pricePerKg +
                ", orderType=" + orderType +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public enum Type {Buy, Sell}
}

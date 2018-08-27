package com.silverbars.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

final public class PricePerKg implements Comparable<PricePerKg> {

    private final Money amount;

    public PricePerKg(Money amount) {
        this.amount = amount;
    }

    public static PricePerKg of(CurrencyUnit currency, double amount) {
        return new PricePerKg(Money.of(currency, amount));
    }

    @Override
    public String toString() {
        return "PricePerKg{" +
                "amount=" + amount +
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

    @Override
    public int compareTo(PricePerKg o) {
        return amount.compareTo(o.amount);
    }
}

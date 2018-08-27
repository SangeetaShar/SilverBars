package com.silverbars.service;

import com.silverbars.domain.Bid;
import com.silverbars.domain.Order;
import com.silverbars.domain.OrderSummary;

import javax.measure.Quantity;
import javax.measure.quantity.Mass;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

public class LiveOrderBoardImpl implements LiveOrderBoard {
    private final List<Order> registeredOrders = new ArrayList<>();
    private final Comparator<OrderSummary> byTypeAndPrice = new OrderComparator();

    @Override
    public void register(Order order) {
        registeredOrders.add(order);
    }

    @Override
    public void cancel(Order order) {
        registeredOrders.remove(order);
    }

    @Override
    public List<OrderSummary> summary() {
        List<OrderSummary> collect = registeredOrders.stream().
                collect(groupingBy(Bid::forOrder, mapping(Order::quantity, toList()))).
                entrySet().stream().
                map(toOrderSummary()).
                sorted(byTypeAndPrice).
                collect(toList());

        System.out.println("LiveOrderBoardImpl Summary ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        System.out.println("Buying >>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        summarizeOrderType(collect, Order.Type.Buy);
        System.out.println("Selling >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        summarizeOrderType(collect, Order.Type.Sell);
        System.out.println("END");


        return collect;
    }

    private void summarizeOrderType(List<OrderSummary> orderSummaries, Order.Type type) {
        orderSummaries.stream().filter(o -> o.orderType() == type).forEach(System.out::println);
    }

    private Function<Map.Entry<Bid, List<Quantity<Mass>>>, OrderSummary> toOrderSummary() {
        return entry -> new OrderSummary(entry.getKey(), entry.getValue());
    }
}

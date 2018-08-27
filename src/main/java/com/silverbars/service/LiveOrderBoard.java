package com.silverbars.service;

import com.silverbars.domain.Order;
import com.silverbars.domain.OrderSummary;

import java.util.List;

public interface LiveOrderBoard {
    void register(Order order);

    void cancel(Order order);

    List<OrderSummary> summary();
}

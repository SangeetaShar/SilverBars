package com.silverbars.service;

import com.silverbars.domain.Order;
import com.silverbars.domain.OrderSummary;
import com.silverbars.domain.PricePerKg;
import org.junit.Before;
import org.junit.Test;

import javax.measure.Quantity;
import javax.measure.quantity.Mass;

import static com.silverbars.util.DSL.*;
import static com.silverbars.util.Users.Alice;
import static com.silverbars.util.Users.Bob;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class LiveOrderBoardImplTest {

    private LiveOrderBoardImpl board;

    @Before
    public void setUp() throws Exception {
        board = new LiveOrderBoardImpl();
    }

    @Test
    public void shouldStartWithNoOrdersDisplayed() {

        assertThat(board.summary()).isEmpty();
    }

    @Test
    public void shouldDisplayRegisteredOrder() {

        board.register(buy(kg(3.5), £(306), Alice));

        assertThat(board.summary()).containsExactly(
                buyOrderSummary(kg(3.5), £(306))
        );
    }

    @Test
    public void shouldDisplaySummaryOfThoseRegisteredOrdersWhichTypeAndBidPriceMatch() {

        board.register(buy(kg(3.5), £(306), Alice));
        board.register(buy(kg(2.5), £(306), Bob));

        assertThat(board.summary()).containsExactly(
                buyOrderSummary(kg(6.0), £(306))
        );
    }


    @Test
    public void shouldAllowToCancelRegisteredOrderWhenRequestedByUserWhoPlacedIt() {

        board.register(buy(kg(3.5), £(306), Alice));
        board.cancel(buy(kg(3.5), £(306), Alice));

        assertThat(board.summary()).isEmpty();
    }

    @Test
    public void shouldNotCancelRegisteredOrderWhenRequestedByUserWhoDidNotPlaceIt() {

        board.register(buy(kg(3.5), £(306), Alice));
        board.cancel(buy(kg(3.5), £(306), Bob));

        assertThat(board.summary()).containsExactly(
                buyOrderSummary(kg(3.5), £(306))
        );
    }

    @Test
    public void shouldAllowToCancelOneOfSeveralRegisteredOrders() {

        board.register(buy(kg(3.5), £(306), Alice));
        board.register(buy(kg(2.5), £(306), Alice));
        board.register(buy(kg(1.5), £(306), Alice));

        board.cancel(buy(kg(3.5), £(306), Alice));

        assertThat(board.summary()).containsExactly(
                buyOrderSummary(kg(4.0), £(306))
        );
    }

    @Test
    public void shouldProduceDistinctSummariesForOrdersWithDifferentPrice() {

        board.register(buy(kg(3.5), £(306), Alice));
        board.register(buy(kg(7.0), £(250), Alice));

        assertThat(board.summary()).containsExactly(
                buyOrderSummary(kg(3.5), £(306)),
                buyOrderSummary(kg(7.0), £(250))
        );
    }

    @Test
    public void shouldSortSellOrdersInAscendingOrder() {

        board.register(sell(kg(3.5), £(306), Alice));
        board.register(sell(kg(7.0), £(250), Alice));

        assertThat(board.summary()).containsExactly(
                sellOrderSummary(kg(7.0), £(250)),
                sellOrderSummary(kg(3.5), £(306))
        );
    }

    @Test
    public void shouldSortBuyOrdersInDescendingOrder() {

        board.register(buy(kg(7.0), £(250), Alice));
        board.register(buy(kg(3.5), £(306), Alice));

        assertThat(board.summary()).containsExactly(
                buyOrderSummary(kg(3.5), £(306)),
                buyOrderSummary(kg(7.0), £(250))
        );
    }

    @Test
    public void shouldDisplayBuyOrdersBeforeSellOrders() {

        board.register(buy(kg(7.0), £(250), Alice));
        board.register(sell(kg(7.0), £(430), Alice));

        assertThat(board.summary()).containsExactly(
                buyOrderSummary(kg(7.0), £(250)),
                sellOrderSummary(kg(7.0), £(430))
        );
    }

    private static OrderSummary buyOrderSummary(Quantity<Mass> quantity, PricePerKg pricePerKg) {
        return new OrderSummary(quantity, pricePerKg, Order.Type.Buy);
    }

    private static OrderSummary sellOrderSummary(Quantity<Mass> quantity, PricePerKg pricePerKg) {
        return new OrderSummary(quantity, pricePerKg, Order.Type.Sell);
    }
}

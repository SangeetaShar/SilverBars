package com.silverbars;

import com.silverbars.domain.Order;
import com.silverbars.service.LiveOrderBoardImpl;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class LiveOrderRunnable extends Thread {

    private final LiveOrderBoardImpl liveOrderBoardImpl;
    private final CyclicBarrier gate;
    private final Order order;
    private final boolean toRegister;

    public LiveOrderRunnable(final String threadName, final LiveOrderBoardImpl liveOrderBoardImpl, final CyclicBarrier gate,
                             final Order order, final boolean toRegister) {
        super(threadName);
        this.liveOrderBoardImpl = liveOrderBoardImpl;
        this.gate = gate;
        this.order = order;
        this.toRegister = toRegister;
    }

    @Override
    public void run() {
        try {

            if (toRegister) {
                liveOrderBoardImpl.register(this.order);
            } else {
                liveOrderBoardImpl.cancel(this.order);
            }
            gate.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

}

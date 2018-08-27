package com.silverbars;

import com.silverbars.domain.Order;
import com.silverbars.service.LiveOrderBoardImpl;

import java.sql.SQLException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static com.silverbars.util.DSL.*;
import static com.silverbars.util.Users.Alice;
import static com.silverbars.util.Users.Bob;

public class RunLiveOrderBoard {

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException, SQLException {
        LiveOrderBoardImpl liveOrderBoardImpl = new LiveOrderBoardImpl();

        final CyclicBarrier gate = new CyclicBarrier(3);
        Order orderToBeAddedForCancellation = buy(kg(3.5), £(306), Alice);
        LiveOrderRunnable lob1 = new LiveOrderRunnable("LOB1", liveOrderBoardImpl, gate,
                orderToBeAddedForCancellation, true);
        LiveOrderRunnable lob2 = new LiveOrderRunnable("LOB2", liveOrderBoardImpl, gate,
                buy(kg(7.0), £(250), Alice), true);
        Order sellOrderToBeAddedForCancellation = sell(kg(2.0), £(250), Alice);
        LiveOrderRunnable lob3 = new LiveOrderRunnable("LOB3", liveOrderBoardImpl, gate,
                sellOrderToBeAddedForCancellation, true);
        LiveOrderRunnable lob4 = new LiveOrderRunnable("LOB4", liveOrderBoardImpl, gate,
                buy(kg(4.5), £(306), Bob), true);
        LiveOrderRunnable lob5 = new LiveOrderRunnable("LOB5", liveOrderBoardImpl, gate,
                sell(kg(1), £(206), Bob), true);
        LiveOrderRunnable lob6 = new LiveOrderRunnable("LOB6", liveOrderBoardImpl, gate,
                sell(kg(4.0), £(250), Bob), true);
        LiveOrderRunnable lob7 = new LiveOrderRunnable("LOB7", liveOrderBoardImpl, gate,
                sell(kg(1), £(206), Bob), true);
        LiveOrderRunnable lob8 = new LiveOrderRunnable("LOB8", liveOrderBoardImpl, gate,
                sellOrderToBeAddedForCancellation, false);
        LiveOrderRunnable lob9 = new LiveOrderRunnable("LOB9", liveOrderBoardImpl, gate,
                orderToBeAddedForCancellation, false);
        lob1.start();
        lob2.start();
        lob3.start();
        gate.await();
        lob4.start();
        lob5.start();
        lob6.start();
        gate.await();
        lob7.start();
        lob8.start();
        lob9.start();
        gate.await();
        System.out.println("all threads started");

        Thread.sleep(2000);
        liveOrderBoardImpl.summary();


     /*   if(hr1.getHire()>0){
            hire = hr1.getHire();
        }
        if(hr2.getHire()>0){
            hire = hr2.getHire();
        }
        if(hr3.getHire()>0){
            hire = hr3.getHire();
        }
        if(hr4.getHire()>0){
            hire = hr4.getHire();
        }
        System.out.println("Car with Registration rg1 is hired with Hire number " + hire);
        hireService.hire("test1", "l1", "reg1", "2017-11-22", 4, 30.0);*/

        /*        *//*System.out.println("Car with Registration rg1 is now marked as Returned and ready for Hire");
        hireService.markReturned("cd1", hire);*//*

        HireReturnedRunnable hrr1 = new HireReturnedRunnable("HRR1", hireService, gate,hire);
        hr2 = new LiveOrderRunnable("HR2", hireService, gate,"test1","l1","reg1");
        hr3 = new LiveOrderRunnable("HR3", hireService, gate,"test1","l1","reg1");
        hr4 = new LiveOrderRunnable("HR4", hireService, gate,"test1","l1","reg1");
        hrr1.start();
        hr2.start();
        hr3.start();
        hr4.start();
        gate.await();*/
    }
}

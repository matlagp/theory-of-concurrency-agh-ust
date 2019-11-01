package tw.lab3;

import tw.Logger;

import static tw.Utilities.haltAndCatchFire;
import static tw.Utilities.sleepUpToMillis;

public class Customer implements Runnable {
    private Waiter waiter;
    private int pairID;

    public Customer(Waiter waiter, int pairID) {
        this.waiter = waiter;
        this.pairID = pairID;
    }

    @Override
    public void run() {
        while (true) {
            try {
                sleepUpToMillis(1000); // Minding own business
                waiter.requestTable(this);
                Logger.log("Eating...");
                sleepUpToMillis(1000); // Eating
                waiter.freeTable(this);
            } catch (InterruptedException e) {
                haltAndCatchFire(e);
            }
        }
    }

    public int getPairID() {
        return pairID;
    }
}

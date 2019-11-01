package tw.lab3;

import tw.Logger;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Waiter {
    private Request[] pairRequests;
    private int customersAtTheTable = 0;
    private final Lock tableLock = new ReentrantLock();
    private final Condition tableFree = tableLock.newCondition();
    private final Condition[] pairWantsTable;

    public Waiter(int numberOfPairs) {
        this.pairRequests = new Request[numberOfPairs];
        this.pairWantsTable = new Condition[numberOfPairs];

        for (int i = 0; i < numberOfPairs; i++) {
            pairRequests[i] = Request.NONE;
            pairWantsTable[i] = tableLock.newCondition();
        }
    }

    public void requestTable(Customer customer) throws InterruptedException {
        tableLock.lock();
        try {
            pairRequests[customer.getPairID()] = pairRequests[customer.getPairID()].getNext();
            Logger.log("Requesting table, pair id: " + customer.getPairID());
            if (pairRequests[customer.getPairID()] != Request.BOTH) {
                pairWantsTable[customer.getPairID()].await();
            } else {
                Logger.log("Both customers from pair " + customer.getPairID() + " requested table");
                while (customersAtTheTable != 0) {
                    tableFree.await();
                }

                pairWantsTable[customer.getPairID()].signalAll();
                customersAtTheTable = 2;
                pairRequests[customer.getPairID()] = Request.NONE;
                Logger.log("Both customers from pair " + customer.getPairID() + " got table");
            }
        } finally {
            tableLock.unlock();
        }

    }

    public void freeTable(Customer customer) {
        tableLock.lock();
        try {
            customersAtTheTable--;
            Logger.log("Customer leaving table, pair id: " + customer.getPairID());
            if (customersAtTheTable == 0) {
                tableFree.signal();
            }
        } finally {
            tableLock.unlock();
        }
    }
}

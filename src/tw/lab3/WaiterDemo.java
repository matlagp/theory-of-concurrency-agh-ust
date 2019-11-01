package tw.lab3;

import tw.Utilities;

public class WaiterDemo {
    private static final int numberOfPairs = 10;

    public static void main(String[] args) {
        Waiter waiter = new Waiter(numberOfPairs);
        CustomerPair[] customerPairs = CustomerFactory.makeCustomerPairs(numberOfPairs, waiter);
        Thread[] threads = new Thread[2*numberOfPairs];

        for (int i = 0; i < 2*numberOfPairs; i += 2) {
            threads[i] = new Thread(customerPairs[i/2].getCustomerA());
            threads[i+1] = new Thread(customerPairs[i/2].getCustomerB());
        }

        Utilities.startArrayOfThreads(threads);
    }
}

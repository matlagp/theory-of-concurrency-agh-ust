package tw.lab3;

public final class CustomerFactory {
    public static CustomerPair[] makeCustomerPairs(int numberOfPairs, Waiter sharedWaiter) {
        CustomerPair[] pairs = new CustomerPair[numberOfPairs];
        for (int i = 0; i < numberOfPairs; i++) {
            pairs[i] = new CustomerPair();
            pairs[i].setCustomerA(new Customer(sharedWaiter, i));
            pairs[i].setCustomerB(new Customer(sharedWaiter, i));
        }

        return pairs;
    }
}

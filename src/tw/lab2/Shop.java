package tw.lab2;

import tw.Logger;
import tw.Utilities;

public class Shop {
    private int numberOfCarts;
    private Boolean[] carts;
    private Semaphore entranceSemaphore;
    private Semaphore cartsSemaphore;

    public Shop(int numberOfCarts) {
        this.numberOfCarts = numberOfCarts;
        this.carts = new Boolean[numberOfCarts];
        this.entranceSemaphore = new GeneralSemaphore(numberOfCarts);
        this.cartsSemaphore = new BinarySemaphore(false);

        for (int i = 0; i < numberOfCarts; i++) {
            carts[i] = false;
        }
    }

    public void doShopping() throws InterruptedException {
        enterWhenCartsReady();
        Utilities.sleepUpToMillis(1000);    // Doing shopping
        leaveCartAndGoOut();
    }

    private void enterWhenCartsReady() {
        entranceSemaphore.P();
        cartsSemaphore.P();

        for (int i = 0; i < numberOfCarts; i++) {
            if (!carts[i]) {
                Logger.log("Client " + Thread.currentThread().getId() + " taking cart from slot: " + i);
                carts[i] = true;
                break;
            }
        }

        Logger.log("Client " + Thread.currentThread().getId() + " has entered the shop");
        cartsSemaphore.V();
    }

    private void leaveCartAndGoOut() {
        cartsSemaphore.P();

        for (int i = 0; i < numberOfCarts; i++) {
            if (carts[i]) {
                Logger.log("Client " + Thread.currentThread().getId() + " leaving cart in slot: " + i);
                carts[i] = false;
                break;
            }
        }

        Logger.log("Client " + Thread.currentThread().getId() + " has left the shop");
        cartsSemaphore.V();
        entranceSemaphore.V();
    }
}

package tw.lab2;

public class GeneralSemaphore implements Semaphore {
    private int counter;

    public GeneralSemaphore(int counter) {
        if (counter < 0) {
            throw new IllegalArgumentException("Semaphore's value can not be negative");
        }
        this.counter = counter;
    }

    @Override
    public synchronized void P() {
        while (counter == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        counter--;
    }

    @Override
    public synchronized void V() {
        counter++;
        notifyAll();
    }
}

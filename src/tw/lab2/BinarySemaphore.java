package tw.lab2;

public class BinarySemaphore implements Semaphore {
    private boolean taken;

    public BinarySemaphore(boolean taken) {
        this.taken = taken;
    }

    @Override
    public synchronized void P() {
        while (taken) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        taken = true;
    }

    @Override
    public synchronized void V() {
        taken = false;
        notifyAll();
    }
}

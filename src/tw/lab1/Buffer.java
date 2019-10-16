package tw.lab1;

import tw.IBuffer;

public class Buffer implements IBuffer {
    private String state = "";
    private boolean empty = true;
    private long modificationTime;

    @Override
    public synchronized void put(String s) {
        while (!isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        state = s;
        modificationTime = System.currentTimeMillis();
        System.out.println("Pushing message: " + state + " at " + modificationTime
                + " by thread " + Thread.currentThread().getId());
        flipEmpty();
        notifyAll();
    }

    @Override
    public synchronized String take() {
        while (isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        long timestamp = System.currentTimeMillis();
        System.out.println("Popping message: " + state + " at " + timestamp
                + " by thread " + Thread.currentThread().getId()
                + " (diff: " + (timestamp - modificationTime) + " millis)");
        flipEmpty();
        notifyAll();
        return state;
    }

    private boolean isEmpty() {
        return empty;
    }

    private void flipEmpty() {
        this.empty = !this.empty;
    }
}

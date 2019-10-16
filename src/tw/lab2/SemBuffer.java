package tw.lab2;

import tw.IBuffer;
import tw.Logger;

public class SemBuffer implements IBuffer {
    private Semaphore consumerSemaphore;
    private Semaphore producerSemaphore;
    private String state = "";

    public SemBuffer(Semaphore consumerSemaphore, Semaphore producerSemaphore) {
        this.consumerSemaphore = consumerSemaphore;
        this.producerSemaphore = producerSemaphore;
    }

    @Override
    public void put(String message) {
        producerSemaphore.P();
        state = message;
        Logger.log("Pushed message: " + message);
        consumerSemaphore.V();
    }

    @Override
    public String take() {
        consumerSemaphore.P();
        String message = state;
        Logger.log("Popped message: " + message);
        producerSemaphore.V();
        return message;
    }
}

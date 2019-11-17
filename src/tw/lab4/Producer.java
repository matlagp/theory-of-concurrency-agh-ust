package tw.lab4;

import tw.Logger;
import tw.Utilities;

public class Producer implements Runnable {
    private static int NEXT_PRODUCER_ID = 0;
    private final int producerId;
    private final int M;
    private Buffer buffer;

    public Producer(Buffer buffer, int M) {
        this.buffer = buffer;
        this.producerId = NEXT_PRODUCER_ID++;
        this.M = M;
    }

    public int getProducerId() {
        return producerId;
    }

    @Override
    public void run() {
        while (true) {
            int numberOfElementsToPut = (int) (Math.random() * M) + 1;
            buffer.put(this, "Producer " + producerId, numberOfElementsToPut);
            Logger.log("Put " + numberOfElementsToPut + " elements");
            try {
                Utilities.sleepUpToMillis(500);
            } catch (InterruptedException e) {
                Logger.log("Sleep interrupted in producer " + producerId);
                e.printStackTrace();
            }
        }
    }
}

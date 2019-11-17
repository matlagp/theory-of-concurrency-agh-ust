package tw.lab4;

import tw.Logger;
import tw.Utilities;

public class Consumer implements Runnable {
    private static int NEXT_CONSUMER_ID = 0;
    private final int consumerId;
    private final int M;
    private Buffer buffer;

    public Consumer(Buffer buffer, int M) {
        this.buffer = buffer;
        this.consumerId = NEXT_CONSUMER_ID++;
        this.M = M;
    }

    public int getConsumerId() {
        return consumerId;
    }

    @Override
    public void run() {
        while (true) {
            int numberOfElementsToTake = (int) (Math.random() * M) + 1;
            buffer.take(this, numberOfElementsToTake);
            Logger.log("Got " + numberOfElementsToTake + " elements");
            try {
                Utilities.sleepUpToMillis(500);
            } catch (InterruptedException e) {
                Logger.log("Sleep interrupted in consumer " + consumerId);
                e.printStackTrace();
            }
        }
    }
}

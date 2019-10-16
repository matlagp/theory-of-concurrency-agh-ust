package tw.lab1;

import tw.IBuffer;

public class Producer implements Runnable {
    private final IBuffer buffer;
    private int number_of_runs;

    public Producer(IBuffer buffer, int number_of_runs) {
        this.buffer = buffer;
        this.number_of_runs = number_of_runs;
    }

    public void run() {
        for(int i = 0;  i < number_of_runs;   i++) {
            buffer.put("message " + i);
            // synchronized (buffer) {
            //     while (!buffer.isEmpty()) {
            //         try {
            //             buffer.wait();
            //         } catch (InterruptedException e) {
            //             e.printStackTrace();
            //             Thread.currentThread().interrupt();
            //         }
            //     }
            //     long timestamp = System.currentTimeMillis();
            //     buffer.put("message "+i, timestamp);
            //     System.out.println("This is Producer " + this.hashCode() + " at " + timestamp + ": message " + i);
            //     buffer.flipEmpty();
            //     buffer.notifyAll();
            // }
            // System.out.println("This is Producer " + this.hashCode() + ": message " + i);
        }
    }
}



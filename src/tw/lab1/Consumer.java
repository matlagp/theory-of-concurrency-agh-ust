package tw.lab1;

import tw.IBuffer;

public class Consumer implements Runnable {
    private final IBuffer buffer;
    private int number_of_runs;

    public Consumer(IBuffer buffer, int number_of_runs) {
        this.buffer = buffer;
        this.number_of_runs = number_of_runs;
    }

    public void run() {
        for(int i = 0;  i < number_of_runs;   i++) {
            String message = buffer.take();
        }
    }
}

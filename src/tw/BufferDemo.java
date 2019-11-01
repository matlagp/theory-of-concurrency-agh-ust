package tw;

import tw.lab1.Buffer;
import tw.lab1.Consumer;
import tw.lab1.Producer;
import tw.lab3.BoundedBuffer;

public class BufferDemo {
    public static void main(String[] args) {
        IBuffer buffer = new BoundedBuffer();   // Change here for other implementations
        Producer p1 = new Producer(buffer, 5);
        Producer p2 = new Producer(buffer, 5);
        Consumer c1 = new Consumer(buffer, 7);
        Consumer c2 = new Consumer(buffer, 3);

        Thread tp1 = new Thread(p1);
        Thread tp2 = new Thread(p2);
        Thread tc1 = new Thread(c1);
        Thread tc2 = new Thread(c2);

        tp1.start();
        tp2.start();
        tc1.start();
        tc2.start();

        try {
            tp1.join();
            tp2.join();
            tc1.join();
            tc2.join();
        } catch (InterruptedException ie) {
            System.out.println("Exception while threads running.\n");
        }
    }
}

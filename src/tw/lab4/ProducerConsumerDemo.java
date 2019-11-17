package tw.lab4;

import tw.Utilities;

public class ProducerConsumerDemo {
    private static final int M = 5;
    private static final int NUMBER_OF_PRODUCERS = 2;
    private static final int NUMBER_OF_CONSUMERS = 3;

    public static void main(String[] args) {
        Buffer b = new Buffer(M*2);
        Producer[] producers = new Producer[NUMBER_OF_PRODUCERS];
        Consumer[] consumers = new Consumer[NUMBER_OF_CONSUMERS];
        Thread[] threads = new Thread[NUMBER_OF_PRODUCERS + NUMBER_OF_CONSUMERS];

        for (int i = 0; i < NUMBER_OF_PRODUCERS; i++) {
            producers[i] = new Producer(b, M);
            threads[i] = new Thread(producers[i]);
        }

        for (int i = 0; i < NUMBER_OF_CONSUMERS; i++) {
            consumers[i] = new Consumer(b, M);
            threads[NUMBER_OF_PRODUCERS + i] = new Thread(consumers[i]);
        }

        Utilities.startArrayOfThreads(threads);
    }
}

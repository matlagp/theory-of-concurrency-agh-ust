package tw.lab4;

import tw.Utilities;

public class StreamProcessingDemo {
    private static final int PROCESSORS_NUMBER = 10;
    private static final int JOBS_NUMBER = 32;

    public static void main(String[] args) {
        JobBuffer jb = new JobBuffer(JOBS_NUMBER);
        Processor[] processors = new Processor[PROCESSORS_NUMBER];
        Thread[] threads = new Thread[PROCESSORS_NUMBER];
        for (int i = 0; i < PROCESSORS_NUMBER; i++) {
            processors[i] = new Processor(jb, i*100);
            threads[i] = new Thread(processors[i]);

            processors[i].setLazyFactor(0);
        }
        processors[0].setProducer(true);
        processors[PROCESSORS_NUMBER - 1].setConsumer(true);

        processors[PROCESSORS_NUMBER/2].setLazyFactor(10_000);

        Utilities.startArrayOfThreads(threads);
    }
}

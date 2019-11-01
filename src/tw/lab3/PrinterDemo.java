package tw.lab3;

import java.util.LinkedList;

import static tw.Utilities.startArrayOfThreads;

public class PrinterDemo {
    private static final int numberOfProducers = 10;
    private static final int numberOfPrinters = 4;

    public static void main(String[] args) {
        LinkedList<Printer> printers = new LinkedList<>();
        for (int i = 0; i < numberOfPrinters; i++) {
            printers.add(new Printer());
        }
        PrinterMonitor printerMonitor = new PrinterMonitor(printers);
        PrinterUser[] users = new PrinterUser[numberOfProducers];
        Thread[] threads = new Thread[numberOfProducers];
        for (int i = 0; i < numberOfProducers; i++) {
            users[i] = new PrinterUser(printerMonitor);
            threads[i] = new Thread(users[i]);
        }

        startArrayOfThreads(threads);
    }
}

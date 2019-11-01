package tw.lab3;

import tw.Logger;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrinterMonitor {
    private Queue<Printer> printers;
    private final Lock printersLock = new ReentrantLock();
    private final Condition notEmpty = printersLock.newCondition();
    private final Condition notFull = printersLock.newCondition();
    private final int capacity;

    public PrinterMonitor(Queue<Printer> printers) {
        this.printers = printers;
        this.capacity = printers.size();
    }

    public Printer getFreePrinter() throws InterruptedException {
        printersLock.lock();
        try {
            while (printers.isEmpty()) {
                notEmpty.await();
            }
            Printer freePrinter = printers.remove();
            Logger.log("Printer Monitor removed free printer: " + freePrinter.getPrinterID());
            notFull.signal();
            return freePrinter;
        } finally {
            printersLock.unlock();
        }
    }

    public void freePrinter(Printer printer) throws InterruptedException {
        printersLock.lock();
        try {
            while (printers.size() >= capacity) {
                notFull.await();
            }
            printers.add(printer);
            Logger.log("Printer Monitor added free printer: " + printer.getPrinterID());
            notEmpty.signal();
        } finally {
            printersLock.unlock();
        }
    }
}

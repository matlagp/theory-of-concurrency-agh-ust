package tw.lab3;

import tw.Logger;

public class PrinterUser implements Runnable {
    private int jobID = 0;
    private PrinterMonitor printerMonitor;

    public PrinterUser(PrinterMonitor printerMonitor) {
        this.printerMonitor = printerMonitor;
    }

    @Override
    public void run() {
        while (true) {
            String messageToPrint = createPrintJob();
            try {
                Printer printer = printerMonitor.getFreePrinter();
                printer.print(messageToPrint);
                printerMonitor.freePrinter(printer);
            } catch (InterruptedException e) {
                Logger.log("Interrupted while waiting for printer");
                e.printStackTrace();
            }
        }
    }

    private String createPrintJob() {
        String message = "message " + Thread.currentThread().getId() + "." + (++jobID);
        Logger.log("Producer created print job: " + message);
        return message;
    }
}

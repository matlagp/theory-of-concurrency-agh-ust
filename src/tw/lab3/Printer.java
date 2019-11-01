package tw.lab3;

import tw.Logger;
import tw.Utilities;

public class Printer {
    private static int newPrinterID = 0;
    private int printerID;

    public Printer() {
        this.printerID = newPrinterID++;
    }

    public void print(String message) {
        Logger.log("Printer " + printerID + " printing message: " + message);
        try {
            Utilities.sleepUpToMillis(1000);    // Angry printer noises
        } catch (InterruptedException e) {
            Logger.log("Printer " + printerID + " interrupted");
            e.printStackTrace();
        }
    }

    public int getPrinterID() {
        return printerID;
    }
}

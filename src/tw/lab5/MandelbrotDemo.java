package tw.lab5;

import tw.TablePrinter;
import tw.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MandelbrotDemo {
    private final static int HEIGHT = 1000;
    private final static int WIDTH = 1000;
    private final static int NUMBER_OF_TASKS = 3;

    public static void main(String[] args) {
        int[] numberOfThreads = {1, 4, 8, 16};
        String[][] table = new String[numberOfThreads.length + 1][NUMBER_OF_TASKS + 1];
        String[] headerRow = new String[NUMBER_OF_TASKS + 1];
        headerRow[0] = "";
        headerRow[1] = "# of threads";
        headerRow[2] = "10x # of threads";
        headerRow[3] = "task for each pixel";
        table[0] = headerRow;
        int j = 1;
        for (int i: numberOfThreads) {
            String[] tableRow = new String[NUMBER_OF_TASKS + 1];
            tableRow[0] = String.format("%d thread(s)", i);
            long timeOfComputation1 = computeMandelbrot(i, i);
            long timeOfComputation2 = computeMandelbrot(i, 10*i);
            long timeOfComputation3 = computeMandelbrot(i, HEIGHT*WIDTH);
            tableRow[1] = String.format("%d", timeOfComputation1);
            tableRow[2] = String.format("%d", timeOfComputation2);
            tableRow[3] = String.format("%d", timeOfComputation3);
            table[j++] = tableRow;
        }

        TablePrinter printer = new TablePrinter(table, '|', '-', '+', ' ');
        printer.print();
    }

    private static long computeMandelbrot(int numberOfThreads, int numberOfTasks) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numberOfThreads);
        List<Mandelbrot> tasks = new ArrayList<>();
        for (int j = 0; j < numberOfTasks; j++) {
            tasks.add(new Mandelbrot(
                    j*(HEIGHT/numberOfTasks),
                    (j+1)*(HEIGHT/numberOfTasks),
                    j*(WIDTH/numberOfTasks),
                    (j+1)*(WIDTH/numberOfTasks)));
        }

        long startTime = System.currentTimeMillis();
        try {
            executor.invokeAll(tasks);
            executor.shutdown();
        } catch (InterruptedException e) {
            Utilities.haltAndCatchFire(e);
        }
        return Utilities.timeDeltaInMillis(startTime, System.currentTimeMillis());
    }
}

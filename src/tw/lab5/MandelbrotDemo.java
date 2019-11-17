package tw.lab5;

import tw.TablePrinter;
import tw.Utilities;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MandelbrotDemo extends JFrame {
    private final static int HEIGHT = 1000;
    private final static int WIDTH = 1000;
    private final static int NUMBER_OF_TASKS = 3;
    private BufferedImage I;

    public MandelbrotDemo() {
        super("Mandelbrot Set");
        setBounds(100, 100, WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public static void main(String[] args) throws IOException {
        int[] numberOfThreads = {1, 4, 8, 16};
        String[][] table = new String[numberOfThreads.length + 1][NUMBER_OF_TASKS + 1];
        String[] headerRow = new String[NUMBER_OF_TASKS + 1];
        headerRow[0] = "";
        headerRow[1] = "# of threads";
        headerRow[2] = "10x # of threads";
        headerRow[3] = "task for each column";
        table[0] = headerRow;
        int j = 1;
        MandelbrotDemo demo= new MandelbrotDemo();
        for (int i: numberOfThreads) {
            String[] tableRow = new String[NUMBER_OF_TASKS + 1];
            tableRow[0] = String.format("%d thread(s)", i);
            long timeOfComputation1 = demo.computeMandelbrot(i, i);
            long timeOfComputation2 = demo.computeMandelbrot(i, 10*i);
            long timeOfComputation3 = demo.computeMandelbrot(i, WIDTH);
            tableRow[1] = String.format("%d", timeOfComputation1);
            tableRow[2] = String.format("%d", timeOfComputation2);
            tableRow[3] = String.format("%d", timeOfComputation3);
            table[j++] = tableRow;
        }

        TablePrinter printer = new TablePrinter(table, '|', '-', '+', ' ');
        printer.print();
        demo.setVisible(true);
    }

    private long computeMandelbrot(int numberOfThreads, int numberOfTasks) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numberOfThreads);
        List<Mandelbrot> tasks = new ArrayList<>();
        for (int j = 0; j < numberOfTasks; j++) {
            tasks.add(new Mandelbrot(
                    I,
                    j*(WIDTH/numberOfTasks),
                    (j+1)*(WIDTH/numberOfTasks),
                    0,
                    HEIGHT));
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

package tw.lab5;

import tw.Utilities;

import java.util.concurrent.Callable;

public class Mandelbrot implements Callable<Integer> {
    private final int MAX_ITER = 1570;
    private final double ZOOM = 450;
    private double zx, zy, cX, cY, tmp;
    private final int minX, maxX, minY, maxY;

    public Mandelbrot(int minX, int maxX, int minY, int maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    private void computeMandelbrot() {
        for (int y = minY; y < maxY; y++) {
            for (int x = minX; x < maxX; x++) {
                zx = zy = 0;
                cX = (x - 400) / ZOOM;
                cY = (y - 300) / ZOOM;
                int iter = MAX_ITER;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
            }
        }
    }

    public Integer call() {
        long start = System.currentTimeMillis();
        computeMandelbrot();
        return (int) Utilities.timeDeltaInMillis(start, System.currentTimeMillis());
    }
}

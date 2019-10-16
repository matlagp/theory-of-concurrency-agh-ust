package tw.lab1;

public class IncThread extends Thread {
    private Counter s;

    public IncThread(Counter s) {
        this.s = s;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100_000_000; i++) {
            s.increment();
        }
    }
}

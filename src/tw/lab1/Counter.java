package tw.lab1;

public class Counter {
    private int i;

    public Counter(int i) {
        this.i = i;
    }

    public int getI() {
        return i;
    }

    public synchronized void increment() {
        this.i++;
    }

    public synchronized void decrement() {
        this.i--;
    }
}

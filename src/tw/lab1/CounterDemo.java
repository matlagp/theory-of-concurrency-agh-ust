package tw.lab1;

public class CounterDemo {

    public static void main(String[] args) {
        Counter c = new Counter(0);
        IncThread i = new IncThread(c);
        DecThread d = new DecThread(c);

        i.start();
        d.start();

        try {
            i.join();
            d.join();
        } catch (InterruptedException ie) {
            return;
        }

        System.out.println(c.getI());
    }
}

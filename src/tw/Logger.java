package tw;

public class Logger {
    public static void log(String message) {
        System.out.println("Thread " + Thread.currentThread().getId()
                + " at " + System.currentTimeMillis()
                + ": " + message);
    }
}

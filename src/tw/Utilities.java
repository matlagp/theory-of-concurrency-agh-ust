package tw;

public final class Utilities {
    private Utilities() {}  // this class is not meant to be instantiated

    public static void sleepUpToMillis(int millis) throws InterruptedException {
        Thread.sleep((long) (Math.random() * millis));
    }

    public static void startArrayOfThreads(Thread[] threads) {
        for (Thread t: threads) {
            t.start();
        }
    }

    public static void haltAndCatchFire(InterruptedException e) {
        Logger.log("Interrupted");
        e.printStackTrace();
    }
}

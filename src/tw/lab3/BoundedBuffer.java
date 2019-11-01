package tw.lab3;

import tw.IBuffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// See https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/locks/Condition.html
public class BoundedBuffer implements IBuffer {
    private final Lock lock = new ReentrantLock();
    private final Condition notFull  = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private final int MESSAGES_IN_BUFFER = 2;
    private final String[] items = new String[MESSAGES_IN_BUFFER];
    private int putptr, takeptr, count;

    private long modificationTime;

    @Override
    public void put(String message) {
        lock.lock();
        try {
            while (count == items.length)
                notFull.await();
            items[putptr] = message;
            if (++putptr == items.length) putptr = 0;
            ++count;
            modificationTime = System.currentTimeMillis();
            System.out.println("Pushing message: " + message + " at " + modificationTime
                    + " by thread " + Thread.currentThread().getId());
            notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String take() {
        lock.lock();
        try {
            while (count == 0)
                notEmpty.await();
            String x = items[takeptr];
            if (++takeptr == items.length) takeptr = 0;
            --count;
            long timestamp = System.currentTimeMillis();
            System.out.println("Popping message: " + x + " at " + timestamp
                    + " by thread " + Thread.currentThread().getId()
                    + " (diff: " + (timestamp - modificationTime) + " millis)");
            notFull.signal();
            return x;
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            return null;
        } finally {
            lock.unlock();
        }
    }
}

package tw.lab4;

import tw.Logger;
import tw.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private final int SIZE;
    private List<String> data = new ArrayList<>();

    private final Lock lock = new ReentrantLock();
    private Condition firstProducer = lock.newCondition();
    private Condition otherProducers = lock.newCondition();
    private Condition firstConsumer = lock.newCondition();
    private Condition otherConsumers = lock.newCondition();

    Optional<Producer> firstProducerOptional = Optional.empty();
    Optional<Consumer> firstConsumerOptional = Optional.empty();

    public Buffer(int SIZE) {
        this.SIZE = SIZE;
    }

    public void put(Producer producer, String message, int number) {
        if (number > SIZE/2) {
            Logger.log("Can not put more than " + SIZE + " elements, tried: " + number);
            return;
        }
        lock.lock();
        while (firstProducerOptional.isPresent()) {
            try {
                Logger.log("Producer " + producer.getProducerId() + " waiting in line");
                otherProducers.await();
            } catch (InterruptedException e) {
                Utilities.haltAndCatchFire(e);
            }
        }
        Logger.log("Producer " + producer.getProducerId() + " is the first in producers' line");
        firstProducerOptional = Optional.of(producer);
        while (SIZE - data.size() < number) {
            try {
                Logger.log("Producer " + producer.getProducerId() + " waiting for space\n\t" +
                        "buffer state: " + bufferState() + ", need " + number + " free slots");
                firstProducer.await();
            } catch (InterruptedException e) {
                Utilities.haltAndCatchFire(e);
            }
        }
        for (int i = 0; i < number; i++) {
            data.add(message);
        }
        firstProducerOptional = Optional.empty();
        otherProducers.signal();
        firstConsumer.signal();
        lock.unlock();
    }

    public Optional<List<String>> take(Consumer consumer, int number) {
        if (number > SIZE/2) {
            Logger.log("Can not put more than " + SIZE + " elements, tried: " + number);
            return Optional.empty();
        }
        lock.lock();
        while (firstConsumerOptional.isPresent()) {
            try {
                Logger.log("Consumer " + consumer.getConsumerId() + " waiting in line");
                otherConsumers.await();
            } catch (InterruptedException e) {
                Utilities.haltAndCatchFire(e);
            }
        }
        Logger.log("Consumer " + consumer.getConsumerId() + " is the first in consumers' line");
        firstConsumerOptional = Optional.of(consumer);
        while (data.size() < number) {
            try {
                Logger.log("Consumer " + consumer.getConsumerId() + " waiting for elements\n\t" +
                        "buffer state: " + bufferState() + ", need " + number + " elements");
                firstConsumer.await();
            } catch (InterruptedException e) {
                Utilities.haltAndCatchFire(e);
            }
        }
        List<String> result = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            result.add(data.remove(0));
        }
        firstConsumerOptional = Optional.empty();
        otherConsumers.signal();
        firstProducer.signal();
        lock.unlock();
        return Optional.of(result);
    }

    private String bufferState() {
        return data.size() + "/" + SIZE;
    }
}

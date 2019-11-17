package tw.lab4;

import tw.Logger;
import tw.Utilities;

import java.util.Optional;

public class Processor implements Runnable {
    private static int NEXT_PROCESSOR_ID = 0;

    private final int processorId;
    private boolean consumer;
    private boolean producer;
    private JobBuffer jobBuffer;
    private int lazyFactor;

    public Processor(JobBuffer jobBuffer, int lazyFactor) {
        this.jobBuffer = jobBuffer;
        this.consumer = false;
        this.producer = false;
        this.processorId = NEXT_PROCESSOR_ID++;
        this.lazyFactor = lazyFactor;
    }

    public int getProcessorId() {
        return processorId;
    }

    public boolean isConsumer() {
        return consumer;
    }

    public void setConsumer(boolean consumer) {
        this.consumer = consumer;
    }

    public boolean isProducer() {
        return producer;
    }

    public void setProducer(boolean producer) {
        this.producer = producer;
    }

    public void setLazyFactor(int lazyFactor) {
        this.lazyFactor = lazyFactor;
    }

    public void process(Job job) throws InterruptedException {
        job.setData(job.getData() + processorId);
        Logger.log("Processor " + processorId + " processing job " + job.getJobId());
        if (producer) {
            Logger.log("Starting job " + job.getJobId());
            job.start();
        } else if (consumer) {
            Logger.log("Finishing job " + job.getJobId());
            job.stop();
        }
        Utilities.sleepUpToMillis(1000 + lazyFactor); // Doing hard computations
        // Logger.log("Processed job " + job.getJobId());
    }

    @Override
    public void run() {
        while (true) {
            Optional<Job> potentialJob = jobBuffer.getFirstJobForProcessor(this);
            if (potentialJob.isPresent()) {
                try {
                    process(potentialJob.get());
                } catch (InterruptedException e) {
                    Logger.log("Caught InterruptedException while processing:");
                    e.printStackTrace();
                } finally {
                    jobBuffer.finishProcessing(potentialJob.get());
                }
            }
        }
    }
}

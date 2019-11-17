package tw.lab4;

import tw.Logger;
import tw.lab2.BinarySemaphore;
import tw.lab2.Semaphore;

import java.util.Optional;

public class JobBuffer {
    private final int SIZE;
    private Job[] jobs;
    private Semaphore[] semaphores;

    public JobBuffer(int SIZE) {
        this.SIZE = SIZE;
        this.jobs = new Job[SIZE];
        this.semaphores = new Semaphore[SIZE];
        for (int i = 0; i < SIZE; i++) {
            jobs[i] = new Job();
            semaphores[i] = new BinarySemaphore(false);
        }
    }

    public synchronized Optional<Job> getFirstJobForProcessor(Processor processor) {
        int semaphoreIndex = 0;
        for (Job j: jobs) {
            if (j.getNextProcessorId() == processor.getProcessorId()) {
                semaphores[semaphoreIndex].P();
                j.setPreviousProcessorId(j.getNextProcessorId());
                j.setNextProcessorId(j.getNextProcessorId()+1);
                Logger.log("Process " + processor.getProcessorId() + " got job " + j.getJobId());
                return Optional.of(j);
            }
            semaphoreIndex++;
        }
        return Optional.empty();
    }

    public void finishProcessing(Job job) {
        int semaphoreIndex = 0;
        for (Job j: jobs) {
            if (job.getJobId() == j.getJobId()) {
                Logger.log("Job " + job.getJobId() + " given back, next processor id: " + job.getNextProcessorId());
                semaphores[semaphoreIndex].V();
                return;
            }
            semaphoreIndex++;
        }
        Logger.log("No job with id " + job.getJobId() + " found");
    }
}

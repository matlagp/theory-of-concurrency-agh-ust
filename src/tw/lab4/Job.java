package tw.lab4;

public class Job {
    private static int NEXT_JOB_ID = 0;

    private final int jobId;
    private int previousProcessorId = -1;
    private int nextProcessorId = 0;
    private boolean running = false;
    private int data = 0;

    public Job() {
        this.jobId = NEXT_JOB_ID++;
    }

    public int getPreviousProcessorId() {
        return previousProcessorId;
    }

    public void setPreviousProcessorId(int previousProcessorId) {
        this.previousProcessorId = previousProcessorId;
    }

    public int getNextProcessorId() {
        return nextProcessorId;
    }

    public void setNextProcessorId(int nextProcessorId) {
        this.nextProcessorId = nextProcessorId;
    }

    public boolean isRunning() {
        return running;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public void start() {
        this.running = true;
    }

    public void stop() {
        this.data = 0;
        this.previousProcessorId = -1;
        this.nextProcessorId = 0;
        this.running = false;
    }

    public int getJobId() {
        return jobId;
    }
}

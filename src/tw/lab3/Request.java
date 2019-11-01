package tw.lab3;

public enum Request {
    NONE,
    ONE,
    BOTH;

    private Request next;

    static {
        NONE.next = ONE;
        ONE.next = BOTH;
        BOTH.next = BOTH;
    }

    public Request getNext() {
        return next;
    }
}

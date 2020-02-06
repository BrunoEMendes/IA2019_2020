package tests;

public class Timer {
    private final long startTime;

    public Timer() {
        startTime = System.nanoTime();
    }

    public double elapsedSeconds() {
        long now = System.nanoTime();
        return (now - startTime) / 1000000000.0;
    }

    public long elapsedNanoseconds() {
        long now = System.nanoTime();
        return (now - startTime);
    }
}

package team.zenit.utils;

import static org.apache.commons.lang3.RandomUtils.nextInt;

public class TimerUtil {
    // existing millisecond-based timer field used by current code
    public long lastMS = System.currentTimeMillis();

    // added nanosecond-based timer field to provide Timer.java functionality
    private long timeNS = System.nanoTime();

    public TimerUtil() {
        reset();
    }

    public final long getDifference() {
        return getCurrentMS() - lastMS;
    }


    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    // reset both ms- and ns- based timers
    public TimerUtil reset() {
        this.lastMS = System.currentTimeMillis();
        this.timeNS = System.nanoTime();
        return this;
    }

    // provide a chaining reset similar to the old Timer.reset() for compatibility
    public TimerUtil resetTimer() {
        this.timeNS = System.nanoTime();
        return this;
    }

    public boolean hasReached(double milliseconds) {
        return (double) (this.getCurrentMS() - this.lastMS) >= milliseconds;
    }

    public static long randomDelay(final int minDelay, final int maxDelay) {
        return nextInt(minDelay, maxDelay);
    }

    public boolean hasTimeElapsed(long time, boolean reset) {
        if (System.currentTimeMillis() - lastMS > time) {
            if (reset) reset();
            return true;
        }

        return false;
    }

    public boolean hasTimeElapsed(long time) {
        return System.currentTimeMillis() - this.lastMS > time;
    }

    public boolean delay(float time) {
        return System.currentTimeMillis() - this.lastMS >= time;

    }

    public boolean hasTimeElapsed(double time) {
        return hasTimeElapsed((long) time);
    }

    public long getTime() {
        return System.currentTimeMillis() - this.lastMS;
    }

    public void setTime(long time) {
        this.lastMS = time;
    }

    // --------- Methods merged from Timer.java (high-resolution timer) ---------

    public boolean passedS(double s) {
        return passedMs((long) (s * 1000.0));
    }

    public boolean passedMs(long ms) {
        return passedNS(convertToNS(ms));
    }

    public boolean passedMs(double ms) {
        return passedMs((long) ms);
    }

    public boolean passed(long ms) {
        return passedNS(convertToNS(ms));
    }

    public boolean passed(double ms) {
        return passedMs((long) ms);
    }

    public void setMs(long ms) {
        this.timeNS = System.nanoTime() - convertToNS(ms);
    }

    public boolean passedNS(long ns) {
        return System.nanoTime() - timeNS >= ns;
    }

    public long getPassedTimeMs() {
        return getMs(System.nanoTime() - timeNS);
    }

    public long getMs(long time) {
        return time / 1000000L;
    }

    public long convertToNS(long time) {
        return time * 1000000L;
    }

}
package model;

public class Lock {
    private boolean locked = false;

    public synchronized void waitUntilUnlocked() {
        while(locked) {
            try {
                wait();
            } catch(InterruptedException ignored) {}
        }
    }

    public synchronized void lock() {
        locked = true;
    }
    public synchronized void unlock() {
        locked = false;
        notify();
    }
}

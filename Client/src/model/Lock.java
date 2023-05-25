package model;

/**
 * A simple lock that can be used to synchronize threads.
 */
public class Lock {
    private boolean locked = false;

    /**
     * Waits until the lock is unlocked.
     */
    public synchronized void waitUntilUnlocked() {
        while(locked) {
            try {
                wait();
            } catch(InterruptedException ignored) {}
        }
    }

    /**
     * Locks the lock.
     */
    public synchronized void lock() {
        locked = true;
    }

    /**
     * Unlocks the lock.
     */
    public synchronized void unlock() {
        locked = false;
        notify();
    }
}

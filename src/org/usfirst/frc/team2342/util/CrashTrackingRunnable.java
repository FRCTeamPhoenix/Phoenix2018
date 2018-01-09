package org.usfirst.frc.team2342.util;

/**
 * Runnable class with reports all uncaught throws to CrashTracker
 * Copied from 254 to be used with Looper
 */
public abstract class CrashTrackingRunnable implements Runnable {

    @Override
    public final void run() {
        try {
            runCrashTracked();
        } catch (Throwable t) {
            CrashTracker.logThrowableCrash(t);
            throw t;
        }
    }

    public abstract void runCrashTracked();
}

package com.masum.anroidthread;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * Helper class for managing the background thread used to perform io operations
 * and handle async broadcasts.
 */
public final class AsyncHandler {

    private static final HandlerThread sHandlerThread = new HandlerThread("AsyncHandler");

    private static final Handler sHandler;

    static {
        sHandlerThread.start();
        sHandler = new Handler(sHandlerThread.getLooper());
    }

    /* This class is never initiated */
    private AsyncHandler() {
    }

    /**
     * @param r The {@link Runnable} to execute.
     */
    public static void post(final Runnable r) {
        sHandler.post(r);
    }

}


package com.kashyapmedia.moviesdemo;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {
    private Executor diskIO;
    private Executor networkIO;
    private Executor mainThread;

    private static AppExecutors _instance;

    public static AppExecutors get_instance() {
        if(_instance==null){
            _instance=new AppExecutors();
        }
        return _instance;
    }

    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    private AppExecutors() {
        diskIO= Executors.newSingleThreadExecutor();
        networkIO=Executors.newFixedThreadPool(3);
        mainThread=new MainThreadExecutor();
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getNetworkIO() {
        return networkIO;
    }

    public Executor getMainThread() {
        return mainThread;
    }

    private class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(Runnable command) {
            mainThreadHandler.post(command);

        }
    }
}

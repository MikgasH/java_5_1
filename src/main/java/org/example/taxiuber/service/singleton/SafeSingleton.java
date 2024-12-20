package org.example.taxiuber.service.singleton;

import java.util.concurrent.locks.ReentrantLock;

public class SafeSingleton {
    private static SafeSingleton instance;
    private static final ReentrantLock lock = new ReentrantLock();

    private long orderIdCounter;

    private SafeSingleton() {
    }

    public static SafeSingleton getInstance() {
        lock.lock();
        try {
            if (instance == null) {
                instance = new SafeSingleton();
            }
        } finally {
            lock.unlock();
        }
        return instance;
    }

    public long getNextOrderId() {
        lock.lock();
        try {
            return ++orderIdCounter;
        } finally {
            lock.unlock();
        }
    }
}

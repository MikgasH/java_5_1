package org.example.taxiuber.service.threadpool;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleTaskQueue {
    private final Queue<Callable<?>> queue = new LinkedList<>();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();

    public void put(Callable<?> task) {
        lock.lock();
        try {
            queue.offer(task);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Callable<?> take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                notEmpty.await();
            }
            return queue.poll();
        } finally {
            lock.unlock();
        }
    }
}

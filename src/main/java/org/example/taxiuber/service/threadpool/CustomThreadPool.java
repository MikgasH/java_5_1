package org.example.taxiuber.service.threadpool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomThreadPool {
    private static final Logger logger = LogManager.getLogger(CustomThreadPool.class);

    private final SimpleTaskQueue queue = new SimpleTaskQueue();
    private final List<Worker> workers = new ArrayList<>();
    private final AtomicBoolean isShutdown = new AtomicBoolean(false);

    public CustomThreadPool(int poolSize) {
        logger.info("CustomThreadPool created with poolSize = {}", poolSize);
        for (int i = 0; i < poolSize; i++) {
            Worker w = new Worker("CustomPool-Worker-" + (i+1));
            w.start();
            workers.add(w);
        }
    }

    public void submit(Callable<?> task) {
        if (isShutdown.get()) {
            throw new IllegalStateException("Pool is shut down");
        }
        queue.put(task);
    }

    public void shutdown() {
        logger.info("Shutting down CustomThreadPool...");
        isShutdown.set(true);
        for (int i = 0; i < workers.size(); i++) {
            queue.put(() -> null);
        }
    }

    private class Worker extends Thread {
        public Worker(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (!isShutdown.get()) {
                try {
                    Callable<?> task = queue.take();
                    if (task == null) {
                        // значит, надо выходить
                        break;
                    }
                    task.call();
                } catch (InterruptedException e) {
                    break;
                } catch (Exception ex) {
                    logger.error("Error in worker: ", ex);
                }
            }
            logger.info(getName() + " stopped.");
        }
    }
}

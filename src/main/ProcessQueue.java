package main;

import java.util.LinkedList;
import java.util.Queue;

public class ProcessQueue {
    /**
     * Queue storing requests
     */
    private Queue<HttpRequest> queue;

    /**
     * Lock for atomic operations on queue
     */
    private final Object lock;

    public ProcessQueue() {
        queue = new LinkedList<>();
        lock = new Object();
    }

    /**
     * Add a new request to the queue
     * @param request the HttpRequest
     */
    public void add(HttpRequest request) {
        synchronized (lock) {
            queue.add(request);
        }
    }

    /**
     * Pop a request from the top of the queue
     * @return the HttpRequest popped (and removed from queue)
     */
    public synchronized HttpRequest remove() {
        synchronized (lock) {
            return queue.remove();
        }
    }
}
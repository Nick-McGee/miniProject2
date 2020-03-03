package main;

import java.util.LinkedList;
import java.util.Queue;

public class ProcessQueue {
    /**
     * Maximum number of requests in the queue
     */
    private static final int QUEUE_BOUND = 100;
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
            if(queue.size() < QUEUE_BOUND)
                this.queue.add(request);
        }
    }

    /**
     * Pop a request from the top of the queue
     * @return the HttpRequest popped (and removed from queue)
     */
    public synchronized HttpRequest remove() {
        synchronized (lock) {
            return this.queue.remove();
        }
    }

    /**
     * Returns the number of remaining requests in the queue
     * @return the length of the Queue
     */
    public int getLength() {
        synchronized (lock) {
            return this.queue.size();
        }
    }
}
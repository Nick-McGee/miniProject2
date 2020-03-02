package main;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Webserver is the "Master" thread in this scenario
 */
public class Webserver {

    /**
     * boolean indicating whether webserver is listening
     * Used for stopping slave threads
     * Atomic to avoid read/write race conditions
     */
    private AtomicBoolean listening;
    /**
     * Stores slave threads
     */
    Thread[] slaves;

    /**
     * Stores incoming requests
     */
    ProcessQueue requests;

    /**
     * Monitor for threads
     */
    private final Object monitor;

    public Webserver() {
        this.listening = new AtomicBoolean(false);
        this.requests = new ProcessQueue();
        this.monitor = new Object();
    }
    /**
     * Listens for requests
     * @param threads the number of threads to process web requests on
     */
    public void listen(int threads) {
        this.setListening(true);
        slaves = new Thread[threads];
        //Spawn slave threads
        for(int i = 0; i < threads; i++) {
            Slave slave = new Slave(i+1, this);
            Thread t = new Thread(slave);
            t.start();
            slaves[i] = t;
        }
    }

    /**
     * Adds a new request to the queue
     * @param request the HttpRequest to add to queue
     */
    public void addRequest(HttpRequest request) {
        this.requests.add(request);
        synchronized (this.monitor) {
            this.monitor.notify(); //Notify any thread that a request has come
        }
    }

    /**
     * Atomically get request from the request ProcessQueue
     * @return an HttpRequest object representing a single request
     */
    public HttpRequest getRequest() {
        return this.requests.remove();
    }

    /**
     * @return number of requests in webserver queue
     */
    public int getQueueSize() {
        return this.requests.getLength();
    }

    /**
     * Atomically sets listening boolean
     * @param listening the new boolean value
     */
    public void setListening(boolean listening) {
        this.listening.set(listening);
    }

    /**
     * Atomically reads listening boolean
     * @return boolean indicating whether to listen
     */
    public boolean isListening() {
        return this.listening.get();
    }

    /**
     * Gives the global monitor for all threads
     *
     * @return monitor the monitor object
     */
    public Object getMonitor() {
        return this.monitor;
    }

    /**
     * Stops the webserver
     */
    public void stop() {
        this.setListening(false);
        synchronized (this.monitor) {
            this.monitor.notifyAll(); //All threads become active and exit
        }

        //Block until all slaves done
        for (Thread slave : slaves) {
            try {
                slave.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

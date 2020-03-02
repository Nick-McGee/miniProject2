package main;

class Slave implements Runnable {
    private int id;        // given thread id
    private Webserver master; //Master thread

    Slave(int id, Webserver master) {
        this.id = id;
        this.master = master;
    }

    /**
     * Slave will run and wait for requests
     * as long as listening is true
     */
    public void run() {
        while(master.isListening()) {
            try {
                synchronized (this.master.getMonitor()) {
                    this.master.getMonitor().wait(); //Wait for a request to come
                }
                /* Notified */
                //Make sure its supposed to be listening
                //once again
                if(master.isListening()) {

                    processRequest();

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Make sure remaining requests in the queue server served
        while(master.getQueueSize() > 0) {
            processRequest();
        }
    }

    private void processRequest() {
        HttpRequest request = master.getRequest();
        request.process(); //simulates processing request, random delay
        System.out.println(
                String.format(
                        "Consumer %d: Completed request ID %d at time %s",
                        id,
                        request.getId(),
                        java.time.LocalTime.now()
                )
        );
    }
}
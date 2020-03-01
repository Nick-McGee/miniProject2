package main;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class HttpRequest {

    private static int globalId;
    private static int randDelay;

    static {
        globalId = 1;
    }

    private int id;

    public static void setRandDelay(int M) {
        HttpRequest.randDelay = M;
    }

    /**
     * Set this request's id
     */
    public HttpRequest() {
        this.id = HttpRequest.globalId++;
    }

    /**
     * Simulate processing of HTTP request
     */
    public void process() {
        Random random = new Random();
        try {
            //Random sleep between 1 and randDelay seconds
            TimeUnit.SECONDS.sleep(
                    random.nextInt(HttpRequest.randDelay) + 1
            );
        } catch(InterruptedException ignored) {}
    }

    /**
     * @return the id of this HttpRequest object
     */
    public int getId() {
        return this.id;
    }

}

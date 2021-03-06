package main;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // Get input
        System.out.print("Slaves (Recommended 50): ");
        int slaveCount = in.nextInt();
        System.out.print("Max random time to sleep (in seconds) for requests (Recommended 3): ");
        int maxTime = in.nextInt();
        System.out.print("Number of requests to simulate: ");
        int numRequests = in.nextInt();

        HttpRequest.setRandDelay(maxTime);

        Webserver webserver = new Webserver();
        webserver.listen(slaveCount); //Listen with # of slaves provided by user

        double time = System.currentTimeMillis();
        //Simulate 200 incoming requests
        for(int i = 0; i < numRequests; i++) {
            try {
                //Sleep for a random time between 0 and 100ms for each request
                Thread.sleep((int)(Math.random() * 100));
                //Produce request
                HttpRequest request = new HttpRequest();

                System.out.println(
                        String.format(
                                "Producer: Queueing request ID %d at time %s",
                                request.getId(),
                                java.time.LocalTime.now()
                        )
                );

                //Queue request
                webserver.addRequest(request);
            } catch(InterruptedException ignore) { }
        }

        //Gracefully stop the webserver and all its slave threads
        webserver.stop();

        time = System.currentTimeMillis() - time;

        System.out.println("Time taken to process " + numRequests + " requests: " + (time/1000) + "s");
        System.out.println("Average time per request: " + ((time/1000)/numRequests) + "s");
    }
}
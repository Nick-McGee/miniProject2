package main;

import java.util.Scanner;

public class Main {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        // Get input
        System.out.print("Slaves (Recommended 50): ");
        int slaveCount = Integer.parseInt(in.nextLine());
        System.out.print("Random time to sleep (in seconds) for requests (Recommended 3): ");
        int maxTime = Integer.parseInt(in.nextLine());

        HttpRequest.setRandDelay(maxTime);

        Webserver webserver = new Webserver();
        webserver.listen(slaveCount); //Listen with # of slaves provided by user

        double time = System.currentTimeMillis();
        //Simulate 200 incoming requests
        for(int i = 0; i < 200; i++) {
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

        System.out.println("Time taken to process 200 requests: " + (time/1000) + "s");
        System.out.println("Average time per request: " + ((time/1000)/200) + "s");
    }
}
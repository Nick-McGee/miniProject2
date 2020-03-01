package main;

import java.util.Scanner;

public class Main {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        // Get input
        System.out.print("Slaves: ");
        int slaves = Integer.parseInt(in.nextLine());
        System.out.print("Time: ");
        int maxTime = Integer.parseInt(in.nextLine());

        // create n slave threads
        for (int i = 0; i < slaves; i++) {
            int wait = (int) (Math.random() * (10 - 5 + 1)) + 5;  // create random wait time (THIS IS WRONG)

            // output producer text
            System.out.println(String.format("Producer: Produced request ID %d, length %d seconds at time %s", i + 1, maxTime, java.time.LocalTime.now()));
            System.out.println(String.format("Producer: Sleeping for %d seconds\n", wait));

            int threadName = i+1;

            // this should add them to the queue, not start them
            Thread slave = new Thread(new Slave(threadName, maxTime));
            slave.start();// create slave thread and run it

            // master wait time
            try {
                Thread.sleep(wait * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Slave implements Runnable {
    int id;        // given thread id
    int maxTime;   // given length

    Slave(int id, int maxTime) {
        this.id = id;
        this.maxTime = maxTime;
    }

    public void run() {
        System.out.println(String.format("Consumer: Assigned request ID %d, processing request for the next %d seconds, current time is %s\n", id, maxTime, java.time.LocalTime.now()));

        // thread wait time (not complete)
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(String.format("Consumer: Completed request ID %d at time %s\n", id, java.time.LocalTime.now()));
    }
}
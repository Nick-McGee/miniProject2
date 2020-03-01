package main;

import java.util.Scanner;

public class Main implements Runnable{

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        // Get input
        System.out.print("Slaves: ");
        int slaves = Integer.parseInt(in.nextLine());
        System.out.print("Time: ");
        int maxTime = Integer.parseInt(in.nextLine());

        // create n slave threads
        for(int i = 0; i < slaves; i++) {
            int wait = (int)(Math.random() * (10-5 + 1)) + 5;  // create random wait time

            // output producer text
            System.out.println(String.format("Producer: Produced request ID %d, length %d seconds at time %s", i+1, maxTime, java.time.LocalTime.now()));
            System.out.println(String.format("Producer: Sleeping for %d seconds", wait));

            // this add them to the queue, not start them
            (new Thread(new Main())).start(); // create slave thread and run it

            // master wait time
            try {
                Thread.sleep(wait * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // running the thread
    public void run() {
        System.out.println("Thread is busy.");

        // thread wait time
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Thread is idle.");
    }
}
package main;

import java.util.Random;
import java.util.Scanner;

public class Main extends Thread{
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

            System.out.println(String.format("Producer: Produced request ID %d, length %d seconds at time %s", i+1, maxTime, java.time.LocalTime.now()));
            System.out.println(String.format("Producer: Sleeping for %d seconds", wait));

            (new Main()).start();  // creating the slave threads

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
        System.out.println("Thread sleeping...");

        // thread wait time
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Thread waking up.");
    }
}
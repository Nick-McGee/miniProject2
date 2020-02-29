package main;

import java.util.Scanner;

public class Main extends Thread{
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        // Get input
        System.out.print("Slaves: ");
        int slaves = Integer.parseInt(in.nextLine());
        System.out.print("Time: ");
        int maxTime = Integer.parseInt(in.nextLine());

        for(int i = 0; i < slaves; i++) {
            System.out.println(String.format("Producer: Produced request ID %d, length %d seconds at time %s", slaves, maxTime, java.time.LocalTime.now()));
            System.out.println(String.format("Producer: Sleeping for %d seconds", maxTime));

            (new Main()).start();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void run() {
        System.out.println("Hello from a thread!");
    }

}
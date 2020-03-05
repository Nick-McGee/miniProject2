# MiniProject2 - COSC 315

## Authors: Nick, Raghav, and Carlos
- Nick's contribution: Design and development of the Java version.
- Raghav's contribution: Design and development of the Java and C++ version.
- Carlos' contribution: Design and development of the C++ version and README write up.

*Note: For Java part of the project we replicated a similar implementation of the apache web server request scheduler. For the C++ version we made a simpler version of the consumer and producer idea.*

**Part 1 (Java program files):**

HttpRequest.java:
- HttpRequest object represents the process we are sending to the queue.
- The HttpRequest object contains an ID and a random time delay.
- A getter method for its ID.
- A process method that randomly causes the request to sleep for a number (between 1 and a random time delay).

ProcessQueue.java:
- ProcessQueue object represents the buffer for the consumer and producer problem.
- The ProcessQueue object is a queue of the HttpRequest objects with a maximum size/bound of 100 and a lock object variable.
- A get length method to get the size of the queue.
- A add and remove method which add a HttpRequest as long as the queue is not full (100) and remove when simply called upon.
- Synchronization is used in the add, remove and getLength method to make sure no other thread is alteraing the size of the queue and returns an incorrect length, count, etc.
- If one of the three methods are called other threads that call one of the other methods will have to wait until the critical part of the code executes before giving the lock back.

Webserver.java:
- The webserver is the master thread for this given scenario.
- The webserver object contains a ProcessQueue object, monitor object and a listening variable (boolean).
- Four getter methods for the queue size, monitor object, request object, and the listening varibale.
- Add method to add the request to the queue. Synchronization is used here to keep other threads from altering the queue while it is being modified by another thread.
- A setter method for the listening variable.
- A listen method that sets the listening variable to true and spawn slave threads to process the request.
- A stop method that stops the webserver (master thread) from execution. Notifies all the slave threads and blocks then until the work is complete. Synchronization is used for when the slaves are notified.

Slave.java:
- The slave object contains a given id and the master thread for the given slave.
- A run method that causes the slave thread to run/execute and wait for request as long as the master thread is listening.
- A processRequest method that processes the http request and prints out the completed request simulating the consumer. 

Main.java:
- Gets user input for number of slaves, maxmium random time sleeping, and the number of requests they want to simulate.
- Create a webserver object and listen with the number of slave threads provided by the user.
- Looping over the simulated number of request, sleep for a random period of time between 0 - 100ms and then produce the request, and then finally add the requets to the process queue.
- Stop the websever and print out the time taken to process the requests and the average time it took for each of the request to be completed.

**Part 2 C++ program files:**

Note: To complile the code on the Linux use the following command in the command prompt:g++ -std=c++11 -pthread -o main main.cpp && ./main

main.cpp:
- In


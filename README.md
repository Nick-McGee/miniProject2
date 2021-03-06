# MiniProject2 - COSC 315

## Authors: Nick, Raghav, and Carlos
- Nick's contribution: Design and development of the Java version.
- Raghav's contribution: Design and development of the Java and C++ version.
- Carlos' contribution: Design and development of the C++ version and README write up.

*Note: For Java part of the project we replicated a similar implementation of the apache web server request scheduler. For the C++ version we made a simpler version of the consumer and producer idea.*

**Part 1 (Java program files):**

*Note: To compile the code on the Linux do the following:*
- Inside the unzipped directory run this command: `javac src/main/*.java && java -cp src/ main.Main`

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
- Synchronization is used in the add, remove and getLength method to make sure no other thread is altering the size of the queue and returns an incorrect length, count, etc.
- If one of the three methods are called other threads that call one of the other methods will have to wait until the critical part of the code executes before giving the lock back.

Webserver.java:
- The webserver is the master thread for this given scenario.
- The webserver object contains a ProcessQueue object, monitor object and a listening variable (boolean).
- Four getter methods for the queue size, monitor object, request object, and the listening variable.
- Add method to add the request to the queue. Synchronization is used here to keep other threads from altering the queue while it is being modified by another thread.
- A setter method for the listening variable.
- A listen method that sets the listening variable to true and spawn slave threads to process the request.
- A stop method that stops the webserver (master thread) from execution. Notifies all the slave threads and blocks then until the work is complete. Synchronization is used for when the slaves are notified.

Slave.java:
- The slave object contains a given id and the master thread for the given slave.
- A run method that causes the slave thread to run/execute and wait for request as long as the master thread is listening.
- A processRequest method that processes the http request and prints out the completed request simulating the consumer. 

Main.java:
- Gets user input for number of slaves, maximum random time sleeping, and the number of requests they want to simulate.
- Create a webserver object and listen with the number of slave threads provided by the user.
- Looping over the simulated number of request, sleep for a random period of time between 0 - 100ms and then produce the request, and then finally add the request to the process queue.
- Stop the websever and print out the time taken to process the requests and the average time it took for each of the requests to be completed.

**Part 2 C++ program files:**

*Note: To compile the code on the Linux use the following command in the command prompt:*
- Inside the unzipped directory run `g++ -std=c++11 -pthread -o main main.cpp && ./main`

main.cpp:
- Similar to the java implementation in the Main.java we get the same input variables from the user and declare necessary variables. One difference is we are using semaphores as opposed to monitors.
- A sleep method that puts a thread to sleep for a specified time.
- A consume method that consumes (pops) work from the buffer. Synchronisation used to give access to one thread at a time to the critical section when the processes are being popped from the queue.
- A consumer method run by each slave thread making sure they consume from the queue as long as the buffer(queue) is not empty 
- A produce method that pushes work into the buffer queue. Synchronisation used to give access to one thread at a time to the critical section (the pushing of the queue). Do not want multiple threads pushing the queue at a time.
- A main method that gets the user input on the number of slaves, max random time sleeping, and the number of jobs to be produced.
- Create the slaves threads and store them.
- Simulate the requests and then sleep each produce for a time period between 0 - 100ms.
- When all the work is queued, notify all of the threads to stop and join them to wait for their completion 
- Once all threads are shut down proceed to exit.

**Experience with the project:**

Carlos: 
- For this project I had to learn C++ as I already had knowledge in Java. When trying to implement the similar web server request scheduler we used for the Java implementation in C++, I was really struggling. But more specficially I struggled most when I was setting up my complier, MakeFile, and other unique things I needed to write C++

Nick:
- For Java, I had a bit of difficulty at first getting it working. I began by just making small programs instead of implementing the actual assignemnt, just so I could understand the functions such as creating slave threads, and creating the processes. Once I understood that, I was able to get the process queue working pretty easily.

Raghav:
- For Java, the implementation was relatively easy. Due to keywords like synchronized, and the fact that every object has a .wait(), .notify() and .notifyAll() method by default, Java was simple and quick.
- For C++, this was harder. Since there are no monitors, we used a condition variable. However, because the C++ compiler sometimes gives vague error messages, it was harder to debug. Moreover, there were somethings that just didn't make sense e.g. adding a thread object to a vector in c++ wasn't as simple as vector.push_back(thread), but vector.push_back(move(thread)). The reason for this was how C++ handles memory. Another thing was that the wait() had to be wrapped in a while loop for C++ to avoid it missing a wake up. Java did this internally. Overall, C++ took longer and was harder but is still faster than Java in general programming.

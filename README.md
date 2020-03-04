# MiniProject2 - COSC 315

####### Authors: Nick, Raghav, and Carlos
- Nick's contribution: Design and development of the Java version
- Raghav's contribution: Design and development of the Java and C++ version
- Carlos' contribution: Design and development of the C++ version and README write up

*Note: For the project we replicated a similar implementation of the apache web server request scheduler*

**Part 1 (Java program files):**

HttpRequest.java:
- HttpRequest object represents the process we are sending to the queue 
- A HttpRequest object contains an ID and a random time delay
- A getter method for its ID
- A process method that randomly causes the request to sleep for a number (between 1 and a random time delay) 

ProcessQueue.java:
- ProcessQueue object represents the buffer for the consumer and producer problem
- The ProcessQueue object is a queue of the HttpRequest objects with a maximum size/bound of 100 and a lock object variable
- A get length method to get the size of the queue
- A add and remove method which add a HttpRequest as long as the queue is not full (100) and remove when simply called upon
- Synchronization is used in the add, remove and getLength method to make sure no other thread is alteraing the size of the queue and returns an incorrect length, count, etc
- If one of the three methods are called other threads that call one of the other methods will have to wait until the critical part of the code executes before giving the lock back

Webserver.java:
- 

Slave.java:
- 

Main.java:
-

**Part 2 (C++ program files:**

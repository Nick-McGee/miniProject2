/* 
 * File:   main.cpp
 * Author: raghav
 *
 * Created on March 4, 2020, 2:19 p.m.
 */
#include <cstdlib>
#include <iostream>
#include <thread>
#include <mutex>
#include <condition_variable>
#include <vector>
#include <queue>
#include <chrono>

using namespace std;


int slaves, M, sim; //Variables asked by user

std::mutex cv_mtx; //mutex for condition var
std::mutex q_mtx; //mutex for queue
bool done = false;
std::condition_variable cv; //Condition variable
std::vector<std::thread> threads; //Thread vector

const int BOUND = 200; //queue/buffer bound
std::queue<int> buffer; //bounded buffer 

/**
 * Sleeps for a certain amount of time
 * @param time the length of time in either seconds or milliseconds
 * @param isSeconds specify if time is in seconds, else milliseconds
 */
void sleep(int time, bool isSeconds) {
    this_thread::sleep_for(
        isSeconds ? chrono::seconds(time) : chrono::milliseconds(time)
    );
}

/**
 * Consumes work from the buffer
 * @param id the id of the thread
 */
void consume(int id) {

    int work = -1;
    q_mtx.lock();
    {
        if(buffer.size() > 0) {
            work = buffer.front();
            buffer.pop();
       } 
    }
    q_mtx.unlock();
    
    if(work > -1) {
        cout << "Consumer " << id << " received work : " << work << endl;
        sleep(rand() % M + 1, true); //Simulate doing work      
    }
}

/**
 * Method run by each slave thread
 * @param id the id of the thread
 */
void consumer(int id) {
    while(!done || buffer.size() > 0) {
        while(0 == buffer.size() && !done) {
            std::unique_lock<std::mutex> lck(cv_mtx);
            cv.wait(lck);            
        }
        consume(id);
    }
}

/**
 * Produces work into the buffer
 * @param i the work to produce (represented by just an int)
 */
void produce(int i) {
    q_mtx.lock();
    {
        if(buffer.size() < BOUND)
            buffer.push(i);
    }
    q_mtx.unlock();
    
    cv.notify_one();
}

int main(int argc, char** argv) {
    
    /* Get user input */
    cout << "Enter slave threads count: ";
    cin >> slaves;
    cout << "Enter M (max rand seconds): ";
    cin >> M;
    cout << "Enter number of jobs to produce (max bound = 200): ";
    cin >> sim;
       
    /* Create slave threads */
    for(int i = 0; i < slaves; i++) {
        std::thread slv(consumer, i+1);
        threads.push_back(move(slv)); /* Push into vector to join later */
    }
    
    /* Simulate requests. # of requests provided by user */
    for(int i = 0; i < sim; i++) {
        cout << "Producer queueing : " << i << endl;
        produce(i+1);
        sleep(rand() % 100, false); /* Sleep between 0-100ms each produce */
    }
    
    /* All work queued */
    done = true;
    cv.notify_all(); /* Notify all threads to shut down */
    
    /* Join all threads i.e. wait for all threads to shutdown before exiting */
    for(int i = 0; i < threads.size(); i++) {
        if(threads[i].joinable())
            threads[i].join();
    }

    return 0;
}


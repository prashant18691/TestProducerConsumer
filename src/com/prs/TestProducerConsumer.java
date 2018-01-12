package com.prs;

import java.util.LinkedList;

public class TestProducerConsumer {

    public static void main(String[] args) {
        Producer p = new Producer();
        Thread t1 = new Thread(new Runnable(){
            public void run(){
                try {
                    p.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"T1");
        Thread t2 = new Thread(new Runnable(){
            public void run(){
                try {
                    p.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"T2");
        t1.start();
        t2.start();
    }
}

class Producer{
    static LinkedList<Integer> list = new LinkedList<>();
    final int capacity = 2;

    public void produce() throws InterruptedException {
        int value = 0;
        while(true) {
            synchronized (this) {
                while (capacity == list.size()) {
                        wait();
                  }
                System.out.println("Producer producing value " + value+" "+Thread.currentThread().getName());
                list.add(value++);
                notify();
                Thread.sleep(1500);
            }
        }

    }

    public void consume() throws InterruptedException {
        while(true) {
            synchronized (this) {
                while (list.size() == 0) {
                        wait();
                  }
                int value = list.removeFirst();
                System.out.println("Consumer consumed value " + value+" "+Thread.currentThread().getName());
                Thread.sleep(1500);
                notify();
            }
        }
    }

}

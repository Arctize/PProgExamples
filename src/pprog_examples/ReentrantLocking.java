package pprog_examples;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLocking {

  public static void test() {
    ReentrantLock lockA = new ReentrantLock();
    ReentrantLock lockB = new ReentrantLock();

    Thread t1 = new Thread() {
      public void run() {
        while (true) {
          if (lockA.tryLock()) {
            System.out.println("T1: step 1, lock on A");
            try {
              if (lockB.tryLock()) {
                System.out.println("T1: step 3, lock on B");
                try {
                  System.out.println("T1 done");
                  break;
                } finally {
                  lockB.unlock();
                }
              }
            } finally {
              lockA.unlock();
            }
          }
        }
      }
    };
    Thread t2 = new Thread() {
      public void run() {
        while (true) {
          if (lockB.tryLock()) {
            System.out.println("T2: step 2, lock on B");
            try {
              if (lockA.tryLock()) {
                System.out.println("T2: step 4, lock on A");
                try {
                  System.out.println("T2 done");
                  break;
                } finally {
                  lockA.unlock();
                }
              }
            } finally {
              lockB.unlock();
            }
          }
        }
      }
    };
    t1.start();
    t2.start();

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
    }
    if (t1.isAlive() && t2.isAlive()) {
      System.out.println("Both threads still alive and deadlocked.");
      System.exit(1);
    } else {
      System.out.println("Both threads exited. No deadlock.");
      System.exit(0);
    }
  }

  public static void main(String[] args) {
    test();
  }

}

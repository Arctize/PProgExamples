package pprog_examples;

public class LectureExamples {
	public static void main(String[] args) {
		floatingPointSux();
		synchronizedOverhead();
		concurrentAccess();
		deadLock();
	}

	/*
	 * Shows the overhead of making a method synchronized
	 */
	@SuppressWarnings("unused")
	private static void synchronizedOverhead() {
		Object lock = new Object();
		long tstart = System.nanoTime();
		synchronized (lock) {
			for (int i = 0; i < 1000000; i++) {
			}
		}
		;
		System.out.printf("%-30s %d\n", "Time with synchronized: ", System.nanoTime() - tstart);

		tstart = System.nanoTime();
		for (int i = 0; i < 1000000; i++) {
		}
		System.out.printf("%-30s %d\n", "Time without synchronized: ", System.nanoTime() - tstart);
	}

	/*
	 * Shows that floating point has limited precision, and associativity does not
	 * apply, e.g. (a+b)+c != a+(b+c)
	 */
	@SuppressWarnings("unused")
	static void floatingPointSux() {
		double d1 = 1.e50;
		double d2 = -1.e50;
		double d3 = 17.e00;

		double t1 = d1 + d2 + d3;
		double t2 = d1 + (d2 + d3);

		System.out.printf("\n%-20s %s\n%-20s %s \n\n", "(a + b) + c = ", t1, "a + (b + c) = ", t2);
	}

	static int num = 0;

	private static synchronized void synchronizedInc() {
		num++;
	}

	private static void nonSynchronizedInc() {
		num++;
	}

	/*
	 * Shows that addition is not an atomic operation, and concurrent access to the
	 * same variable can lead to unexpected behaviour.
	 */
	static void concurrentAccess() {
		final int iterations = 1000000;
		final int numThreads = 10;
		Thread[] threads = new Thread[numThreads];

		num = 0;
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread() {
				public void run() {
					for (int i = 0; i < iterations; i++)
						nonSynchronizedInc(); // Use the non-synchronized method to increment num
				}
			};
			threads[i].start();
		}
		for (int i = 0; i < threads.length; i++) {
			join(threads[i]);
		}
		System.out.println("\nConcurrent access using a non-synchronized method: ");
		System.out.printf(" %-30s %10d\n %-30s %10d\n %-30s %10d\n\n", "Iterations: ", numThreads * iterations,
				"Actual number: ", num, "Lost incrementations: ", numThreads * iterations - num);

		num = 0;
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread() {
				public void run() {
					for (int i = 0; i < iterations; i++)
						synchronizedInc(); // Use the synchronized method to increment num
				}
			};
			threads[i].start();
		}
		for (int i = 0; i < threads.length; i++) {
			join(threads[i]);
		}
		System.out.println("Concurrent access using a synchronized method: ");
		System.out.printf(" %-30s %10d\n %-30s %10d\n %-30s %10d\n\n", "Iterations: ", numThreads * iterations,
				"Actual number: ", num, "Lost incrementations: ", numThreads * iterations - num);

	}

	/*
	 * Creates a deadlock by starting two simultaniously running threads which first
	 * lock on A and B, and then try to lock on B and A respectively, so they end up
	 * in a state which doesn't let any of them progress further.
	 */
	@SuppressWarnings("unused")
	private static void deadLock() {
		Object lockA = new Object();
		Object lockB = new Object();
		Thread t1, t2;
		t1 = new Thread() {
			public void run() {
				synchronized (lockA) {
					System.out.println("Step 1");
					ssleep(100);
					synchronized (lockB) {
						System.out.println("Step 3");
					}
				}
			}
		};
		t2 = new Thread() {
			public void run() {
				synchronized (lockB) {
					System.out.println("Step 2");
					ssleep(100);
					synchronized (lockA) {
						System.out.println("Step 4");
					}
				}
			}
		};
		t1.start();
		t2.start();

		ssleep(1000);
		if (t1.isAlive() && t2.isAlive()) {
			System.out.println("Both threads still alive and deadlocked.");
			System.exit(1);
		} else {
			System.out.println("Both threads exited. No deadlock.");
			System.exit(0);
		}
	}

	/*
	 * Cheap method to avoid try-catch blocks
	 */
	static void join(Thread t) {
		try {
			t.join();
		} catch (InterruptedException e) {
		}
	}

	/*
	 * Cheap method to avoid try-catch blocks
	 */
	static void ssleep(long t) {
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
		}
	}
}

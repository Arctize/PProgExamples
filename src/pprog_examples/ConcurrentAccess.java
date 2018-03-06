package pprog_examples;

public class ConcurrentAccess {
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
	static void test() {
		final int iterations = 1000000;
		final int numThreads = 10;
		Thread[] threads = new Thread[numThreads];

		/*
		 * We create <numThreads> threads which simultaneously start increasing num
		 * *without* synchronization.
		 */
		num = 0;
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread() {
				public void run() {
					for (int i = 0; i < iterations; i++)
						nonSynchronizedInc();
				}
			};
			threads[i].start();
		}
		for (int i = 0; i < threads.length; i++) {
			Util.join(threads[i]);
		}
		System.out.println("\nConcurrent access using a non-synchronized method: ");
		System.out.printf(" %-30s %10d\n %-30s %10d\n %-30s %10d\n\n", "Iterations: ", numThreads * iterations,
				"Actual number: ", num, "Lost incrementations: ", numThreads * iterations - num);

		/*
		 * We create <numThreads> threads which simultaneously start increasing num
		 * *with* synchronization.
		 */
		num = 0;
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread() {
				public void run() {
					for (int i = 0; i < iterations; i++)
						synchronizedInc();
				}
			};
			threads[i].start();
		}
		for (int i = 0; i < threads.length; i++) {
			Util.join(threads[i]);
		}
		System.out.println("Concurrent access using a synchronized method: ");
		System.out.printf(" %-30s %10d\n %-30s %10d\n %-30s %10d\n\n", "Iterations: ", numThreads * iterations,
				"Actual number: ", num, "Lost incrementations: ", numThreads * iterations - num);

	}

	public static void main(String[] args) {
		test();
	}

}

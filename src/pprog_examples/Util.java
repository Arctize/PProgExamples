package pprog_examples;

public class Util {
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

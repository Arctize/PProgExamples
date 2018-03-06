package pprog_examples;

public class Deadlock {
	/*
	 * Creates a deadlock by starting two simultaniously running threads which first
	 * lock on A and B, and then try to lock on B and A respectively, so they end up
	 * in a state which doesn't let any of them progress further.
	 */
	public static void test() {
		Object lockA = new Object();
		Object lockB = new Object();
		Thread t1, t2;
		t1 = new Thread() {
			public void run() {
				synchronized (lockA) {
					System.out.println("Step 1");
					Util.ssleep(100); // Wait for thread 2 to take a lock on B
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
					Util.ssleep(100);
					synchronized (lockA) {
						System.out.println("Step 4");
					}
				}
			}
		};
		t1.start();
		t2.start();

		Util.ssleep(1000);
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

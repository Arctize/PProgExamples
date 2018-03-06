package pprog_examples;

public class OverheadOfSynchronized {

	static int num = 0;

	private static synchronized void synchronizedInc() {
		num++;
	}

	private static void inc() {
		num++;
	}

	/*
	 * Shows the overhead of a synchronized method by calling the synchronized and
	 * unsynchronized method n times each and comparing the time used.
	 */
	public static void test() {
		final int n = 1000000;

		long tstart = System.nanoTime();
		for (int i = 0; i < n; i++)
			synchronizedInc();
		System.out.printf("%-30s %10d\n", "Time with synchronized: ", System.nanoTime() - tstart);

		tstart = System.nanoTime();
		for (int i = 0; i < n; i++)
			inc();
		System.out.printf("%-30s %10d\n", "Time without synchronized: ", System.nanoTime() - tstart);
	}

	public static void main(String[] args) {
		test();
	}
}

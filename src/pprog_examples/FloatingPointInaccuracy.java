package pprog_examples;

public class FloatingPointInaccuracy {

	/*
	 * Shows that floating point has limited precision, and associativity does not
	 * apply, e.g. (a+b)+c != a+(b+c)
	 */
	public static void test() {
		double d1 = 1.e50;
		double d2 = -1.e50;
		double d3 = 17.e00;

		double t1 = d1 + d2 + d3;
		double t2 = d1 + (d2 + d3);

		System.out.printf("%-20s %s\n%-20s %s\n", "(a + b) + c = ", t1, "a + (b + c) = ", t2);
	}
	
	public static void main(String[] args) {
		test();
	}
}

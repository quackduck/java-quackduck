package base;

public class Func {

	public static void main(String[] args) {

	}
	
	public static double quadratic(double a, double b, double c) {
		double root1 = ((b*-1) + Math.sqrt((b*b) - 4*a*c))/(2*a);
		double root2 = ((b*-1) - Math.sqrt((b*b) - 4*a*c))/(2*a);
		return Math.max(root1, root2);
	}

}

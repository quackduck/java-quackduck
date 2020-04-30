package base;

public class Func {

	public static void main(String[] args) {
		quadratic(6, 0, 0);
	}
	
	public static void quadratic(double a, double b, double c) {
		double discriminant = Math.sqrt((b * b) - 4 * a * c);
		double root1 = ((b*-1) + discriminant)/(2*a);
		double root2 = ((b*-1) - discriminant)/(2*a);
		System.out.println(root1 + ", " + root2);
	}

}

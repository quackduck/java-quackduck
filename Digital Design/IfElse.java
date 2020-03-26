import java.util.Scanner;
public class IfElse {

	public static void main(String[] args) {

		new IfElse().BabySitter();
	}

	public void NegativePositive() {
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter a number: ");
		double num = scan.nextDouble();
		scan.close();
		if (num > 0) {
			System.out.println("Its Positive");
		} else if (num < 0) {
			System.out.println("Its Negative");
		} else {
			System.out.println("It's Zero");
		}
	}

	public void OddEven() {
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter a number: ");
		int num = scan.nextInt();
		scan.close();
		if (num % 2 == 0) {
			System.out.println("Its Even");
		} else {
			System.out.println("It's Odd");
		}
	}
	
	public void BabySitter() {
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter a number: ");
		int num = scan.nextInt();
		scan.close();
		if (num > 35) {
			System.out.println("Too Old");
		} else if (num > 18) {
			System.out.println("Perfect");
		} else {
			System.out.println("Too Young");
		}
	}

}

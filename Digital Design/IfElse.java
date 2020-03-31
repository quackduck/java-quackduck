import java.util.Scanner;
public class IfElse {

	public static void main(String[] args) {

		new IfElse().DiscountStuff();
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

	public void DiscountStuff() {
		Scanner scan = new Scanner(System.in);
		System.out.print("How many tickets:  ");
		int num = scan.nextInt();
		System.out.print("How old are you:  ");
		int age = scan.nextInt();
		int tc = num*300;
		scan.close();
		int discount = 0;
		if (num > 5 && age < 20) {
			discount = 20;
		} else if (num > 3 && num < 5 && age >= 20 && age <= 50) {
			discount = 10;
		} else if (num == 2 && age > 50) {
			discount = 5;
		} else if (num == 1) {
			discount = 3;
		} else {
			if (age <= 0) {
				System.out.println("Seriously?");
				System.exit(0);
			} else {
				discount = 0;
			}
		}

		if (discount > 0) {
			System.out.println("You get a discount of " + discount + " percent!");
			System.out.println("The price you need to pay is " + (tc - (((double)tc)*(((double) discount)/100))));
		} else {
			System.out.println("The price you need to pay is " + tc);
		}

	}

}

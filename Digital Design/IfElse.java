import java.util.Scanner;
public class IfElse {

	public static void main(String[] args) {

		new IfElse().monthStuff();
	}

	public void monthStuff() { 

		Scanner scan = new Scanner(System.in); 

		while (true) { 

			int monthnum = scan.nextInt(); 

			if (monthnum>12 || monthnum<1) { 

				System.out.println("Enter another Number"); 

			} else { 

				break; 

			} 

		} 

	} 

	public void ageName() {
		Scanner scan = new Scanner(System.in);
		String name = scan.nextLine();
		int age = scan.nextInt();
		scan.close();
		for (int i = 1; i <= age; i++) {
			System.out.println(name);
		}
	}

	public void factorial(long x) {
		for (long i = (x-1) ; i > 0; i--) {
			x *= i;
		}
		System.out.println(x);
	}

	public void hellohello() {
		for (int i = 1; i <= 50 ; i++) {
			System.out.print("hello");
		}
	}

	public void OddNums() {
		for (int i = 1; i < 40 ; i+=2) {
			System.out.println(i);
		}
	}

	public void EvenNums() {
		for (int i = 2; i <= 20 ; i+=2) {
			System.out.println(i);
		}
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

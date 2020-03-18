import java.util.Scanner;

public class ClockAngle {
	
	static double Min = 30;
	static double Hour = 6;
	static double MinAngle;
	static double HourAngle;
	static double angle;
	static String input;
	public static void main(String[] args) {
		Scanner scannerIn = new Scanner(System.in);
		while (true) {
			System.out.println("Enter time (eg. 6:30). Type exit to exit");
			input = scannerIn.nextLine();
			if (input.equals("exit")) {
				scannerIn.close();
				break;
			}
			Hour = parser(input, 0);
			Min = parser(input, 1);
			MinAngle = 360*(reduce(Min, "m")/60);
			HourAngle = 360*(reduce(Hour, "h")/12);
			HourAngle += 30*(reduce(Min, "m")/60);
			angle = Math.abs(HourAngle - MinAngle);
			System.out.print("The angle formed by the minute and hour hands is ");
			if (angle > 180) {
				angle = 360 - angle;
			}
			System.out.print(angle);
			System.out.print(" degrees");
			System.out.println("");
		}
		
		
	}
	
	public static double reduce(double x, String options) {
		if (x >= 12 && options.equals("h")) {
			x -= 12;
			x = reduce(x, options);
		}
		if (x >= 60 && options.equals("m")) {
			x -= 60;
			Hour++;
			x = reduce(x, options);
		}
		
		if (x < 0 && options.equals("h")) {
			x += 12;
			x = reduce(x, options);
		}
		if (x < 0 && options.equals("m")) {
			x += 60;
			x = reduce(x, options);
		}
		
		return x;
	}
	
	public static double parser(String x, int index) {
		String[] arrStr = x.split(":");
		return Double.parseDouble(arrStr[index]);
	}

}

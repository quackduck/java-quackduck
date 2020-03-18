import java.util.Scanner;


public class ReverseString {

	private static String strNewString;
	
	public static void main(String[] args) {
		System.out.println("Type exit to exit or enter input");
		Scanner scannerIn = new Scanner(System.in);
        while (true) {
        	strNewString = scannerIn.nextLine();
        	if (strNewString.equals("exit")) {
        		scannerIn.close();
        		break;
        	} else {
            	System.out.println(strReverse(strNewString));
        	}
        }
	}
	
	public static String strReverse (String x) {
		String strResult = "";
		for (int i = x.length() - 1; i >= 0; i--) {
			strResult += x.charAt(i);
		}
		return strResult;
	}
}



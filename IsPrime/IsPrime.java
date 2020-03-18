import java.util.ArrayList;
import base.ReadWrite;
public class IsPrime {
	
	public static int till = 1000;
	public static ArrayList<String> list = new ArrayList<String>();
	public static String result = "";
	
	public static void main(String[] args) {
		for (int i = 2; i<10000000; i++ ) {
			if (isPrime(i)) {
				list.add(Integer.toString(i));
			}
			
			if (list.size() >= till) {
				break;
			}
		}
		for (int i = 1; i<=till; i++) {
			result += list.get(i-1) + " ";
			if (i % 10 == 0) {
				result += System.lineSeparator();
			}
		}
		
		
		ReadWrite readwrite = new ReadWrite();
		readwrite.setPath(System.getProperty("user.dir") + "/First" + till + "Primes.txt");
		readwrite.setContent(result);
		readwrite.create();
	}
	public static Boolean isPrime(int x) {
		boolean result = true;
		for (int i = 2; i*i<=x; i++) {
			if (x%i == 0) {
				result = false;
				break;
			}
		}
		return result;
	}
}

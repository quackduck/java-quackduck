import java.util.Random;


public class RandomBits {
	
	static int numOfChars = 800000000;

	public static void main(String[] args) {
		Random bitmaker = new Random();
		int bit = 0;
		for (int i = 0; i < numOfChars; i++) {
			for (int ii = 0; ii < 8; ii++) {
				if (bitmaker.nextBoolean()) {
					bit = 1;
				} else {
					bit = 0;
				}
				
				System.out.print(bit);
				try {
					Thread.sleep(0, 1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			System.out.print("");
		}

	}

}

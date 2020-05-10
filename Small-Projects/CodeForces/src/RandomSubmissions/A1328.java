package RandomSubmissions;

import java.util.Scanner;

public class A1328 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int numberOfCases = scan.nextInt();
        int a = 0;
        int b = 0;
        for (int i = 0; i < numberOfCases; i++) {
            a = scan.nextInt();
            b = scan.nextInt();
            int result = 0;
            if (a % b != 0) {
                result = b - (a % b);
            }
            System.out.println(result);
        }
    }
}


// a + b - remainder /b


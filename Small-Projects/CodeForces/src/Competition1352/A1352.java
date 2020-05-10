package Competition1352; // https://codeforces.com/contests/1352
import java.util.regex.*;
import java.util.Scanner;
public class A1352 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int xduhu = scanner.nextInt();
        for (int i = 0; i < xduhu; i++) {
            int num = scanner.nextInt();
            String strnum = Integer.toString(num);
            Matcher matcher = Pattern.compile("[1-9]").matcher(strnum);
            long matches = matcher.results().count();
            System.out.println(matches);
            for (int ii = strnum.length(); ii > 0; ii--) {
                StringBuilder zeros = new StringBuilder();
                zeros.append("0".repeat(ii - 1));
                char c = strnum.charAt(strnum.length() - ii);
                if (c != '0') {
                    System.out.print(c + zeros.toString() + " ");
                }
            }
            System.out.println();
        }
    }
}

package functions;

public class Divisors {

    public static int gcd(int a, int b) {
        if (a == 0 && b == 0) {return 0;}
        int num1 = Math.abs(a);
        int num2 = Math.abs(b);
        int buffer = 0;
        while (num2 != 0) {
            buffer = num2;
            num2 = num1 % num2;
            num1 = buffer;
        }
        return num1;
    }

    // Returns the least common multiple of a and b.
    public static int lcm(int a, int b) {
        if (a == 0 || b == 0) return 0;
        return Math.abs(a) * (Math.abs(b) / gcd(a, b));
    }

    // Returns true if a and b are relatively prime; false otherwise.
    public static boolean areRelativelyPrime(int a, int b) {
        return gcd(a, b) == 1;
    }

    // Returns the number of integers between 1 and n that are
    // relatively prime with n.
    public static int totient(int n) {
        int result = 0;
        if (n == 1) {return 1;}
        for (int i = 1; i < n; i++) {
            if (areRelativelyPrime(i, n)) result++;
        }
        return result;
    }


    public static void main(String[] args) {
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        System.out.println("gcd(" + a + ", " + b + ") = " + gcd(a, b));
        System.out.println("lcm(" + a + ", " + b + ") = " + lcm(a, b));
        System.out.println("areRelativelyPrime(" + a + ", " + b + ") = " + areRelativelyPrime(a, b));
        System.out.println("totient(" + a + ") = " + totient(a));
        System.out.println("totient(" + b + ") = " + totient(b));
    }
}

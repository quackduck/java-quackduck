import java.util.Scanner;

public class GeometricSequences {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the three first terms of the geometric sequence");
        while (true) {
            System.out.println(solveGeometricSequence(scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble()));
        }
    }

    public static boolean isGeometricSequence(double term1, double term2, double term3){
        return (term2/term1) == (term3/term2);
    }

    public static String solveGeometricSequence(double term1, double term2, double term3){
        if (isGeometricSequence(term1, term2, term3)) {
            double commonRatio = term2/term1;
            if (term1 != 1) {
                return term1 + "*(" + commonRatio + "^(n-1))";
            } else {return commonRatio + "^(n-1)";}
        } else {return "Not a geometric sequence";}

    }

}

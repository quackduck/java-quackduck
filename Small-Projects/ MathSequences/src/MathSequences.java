public class MathSequences {
    public static void main (String[] args) {
        if (args.length > 3) {
            System.out.println(solveQuadraticSequence(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3])));
        } else if (args.length == 3) {
            System.out.println(solveQuadraticSequence(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2])));
        }
    }

    public static Boolean isLinearSequence(double term1, double term2, double term3) {
        return (term3 - term2) == (term2 - term1);
    }

    public static Boolean isQuadraticSequence(double term1, double term2, double term3, double term4) {
        return isLinearSequence(term2 - term1, term3 - term2, term4 - term3);
    }

    public static String solveQuadraticSequence(double term1, double term2, double term3, double term4) {
        if (isQuadraticSequence(term1, term2, term3, term4)) {
            return solveQuadraticSequence(term1, term2, term3);
        }
        return "Not a sequence";
    }

    public static String solveQuadraticSequence(double term1, double term2, double term3) {
        double a = ((term3 - term2) - (term2 - term1)) / 2;
        term1 -= a;
        term2 -= 4*a;
        term3 -= 9*a;
        return  a + "n² + " + solveLinearSequence(term1, term2, term3);
    }

    public static String solveLinearSequence(double term1, double term2, double term3) {
        if (isLinearSequence(term1, term2, term3)) {
            double firstCoefficient = term2 - term1;
            double constant = term1 - firstCoefficient;
            return firstCoefficient + "n + " + constant;
        }
        return "Not a sequence";
    }
}

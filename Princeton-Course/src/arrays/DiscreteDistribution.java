package arrays;

public class DiscreteDistribution {
    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        int[] a = new int[args.length];
        int[] cumulativeSums = new int[args.length];
        for (int i = 1; i < args.length; i++) {
            a[i] = Integer.parseInt(args[i]);
        }

        for (int i = 1; i < a.length; i++) {
            cumulativeSums[i] = a[i] + cumulativeSums[i-1];
        }

        int number = 0;
        for (int i = 0; i < m; i++) { // m is the number of numbers to print
            number = (int) (Math.random() * cumulativeSums[cumulativeSums.length - 1]);
            for (int j = 1; j < cumulativeSums.length; j++) {
                if (number < cumulativeSums[j] && number >= cumulativeSums[j-1]) {
                    System.out.print(j + "\t");
                    break;
                }
            }
        }
        System.out.println();
    }
}

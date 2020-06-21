package arrays;

public class ThueMorse {
    //https://coursera.cs.princeton.edu/introcs/assignments/arrays/specification.php
    public static void main(String[] args) {
        int squareSideLength = Integer.parseInt(args[0]);
        int[] sequence = new int[squareSideLength * squareSideLength];
        for (int i = 1; i < sequence.length; i++) {
//            if (i==0) {
//                sequence[i] = 0; // just make the thing start at 1 so we take advantage of Java's default initialization to 0
//            }
            if (i%2==0) {
                sequence[i] = sequence[i/2];
            } else {
                sequence[i] = 1 - sequence[i-1];
            }
        }
        for (int i = 0; i < squareSideLength; i++) {
            for (int j = 0; j < squareSideLength; j++) {
                System.out.print((sequence[(i*squareSideLength)+j] == 0 ? "+" : "-") + "  ");
            }
            System.out.println();
        }
    }
}

package arrays;

public class ThueMorse {
    public static void main(String[] args) {
        int squareSideLength = Integer.parseInt(args[0]);
        int[] sequence = new int[squareSideLength * squareSideLength];
        for (int i = 1; i < sequence.length; i++) {
            if (i%2==0) {
                sequence[i] = sequence[i/2];
            } else {
                sequence[i] = 1 - sequence[i-1];
            }
        }
        for (int i = 0; i < squareSideLength; i++) {
            for (int j = 0; j < squareSideLength; j++) {
                System.out.print(((sequence[i] - sequence[j]) == 0 ? "+" : "-") + "  ");
            }
            System.out.println();
        }
    }
}

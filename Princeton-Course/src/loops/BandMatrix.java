package loops;

public class BandMatrix {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[1]);
        int width = Integer.parseInt(args[0]);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                if (Math.abs(i-j) <= n) {
                    System.out.print("*  ");
                } else {
                    System.out.print("0  ");
                }
            }
            System.out.println();
        }
    }
}

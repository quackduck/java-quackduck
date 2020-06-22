package arrays;

public class Minesweeper {
    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]); //x
        int n = Integer.parseInt(args[1]); //y
        int k = Integer.parseInt(args[2]);
        boolean[][] hasMine = new boolean[m+2][n+2];
        //int[][] numOfSurroundingMines = new int[m][n];
        int x = 0;
        int y = 0;
        if (k > (m*n) ) {
            k = m * n;
        }
        for (int i = 0; i < k; i++) {
            x = (int) (Math.random() * m);
            y = (int) (Math.random() * n);
            if (!hasMine[x+1][y+1]) {
                hasMine[x+1][y+1] = true;
            } else {
                i--;
            }
        }
        int minesSurrounding = 0;
        for (int n2 = 1; n2 <= n; n2++) {
            for (int m2 = 1; m2 <= m; m2++) {
                minesSurrounding = 0;
                if (hasMine[m2][n2]) {
                    System.out.print("*  ");
                } else {
                    if (hasMine[m2-1][n2-1]) {
                        minesSurrounding++;
                    }
                    if (hasMine[m2+1][n2+1]) {
                        minesSurrounding++;
                    }
                    if (hasMine[m2+1][n2-1]) {
                        minesSurrounding++;
                    }
                    if (hasMine[m2-1][n2+1]) {
                        minesSurrounding++;
                    }
                    if (hasMine[m2-1][n2]) {
                        minesSurrounding++;
                    }
                    if (hasMine[m2+1][n2]) {
                        minesSurrounding++;
                    }
                    if (hasMine[m2][n2-1]) {
                        minesSurrounding++;
                    }
                    if (hasMine[m2][n2+1]) {
                        minesSurrounding++;
                    }

                    System.out.print(minesSurrounding + "  ");
                }
            }
            System.out.println();
        }
    }
}

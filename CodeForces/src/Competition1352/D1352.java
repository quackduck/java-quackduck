package Competition1352; // https://codeforces.com/contests/1352
import java.util.*;
public class D1352 {
    static int sizeOfAlice = 0;
    static int sizeOfBob = 0;
    static int aliceLatestMove = 0;
    static int bobLatestMove = 0;
    static int numOfMoves = 0;
    static ArrayList<Integer> list = new ArrayList<Integer>();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int xduhu = scanner.nextInt();
        for (int i = 0; i < xduhu; i++) { // for every test case
            int x = scanner.nextInt();
            for (int j = 0; j < x; j++) { // for every element in the arraylist
                list.add(scanner.nextInt());
            }
            while (list.size() != 0) {
                aliceLatestMove = 0;
                    do {
                        sizeOfAlice += list.get(0);
                        aliceLatestMove += list.get(0);
                        list.remove(0);
                        if (list.size() == 0) {
                            break;
                        }
                    } while (bobLatestMove >= aliceLatestMove);
                numOfMoves++;
                bobLatestMove = 0;
                if (list.size() == 0) {break;}
                do {
                    sizeOfBob += list.get(list.size() - 1);
                    bobLatestMove += list.get(list.size() - 1);
                    list.remove(list.size() - 1);
                    if (list.size() == 0) {
                        break;
                    }
                }
                while (aliceLatestMove >= bobLatestMove);
                numOfMoves++;
            }
            System.out.println(numOfMoves + " " + sizeOfAlice + " " + sizeOfBob);
            sizeOfAlice = 0;
            sizeOfBob = 0;
            numOfMoves = 0;
            aliceLatestMove = 0;
            bobLatestMove = 0;
        }
    }
}
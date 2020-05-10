package loops;

public class RandomWalker {
    public static void main(String[] args) {
        int x = 0;
        int y = 0;
        int steps = 0;
        int r = Integer.parseInt(args[0]);
        System.out.println("(0, 0)");
        for (steps = 0; Math.abs(x) + Math.abs(y) != r; steps++) {
            if (Math.random() < 0.5) {
                x += Math.random() < 0.5 ? 1 : -1;
            } else {
                y += Math.random() < 0.5 ? 1 : -1;
            }
            System.out.println("(" + x + ", " + y + ")");
        }
        System.out.println("steps = " + steps);
    }
}

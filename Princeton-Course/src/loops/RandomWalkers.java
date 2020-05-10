package loops;

public class RandomWalkers {
    public static void main(String[] args) {
        int intX = 0;
        int intY = 0;
        int intSteps = 0;
        int intR = Integer.parseInt(args[0]);
        int intTrials = Integer.parseInt(args[1]);
        long longSumOfSteps = 0;
        for (int i = 0; i < intTrials; i++) {
            intX = 0;
            intY = 0;
            for (intSteps = 0; Math.abs(intX) + Math.abs(intY) != intR; intSteps++) {
                if (Math.random() < 0.5) {
                    intX += Math.random() < 0.5 ? 1 : -1;
                } else {
                    intY += Math.random() < 0.5 ? 1 : -1;
                }
            }
            longSumOfSteps += intSteps;
        }
        System.out.println((double) longSumOfSteps /(double) intTrials);
    }
}


package arrays;

public class Birthday {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        int[] stats = new int[n+2];
        boolean[] room = new boolean[n];
        int[] cumulativeSums = new int[stats.length];
        int person = 0;
        int numOfPeopleInRoom = 0;
        boolean roomHasDuplicateBday = false;
        for (int i = 0; i < trials; i++) {
            for (int j = 0; j < room.length; j++) {
                room[j] = false;
            }
            roomHasDuplicateBday = false;
            person = (int) (Math.random() * n);
            room[person] = true;
            numOfPeopleInRoom = 1;
            while (!roomHasDuplicateBday) {
                person = (int) (Math.random() * n);
                if (room[person]) {
                    roomHasDuplicateBday = true;
                }
                room[person] = true;
                numOfPeopleInRoom++;
            }
            stats[numOfPeopleInRoom]++;
        }

        for (int i = 1; i < stats.length; i++) {
            cumulativeSums[i] = stats[i] + cumulativeSums[i-1];
        }

        for (int i = 1; i < stats.length; i++) {
            System.out.println(i + "\t" + stats[i] + "\t" + (double)cumulativeSums[i]/(double)trials);
            if ((double)cumulativeSums[i]/(double)trials > 0.5) {
                break;
            }
        }
    }
}

package functions;


public class AudioCollage {

    // Returns a new array that rescales a[] by a multiplicative factor of alpha.
    public static double[] amplify(double[] a, double alpha) {
        double[] result = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] * alpha;
        }
        return result;
    }

    // Returns a new array that is the reverse of a[].
    public static double[] reverse(double[] a) {
        double[] result = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[(a.length-1) - i];
        }
        return result;
    }

    // Returns a new array that is the concatenation of a[] and b[].
    public static double[] merge(double[] a, double[] b) {
        double[] result = new double[a.length + b.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i];
        }
        for (int i = 0; i < b.length; i++) {
            result[i + a.length] = b[i];
        }
        return result;
    }

    // Returns a new array that is the sum of a[] and b[],
    // padding the shorter arrays with trailing 0s if necessary.
    public static double[] mix(double[] a, double[] b) {
        double[] result;
        if (a.length > b.length) {
            result = new double[a.length];
        } else {
            result = new double[b.length];
        }
        for (int i = 0; i < result.length; i++) {
            if (i > (a.length - 1)) {
                result[i] = b[i];
            } else if (i > (b.length - 1)) {
                result[i] = a[i];
            } else {
                result[i] = a[i] + b[i];
            }
        }
        return result;
    }

    // Returns a new array that changes the speed by the given factor.
    public static double[] changeSpeed(double[] a, double alpha) {
        double[] result = new double[(int) (a.length/alpha)];
        for (int i = 0; i < result.length; i++) {
            result[i] = a[(int) (i * alpha)];
        }
        return result;
    }

    private static double[] mirror (double[] a) {
        return merge(a, reverse(a));
    }

    // Creates an audio collage and plays it on standard audio.
    // See below for the requirements.
    public static void main(String[] args) {
        double[] disclaimer = StdAudio.read("exposure.wav");
        double[] scratch = StdAudio.read("scratch.wav");
        double[] piano = StdAudio.read("piano.wav");
        double[] singer = StdAudio.read("singer.wav");
        double[] result = merge(changeSpeed(disclaimer, 2), merge(reverse(scratch), merge(amplify(piano, 2), mix(reverse(disclaimer), reverse(singer)))));
        result = mirror(result);
        StdAudio.play(result);
    }
}

public class Harmonic {
	public static boolean showProgress = false;
	public static long x = 1000;
	public static double revx = 0;

	public static void main(String[] args) {
		
		if (args.length>0) { x = Long.parseLong(args[0]);}
		if (args.length>1) { revx = Long.parseLong(args[1]);}
		if (args.length>2) { showProgress = Boolean.parseBoolean(args[2]);}
		
		long start1 = System.currentTimeMillis();
		System.out.print("Harmonic: ");
		System.out.print(harmonic(x));
		System.out.println("");
		long end1 = System.currentTimeMillis();
		System.out.print("Inverse Harmonic: ");
		System.out.print(revharmonic(revx));
		System.out.println("");
		long end2 = System.currentTimeMillis();
		System.out.print("Harmonic took ");
		System.out.print(end1-start1);
		System.out.print(" milliseconds");
		System.out.println("");
		System.out.print("RevHarmonic took ");
		System.out.print(end2-end1);
		System.out.print(" milliseconds");
		System.out.println("");
	}

	public static double harmonic(long n) {
		double result = 0.0;
		for (long i = 1; i <= n; i++) {
			result += (double)1/i;
			if (showProgress && n>=100000000 && i % (Math.round((double)n/100)) == 0) {
				System.out.println(i/(Math.round((double)(n/100))));
				System.out.println(result);
			}
			
		}

		return result;
	}

	public static long revharmonic(double x) {
		double harmonicresult = 0.0;
		long i = 0;
		while (harmonicresult < x) {
			i++;
			harmonicresult += (double)1 / i;
		}
		return i;

	}

}

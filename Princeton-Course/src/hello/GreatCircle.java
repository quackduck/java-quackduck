package hello;

public class GreatCircle {
    public static void main(String[] args) {
        double x1 = Double.parseDouble(args[0]);
        double y1 = Double.parseDouble(args[1]);
        double x2 = Double.parseDouble(args[2]);
        double y2 = Double.parseDouble(args[3]);
        x1 = Math.toRadians(x1);
        x2 = Math.toRadians(x2);
        y1 = Math.toRadians(y1);
        y2 = Math.toRadians(y2);
        double term1 = Math.pow(Math.sin((y2 - y1) / 2.0), 2.0);
        double term2 = Math.pow(Math.sin((x2 - x1) / 2.0), 2.0);
        term1 = Math.cos(x1) * Math.cos(x2) * term1;
        double TheRoot = term1 + term2;
        TheRoot = Math.sqrt(TheRoot);
        double result = Math.asin(TheRoot) * 2.0 * 6371.0;
        System.out.println(result + " kilometers");
    }
}

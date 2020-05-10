public class CMYKtoRGB {
    public static void main(String[] args) {
        double white = 1 - Double.parseDouble(args[3]);
        double red = 255 * white * (1 - Double.parseDouble(args[0]));
        double green = 255 * white * (1 - Double.parseDouble(args[1]));
        double blue = 255 * white * (1 - Double.parseDouble(args[2]));
        System.out.println("red   = " + Math.round(red));
        System.out.println("green = " + Math.round(green));
        System.out.println("blue  = " + Math.round(blue));
    }
}

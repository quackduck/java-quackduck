package hello;

public class RightTriangle {
    public static void main(String[] args) {
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        int c = Integer.parseInt(args[2]);
        System.out.println((((a * a) == ((b * b) + (c * c))) || ((b * b) == ((a * a) + (c * c))) || ((c * c) == ((b * b) + (a * a)))) && ((a >= 0) && (b >= 0) && (c >= 0)));
    }
}

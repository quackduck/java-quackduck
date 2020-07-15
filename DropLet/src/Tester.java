public class Tester {
    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        Thread receiverThread = new Thread(receiver::go);
        receiverThread.start();
        Sender sender = new Sender("127.0.0.1", receiver.code, "/Users/ishan/Desktop/How.To.Train.Your.Dragon.The.Hidden.World.2019.1080p.BluRay.x264-[YTS.AM].mp4");
        sender.go();
    }
}
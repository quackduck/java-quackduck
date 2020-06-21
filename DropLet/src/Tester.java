public class Tester {
    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        Thread receiverThread = new Thread(receiver::go);
        receiverThread.start();
        Sender sender = new Sender("127.0.0.1", receiver.code, "/Users/ishan/Desktop/video.mov");
        sender.go();
//        try {
//            while (receiverThread.isAlive() || receiverThread.isAlive()) {
//                receiverThread.wait(10);
//                receiverThread.wait(10);
//            }
//        }catch (Exception e) {e.printStackTrace();}
    }
}
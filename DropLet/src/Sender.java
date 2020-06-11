import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Sender {

    public Socket senderSock = null;
    public OutputStream writer = null;
    public Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        new Sender().go();
    }

    public void go () {
        try {
            System.out.println("Enter the full path of the file you want to send");
            File theFileToSend = new File(in.nextLine());
            Scanner fileScanner;
            System.out.println("Put in the IP please:");
            String ip = in.nextLine();
            System.out.println("Put in the Code please:");
            senderSock = new Socket(ip, in.nextInt());
            OutputStream sendFile = senderSock.getOutputStream();
//          fileScanner = new Scanner(TheFileToSend);
            FileInputStream reader = new FileInputStream(theFileToSend);
            BufferedInputStream fileReader = new BufferedInputStream(reader);
            byte[] buffer = new byte[1024];
            while (reader.read(buffer) > 0) {
                sendFile.write(buffer);
            }
//            fileScanner.useDelimiter("");
//            writer.println(TheFileToSend.getName());
//            writer.flush();
//            String message;
//            while (fileScanner.hasNext()) {
//                message = fileScanner.next();
//                System.out.println(message);
//                writer.write(message);
//                writer.flush();
//            }
            System.out.println("Boom! File sent :)");
        } catch (Exception e) {e.printStackTrace();}
    }
}

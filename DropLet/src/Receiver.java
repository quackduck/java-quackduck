import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Receiver {

	ServerSocket serverSock = null;
	Socket senderSock = null;
	InputStream senderInput = null;

	public static void main(String[] args) {
		new Receiver().go();
	}

	public void go() {
		try {
			Scanner in = new Scanner(System.in);
			serverSock = new ServerSocket(0);
			System.out.println("Okie so share this code here: " + serverSock.getLocalPort());
			System.out.println("The IP is " + (InetAddress.getLocalHost().getHostAddress()));
			System.out.println("What would like the filename to be?");
			String path = System.getProperty("user.home") + "/Downloads/" + in.nextLine();
			senderSock = serverSock.accept();
			senderInput = senderSock.getInputStream();
			File file = new File(path);
			int i;
			String[] temp = path.split("\\.", 2);
			for (i = 2 ; !file.createNewFile(); i++) {
				file = new File(temp[0] + " " + i + "." + temp[1]);
			}
//			FileWriter filewriter = new FileWriter(path, true);
//			int characterSent;
//			while (!senderSock.isClosed() && (characterSent = senderInput.read()) != -1) {
//				filewriter.write(characterSent);
//				filewriter.flush();
//			}
			FileOutputStream fileStream = new FileOutputStream(file, true);
			byte[] buffer = new byte[1024];
			while (senderInput.read(buffer) > 0) {
				fileStream.write(buffer);
			}

			System.out.println("File saved to Downloads :)");
//			filewriter.close();
		} catch (Exception e) {
			System.out.println("What in the world? I'm a red-shirt so you know what's gonna happen to me");
			System.exit(42);
		} ///Users/ishan/Desktop/video.mov
	}
}

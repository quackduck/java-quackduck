import javax.swing.plaf.TableHeaderUI;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;

public class Receiver {

	ServerSocket serverSock = null;
	Socket senderSock = null;
	InputStream senderReader = null;
	FileOutputStream fileWriter = null;
	File file = null;
	HashMap<String, String> metadata = new HashMap<>();

	public static void main(String[] args) {
		new Receiver().go();
	}

	public void go() {
		try {
			Scanner in = new Scanner(System.in);
			serverSock = new ServerSocket(0);
			System.out.println("Okie so share this code here: " + serverSock.getLocalPort());
			System.out.println("The IP is " + (InetAddress.getLocalHost().getHostAddress()));
			System.out.println("What would you like the filename to be? WARNING! IF THIS FILE EXISTS ALREADY, IT WILL BE OVERWRITTEN.");
			file = new File(System.getProperty("user.home") + "/Downloads/" + in.nextLine());
			fileWriter = new FileOutputStream(file, true);
			senderSock = serverSock.accept();
			senderReader = senderSock.getInputStream();
			getMetadata();
			getFile();
			System.out.println("File saved to Downloads :)");
			close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("What in the world? I'm a red-shirt so you know what's gonna happen to me");
			System.exit(42);
		}
	}

	public void getFile () {
		long fileSizeInBytes = Long.parseLong(metadata.get("FileSizeInBytes"));
		long numOfBytesReceived = 0L;
		try {
			byte[] buffer = new byte[1024]; ///Users/ishan/Desktop/video.mov
			int count;
			System.out.println();
			long beforeTime = System.currentTimeMillis();
			//int i = 0;
			//int itersBeforeProgressUpdate = 10;
			//double afterTime = 0.0;
			double donePercent = 0;
			while ((count = senderReader.read(buffer)) > 0) {
				fileWriter.write(buffer, 0, count);
				numOfBytesReceived += count;//
				donePercent = 100*((double)numOfBytesReceived/(double)fileSizeInBytes);
//				if (i % itersBeforeProgressUpdate == 0) {
					progressPercentage(numOfBytesReceived, fileSizeInBytes, (100.0 - donePercent) * ((System.currentTimeMillis() - beforeTime)/donePercent));
//					beforeTime = System.currentTimeMillis();
//				}
//				if (numOfBytesReceived == fileSizeInBytes) {
//					progressPercentage(numOfBytesReceived, fileSizeInBytes, 0);
//				}
//				i++;
			}
			fileWriter.flush();
		} catch (Exception e) {e.printStackTrace();}
	}

	public static void progressPercentage(long done, long total, double estimatedTimeInSeconds) {
		int size = 20;
		String iconLeftBoundary = "[";
		String iconDone = "=";
		String iconRemain = " ";
		String iconRightBoundary = "]";
		if (done > total) {
			throw new IllegalArgumentException();
		}
		int donePercents = (int) (100*(done/total));
		int doneLength = size * donePercents / 100;
		StringBuilder bar = new StringBuilder(iconLeftBoundary);
		for (int i = 0; i < size; i++) {
			if (i < doneLength) {
				bar.append(iconDone);
			} else {
				bar.append(iconRemain);
			}
		}
		bar.append(iconRightBoundary);

		System.out.print("\r" + bar + " " + donePercents + "% (" + done + "/" + total + " B) " + Math.round(10*estimatedTimeInSeconds)/10 + "s left");

		if (done == total) {
			System.out.print("\n");
		}
	}

	public void close (){
		try {
			fileWriter.close();
			senderReader.close();
			senderSock.close();
		} catch (Exception e) {e.printStackTrace();}
	}

	public void getMetadata () {
		try {
			byte[] buffer = new byte[1];
			StringBuilder metadataUnparsed = new StringBuilder();
			String character = "";
			while (senderReader.read(buffer) > 0) {
				if (!(character = new String(buffer, StandardCharsets.UTF_8)).equals("/")) {
					metadataUnparsed.append(character);
				} else {
					break;
				}
			}
			String[] halfParsed = metadataUnparsed.toString().split(",");
			String[] parsed;
			for (String s : halfParsed) {
				parsed = s.split("=");
				metadata.put(parsed[0], parsed[1]);
			}
		} catch (Exception e) {e.printStackTrace();}
	}
}

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Teacher {

	ArrayList<PrintWriter> clientOutputStreams;
	int ServerID = 0;
	public ArrayList<String> names = new ArrayList<>();
	public String subject = "";

	public class ClientHandler implements Runnable {
		public BufferedReader reader;
		public PrintWriter writer;
		public Socket sock;
		public String name;

		public ClientHandler(Socket clientSocket, PrintWriter printWriter) {
			
			try {
				sock = clientSocket;
				InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
				reader = new BufferedReader(isReader);
				writer = printWriter;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		public String format() {
			if (names.size() == 1) {
				return "You are the only person chatting";
			}
			String formatted = "";
			for (int i = 0; i < names.size(); i++) {
				if (i == (names.size() - 1)) {
					formatted += " you";
				} else if (i == (names.size() - 2)) {
					formatted += names.get(i) + " and";
				} else {
					formatted += names.get(i) + ", ";
				}
			}
			return formatted + " are chatting";
		}

		public void run() {
			try {
				name = reader.readLine();
			} catch (IOException e) {
				if(!sock.isClosed()) {
					e.printStackTrace();// the connection reset error happens when the student leaves and the socket disconnects so we check if the socket is even open.
				}
			}
			names.add(name);
			writer.println(format());
			String message;
			try {
				while (!sock.isClosed() && (message = reader.readLine()) != null) {
					tellEveryone(message);
				}
				names.remove(name);
				clientOutputStreams.remove(writer);
			} catch (Exception ex) {
				if(!sock.isClosed()) {
					ex.printStackTrace();// the connection reset error happens when the student leaves and the socket disconnects so we check if the socket is even open.
				}
			}
		}
	}

	public class StudentRunner implements Runnable {
		@Override
		public void run() { new Student(ServerID, "127.0.0.1").go(); } // use the loop back address as the ip of the server
	}

	public void go () {
		clientOutputStreams = new ArrayList<>();
		ServerSocket serverSock = null;
		try {
			try {
				serverSock = new ServerSocket(0);
			} catch (IOException ex) {
				System.out.println("There is no free space for the server on this network. :(");
				System.exit(1);
			}
			System.out.println("What subject are you teaching today? :)");
			Scanner theScanner = new Scanner(System.in);
			subject = theScanner.nextLine();
			// we don't close the scanner because if we do so it will also close System.in and we won't be able to get input later in the Student class.
			System.out.println("The IP address of the Server is " + (InetAddress.getLocalHost().toString().split("/")[1]));
			System.out.println("The Server ID is " + (ServerID = serverSock.getLocalPort()));
			System.out.println("Share this ID with your students so they can chat with you. Keep this server running so they can connect. If you stop this, the ID may be different next time.");
			new Thread(new StudentRunner()).start();
			while(true) {
				Socket clientSocket = serverSock.accept();
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
				clientOutputStreams.add(writer);
				for (String x : names) {
					writer.println(x);
				}
				writer.println("end");
				writer.println(subject);
				writer.flush();
				Thread t = new Thread(new ClientHandler(clientSocket, writer));
				t.start();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public void tellEveryone (String message) {
		for (PrintWriter client : clientOutputStreams) {
			try {
				client.println(message);
				client.flush();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main (String[] args) {
		new Teacher().go();
	}
}

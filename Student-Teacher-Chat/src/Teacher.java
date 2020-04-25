import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Teacher {

	ArrayList<PrintWriter> clientOutputStreams;
	int ServerID = 0;
	public ArrayList<String> names = new ArrayList<String>();

	public class ClientHandler implements Runnable {
		public BufferedReader reader;
		public PrintWriter writer;
		public Socket sock;
		public String name;
		public int index;

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
					formatted += "you ";
				} else if (i == (names.size() - 2)) {
					formatted += names.get(i) + " and ";
				} else {
					formatted += names.get(i) + ", ";
				}

			}

			return formatted + "are chatting";
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
					if (message.equals("STOP LISTENING")) {
						reader.close();
					} else {
						tellEveryone(message);
					}
				}

				if (!sock.isClosed()) {
					names.remove(name);
				}
			} catch (Exception ex) {
				if(!sock.isClosed()) {
					ex.printStackTrace();// the connection reset error happens when the student leaves and the socket disconnects so we check if the socket is even open.
				}
			}
		}
	}

	public class StudentHandler implements Runnable {
		@Override
		public void run() {
			new Student(ServerID).go();
		}
	}

	public void go () {
		clientOutputStreams = new ArrayList<PrintWriter>();
		ServerSocket serverSock = null;
		try {
			try {
				serverSock = new ServerSocket(0);
				System.out.println("The Server ID is " + (ServerID = serverSock.getLocalPort()));
				System.out.println("Share this ID with your students so they can chat with you. Keep this server running so they can connect. If you stop this, the ID may be different next time.");
			} catch (IOException ex) {
				System.out.println("There is no free space for the server on this network :( ");
				System.exit(1);
			}
			System.out.println("Server running");
			new Thread(new StudentHandler()).start();
			while(true) {
				Socket clientSocket = serverSock.accept();
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
				clientOutputStreams.add(writer);
				cleanList();
				for (String x : names) {
					writer.println(x);
				}
				writer.println("end");
				writer.flush();
				Thread t = new Thread(new ClientHandler(clientSocket, writer));
				t.start();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public void tellEveryone (String message) {
		for (PrintWriter clientOutputStream : clientOutputStreams) {
			try {
				clientOutputStream.println(message);
				clientOutputStream.flush();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	public void cleanList() {
		while (names.contains(null)) {
			names.remove(null);
		}
	}
	public static void main (String[] args) {
		new Teacher().go();
	}
}

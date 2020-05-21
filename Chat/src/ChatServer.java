import java.io.*;
import java.net.*;
import java.util.*;


public class ChatServer {

	ArrayList<PrintWriter> clientOutputStreams;

	public int PortOfServer = 5000;
	public ArrayList<String> names = new ArrayList<String>();

	public class toCloseOrNotToClose implements Runnable {
		Scanner in = new Scanner(System.in);
		public ServerSocket sock;
		public toCloseOrNotToClose (ServerSocket socket) {
			sock = socket;
		}
		public void run () {
			while (true) {
				if (in.nextLine().equals("exit")) {
						try {
							sock.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						System.exit(0);
					} else {
						System.out.println("Type \"exit\" to exit");
					}
				}
			}
		}

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
						System.out.println(message);
						tellEveryone(message);
					}
				}

				if (sock.isClosed()) {
					System.out.println(names + " before removing");
					names.remove(name);
					System.out.println(names + " after removing");
				}
			} catch (Exception ex) {
				if(!sock.isClosed()) {
					ex.printStackTrace();// the connection reset error happens when the student leaves and the socket disconnects so we check if the socket is even open.
				}
			}
		}
	}


	public void go () {
		clientOutputStreams = new ArrayList<PrintWriter>();
		ServerSocket serverSock = null;
		try {
			try {
				serverSock = new ServerSocket(PortOfServer);
			} catch (IOException ex) {
				System.out.println("The port is occupied");
				System.out.println("The server has closed");
				System.exit(1);
			} 
			Thread close = new Thread (new toCloseOrNotToClose(serverSock));
			close.start();
			System.out.println("Server running. Use \"exit\" to close the server");
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
				System.out.println("CONNECTION RECEIVED");
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
		new ChatServer().go();
	}
}

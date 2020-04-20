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
		public Socket sock;
		public String name;
		public int index;

		public ClientHandler(Socket clientSocket) {
			try {
				sock = clientSocket;
				InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
				reader = new BufferedReader(isReader);
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
				e.printStackTrace();
			}
			names.add(name);
			clientOutputStreams.get(clientOutputStreams.size() - 1).println(format());
			String message;
			boolean readmore = true;
			try {
				while (readmore && !sock.isClosed() && (message = reader.readLine()) != null) {
					if (message.equals("STOP LISTENING")) {
						reader.close();
						readmore = false;
					} else {
						System.out.println(message);
						tellEveryone(message);
					}
				}

				if (sock.isClosed() || !readmore) {
					if (names.contains(name)) {
						names.remove(name);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}


	public void go () {
		clientOutputStreams = new ArrayList<PrintWriter>();
		ServerSocket serverSock = null;
		try {
			try {
				serverSock = new ServerSocket(0);
				System.out.println("The Server ID is " + serverSock.getLocalPort());
				System.out.println("Share this ID with your students so they can chat with you. Keep this server running so they can connect. If you stop this, the ID may be different next time.");
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
				if (names.size() > 0) {
					for (String x : names) {
						writer.println(x);
					}
				}
				writer.println("end");
				writer.flush();
				System.out.println("CONNECTION RECEIVED");
				Thread t = new Thread(new ClientHandler(clientSocket));
				t.start();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public void tellEveryone (String message) {
		Iterator<PrintWriter> it = clientOutputStreams.iterator();
		while (it.hasNext()) {
			try {
				PrintWriter writer = it.next();
				writer.println(message);
				writer.flush();
			} catch(Exception ex) {ex.printStackTrace();}
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

import java.io.*;
import java.net.*;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;


public class Chat_Teacher {

	LinkedHashMap<String, PrintWriter> namesToWriters = new LinkedHashMap<String, PrintWriter>();
	int ServerID = 0;
	public String subject = "";
	public Set<String> namesSet = namesToWriters.keySet();
	public Chat_Student student = null;
	Scanner theScanner = new Scanner(System.in);
	public Chat_Teacher teacher = this;

	public class ClientHandler implements Runnable {
		public BufferedReader reader;
		public PrintWriter writer;
		public Socket sock;
		public String name;

		public ClientHandler(Socket clientSocket) {
			
			try {
				sock = clientSocket;
				writer = new PrintWriter(clientSocket.getOutputStream());
				reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		public String format() {
			String[] names = new String[namesSet.size()];
			namesSet.toArray(names);
			if (names.length == 1) {
				return "You are the only person chatting";
			}
			String formatted = "";
			for (int i = 0; i < names.length; i++) {
				if (i == (names.length - 1)) {
					formatted += " you";
				} else if (i == (names.length - 2)) {
					formatted += names[i] + " and";
				} else {
					formatted += names[i] + ", ";
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
			namesToWriters.put(name, writer);
			writer.println(format());
			String message;
			try {
				while (!sock.isClosed() && (message = reader.readLine()) != null) {
					tellEveryone(message);
				}
				namesToWriters.remove(name);
			} catch (Exception ex) {
				if(!sock.isClosed()) {
					ex.printStackTrace();// the connection reset error happens when the student leaves and the socket disconnects so we check if the socket is even open.
				}
			}
		}
	}

	public class StudentRunner implements Runnable {
		@Override
		public void run() {(student = new Chat_Student(ServerID, "127.0.0.1", teacher)).go(); } // use the loop back address as the ip of the server
	}

	public void execCommands (String theCommand) {
		String target = "";
		try {
			String command = theCommand.split(" ")[0];
			if (theCommand.split(" ", 2).length > 1) {
				target = theCommand.split(" ", 2)[1]; // split into two parts and select the second
			}
			target = target.strip();
			switch (command) {
				case "remove" :
					removeName(target);
					break;
				case "close" :
					student.close();
					break;
				default:
					System.out.println("Invalid command");
			}
		} catch (Exception ignored) {}
	}

	public void go () {
		ServerSocket serverSock = null;
		try {
			try {
				serverSock = new ServerSocket(0);
			} catch (IOException ex) {
				System.out.println("There is no free space for the server on this network. :(");
				System.exit(1);
			}
			System.out.println("What subject are you teaching today? :)");
			subject = theScanner.nextLine();
			// we don't close the scanner because if we do so it will also close System.in and we won't be able to get input later in the Chat_Student class.
			System.out.println("The IP address of the Server is " + (InetAddress.getLocalHost().toString().split("/")[1]));
			System.out.println("The Server ID is " + (ServerID = serverSock.getLocalPort()));
			System.out.println("Share this ID with your students so they can chat with you. Keep this server running so they can connect. If you stop this, the ID may be different next time.");
			System.out.println("You can enter commands here. Commands you can run are: \"remove <name of student to remove>\"(removes the specified student. You can also remove yourself) and \"close\"(Shuts the program down). Commands can only be run after setup(entering name, prefs etc.)");
			new Thread(new StudentRunner()).start();
			while(true) {
				try {
					Socket clientSocket = serverSock.accept();
					PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
					for (String x : namesSet) {
						writer.println(x);
					}
					writer.println("end");
					writer.println(subject);
					writer.flush();
					Thread t = new Thread(new ClientHandler(clientSocket));
					t.start();
				} catch (Exception e) {
					//a problem with just one client. Don't want that to affect everyone else
				}
			}
		} catch(Exception ex) {
			System.out.println("An error occurred. The program is shutting down.");
			System.exit(1);
		}
	}

	public void tellEveryone (String message) {
		for (PrintWriter clientWriter : namesToWriters.values()) {
			try {
				clientWriter.println(message);
				clientWriter.flush();
			} catch (Exception ex) {
				//just one client didn't get the message.
			}
		}
	}

	public void removeName(String targetName) {
		if (namesSet.contains(targetName)) {
			namesToWriters.get(targetName).close();
			namesToWriters.remove(targetName);
			tellEveryone(targetName + " has been removed");
		}
	}

	public static void main (String[] args) {
		new Chat_Teacher().go();
	}
}

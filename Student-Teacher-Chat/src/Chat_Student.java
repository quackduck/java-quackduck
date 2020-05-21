import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.*;


public class Chat_Student {

	public String IPofServer = "";
	public int ServerID = 0;
	public ReadWrite readwrite = new ReadWrite();
	public String userName;
	public boolean toRecord = true;
	public ArrayList<String> list = new ArrayList<String>();
	public Scanner scannerIn = new Scanner(System.in);
	public boolean startedFromConstructor = false;
	public JTextArea incoming;
	public JTextField outgoing;
	public Chat_Teacher teacher = null;
	BufferedReader reader;
	PrintWriter writer;
	Socket sock;
	JFrame frame;
	public Chat_Student(int theServerID, String theIPofServer, Chat_Teacher theCallingTeacher) {
		ServerID = theServerID;
		IPofServer = theIPofServer;
		startedFromConstructor = true;
		teacher = theCallingTeacher;
	}

	public Chat_Student() {}

	public static void main(String[] args) {new Chat_Student().go();}

	public void go() {
		if (!startedFromConstructor){ // we check whether the IP of the server has already been set using the constructor - Chat_Student(int, String)
			System.out.println("Enter the IP address of the server");
			IPofServer = scannerIn.nextLine();
		}
		if (!startedFromConstructor) { // we check whether ServerID has already been set using the constructor - Chat_Student(int, String)
			System.out.println("Enter Server ID");
			try {
				ServerID = Integer.parseInt(scannerIn.nextLine());
			} catch (Exception e) {
				System.out.println("Invalid ID");
				System.exit(0);
			}
		}
		setUpNetworking();
		String personName = "";
		try {
			while ((personName = reader.readLine()) != null && !personName.equals("end")) { // we get the list of names of people who are already connected to the server. The server sends "end" when the list is finished
				list.add(personName.toLowerCase());
			}
		} catch (IOException e) {
			System.out.println("Error while trying to get list of names from server. This is just a minor issue. The program will continue to work");
		}

		readwrite.setPath(System.getProperty("user.home") + "/Desktop/ChatPrefs.txt");
		if (readwrite.exists()) {
			String[] stringArr = readwrite.read().split(System.lineSeparator());
			String nameBuffer = stringArr[0];
			System.out.println("Hi " + nameBuffer + "!");
			System.out.println("Do you want to use the prefs? Hit enter. If not, type anything else");
			if (scannerIn.nextLine().equals("")) {
				try {
					toRecord = Boolean.parseBoolean(stringArr[1]);
					userName = nameBuffer;
				} catch (Exception e) {
					System.out.println("Oops! An error occurred.");
					defaultSetup();
				}
				if(list.contains(userName.toLowerCase())) {
					System.out.println("Sorry, the username in your prefs has been taken");
					defaultSetup();
				}
			} else {
				defaultSetup();
			}

		} else {
			defaultSetup();
		}
		if (toRecord && readwrite.exists()) {
			Date date = new Date();
			readwrite.setPath(System.getProperty("user.home") + "/Desktop/ChatRecord.txt");
			String theStart = System.lineSeparator() + System.lineSeparator() + date.toString() + System.lineSeparator();
			readwrite.setContent(theStart);
			readwrite.append();
		}
		try {
			frame = new JFrame(userName + "'s Chat - " + reader.readLine());
		} catch (IOException e) {
			frame = new JFrame(userName + "'s Chat");
		}
		setupGUI();
		writer.println(userName);
		writer.println(userName + " has joined the chat");
		writer.flush();
		if (startedFromConstructor) {
			new Thread(new CommandSender()).start();
		}
		new Thread (new IncomingReader()).start();
	}

	public class CommandSender implements Runnable {
		@Override
		public void run() {
			teacher.execCommands(scannerIn.nextLine());
		}
	}

	private void setupGUI() {
		JPanel mainPanel = new JPanel();
		outgoing = new JTextField(30);
		incoming = new JTextArea(20,30);
		incoming.setBackground(generateBrightRandomColor());
		outgoing.setBackground(generateBrightRandomColor());
		mainPanel.setBackground(generateBrightRandomColor());
		DefaultCaret caret = (DefaultCaret) incoming.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		incoming.setLineWrap(false);
		incoming.setWrapStyleWord(true);
		incoming.setEditable(false);
		JScrollPane textScroller = new JScrollPane(incoming);
		outgoing.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					sendStuff();
				}
			}
		});
		mainPanel.add(textScroller);
		mainPanel.add(outgoing);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(425, 425);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
		frame.setVisible(true);
		outgoing.requestFocusInWindow();
	}

	private Color generateBrightRandomColor () {
		float x = 0.15f; // high value = wider range of colors with higher probability of darker colors. 0.1 makes light pastels. 0.2 makes pastels
		float r = (float) ((Math.random() * x)+(1-x));
		float g = (float) ((Math.random() * x)+(1-x));
		float b = (float) ((Math.random() * x)+(1-x));
		return new Color(r, g, b);
	}

	private void defaultSetup () {
		System.out.println("Enter your chat username");
		userName = scannerIn.nextLine();
		while (userName.strip().equals("") || list.contains(userName.toLowerCase())) {
			if (userName.strip().equals("")) {
				System.out.println("Seriously? Blank names aren't allowed :) Choose another one");
			}
			else {
				System.out.println("That name's been taken :( Choose another one");
			}
			userName = scannerIn.nextLine();
		}
		System.out.println("If you want to save a record of your chat, press enter. Else type anything else");
		if (!scannerIn.nextLine().equals("")) {
			toRecord = false;
		}
		System.out.println("Save prefs? Type yes. If not just hit enter");
		if (scannerIn.nextLine().equalsIgnoreCase("yes")) {
			readwrite.setPath(System.getProperty("user.home") + "/Desktop/ChatPrefs.txt");
			readwrite.setContent(userName + System.lineSeparator() + toRecord + System.lineSeparator() + System.lineSeparator() + "Please do not edit the second line of this file to anything other than \"true\" or \"false\". Setting the second line to anything other than those values will mean that the next time you use prefs your chat will not be recorded. You can edit the first line to whatever you wish your name to be.");
			readwrite.create();
		}
	}

	private void setUpNetworking() {
		try {
			sock = new Socket(IPofServer, ServerID);
			reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			writer = new PrintWriter(sock.getOutputStream());
		} catch(Exception ex) {
			System.out.println("The server isn't online or the Server info is incorrect(IP and/or ID). Bye!");
			System.exit(1);
		}
	}

	public void sendStuff() {
		String text = outgoing.getText().strip();
		try {
			switch(text) {
				case "clear":
					incoming.setText("");
					break;
				case "bye":
				case "au revoir":
					writer.println(userName + ":  " + text);
					writer.flush();
					close();
					break;
				case "close":
				case "exit":
					close();
					break;
				case "":
					break;
				default:
					writer.println(userName + ":  " + text);
					writer.flush();
			}
		} catch(Exception ex) {ex.printStackTrace();}
		outgoing.setText("");
		outgoing.requestFocusInWindow();
	}

	public void close() {
		frame.setVisible(false);
		writer.println(userName + " has left the chat");
		writer.flush();
		writer.close();
		try {
			reader.close();
			sock.close();
		} catch (IOException e) {
			//no need to handle this. The program is going to exit anyways
		}
		System.exit(0);
	}

	public class IncomingReader implements Runnable {
		@Override
		public void run () {
			String message;
			try {
				while (!sock.isClosed() && (message = reader.readLine()) != null) {
					incoming.append(message + System.lineSeparator());
					if (toRecord) {
						readwrite.setPath(System.getProperty("user.home") + "/Desktop/ChatRecord.txt");
						readwrite.setContent(message + System.lineSeparator());
						readwrite.append();
					}
				}
				System.out.println("You were removed or the teacher has ended the chat. The program is shutting down...");
				close();
			} catch (Exception e) {
				run(); // dont stop reading just because one message was not sent.
			}
		}
	}
}

import base.ReadWrite;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.*;


public class Student {

	public String IPofServer = "";
	public int ServerID = 0;
	public ReadWrite readwrite = new ReadWrite(System.getProperty("user.home") + "/Desktop/ChatRecord.txt");
	public String yourName;
	public String record = "";
	public boolean toRecord = true;
	public ArrayList<String> list = new ArrayList<String>();
	public Scanner scannerIn = new Scanner(System.in);
	public boolean staySilentNoOutput = false;
	JTextArea incoming;
	JTextField outgoing;
	BufferedReader reader;
	PrintWriter writer;
	Socket sock;
	JFrame frame;
	public Student(int theServerID, String theIPofServer) {
		ServerID = theServerID;
		IPofServer = theIPofServer;
		staySilentNoOutput = true;
	}

	public Student() {}

	public static void main(String[] args) {
		new Student().go();
	}

	public void go() {
		if (IPofServer.equals("")){ // we check whether the ip of the server has already been set using the constructor - Student(int, String)
			System.out.println("Enter the IP address of the server");
			IPofServer = scannerIn.nextLine();
		}

		if (ServerID == 0) { // we check whether ServerID has already been set using the constructor - Student(int, String)
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
			while ((personName = reader.readLine()) != null && !personName.strip().equals("end")) { // we get the list of names of people who are already connected to the server. The server sends "end" when the list is finished
				list.add(personName.toLowerCase());
			}

		} catch (IOException e) {
			System.out.println("Error while trying to get list of names from server. This is just a minor issue. The program will continue to work");
		}

		readwrite.setPath(System.getProperty("user.home") + "/Desktop/ChatPrefs.txt");
		if (readwrite.exists()) {
			System.out.println("Use Prefs? Hit enter. If not, type anything else");
			if (scannerIn.nextLine().equals("")) {
				String[] stringArr = readwrite.read().split(System.lineSeparator());
				toRecord = Boolean.parseBoolean(stringArr[1]);
				yourName = stringArr[0];
				if(list.contains(yourName.toLowerCase())) {
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
			frame = new JFrame(yourName + "'s Chat - " + reader.readLine());
		} catch (IOException e) {
			frame = new JFrame(yourName + "'s Chat");
		}
		JPanel mainPanel = new JPanel();
		incoming = new JTextArea(20,30);
		DefaultCaret caret = (DefaultCaret) incoming.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		incoming.setLineWrap(true);
		incoming.setWrapStyleWord(true);
		incoming.setEditable(false);
		JScrollPane qScroller = new JScrollPane(incoming);
		outgoing = new JTextField(30);
		outgoing.addKeyListener(new KeyPressListener());
		mainPanel.add(qScroller);
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
		writer.println(yourName);
		writer.println(yourName + " has joined the chat");
		writer.flush();
		scannerIn.close();
		Thread readerThread = new Thread (new IncomingReader());
		readerThread.start();
	}

	private void defaultSetup () {
		System.out.println("Enter your chat username");
		while ((yourName = scannerIn.nextLine()).strip().equals("")) {
			System.out.println("Seriously? Choose another name.");
		}
		while (list.contains(yourName.toLowerCase()) || yourName.strip().equals("")) {
			System.out.println("That name's taken or is invalid. Choose another one");
			yourName = scannerIn.nextLine();
		}
		System.out.println("If you want to save a record of your chat, press enter. Else type anything else");
		if (!scannerIn.nextLine().equals("")) {
			toRecord = false;
		}
		System.out.println("Save prefs? Type yes. If not just hit enter");
		if (scannerIn.nextLine().equalsIgnoreCase("yes")) {
			readwrite.setPath(System.getProperty("user.home") + "/Desktop/ChatPrefs.txt");
			readwrite.setContent(yourName + System.lineSeparator() + toRecord + System.lineSeparator() + System.lineSeparator() + "Please do not edit the second line of this file to anything other than \"true\" or \"false\". Setting the second line to anything other than those values will mean that the next time you use prefs your chat will not be recorded in the file. You can edit the first line to whatever you wish your name to be.");
			readwrite.create();
		}
	}

	private void setUpNetworking() {
		try {
			sock = new Socket(IPofServer, ServerID);
			reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			writer = new PrintWriter(sock.getOutputStream());
			if (!staySilentNoOutput) {
				System.out.println("Connected to Server");
			}
		} catch(IOException ex) {System.out.println("The server isn't online or the Server info is incorrect(IP and ID). Bye!"); System.exit(1);}
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
					writer.println(yourName + ":  " + text);
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
					writer.println(yourName + ":  " + text);
					writer.flush();
			}
		} catch(Exception ex) {ex.printStackTrace();}
		outgoing.setText("");
		outgoing.requestFocusInWindow();
	}

	public void close() {
		frame.setVisible(false);
		writer.println(yourName + " has left the chat");
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

	public class KeyPressListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {}
		@Override
		public void keyPressed(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				sendStuff();
			}
		}
	}

	public class IncomingReader implements Runnable {
		@Override
		public void run () {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					System.out.println(message);
					incoming.append(message + System.lineSeparator());
					if (toRecord) {
						readwrite.setPath(System.getProperty("user.home") + "/Desktop/ChatRecord.txt");
						readwrite.setContent(message + System.lineSeparator());
						readwrite.append();
					}
				}
			} catch (Exception e) {System.out.println("The server has diconnected");}
		}
	}
}

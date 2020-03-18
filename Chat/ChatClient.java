import java.io.*;
import base.ReadWrite;
import java.net.*;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;
//import java.util.*;


public class ChatClient {
	
	public static String PrivateIPofServer = "chatserver.local"; // this is my IP (I'm hosting the server on my computer)
	public static int PortOfServer = 5000; // Leave this alone
	public static ReadWrite readwrite = new ReadWrite(System.getProperty("user.home") + "/Desktop/ChatRecord.txt");
	public static String yourName;
	public static String record = "";
	public static boolean toRecord = true;
	JTextArea incoming;
	JTextField outgoing;
	BufferedReader reader;
	PrintWriter writer;
	Socket sock;
	public static void main(String[] args) {
		new ChatClient().go();
	}

	public void go() {

		if (readwrite.exists()) {
			record += readwrite.read() + System.lineSeparator() + System.lineSeparator();
		}
		Scanner scannerIn = new Scanner(System.in);
		System.out.println("Enter your chat username");
		while ((yourName = scannerIn.nextLine()).strip() == "") {
			System.out.println("Seriously? Choose another name.");
		}
		System.out.println("Enter Server Address (or press enter to use default)");
		PrivateIPofServer = scannerIn.nextLine();
		if (PrivateIPofServer.equals("")) {
			PrivateIPofServer = "chatserver.local";
		}
		System.out.println("If you want to save a record of your chat, press enter. Else type the letter N");
		if (scannerIn.nextLine().equalsIgnoreCase("n")) {
			toRecord = false;
		}
		scannerIn.close();
		JFrame frame = new JFrame ("Chat");
		JPanel mainPanel = new JPanel();
		incoming = new JTextArea (20,30);
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
		setUpNetworking();
		Thread readerThread = new Thread (new IncomingReader());
		readerThread.start();
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(800, 500);
		frame.setVisible(true);
		outgoing.requestFocusInWindow();
	}

	private void setUpNetworking() {
		try {
			sock = new Socket(PrivateIPofServer, PortOfServer);
			InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
			reader = new BufferedReader(streamReader);
			writer = new PrintWriter(sock.getOutputStream());
			writer.println(yourName);
			writer.println(yourName + " has joined the chat");
			writer.flush();
			System.out.println("Connected!");
		} catch(IOException ex) {ex.printStackTrace();}
	}

	public class KeyPressListener implements KeyListener {

		public void keyTyped(KeyEvent e) {

		}

		public void keyPressed(KeyEvent e) {

		}

		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				sendStuff();
			}
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
		writer.println(yourName + " has left the chat");
		writer.flush();
		writer.println("STOP LISTENING");
		writer.flush();
		if (toRecord) {
		 readwrite.setContent(record);
		 readwrite.create();
		}
		writer.close();
		try {
			reader.close();
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	public class IncomingReader implements Runnable {
		public void run () {
			String message;
			try {
				while (!sock.isClosed() && (message = reader.readLine()) != null) {
					System.out.println(message);
					record += message + System.lineSeparator();
					incoming.append(message + System.lineSeparator());
				}
			} catch (Exception ex) {ex.printStackTrace();}
		}
	}
}

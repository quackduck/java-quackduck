package logger;
import base.ReadWrite;
import java.util.ArrayList;

public class Logger {

	public static String n = System.lineSeparator();
	public static ReadWrite readwrite = new ReadWrite();
	public static int counter = 0;

	public static void main(String[] args) {

		readwrite.setPath("/users/ishan/desktop/stuff/LOG.txt");
		String stuff = "aaaaaaaaaaaaaaaaaaaa" + n + "bbbbbbbbbbbbbbbbbbbb" + n + "cccccccccccccccccccc" + n + "dddddddddddddddddddd" + n + "eeeeeeeeeeeeeeeeeeee" + n + "foo" + n + n + "check"  + n + "heeheeeee" + n + "hoooo" + n + "bleh?" + n +"WORKS!" + n +"WORKS!" + n +"WORKS!" + n +"WORKS!" + n +"WORKS!" + n +"W!" + n + "heehheggsggsgsgsgsgsggsgsgsgsggggggee" ;
		//System.out.println(stuff.length());
		log3(stuff, 20);
	}

	public static void log(String content) {

		String[] arrcontent = content.split(n);
		//System.out.println();
		int numofbytes = 0;
		int linenumber = 0;
		for (int i=0; i<arrcontent.length; i++) {
			//try {Thread.sleep(1000);} catch (InterruptedException stuff) {}				
			numofbytes = readwrite.getContent().getBytes().length;
			if (numofbytes>100 || linenumber > 0) {
				linenumber++;
				if (linenumber > readwrite.getContent().split(n).length) {
					linenumber = 1;
				}
				readwrite.setContent(changeline(readwrite.getContent(), linenumber - 1, arrcontent[i]));
				System.out.println("here");
			} else {
				readwrite.setContent(readwrite.getContent() + arrcontent[i] + n);
			}
			readwrite.create();
		}

	}
	
	public static void log2(String content, int linelimit) {

		String[] arrcontent = content.split(n);
		//System.out.println();
		int numoflines = 0;
		int linenumber = 0;
		for (int i=0; i<arrcontent.length; i++) {
			try {Thread.sleep(1000);} catch (InterruptedException stuff) {}				
			numoflines = readwrite.getContent().split(n).length;
			if (numoflines>=linelimit) {
				linenumber++;
				if (linenumber > linelimit) {
					linenumber = 1;
				}
				readwrite.setContent(changeline(readwrite.getContent(), linenumber - 1, arrcontent[i]));
			} else {
				readwrite.setContent(readwrite.getContent() + arrcontent[i] + n);
			}
			readwrite.create();
		}

	}
	
	public static void log3(String content, int bytelimit) {

		String[] arrcontent = content.split(n);
		String strremaining = "";
		ArrayList<String> remaining = new ArrayList<String>();
		for(int i = 0; i > arrcontent.length; i++) {
			remaining.add(arrcontent[i]);
		}
		//System.out.println();
		int numofbytes = 0;
		for (int i=0; i<arrcontent.length; i++) {
			remaining.remove(arrcontent[i]);
			//try {Thread.sleep(1000);} catch (InterruptedException stuff) {}				
			numofbytes = readwrite.getContent().getBytes().length;
			if (numofbytes>bytelimit) {
				counter++;
				readwrite.setPath("/users/ishan/desktop/stuff/LOG" + Integer.toString(counter) + ".txt");
				
				for(int i2 = 0; i2 < remaining.size(); i2++) {
					strremaining += remaining.get(i2) + n;
				}
				readwrite.setContent("");
				
				strremaining = strremaining.strip();
				System.out.println(strremaining);
				log3(strremaining, bytelimit);
			} else {
				readwrite.setContent(readwrite.getContent() + arrcontent[i] + n);
			}
			readwrite.create();
		}

	}

	public static String changeline(String input, int index, String toadd) {

		String[] tokens = input.split(n);	
		tokens[index] = toadd;
		String result = tokens[0];
		for (int i = 1; i<tokens.length; i++) {
			result += n + tokens[i];
		}
		return result;
	}



}

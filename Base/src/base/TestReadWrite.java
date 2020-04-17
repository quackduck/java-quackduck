package base;

public class TestReadWrite {

	static String n = System.lineSeparator();
	static String content = "foo" + n + "bar";

	public static void main(String[] args) {
		ReadWrite readwrite = new ReadWrite();
		readwrite.setPath("/Users/Ishan/Desktop/ReadWriteOutput.txt");
		readwrite.setContent(content);
		
		if (args.length >= 1) {
			readwrite.setPath(args[0]);
		}
		if (args.length >= 2) {
			readwrite.setContent(args[1]);
			for (int i = 2; i < args.length; i++ ) {
				readwrite.setContent(readwrite.getContent() + " " + args[i]);
			}
		}
		readwrite.create();
		//readwrite.setPath("/Users/Ishan/Desktop/eclipse/ReadWrite/TestReadWrite.java");
		//readwrite.setContent(readwrite.read());
		//readwrite.create();
	}

}
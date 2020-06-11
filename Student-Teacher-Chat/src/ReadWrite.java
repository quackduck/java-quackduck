import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ReadWrite {

	private String path = "";
	private String content = "";
	private File file;
	public static String newline = System.lineSeparator();
	
	public ReadWrite() {}

	public void setPath(String x) {
		path = x;
	}

	public void setContent(String x) {
		content = x;
	}

	public void create() {
		try {
			file = new File(path);
			file.createNewFile();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		try {
			FileWriter writer = new FileWriter(path);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

	}

	public void append() {
		try {
			file = new File(path);
			file.createNewFile();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		try {
			FileWriter writer = new FileWriter(path, true);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

	}

	public String read() {
		StringBuilder data = new StringBuilder();
		try {
			file = new File(path);
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()) {
				data.append(scan.nextLine()).append(newline);
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return data.toString();
	}
	
	public boolean exists() {
		file = new File(path);
		return file.exists();
	}
}

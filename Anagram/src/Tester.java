
public class Tester {
	
	public static String[][] thing = {{"test", "wee", "yes", "gahhh", ""},
									  {"test", "eew", "sye", "hhhag", ""}};
	
	public static Anagram anagram = new Anagram();
	
	public static void main(String[] args) {
		for (int i = 0; i < thing[0].length; i++) {
			System.out.println(anagram.isAnagram(thing[0][i], thing[1][i]));
		}
		
	}
	

}

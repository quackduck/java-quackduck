import java.util.*;
public class Anagram {
	
	public String str1 = "school master";
	public String str2 = "the classroom";

	public static void main(String[] args) {
		new Anagram().go();
	}
	
	public void go () {
		System.out.println(isAnagram(str1, str2));
	}
	
	public boolean isAnagram (String str1, String str2) {
		
		if (str1.length() != str2.length()) {
			return false;
		}
		
		Map<Character, Integer> hashmapcharnums1 = new HashMap<Character, Integer>();
		Map<Character, Integer> hashmapcharnums2 = new HashMap<Character, Integer>();
		
		for (int i = 0; i < str1.length(); i++) {
			char thechar = str1.charAt(i);
			if (hashmapcharnums1.containsKey(thechar)) {
				hashmapcharnums1.put(thechar, hashmapcharnums1.get(thechar) + 1);
			} else {
				hashmapcharnums1.put(thechar, 1);
			}
		}
		
		for (int i = 0; i < str2.length(); i++) {
			char thechar = str2.charAt(i);
			if (hashmapcharnums2.containsKey(thechar)) {
				hashmapcharnums2.put(thechar, hashmapcharnums2.get(thechar) + 1);
			} else {
				hashmapcharnums2.put(thechar, 1);
			}
		}
		
		return hashmapcharnums1.equals(hashmapcharnums2);
	}
	
}

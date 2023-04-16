package me.hardstyles.blitz.kit;

public class KitUtils {
	
	public static String getKitTag(int level) {
		if(level == 0)
			return "";
		else if(level == 1)
			return " I";
		else if(level == 2)
			return " II";
		else if(level == 3)
			return " III";
		else if(level == 4)
			return " IV";
		else if(level == 5)
			return " V";
		else if(level == 6)
			return " VI";
		else if(level == 7)
			return " VII";
		else if(level == 8)
			return " VIII";
		else if(level == 9)
			return " IX";
		else if(level >= 10)
			return " X";
		return " ERROR";
	}

}

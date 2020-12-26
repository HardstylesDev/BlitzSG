package me.syesstyles.blitz.perk;

public class PerkUtils {
	
	public static String getPerkTag(int level) {
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
		else if(level >= 5)
			return " V";
		return " ERROR";
	}

}

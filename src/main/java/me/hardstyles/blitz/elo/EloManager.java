package me.hardstyles.blitz.elo;

import java.util.ArrayList;

public class EloManager {
	
	ArrayList<EloLevel> eloLevels;
	
	public EloManager() {
		eloLevels = new ArrayList<EloLevel>();
		loadLevels();
	}
	
	private void loadLevels(){
		eloLevels.add(new EloLevel(0, "§8"));
		eloLevels.add(new EloLevel(100, "§7"));
		eloLevels.add(new EloLevel(200, "§6"));
		eloLevels.add(new EloLevel(300, "§c"));
		eloLevels.add(new EloLevel(400, "§a"));
		eloLevels.add(new EloLevel(500, "§d"));
	}

	public ArrayList<EloLevel> getEloLevels() {
		return eloLevels;
	}
	
	public EloLevel getEloLevel(int eloRating) {
		for(EloLevel level : eloLevels) {
			if(eloRating < level.getRequiredElo())
				return eloLevels.get(eloLevels.indexOf(level)-1);
		}
		return eloLevels.get(eloLevels.size()-1);
	}

}

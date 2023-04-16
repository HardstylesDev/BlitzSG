package me.hardstyles.blitz.elo;

public class EloLevel {

	private int requiredElo;
	private String prefix;
	
	public EloLevel(int requiredElo, String prefix) {
		this.requiredElo = requiredElo;
		this.prefix = prefix;
	}

	public int getRequiredElo() {
		return requiredElo;
	}

	public void setRequiredElo(int requiredElo) {
		this.requiredElo = requiredElo;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
}

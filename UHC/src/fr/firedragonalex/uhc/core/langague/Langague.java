package fr.firedragonalex.uhc.core.langague;

public enum Langague {
	
	ENGLISH(0),
	FRENCH(1);
	
	private int id;
	
	private Langague(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
}

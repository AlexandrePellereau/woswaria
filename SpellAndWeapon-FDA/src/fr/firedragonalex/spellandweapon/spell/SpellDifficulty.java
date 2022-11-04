package fr.firedragonalex.spellandweapon.spell;

public enum SpellDifficulty {
	
	EASY(40),MEDIUM(20),HARD(10);
	
	private int ticksPerCharacter;
	
	private SpellDifficulty(int ticksPerCharacter) {
		this.ticksPerCharacter = ticksPerCharacter;
	}
	
	public int getTicksPerCharacter() {
		return this.ticksPerCharacter;
	}
}

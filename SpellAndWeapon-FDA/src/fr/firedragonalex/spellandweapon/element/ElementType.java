package fr.firedragonalex.spellandweapon.element;

public enum ElementType {
	
	WATER("Eau","§1"),WIND("Vent","§3"),FIRE("Feu","§c"),ICE("Glace","§b"),DARK("Sombre","§0"),VEGETAL("Plante","§2"),STONE("Pierre","§6"),ELECTRICITY("Electricité","§e"),LIGHT("Lumière","§f"),PHYSICAL("Physique","§7");
	
	private String name;
	private String color;
	
	private ElementType(String name, String color) {
		this.name = name;
		this.color = color;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getColor() {
		return this.color;
	}

}

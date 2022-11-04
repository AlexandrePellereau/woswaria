package fr.firedragonalex.uhc.specific;

public enum Side {
	
	GREEN("verts"),
	BLUE("bleus"),
	RED("rouges"),
	;

	private String name;

	private Side(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}

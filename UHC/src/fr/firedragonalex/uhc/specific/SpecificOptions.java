package fr.firedragonalex.uhc.specific;

import java.util.Arrays;
import java.util.List;

public class SpecificOptions {
	
	private static List<Role> composition;
	
	public static void update() {
		SpecificOptions.composition = Arrays.asList(
			Role.YUU_OMINAE
			);
	}
	
	public static List<Role> getComposition() {
		return SpecificOptions.composition;
	}

}

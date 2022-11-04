package fr.firedragonalex.livetiktokonsign;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.block.Sign;
import org.bukkit.scheduler.BukkitRunnable;

public class EveryTicks extends BukkitRunnable{
	
	private String pathFile;
	
	public EveryTicks(String pathFile) {
		this.pathFile = pathFile;
	}

	@Override
	public void run() {
		File folder = new File(pathFile);
		if (!folder.exists()) {
			return;
		}
		File saveFile = new File(pathFile);
		if (!saveFile.exists()) {
			return;
		}else {
			try {
				FileReader reader = new FileReader(saveFile);
				BufferedReader br = new BufferedReader(reader);
				String line;
				while ((line = br.readLine()) != null) {
					String[] splitLine = line.split(":");
					SignType signType = SignType.valueOf(splitLine[0]);
					if (signType != null) {
						//System.out.println(signType+","+splitLine[1]);
						Main.informations.put(signType, splitLine[1]);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				
			}
		}
		for (Entry<Sign, SignType> entry : Main.allSigns.entrySet()) {
			Main.updateSign(entry.getKey(), entry.getValue());
		}
	}

}

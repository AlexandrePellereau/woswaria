package fr.firedragonalex.livetiktokonsign;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.bukkit.Bukkit;

public class ScriptPython {
	
	private Process mProcess;
	private String nameScript;
	private String argument;
	
	public ScriptPython(String nameScript) {
		this.nameScript = nameScript;
	}
	
//	private void copy(File orignal, String newPath) {
//		Path copied = Paths.get(newPath);
//	    Path originalPath = orignal.toPath();
//	    try {
//			Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public void runScript(String argument){
		   //this.copy(new File("src/"+nameScript+".py"), "plugins/"+nameScript+".py");
		   Path path = Paths.get("plugins");
		   path = path.toAbsolutePath();
		   this.argument = argument;
	       Process process;
	       try{
	             process = Runtime.getRuntime().exec(new String[]{"py "+path.toString()+"\\"+nameScript+".py",argument});
	             mProcess = process;
	       }catch(Exception e) {
	          System.out.println("Exception Raised" + e.toString());
	          return;
	       }
	       InputStream stdout = mProcess.getInputStream();
	       BufferedReader reader = new BufferedReader(new InputStreamReader(stdout,StandardCharsets.UTF_8));
	       String line;
	       try{
	          while((line = reader.readLine()) != null){
	               //System.out.println("[python-script-"+nameScript+"]: "+ line);
	               Bukkit.broadcastMessage("[python-script-"+nameScript+"]: "+ line);
	          }
	       }catch(IOException e){
	             System.out.println("Exception in reading output"+ e.toString());
	             return;
	       }
	       System.out.print("Code keep to executing.");
	}
	
	public void finish() {
		this.mProcess.destroy();
	}
}

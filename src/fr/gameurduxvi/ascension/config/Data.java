package fr.gameurduxvi.ascension.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.gameurduxvi.ascension.MainGame;
import fr.gameurduxvi.meneliaapi.MeneliaAPI;

public class Data {
	private static File file;
	private static FileConfiguration fileConfig;
	
	public void load() {
		file = new File("plugins/Menelia/ascension.yml");
		Bukkit.getConsoleSender().sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " Config File &e" + file.getPath() + " (Data.java)&5 has been loaded !"));
		if(!file.exists()) {
			try {
				file.createNewFile();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
				
		fileConfig = YamlConfiguration.loadConfiguration(file);
		
	}
	
	public static void saveConfig() {
		try {
			fileConfig.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File getFile() {
		return file;
	}
	
	public static FileConfiguration getConfig() {
		return fileConfig;
	}
}

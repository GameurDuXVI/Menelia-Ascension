package fr.gameurduxvi.ascension.worldedit;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;

import fr.gameurduxvi.ascension.MainGame;
import fr.gameurduxvi.ascension.database.Games;
import fr.gameurduxvi.ascension.database.Maps;
import fr.gameurduxvi.meneliaapi.MeneliaAPI;

public class SchematicLoad implements Runnable {
	
	private Player player;
	private String map;
	private boolean useNumber;
	private int gameNumber;
	private boolean usPlayerVar;

	public SchematicLoad(Player player, String map, boolean useNumber, int gameNumber) {
		this.player = player;
		this.map = map;
		this.useNumber = useNumber;
		this.gameNumber = gameNumber;
		usPlayerVar = true;
	}
	
	public SchematicLoad(String map, boolean useNumber, int gameNumber) {
		this.map = map;
		this.useNumber = useNumber;
		this.gameNumber = gameNumber;
		usPlayerVar = false;
	}

	@Override
	public void run() {
		Bukkit.getScheduler().runTaskAsynchronously(MainGame.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				for(Maps loopMap: MainGame.getInstance().getMaps()) {
					if(map.equalsIgnoreCase(loopMap.getName())) {
						
						if(useNumber) {
							if(usPlayerVar)player.sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &bLoading map for game " + gameNumber));
							
							Location loc = new Location(loopMap.getBuildCyan().getWorld(), (gameNumber * 5000) - 500 , 0, (loopMap.getId() * 5000) - 500);
							Vector vector = new Vector(loc.getX(), loc.getY(), loc.getZ());
							BukkitWorld world = new BukkitWorld(loc.getWorld());
								
							try {
								if(usPlayerVar)Bukkit.getConsoleSender().sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &e" + player.getName() + " &6request for pasting a schematic from &e" + MainGame.getGameFunctions().getSchematicFile(map).getPath() + " &6in world &e" + loc.getWorld().getName() + " &6at &ex:" + loc.getX() + " y:" + loc.getY() + " z:" + loc.getZ()));
								if(!usPlayerVar)Bukkit.getConsoleSender().sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &6The map " + gameNumber + " is resetting automaticaly with schematic &e" + MainGame.getGameFunctions().getSchematicFile(map).getPath() + " &6in world &e" + loc.getWorld().getName() + " &6at &ex:" + loc.getX() + " y:" + loc.getY() + " z:" + loc.getZ()));
								ClipboardFormat.SCHEMATIC.load(MainGame.getGameFunctions().getSchematicFile(map)).paste(world, vector, false, true, null);
							} catch (IOException e) {
								e.printStackTrace();
							}
								
							if(usPlayerVar)player.sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &bLoading complete for game " + gameNumber));	
						}
						
						else {
							for(Games game: MainGame.getInstance().getGames()) {
								if(usPlayerVar)player.sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &bLoading map for game " + game.getId()));
								
								Location loc = new Location(loopMap.getBuildCyan().getWorld(), (game.getId() * 5000) - 500 , 0, (loopMap.getId() * 5000) - 500);
								Vector vector = new Vector(loc.getX(), loc.getY(), loc.getZ());
							    BukkitWorld world = new BukkitWorld(loc.getWorld());
								
							    try {
							    	if(usPlayerVar)Bukkit.getConsoleSender().sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &e" + player.getName() + " &6request for pasting a schematic from &e" + MainGame.getGameFunctions().getSchematicFile(map).getPath() + " &6in world &e" + loc.getWorld().getName() + " &6at &ex:" + loc.getX() + " y:" + loc.getY() + " z:" + loc.getZ()));
							    	if(!usPlayerVar)Bukkit.getConsoleSender().sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &6The map " + gameNumber + " is resetting automaticaly with schematic &e" + MainGame.getGameFunctions().getSchematicFile(map).getPath() + " &6in world &e" + loc.getWorld().getName() + " &6at &ex:" + loc.getX() + " y:" + loc.getY() + " z:" + loc.getZ()));
									ClipboardFormat.SCHEMATIC.load(MainGame.getGameFunctions().getSchematicFile(map)).paste(world, vector, false, true, null);
							    } catch (IOException e) {
									e.printStackTrace();
								}
								
							    if(usPlayerVar)player.sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &bLoading complete for game " + game.getId()));							
							}
						}
					}
				}
			}
		});
	}

}

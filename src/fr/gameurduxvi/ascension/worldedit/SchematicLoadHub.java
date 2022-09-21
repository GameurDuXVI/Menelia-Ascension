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
import fr.gameurduxvi.meneliaapi.MeneliaAPI;

public class SchematicLoadHub implements Runnable {
	
	private Player player;
	private boolean useNumber;
	private int gameNumber;

	public SchematicLoadHub(Player player, boolean useNumber, int gameNumber) {
		this.player = player;
		this.useNumber = useNumber;
		this.gameNumber = gameNumber;
	}

	@Override
	public void run() {
		Bukkit.getScheduler().runTaskAsynchronously(MainGame.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				if(useNumber) {
					player.sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &bLoading hub for game " + gameNumber));
							
					Location loc = new Location(MainGame.getInstance().getSpawnLocation().getWorld(), (gameNumber * 5000) , 70, 0);
					Vector vector = new Vector(loc.getX(), loc.getY(), loc.getZ());
					BukkitWorld world = new BukkitWorld(loc.getWorld());
								
					try {
						Bukkit.getConsoleSender().sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &e" + player.getName() + " &6request for pasting a schematic from &e" + MainGame.getGameFunctions().getSchematicFile("Hub").getPath() + " &6in world &e" + loc.getWorld().getName() + " &6at &ex:" + loc.getX() + " y:" + loc.getY() + " z:" + loc.getZ()));
						ClipboardFormat.SCHEMATIC.load(MainGame.getGameFunctions().getSchematicFile("Hub")).paste(world, vector, false, true, null);
					} catch (IOException e) {
						e.printStackTrace();
					}
								
					player.sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &bLoading complete for game " + gameNumber));	
				}
				else {
					for(Games game: MainGame.getInstance().getGames()) {
						player.sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &bLoading hub for game " + game.getId()));
							
						Location loc = new Location(MainGame.getInstance().getSpawnLocation().getWorld(), (game.getId() * 5000) , 70, 0);
						Vector vector = new Vector(loc.getX(), loc.getY(), loc.getZ());
					    BukkitWorld world = new BukkitWorld(loc.getWorld());
								
					    try {
							Bukkit.getConsoleSender().sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &e" + player.getName() + " &6request for pasting a schematic from &e" + MainGame.getGameFunctions().getSchematicFile("Hub").getPath() + " &6in world &e" + loc.getWorld().getName() + " &6at &ex:" + loc.getX() + " y:" + loc.getY() + " z:" + loc.getZ()));
							ClipboardFormat.SCHEMATIC.load(MainGame.getGameFunctions().getSchematicFile("Hub")).paste(world, vector, false, true, null);
						} catch (IOException e) {
							e.printStackTrace();
						}
								
					    player.sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &bLoading complete for game " + game.getId()));							
					}
				}
			}
		});
	}

}

package fr.gameurduxvi.ascension.worldedit;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.boydti.fawe.object.schematic.Schematic;
import com.boydti.fawe.util.TaskManager;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.regions.CuboidRegion;

import fr.gameurduxvi.ascension.MainGame;
import fr.gameurduxvi.ascension.database.Maps;
import fr.gameurduxvi.meneliaapi.MeneliaAPI;

public class SchematicGenerate implements Runnable {

	private Player player;
	private String map;

	public SchematicGenerate(Player player, String map) {
		this.player = player;
		this.map = map;
	}
	@Override
	public void run() {
		TaskManager.IMP.async(new Runnable() {
		    @Override
		    public void run() {
		    	for(Maps loopMap: MainGame.getInstance().getMaps()) {
					if(map.equalsIgnoreCase(loopMap.getName())) {
						Location loc1 = new Location(loopMap.getBuildCyan().getWorld(), -500 , 0, (loopMap.getId() * 5000) - 500);
						Location loc2 = new Location(loopMap.getBuildCyan().getWorld(), 500 , 250, (loopMap.getId() * 5000) + 500);
						
						Vector bot = new Vector(loc1.getX(), loc1.getY(), loc1.getZ());
						Vector top = new Vector(loc2.getX(), loc2.getY(), loc2.getZ());@SuppressWarnings("deprecation")
						CuboidRegion region = new CuboidRegion(new BukkitWorld(loc1.getWorld()), bot, top);
						Schematic schem = new Schematic(region);
						Bukkit.getScheduler().runTaskLaterAsynchronously(MainGame.getInstance(), new Runnable() {
							
							@Override
							public void run() {
								try {
									schem.save(MainGame.getGameFunctions().getSchematicFile(map), ClipboardFormat.SCHEMATIC);
								} catch (IOException e) {
								}
								player.sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &aCreating complete !"));
							}
						}, 400);
					}
				}
		    }
		});
	}

}

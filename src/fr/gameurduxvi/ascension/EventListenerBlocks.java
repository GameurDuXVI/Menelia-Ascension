package fr.gameurduxvi.ascension;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import fr.gameurduxvi.ascension.database.Games;
import fr.gameurduxvi.ascension.database.GamesData;
import fr.gameurduxvi.ascension.database.Maps;

public class EventListenerBlocks implements Listener{
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if(e.getPlayer().getGameMode().equals(GameMode.ADVENTURE)||e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
			if(MainGame.getGameFunctions().isInGame((Player) e.getPlayer())) {
				Games game = MainGame.getGameFunctions().getGame((Player) e.getPlayer());
				Maps map = MainGame.getGameFunctions().getMap(game);
				if(game.getInGame()) {
					for(GamesData data: game.getPlayers()) {
						if(data.getPlayer().equals(e.getPlayer())) {
							
							boolean place = false;
							
							if(data.getTeam() == 1) {
								Location loc1 = new Location(map.getBuildGreen().getWorld(), (5000 * game.getId()) + map.getBuildGreen().getX() + 3, map.getBuildGreen().getY() + 75, map.getBuildGreen().getZ() + 3);
								Location loc2 = new Location(map.getBuildGreen().getWorld(), (5000 * game.getId()) + map.getBuildGreen().getX() - 3, map.getBuildGreen().getY(), map.getBuildGreen().getZ() - 3);
								if(playerIsInArea(e.getBlock().getLocation(), loc1, loc2)) {
									place = true;
								}
							}
							else if(data.getTeam() == 2) {
								Location loc1 = new Location(map.getBuildPurple().getWorld(), (5000 * game.getId()) + map.getBuildPurple().getX() + 3, map.getBuildPurple().getY() + 75, map.getBuildPurple().getZ() + 3);
								Location loc2 = new Location(map.getBuildPurple().getWorld(), (5000 * game.getId()) + map.getBuildPurple().getX() - 3, map.getBuildPurple().getY(), map.getBuildPurple().getZ() - 3);
								if(playerIsInArea(e.getBlock().getLocation(), loc1, loc2)) {
									place = true;
								}
							}
							else if(data.getTeam() == 3) {
								Location loc1 = new Location(map.getBuildYellow().getWorld(), (5000 * game.getId()) + map.getBuildYellow().getX() + 3, map.getBuildYellow().getY() + 75, map.getBuildYellow().getZ() + 3);
								Location loc2 = new Location(map.getBuildYellow().getWorld(), (5000 * game.getId()) + map.getBuildYellow().getX() - 3, map.getBuildYellow().getY(), map.getBuildYellow().getZ() - 3);
								if(playerIsInArea(e.getBlock().getLocation(), loc1, loc2)) {
									place = true;
								}
							}
							else if(data.getTeam() == 4) {
								Location loc1 = new Location(map.getBuildCyan().getWorld(), (5000 * game.getId()) + map.getBuildCyan().getX() + 3, map.getBuildCyan().getY() + 75, map.getBuildCyan().getZ() + 3);
								Location loc2 = new Location(map.getBuildCyan().getWorld(), (5000 * game.getId()) + map.getBuildCyan().getX() - 3, map.getBuildCyan().getY(), map.getBuildCyan().getZ() - 3);
								if(playerIsInArea(e.getBlock().getLocation(), loc1, loc2)) {
									place = true;
								}
							}
							
							if(!place) {
								//e.getPlayer().sendMessage(MeneliaAPI.getInstance().getLang(e.getPlayer(), "§cTu ne peux pas placer ici !", "§cYou can't place here !"));
								e.setCancelled(true);
							}
						}
					}
				}
				else {
					e.setCancelled(true);
				}				
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if(e.getPlayer().getGameMode().equals(GameMode.ADVENTURE)||e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
			if(MainGame.getGameFunctions().isInGame((Player) e.getPlayer())) {
				Games game = MainGame.getGameFunctions().getGame((Player) e.getPlayer());
				Maps map = MainGame.getGameFunctions().getMap(game);
				if(game.getInGame()) {
					for(GamesData data: game.getPlayers()) {
						if(data.getPlayer().equals(e.getPlayer())) {
							
							boolean place = false;
							
							if(data.getTeam() == 1) {
								Location loc1 = new Location(map.getBuildGreen().getWorld(), (5000 * game.getId()) + map.getBuildGreen().getX() + 3, map.getBuildGreen().getY() + 75, map.getBuildGreen().getZ() + 3);
								Location loc2 = new Location(map.getBuildGreen().getWorld(), (5000 * game.getId()) + map.getBuildGreen().getX() - 3, map.getBuildGreen().getY(), map.getBuildGreen().getZ() - 3);
								if(playerIsInArea(e.getBlock().getLocation(), loc1, loc2)) {
									place = true;
								}
							}
							else if(data.getTeam() == 2) {
								Location loc1 = new Location(map.getBuildPurple().getWorld(), (5000 * game.getId()) + map.getBuildPurple().getX() + 3, map.getBuildPurple().getY() + 75, map.getBuildPurple().getZ() + 3);
								Location loc2 = new Location(map.getBuildPurple().getWorld(), (5000 * game.getId()) + map.getBuildPurple().getX() - 3, map.getBuildPurple().getY(), map.getBuildPurple().getZ() - 3);
								if(playerIsInArea(e.getBlock().getLocation(), loc1, loc2)) {
									place = true;
								}
							}
							else if(data.getTeam() == 3) {
								Location loc1 = new Location(map.getBuildYellow().getWorld(), (5000 * game.getId()) + map.getBuildYellow().getX() + 3, map.getBuildYellow().getY() + 75, map.getBuildYellow().getZ() + 3);
								Location loc2 = new Location(map.getBuildYellow().getWorld(), (5000 * game.getId()) + map.getBuildYellow().getX() - 3, map.getBuildYellow().getY(), map.getBuildYellow().getZ() - 3);
								if(playerIsInArea(e.getBlock().getLocation(), loc1, loc2)) {
									place = true;
								}
							}
							else if(data.getTeam() == 4) {
								Location loc1 = new Location(map.getBuildCyan().getWorld(), (5000 * game.getId()) + map.getBuildCyan().getX() + 3, map.getBuildCyan().getY() + 75, map.getBuildCyan().getZ() + 3);
								Location loc2 = new Location(map.getBuildCyan().getWorld(), (5000 * game.getId()) + map.getBuildCyan().getX() - 3, map.getBuildCyan().getY(), map.getBuildCyan().getZ() - 3);
								if(playerIsInArea(e.getBlock().getLocation(), loc1, loc2)) {
									place = true;
								}
							}
							
							if(!place) {
								//e.getPlayer().sendMessage(MeneliaAPI.getInstance().getLang(e.getPlayer(), "§cTu ne peux pas casser ici !", "§cYou can't break here !"));
								e.setCancelled(true);
							}
							/*else {
								if(e.getBlock().getType().equals(Material.SAND)) {
									e.setCancelled(true);
									e.getBlock().setType(Material.AIR);
									
							    	ItemStack sand = new ItemStack(Material.SAND);
							    	ItemMeta metaSand = sand.getItemMeta();
							    	
							    	metaSand.setDisplayName(MeneliaAPI.getInstance().getLang(e.getPlayer(), "§9Sable", "§9Sand"));
							    	metaSand.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
							    	
							    	sand.setItemMeta(metaSand);
							    	
									e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), sand);
								}
								else if(e.getBlock().getType().equals(Material.LADDER)) {
									e.setCancelled(true);
									e.getBlock().setType(Material.AIR);
									
									ItemStack ladder = new ItemStack(Material.LADDER);
							    	ItemMeta metaLadder = ladder.getItemMeta();
							    	
							    	metaLadder.setDisplayName(MeneliaAPI.getInstance().getLang(e.getPlayer(), "§9Échelle", "§9Ladder"));
							    	metaLadder.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
							    	
							    	ladder.setItemMeta(metaLadder);
							    	
									e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), ladder);
								}
							}*/
						}
					}
				}
				else {
					e.setCancelled(true);
				}				
			}
		}
	}
	
	public boolean playerIsInArea(Location loc, Location loc1, Location loc2){
        /*int x1,x2,y1,y2,z1,z2;
        x1 = loc1.getX() > loc2.getX() ? (int) loc2.getX() : (int) loc1.getX();
        y1 = loc1.getY() > loc2.getY() ? (int) loc2.getY() : (int) loc1.getY();
        z1 = loc1.getZ() > loc2.getZ() ? (int) loc2.getZ() : (int) loc1.getZ();
       
        x2 = ((int) loc1.getX()) == x1 ? (int) loc2.getX() : (int) loc1.getX();
        y2 = ((int) loc1.getY()) == y1 ? (int) loc2.getY() : (int) loc1.getY();
        z2 = ((int) loc1.getZ()) == z1 ? (int) loc2.getZ() : (int) loc1.getZ();
               
        for (int x = x1; x <= x2; x++){
        	for (int y = y1; y <= y2; y++){
        		for (int z = z1; z <= z2; z++){
        			if (loc.getBlock().getLocation() == new Location(loc1.getWorld(),x,y,z)) {
        				Bukkit.broadcastMessage("In area : true");
        				return true;
        			}
        		}
        	}
        }*/
		/*Bukkit.broadcastMessage("§1" + isBetween(loc.getX(), loc1.getX(), loc2.getX()));
		Bukkit.broadcastMessage("§2" + isBetween(loc.getY(), loc1.getY(), loc2.getY()));
		Bukkit.broadcastMessage("§3" + isBetween(loc.getZ(), loc1.getZ(), loc2.getZ()));*/
		if(isBetween(loc.getX(), loc1.getX(), loc2.getX()) && isBetween(loc.getY(), loc1.getY(), loc2.getY()) && isBetween(loc.getZ(), loc1.getZ(), loc2.getZ())) {
			return true;
		}    
        return false;
    }
	
	public boolean isBetween(double num, double a, double b) {
		if(a > b) {
			if(num >= b && num <= a) {
				return true;
			}
		}
		else {
			if(num <= b && num >= a) {
				return true;
			}
		}
		
		return false;
	}
}

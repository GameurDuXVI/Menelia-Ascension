package fr.gameurduxvi.ascension.Creators;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.gameurduxvi.ascension.MainGame;
import fr.gameurduxvi.ascension.database.Games;

public class VillagerCreator extends BukkitRunnable {
	
	private Games game;
	private Location loc;
	@SuppressWarnings("unused")
	private String name;
	private Villager villager;
	//private ArmorStand armorStand;	
	
	public VillagerCreator(Games game, Location loc, String name) {
		this.game = game;
		this.loc = loc;
		this.name = name;
		Bukkit.getScheduler().runTaskLater(MainGame.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				/*Location armorStandLoc = new Location(loc.getWorld(), loc.getX(), loc.getY() + 0.2, loc.getZ());
				armorStand = (ArmorStand) armorStandLoc.getWorld().spawn(armorStandLoc, ArmorStand.class);
				armorStand.setGravity(false);
				armorStand.setCanPickupItems(false);
				armorStand.setCustomName("Demande A Suuuucre pour un message ici");
				armorStand.setCustomNameVisible(true);
				armorStand.setVisible(false);*/
				
				
				
				villager = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
				villager.setCustomName(name);
				villager.setAdult();
				villager.setCollidable(false);
				villager.setAI(false);
				villager.setInvulnerable(true);
				villager.setSilent(true);
			}
		}, 20);
	}
	
	@Override
	public void run() {
		if(game.getPlayers().size()<=1) {
			villager.remove();
			//armorStand.remove();
			cancel();
		}
		Collection<Entity> players = loc.getWorld().getNearbyEntities(loc, 3, 3, 3);
		for(Entity e: players) {
			if(e instanceof Player) {
				Vector dir = ((Player) e).getEyeLocation().clone().subtract(villager.getEyeLocation()).toVector();
		        Location loc = villager.getLocation().setDirection(dir);
		        villager.teleport(loc);
			}
		}
		
	}

}

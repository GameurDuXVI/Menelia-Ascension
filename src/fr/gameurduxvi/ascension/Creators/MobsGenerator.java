package fr.gameurduxvi.ascension.Creators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.gameurduxvi.ascension.MainGame;

public class MobsGenerator extends BukkitRunnable {
	
	Random rand = new Random();
	int randomAmount = 0;
	
	private int sec;
	
	private Location loc;
	private int level;
	private int amountMin;
	private int amountMax;
	private int amountTotal;
	
	private List<Location> spawnLocation = new ArrayList<>();
	private List<Entity> mobs = new ArrayList<>();
	
	public MobsGenerator(Location loc, int level, int amountMin, int amountMax, int amountTotal) {
		this.loc = loc;
		this.level = level;
		this.amountMin = amountMin;
		this.amountMax = amountMax;
		this.amountTotal = amountTotal;
		this.sec = MainGame.getInstance().mobsSpawninterval - MainGame.getInstance().firstMobSpawnTime;
		
		for(Block block: getBlocksAroundCenter(loc, 30)) {
			if(!block.getType().equals(Material.AIR) && !block.getType().equals(Material.LEAVES)) {
				Location aboveLoc = new Location(block.getWorld(), block.getLocation().getX(), block.getLocation().getY() + 1, block.getLocation().getZ());
				
				if(aboveLoc.getBlock().getType().equals(Material.AIR)) {
					spawnLocation.add(aboveLoc);
				}
			}
		}
	}
	
	@Override
	public void run() {
		sec++;
		if(sec >= MainGame.getInstance().mobsSpawninterval + randomAmount) {
			sec = 0;
			
			randomAmount = rand.nextInt(MainGame.getInstance().maxRandomMobSpawnAmount);
			
			/*Collection<Entity> mobs = loc.getWorld().getNearbyEntities(loc, 20, 20, 20);
			int size = mobs.size();
			for(Entity e: mobs) {
				if(e instanceof Item) {
					size--;
				}
			}*/
			
			List<Entity> deathMobs = new ArrayList<>();
			if(mobs.size() > 0) {
				for(Entity e: mobs) {
					if(e.isDead()) {
						deathMobs.add(e);
					}
				}
				for(Entity e: deathMobs) {
					mobs.remove(e);
				}
			}
			
			if(mobs.size() < amountTotal) {
				int amountMaxToSpawn = amountTotal - mobs.size();
				
				int diff = amountMax - amountMin;
				int amountToSpawn = amountMin + rand.nextInt(diff);
				
				if(amountToSpawn > amountMaxToSpawn) {
					amountToSpawn = amountMaxToSpawn;
				}
				
				for(int i = 0; i < amountToSpawn; i++) {
					boolean whileLoop = true;
					Location randomLoc = null;
					int timesLoop = 0;
					while(whileLoop) {
						timesLoop++;
						if(timesLoop >= 50) {
							whileLoop = false;
							break;
						}
						if(spawnLocation.size() == 0) {
							whileLoop = false;
							break;
						}
						
						randomLoc = spawnLocation.get(rand.nextInt(spawnLocation.size()));
						randomLoc = new Location(randomLoc.getWorld(), randomLoc.getBlockX() + 0.5, randomLoc.getBlockY(), randomLoc.getBlockZ() + 0.5);
						
						boolean playerInRange = false;
						Collection<Entity> entitiesAround = randomLoc.getWorld().getNearbyEntities(randomLoc, 10, 10, 10);
						for(Entity e: entitiesAround) {
							if(e instanceof Player) {
								playerInRange = true;
							}
						}
						if(!playerInRange) {
							whileLoop = false;
						}
					}
					
					if(level == 1) {
						EntityType mobType = MainGame.getInstance().level1List.get(rand.nextInt(MainGame.getInstance().level1List.size()));
						
						mobs.add(loc.getWorld().spawnEntity(randomLoc, mobType));
					}
					else if(level == 2) {
						EntityType mobType = MainGame.getInstance().level2List.get(rand.nextInt(MainGame.getInstance().level2List.size()));
						
						mobs.add(loc.getWorld().spawnEntity(randomLoc, mobType));
					}
					else if(level == 3) {
						EntityType mobType = MainGame.getInstance().level3List.get(rand.nextInt(MainGame.getInstance().level3List.size()));
						
						mobs.add(loc.getWorld().spawnEntity(randomLoc, mobType));
					}
					else if(level == 4) {
						EntityType mobType = MainGame.getInstance().level4List.get(rand.nextInt(MainGame.getInstance().level4List.size()));
						
						mobs.add(loc.getWorld().spawnEntity(randomLoc, mobType));
					}
					else if(level == 5) {
						EntityType mobType = MainGame.getInstance().level5List.get(rand.nextInt(MainGame.getInstance().level5List.size()));
						
						mobs.add(loc.getWorld().spawnEntity(randomLoc, mobType));
					}
				}
			}
		}
	}
	
	public static ArrayList<Block> getBlocksAroundCenter(Location loc, int radius) {
	    ArrayList<Block> blocks = new ArrayList<Block>();
	   
	    for (int x = (loc.getBlockX()-radius); x <= (loc.getBlockX()+radius); x++) {
	        for (int y = (loc.getBlockY()-radius); y <= (loc.getBlockY()+radius); y++) {
	            for (int z = (loc.getBlockZ()-radius); z <= (loc.getBlockZ()+radius); z++) {
	                Location l = new Location(loc.getWorld(), x, y, z);
	                if (l.distance(loc) <= radius) {
	                    blocks.add(l.getBlock());
	                }
	            }
	        }
	    }
	   
	    return blocks;
	}
	
}

package fr.gameurduxvi.ascension;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import fr.gameurduxvi.ascension.database.Games;
import fr.gameurduxvi.ascension.database.GamesData;
import fr.gameurduxvi.ascension.database.Maps;
import fr.gameurduxvi.meneliaapi.MeneliaAPI;

public class GameInstance extends BukkitRunnable {
	
	private int times;
	private ScoreboardManager manager;
	private Games game;
	//private Maps map;
	
	private int sec;
	private int min;
	
	private boolean gameEnded;
	
	@SuppressWarnings("deprecation")
	public GameInstance(Games game, Maps map, ScoreboardManager manager) {
		this.game = game;
		//this.map = map;
		this.manager = manager;
		this.gameEnded = false;
		
		for(GamesData data: game.getPlayers()) {
			data.getPlayer().setScoreboard(MainGame.getInstance().manager.getNewScoreboard());
		}
		
		Bukkit.getScheduler().runTaskTimerAsynchronously(MainGame.getInstance(), new BukkitRunnable() {
			
			@Override
			public void run() {
				if(gameEnded) cancel();
				for(Entity e: MainGame.getInstance().spawnLocation.getWorld().getEntities()) {
					if(!(e instanceof Player || e instanceof Item || e instanceof ExperienceOrb || e instanceof Villager || e instanceof ArmorStand)){
						LivingEntity lEntity = (LivingEntity) e;
						lEntity.setRemoveWhenFarAway(true);
						
						char uppercaseChar = e.getType().getName().charAt(0);
						String uppercase = Character.toString(uppercaseChar).toUpperCase();
						
						String lowercase = e.getType().getName().toLowerCase();
						
						int health = (int) Math.round(lEntity.getHealth());
						if(Math.round(lEntity.getHealth())<1) {
							health = 1;
						}
						
						e.setCustomName("§6" + uppercase + lowercase.substring(1) + " §a" + health + "§4❤");
					}
				}
			}
		}, 0, 5);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		times++;
		if(times >= 5) {
			times = 0;
			
			// 1 second loop
			
			sec++;
			if(sec >= 60) {
				sec = 0;
				min++;
			}
			
			String affichage_sec;
			if(sec<10) {
				affichage_sec = "0" + sec;
			}
			else {
				affichage_sec = "" + sec;
			}
			
			for(GamesData data: game.getPlayers()) {
				if(data.getPlayer().getScoreboard().equals(manager.getMainScoreboard())) data.getPlayer().setScoreboard(manager.getNewScoreboard());
				
				Scoreboard board = data.getPlayer().getScoreboard();
				boolean notExist = board.getObjective(data.getPlayer().getName()) == null;
			    Objective objective = board.getObjective(data.getPlayer().getName()) == null ? board.registerNewObjective(data.getPlayer().getName(), "dummy") : board.getObjective(data.getPlayer().getName());
			    
			    if(notExist) objective.setDisplaySlot(DisplaySlot.SIDEBAR);
			    
			    objective.setDisplayName("&8<- &5&lAscension &8->".replace("&", "§"));
			    
			    Team green = null;
			    if(board.getTeam("green") == null) {
			    	green = board.registerNewTeam("green");
			    }
			    else {
			    	green = board.getTeam("green");
			    }			    
				green.setPrefix(MeneliaAPI.getInstance().colorize("&a"));
				
				Team purple = null;
			    if(board.getTeam("purple") == null) {
			    	purple = board.registerNewTeam("purple");
			    }
			    else {
			    	purple = board.getTeam("purple");
			    }			    
			    purple.setPrefix(MeneliaAPI.getInstance().colorize("&5"));
			    
			    Team yellow = null;
			    if(board.getTeam("yellow") == null) {
			    	yellow = board.registerNewTeam("yellow");
			    }
			    else {
			    	yellow = board.getTeam("yellow");
			    }			    
			    yellow.setPrefix(MeneliaAPI.getInstance().colorize("&e"));
			    
			    Team cyan = null;
			    if(board.getTeam("cyan") == null) {
			    	cyan = board.registerNewTeam("cyan");
			    }
			    else {
			    	cyan = board.getTeam("cyan");
			    }			    
			    cyan.setPrefix(MeneliaAPI.getInstance().colorize("&3"));
				
				for(GamesData data2: game.getPlayers()) {
					if(data2.getTeam() == 1) {
						green.addPlayer(data2.getPlayer());
					}
					if(data2.getTeam() == 2) {
						purple.addPlayer(data2.getPlayer());
					}
					if(data2.getTeam() == 3) {
						yellow.addPlayer(data2.getPlayer());
					}
					if(data2.getTeam() == 4) {
						cyan.addPlayer(data2.getPlayer());
					}
				}
				
				Map<Integer, String> list = new HashMap<Integer, String>();
				list.put(11, "&7" + MeneliaAPI.getFunctions().getDateFromDate(new Date(), "/") + " &f&nAsc-" + game.getId());
				list.put(10, "&1");
				list.put(9, "&7⇨ Timer &f: &a" + min + "&8:&a" + affichage_sec);
				list.put(8, "&2");
				list.put(7, "&7⇨ Joueurs &f: &b" + game.getPlayers().size());
				list.put(6, "&3");
				list.put(5, "&7⇨ Monnaie &f: &e" + data.getMoney());
				list.put(4, "&4");
				list.put(3, "&7⇨ Kills &f: &b" + data.getKills());
				list.put(2, "&7⇨ Morts &f: &b" + data.getDeath());
				list.put(1, "&5");
				list.put(0, "&f[&5play&f.&5menelia&f.&5fr&f]");
				
				int i = 0;
				for(Map.Entry<Integer, String> entry : list.entrySet()) {
					i++;
					Bukkit.getScheduler().runTaskLater(MainGame.getInstance(), new Runnable() {
						
						@Override
						public void run() {
							replaceScore(objective, entry.getKey(), MeneliaAPI.getInstance().colorize(entry.getValue()));
						}
					}, i);
				}
				i++;
				Bukkit.getScheduler().runTaskLater(MainGame.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						data.getPlayer().setScoreboard(board);
					}
				}, i);
			    
			    
			    data.getPlayer().setScoreboard(board);	
			}
		}	
		for(GamesData data: game.getPlayers()) {
			if(data.getPlayer().getLocation().getBlockY() <=4 && data.getPlayer().getGameMode() != GameMode.SPECTATOR) {
				String color = "";
				data.setDeath(data.getDeath() + 1);
				if(data.getTeam() == 1) {
					color = "a";
				}
				else if(data.getTeam() == 2) {
					color = "5";
				}
				else if(data.getTeam() == 3) {
					color = "e";
				}
				else if(data.getTeam() == 4) {
					color = "3";
				}
				for(GamesData data2: game.getPlayers()) {
					data2.getPlayer().sendMessage("§7☠ §" + color + data.getPlayer().getName());
				}
				MainGame.getGameFunctions().deadSpectator(data.getPlayer());
			}
		}
		if(game.getPlayers().size() < 2) {
			if(!gameEnded) {
				gameEnded = true;
				Bukkit.broadcastMessage("Ending game num " + game.getId());
				
				// Killing mobs before kicking players
				
				Location middleMapLocation = new Location(Bukkit.getWorld("ascension"), (game.getId() * 5000), 120, (game.getMapId()*5000));
				Collection<Entity> mobs = middleMapLocation.getWorld().getNearbyEntities(middleMapLocation, 1000, 1000, 1000);
				Bukkit.getConsoleSender().sendMessage(mobs.size() + " mobs found");
				for(Entity e: mobs) {
					if(!(e instanceof Player)) {
						e.remove();
					}
					//Bukkit.broadcastMessage("§9Killing a " + e.getType() + " with name " + e.getCustomName());
				}
				
				
				Bukkit.getScheduler().runTaskLater(MainGame.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						for(GamesData data: game.getPlayers()) {
							MainGame.getGameFunctions().leaveGame(data.getPlayer(), true);
						}						
					}
				}, 5);
				MainGame.getGameFunctions().ressetMap(game);
				cancel();
				
			}
		}
		// 0.20 second loop
		
		//TeleportPretection();
	}
	
	/*public void TeleportPretection() {
		Location greenLoc = new Location(map.getLowerTeleporterGreen().getWorld(), map.getLowerTeleporterGreen().getX() + (5000 * game.getId()), map.getLowerTeleporterGreen().getY(), map.getLowerTeleporterGreen().getZ());
		Collection<Entity> mobsGreen = map.getLowerTeleporterGreen().getWorld().getNearbyEntities(greenLoc, 10, 10, 10);
		for(Entity e1: mobsGreen) {
			if(e1 instanceof Player) {
				
				String p1 = ((Player) e1).getName();
				
				Bukkit.broadcastMessage("1 " + p1);
				for(Entity e2: mobsGreen) {
					if(e2 instanceof Zombie) {
						Bukkit.broadcastMessage("2 Zombie");
						if(((Creature) e2).getTarget() instanceof Player) {
							
							String p2 = ((Player) ((Creature) e2).getTarget()).getName();
							Bukkit.broadcastMessage("3 " + p2);
							if(p2.equals(p1)) {
								((Creature) e2).setTarget(null);
								Bukkit.broadcastMessage("4 Stop target");
							}
						}
						//--
					}
				}
				//--
			}
		}
	}*/
	//((Creature) e2).setTarget(null);
	
	public String getEntryFromScore(Objective o, int score) {
	    if(o == null) return null;
	    if(!hasScoreTaken(o, score)) return null;
	    for (String s : o.getScoreboard().getEntries()) {
	        if(o.getScore(s).getScore() == score) return o.getScore(s).getEntry();
	    }
	    return null;
	}

	public boolean hasScoreTaken(Objective o, int score) {
	    for (String s : o.getScoreboard().getEntries()) {
	        if(o.getScore(s).getScore() == score) return true;
	    }
	    return false;
	}

	public void replaceScore(Objective o, int score, String name) {
	    if(hasScoreTaken(o, score)) {
	        if(getEntryFromScore(o, score).equalsIgnoreCase(name)) return;
	        if(!(getEntryFromScore(o, score).equalsIgnoreCase(name))) o.getScoreboard().resetScores(getEntryFromScore(o, score));
	    }
	    o.getScore(name).setScore(score);
	}

}

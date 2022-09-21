package fr.gameurduxvi.ascension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;

import fr.gameurduxvi.ascension.Creators.MobsGenerator;
import fr.gameurduxvi.ascension.Creators.VillagerCreator;
import fr.gameurduxvi.ascension.database.Games;
import fr.gameurduxvi.ascension.database.GamesData;
import fr.gameurduxvi.ascension.database.Maps;
import fr.gameurduxvi.ascension.worldedit.SchematicLoad;
import fr.gameurduxvi.ascension.worldedit.SchematicLoadHub;
import fr.gameurduxvi.meneliaapi.JSON;
import fr.gameurduxvi.meneliaapi.MeneliaAPI;

public class GameFunctions {
	
	public void makeJoin(Player player) {
		if(isInGame(player)) {
			player.sendMessage(MeneliaAPI.getInstance().colorize(MeneliaAPI.getInstance().getLang(player, "&cVous êtes déja dans une partie !", "&cYou are already in a game !")));
			return;
		}
		else {
			for(Games game: MainGame.getInstance().getGames()) {
				if(!game.getInGame()) {
					if(game.getPlayers().size() < MainGame.getInstance().maxInGame) {
						joinGame(player, game.getId());
					}
					break;
				}
			}
		}		
	}
	
	@SuppressWarnings({"deprecation"})
	public void joinGame(Player player, int gameNum) {
		if(isInGame(player)) {
			player.sendMessage(MeneliaAPI.getInstance().colorize(MeneliaAPI.getInstance().getLang(player, "&cVous êtes déja dans une partie !", "&cYou are already in a game !")));
		}
		for(Games game: MainGame.getInstance().getGames()) {
			if(game.getId() == gameNum) {
				game.addPlayer(player);
				
				player.setScoreboard(MainGame.getInstance().manager.getNewScoreboard());
				
				for(Player loopPlayer: Bukkit.getOnlinePlayers()) {
					player.hidePlayer(loopPlayer);
				}
				
				for(GamesData data: game.getPlayers()) {
					data.getPlayer().sendMessage(MeneliaAPI.getInstance().getLang(player, "§8" + player.getName() + " §7a rejoint la partie ! (§e" + game.getPlayers().size() + "§7/§e" + MainGame.getInstance().maxInGame + "§7)", "§8" + player.getName() + " §7has joined the game ! (§e" + game.getPlayers().size() + "§7/§e" + MainGame.getInstance().maxInGame + "§7)"));
					player.showPlayer(data.getPlayer());
					data.getPlayer().showPlayer(player);
				}
				
				player.setGameMode(GameMode.ADVENTURE);
				
				player.setMaxHealth(20);
				player.setFoodLevel(20);
				player.setExp(0);
				player.setLevel(0);
				
				Location loc = new Location(MainGame.getInstance().spawnLocation.getWorld(), MainGame.getInstance().spawnLocation.getX() + (game.getId() * 5000), MainGame.getInstance().spawnLocation.getY(), MainGame.getInstance().spawnLocation.getZ());
				player.teleport(loc);
				
				player.setGameMode(GameMode.ADVENTURE);

				player.getEnderChest().clear();
				player.getInventory().clear();
				
				ItemStack Banner = new ItemStack(org.bukkit.Material.BANNER, 1);
				BannerMeta customMBanner = (BannerMeta) Banner.getItemMeta();	
				customMBanner.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§bTeam", "§bTeam"));	
				customMBanner.setBaseColor(DyeColor.WHITE);
				Banner.setItemMeta(customMBanner);
				player.getInventory().setItem(0, Banner);
				
				ItemStack Paper = new ItemStack(org.bukkit.Material.PAPER, 1);
				ItemMeta customMPaper = Paper.getItemMeta();	
				customMPaper.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§bVote", "§bVote"));		
				Paper.setItemMeta(customMPaper);
				player.getInventory().setItem(4, Paper);
				
				ItemStack Bed = new ItemStack(org.bukkit.Material.BED, 1);
				ItemMeta customMBed = Bed.getItemMeta();	
				customMBed.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§bHub", "§bHub"));		
				Bed.setItemMeta(customMBed);
				player.getInventory().setItem(8, Bed);
				
				game.setTimer(false);
				Bukkit.getScheduler().runTaskLater(MainGame.getInstance(), new Runnable() {
					public void run() {
						checkToStart(game.getId());
						for(GamesData data: game.getPlayers()) {
							data.getPlayer().showPlayer(player);
						}
						
					}
				}, 5);
				
				ArrayList<String> players = new ArrayList<>();
				for(GamesData data: getGame(player).getPlayers()) {
					players.add(data.getPlayer().getUniqueId().toString());
				}
				if(players.size() == 0) {
					MeneliaAPI.getInstance().getGamesManager().getGame("ascension", gameNum).deletePlayers();;
				}
				else {
					MeneliaAPI.getInstance().getGamesManager().getGame("ascension", gameNum).setPlayers(players);
				}
				
				return;
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void refreshScoreboard(int gameId, int time) {
		for(Games game: MainGame.getInstance().getGames()) {
			if(game.getId() == gameId) {
				for(GamesData data: game.getPlayers()) {
					Scoreboard board = data.getPlayer().getScoreboard();
					
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
						if(data2.getTeam() == 1) green.addPlayer(data2.getPlayer());
						if(data2.getTeam() == 2) purple.addPlayer(data2.getPlayer());
						if(data2.getTeam() == 3) yellow.addPlayer(data2.getPlayer());
						if(data2.getTeam() == 4) cyan.addPlayer(data2.getPlayer());
					}
					
					boolean notExist = board.getObjective(data.getPlayer().getName()) == null;
					Objective objective = board.getObjective(data.getPlayer().getName()) == null ? board.registerNewObjective(data.getPlayer().getName(), "dummy") : board.getObjective(data.getPlayer().getName());
					if(notExist)objective.setDisplaySlot(DisplaySlot.SIDEBAR);
					
					objective.setDisplayName("&8<- &5&lAscension &8->".replace("&", "§"));
					
					Map<Integer, String> list = new HashMap<Integer, String>();
					list.put(7, "&7" + MeneliaAPI.getFunctions().getDateFromDate(new Date(), "/") + " &f&nAsc-" + game.getId());
					list.put(6, "&1");
					list.put(5, "&7⇨ Joueurs &f: &c" + game.getPlayers().size() + "&8/&c" + MainGame.getInstance().maxInGame);
					list.put(4, "&2");
					if(time > 0) {
						list.put(3, "&7⇨ Timer &f: &a" + time);
					}
					else {
						list.put(3, MeneliaAPI.getInstance().getLang(data.getPlayer(), "§c En attente d'autres joueurs", "§c Waiting for players"));
					}
					list.put(2, "&3");
					list.put(1, "&4");
					list.put(0, "&f[&5play&f.&5menelia&f.&5fr&f]");
					
					int i = 1;
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
		}
	}
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
	public void checkToStart(int gameId) {
		for(Games game: MainGame.getInstance().getGames()) {
			if(game.getId() == gameId) {
				if(game.getPlayers().size() < MainGame.getInstance().minToStartGame) {
					game.setTimer(false);
					game.setInGame(false);
					refreshScoreboard(game.getId(), 0);
				}
				else {
					if(game.getTimer()) {
						return;
					}
					game.setTimer(true);
					BukkitRunnable task = new BukkitRunnable() {
						
						private int time = MainGame.getInstance().timeInWaitingRoom;
						private int times;
							
						@Override
						public void run() {
							times++;
							if(times >= 5) {
								times = 0;
								time--;
								if(time <= 0) {
									startGame(game);
									cancel();
								}
								else {
									refreshScoreboard(game.getId(), time);
								}
							}
							if(game.getPlayers().size() < MainGame.getInstance().minToStartGame || !game.getTimer()) {
								game.setTimer(false);
								cancel();
							}
						}
					};
					task.runTaskTimer(MainGame.getInstance(), 0, 4);
				}
			}
		}
	}
	
	
	
	public void startGame(Games game) {
		game.setInGame(true);
		MeneliaAPI.getInstance().getGamesManager().getGame("ascension", game.getId()).setGameStatus(1);
				
		// move players from random team into teams
		for(GamesData data: game.getPlayers()) {
			if(data.getTeam() == 0) {
				MainGame.getEventHubListener().addToTeam(data.getPlayer(), 1, false);
				if(data.getTeam()!=1) {
					MainGame.getEventHubListener().addToTeam(data.getPlayer(), 2, false);
					if(data.getTeam()!=2) {
						MainGame.getEventHubListener().addToTeam(data.getPlayer(), 3, false);
						if(data.getTeam()!=3) {
							MainGame.getEventHubListener().addToTeam(data.getPlayer(), 4, false);
							if(data.getTeam()!=4) {
								Bukkit.broadcastMessage("error");
							}
						}
					}
				}
			}
		}		
				
		// selecting a map from votes
		int mapId = 1;
		game.setMapId(mapId);
		
		for(Maps map: MainGame.getInstance().getMaps()) {
			if(map.getId() == mapId) {
				
				int i = 0;
				for(GamesData data: game.getPlayers()) {
					i++;
					Bukkit.getScheduler().runTaskLater(MainGame.getInstance(), new Runnable() {
						
						@Override
						public void run() {
							gameSpawningPlayer(data.getPlayer());						
						}
					}, 2 + i);
				}
				
				Location middleMapLocation = new Location(map.getPlayerLocationGreen().getWorld(), (game.getId() * 5000), 120, (map.getId()*5000));
				Bukkit.getScheduler().runTaskLater(MainGame.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						Collection<Entity> mobs = middleMapLocation.getWorld().getNearbyEntities(middleMapLocation, 1000, 1000, 1000);
						Bukkit.getConsoleSender().sendMessage(mobs.size() + " mobs found in the rang around " + locationToText(middleMapLocation));
						for(Entity e: mobs) {
							if(!(e instanceof Player)) {
								e.remove();
							}
							//Bukkit.broadcastMessage("§9Killing a " + e.getType() + " with name " + e.getCustomName());
						}
					}
				}, 20);
												
				double x = (int) map.getSellGreen().getX();
				double y = (int) map.getSellGreen().getY();
				double z = (int) map.getSellGreen().getZ();
				Location locSellGreen = new Location(map.getSellGreen().getWorld(), (game.getId() * 5000) + x, y, z);
				VillagerCreator task1 = new VillagerCreator(game, locSellGreen, "§eSell");
				task1.runTaskTimer(MainGame.getInstance(), 20, 4);
		
				x = (int) map.getSellPurple().getX();
				y = (int) map.getSellPurple().getY();
				z = (int) map.getSellPurple().getZ();
				Location locSellPurple = new Location(map.getSellPurple().getWorld(), (game.getId() * 5000) + x, y, z);
				VillagerCreator task2 = new VillagerCreator(game, locSellPurple, "§eSell");
				task2.runTaskTimer(MainGame.getInstance(), 20, 4);
						
				x = (int) map.getSellYellow().getX();
				y = (int) map.getSellYellow().getY();
				z = (int) map.getSellYellow().getZ();
				Location locSellYellow = new Location(map.getSellYellow().getWorld(), (game.getId() * 5000) + x, y, z);
				VillagerCreator task3 = new VillagerCreator(game, locSellYellow, "§eSell");
				task3.runTaskTimer(MainGame.getInstance(), 20, 4);
						
				x = (int) map.getSellCyan().getX();
				y = (int) map.getSellCyan().getY();
				z = (int) map.getSellCyan().getZ();
				Location locSellCyan = new Location(map.getSellCyan().getWorld(), (game.getId() * 5000) + x, y, z);
				VillagerCreator task4 = new VillagerCreator(game, locSellCyan, "§eSell");
				task4.runTaskTimer(MainGame.getInstance(), 20, 4);
				
				//Shop Villagers
				
				x = (int) map.getShopGreen().getX();
				y = (int) map.getShopGreen().getY();
				z = (int) map.getShopGreen().getZ();
				Location locShopGreen = new Location(map.getShopGreen().getWorld(), (game.getId() * 5000) + x, y, z);
				VillagerCreator task5 = new VillagerCreator(game, locShopGreen, "§eShop");
				task5.runTaskTimer(MainGame.getInstance(), 20, 4);
		
				x = (int) map.getShopPurple().getX();
				y = (int) map.getShopPurple().getY();
				z = (int) map.getShopPurple().getZ();
				Location locShopPurple = new Location(map.getShopPurple().getWorld(), (game.getId() * 5000) + x, y, z);
				VillagerCreator task6 = new VillagerCreator(game, locShopPurple, "§eShop");
				task6.runTaskTimer(MainGame.getInstance(), 20, 4);
						
				x = (int) map.getShopYellow().getX();
				y = (int) map.getShopYellow().getY();
				z = (int) map.getShopYellow().getZ();
				Location locShopYellow = new Location(map.getShopYellow().getWorld(), (game.getId() * 5000) + x, y, z);
				VillagerCreator task7 = new VillagerCreator(game, locShopYellow, "§eShop");
				task7.runTaskTimer(MainGame.getInstance(), 20, 4);
				
				x = (int) map.getShopCyan().getX();
				y = (int) map.getShopCyan().getY();
				z = (int) map.getShopCyan().getZ();
				Location locShopCyan = new Location(map.getShopCyan().getWorld(), (game.getId() * 5000) + x, y, z);
				VillagerCreator task8 = new VillagerCreator(game, locShopCyan, "§eShop");
				task8.runTaskTimer(MainGame.getInstance(), 20, 4);			
				
				
				// Creating the spawns of mobs
								
				int x1 = (int) map.getLowerTeleporterGreen().getX();
				if(map.getLowerTeleporterPurple().getX() > x1) { x1 = (int) map.getLowerTeleporterPurple().getX(); }
				if(map.getLowerTeleporterYellow().getX() > x1) { x1 = (int) map.getLowerTeleporterYellow().getX(); }
				if(map.getLowerTeleporterCyan().getX() > x1) { x1 = (int) map.getLowerTeleporterCyan().getX(); }
				
				int x2 = (int) map.getLowerTeleporterGreen().getX();
				if(map.getLowerTeleporterPurple().getX() < x2) { x2 = (int) map.getLowerTeleporterPurple().getX(); }
				if(map.getLowerTeleporterYellow().getX() < x2) { x2 = (int) map.getLowerTeleporterYellow().getX(); }
				if(map.getLowerTeleporterCyan().getX() < x2) { x2 = (int) map.getLowerTeleporterCyan().getX(); }

				int z1 = (int) map.getLowerTeleporterGreen().getZ();
				if(map.getLowerTeleporterPurple().getZ() > z1) { z1 = (int) map.getLowerTeleporterPurple().getZ(); }
				if(map.getLowerTeleporterYellow().getZ() > z1) { z1 = (int) map.getLowerTeleporterYellow().getZ(); }
				if(map.getLowerTeleporterCyan().getZ() > z1) { z1 = (int) map.getLowerTeleporterCyan().getZ(); }
				
				int z2 = (int) map.getLowerTeleporterGreen().getZ();
				if(map.getLowerTeleporterPurple().getZ() < z2) { z2 = (int) map.getLowerTeleporterPurple().getZ(); }
				if(map.getLowerTeleporterYellow().getZ() < z2) { z2 = (int) map.getLowerTeleporterYellow().getZ(); }
				if(map.getLowerTeleporterCyan().getZ() < z2) { z2 = (int) map.getLowerTeleporterCyan().getZ(); }
				
				MobZones(game, middleMapLocation, x1, x2, z1, z2);
				
				Location l;
				l = new Location(map.getChestGreen().getWorld(), (game.getId() * 5000) + map.getChestGreen().getX(), map.getChestGreen().getY(), map.getChestGreen().getZ());
				Chest chestGreen = (Chest) l.getBlock().getState();
				chestGreen.getInventory().clear();
				l = new Location(map.getChestPurple().getWorld(), (game.getId() * 5000) + map.getChestPurple().getX(), map.getChestPurple().getY(), map.getChestPurple().getZ());
				Chest chestPurple = (Chest) l.getBlock().getState();
				chestPurple.getInventory().clear();
				l = new Location(map.getChestYellow().getWorld(), (game.getId() * 5000) + map.getChestYellow().getX(), map.getChestYellow().getY(), map.getChestYellow().getZ());
				Chest chestYellow = (Chest) l.getBlock().getState();
				chestYellow.getInventory().clear();
				l = new Location(map.getChestCyan().getWorld(), (game.getId() * 5000) + map.getChestCyan().getX(), map.getChestCyan().getY(), map.getChestCyan().getZ());
				Chest chestCyan = (Chest) l.getBlock().getState();
				chestCyan.getInventory().clear();
				
				
				GameInstance task = new GameInstance(game, map,  MainGame.getInstance().manager);
				task.runTaskTimer(MainGame.getInstance(), 0, 4);
			}
		}
	}
	
	public void MobZones(Games game, Location midLocation, int x1, int x2, int z1, int z2) {
		String ZonesCode = "00111100-01222210-12533521-12344321-12344321-12533521-01222210-00111100";
		String[] ZoneCodeList = ZonesCode.split("-");
		
		int diffX = 0;
		if(x1 > x2) {
			diffX = x1 - x2;
		}
		else {
			diffX = x2 - x1;
		}
		diffX = diffX / 6;
		
		int diffZ = 0;
		if(z1 > z2) {
			diffZ = z1 - z2;
		}
		else {
			diffZ = z2 - z1;
		}
		diffZ = diffZ / 6;
		
		int x = diffX / 2;
		int y = 7;
		int z = diffZ / 2;
		
		//Bukkit.broadcastMessage(diffX + " / " + diffZ);
		
		for(int LetterZone = 1; LetterZone<=8; LetterZone++) {
			
			//Bukkit.broadcastMessage("" + LetterZone + " >> " + ZoneCodeList[LetterZone - 1]);
			
			String[] CodeLevel = ZoneCodeList[LetterZone - 1].split("");
			for(int NumberZone = 1; NumberZone<=8; NumberZone++) {
				//if(!CodeLevel[NumberZone - 1].contains("0")) {
					for(int i = 4; i<=200; i++) {
						Location loc1 = new Location(midLocation.getWorld(), midLocation.getX() + (diffX * (5 - LetterZone)) - x, i, midLocation.getZ() + (diffZ * (5 - NumberZone)) - z);
						Location loc2 = new Location(midLocation.getWorld(), midLocation.getX() + (diffX * (5 - LetterZone)) - x, i + 1, midLocation.getZ() + (diffZ * (5 - NumberZone)) - z);
						if(!loc1.getBlock().getType().equals(Material.AIR) && loc2.getBlock().getType().equals(Material.AIR)) {
							y = i + 1;
							break;
						}
					}
					Location loc = new Location(midLocation.getWorld(), midLocation.getX() + (diffX * (5 - LetterZone)) - x, y, midLocation.getZ() + (diffZ * (5 - NumberZone)) - z);
					
					//defaultLoc.getBlock().setType(Material.REDSTONE_BLOCK);
					
					MobsGenerator generator = new MobsGenerator(loc, Integer.parseInt(CodeLevel[NumberZone - 1]), 10, 15, 15);
					generator.runTaskTimer(MainGame.getInstance(), 20, 20);
					
					game.getMobSpawnList().add(generator);
					//Bukkit.broadcastMessage("  " + NumberZone + " >> " + CodeLevel[NumberZone - 1] + " " + MainGame.getGameFunctions().locationToText(loc));
				//}
				//else {
					//Bukkit.broadcastMessage("  " + NumberZone + " >> none");
				//}
			}
		}
	}
	
	/*private Location calculateMobSpawnLocation(int gameNumber, Location loc, Location locTeleporter, int level) {
		int x;
		int y = (int) locTeleporter.getY();
		int z;
		
		if(loc.getX() > locTeleporter.getX()) {
			x = (int) (loc.getX() - (locTeleporter.getX() + 5000 * gameNumber) );
		}
		else {
			x = (int) ( (locTeleporter.getX() + 5000 * gameNumber) - loc.getX());
		}
		
		if(loc.getZ() > locTeleporter.getZ()) {
			z = (int) (loc.getZ() - locTeleporter.getZ());
		}
		else {
			z = (int) (locTeleporter.getZ() - loc.getZ());
		}
		
		int coordX = x / 5;
		int coordZ = z / 5;
		if(level<=4) {
			int finalX;
			int finalZ;
			
			if(loc.getX() > locTeleporter.getX()) {
				finalX = (int) ( (locTeleporter.getX() + 5000 * gameNumber) + (coordX * level));
			}
			else {
				finalX = (int) ( (locTeleporter.getX() + 5000 * gameNumber) - (coordX * level));
			}
			
			if(loc.getZ() > locTeleporter.getZ()) {
				finalZ = (int) (locTeleporter.getZ() + (coordZ * level));
			}
			else {
				finalZ = (int) (locTeleporter.getZ() - (coordZ * level));
			}
			
			//Bukkit.broadcastMessage("§1x " + x);
			//Bukkit.broadcastMessage("§1z " + z);
			//Bukkit.broadcastMessage("§2coordX " + coordX);
			//Bukkit.broadcastMessage("§2coordZ " + coordZ);
			Bukkit.broadcastMessage("§6[" + level + "] " + MainGame.getGameFunctions().locationToText(new Location(locTeleporter.getWorld(), finalX, y, finalZ)));
			return new Location(locTeleporter.getWorld(), finalX, y, finalZ);
		}
		else {
			
		}
		
		return null;
	}*/
	
	public void deadSpectator(Player player) {
		player.setGameMode(GameMode.SPECTATOR);
		
		player.getActivePotionEffects().clear();
		player.setExp(0);
		player.setLevel(0);
		
		deadSpectatorDrop(player, Material.ROTTEN_FLESH);
		deadSpectatorDrop(player, Material.CARROT_ITEM);
		deadSpectatorDrop(player, Material.POTATO_ITEM);
		deadSpectatorDrop(player, Material.IRON_INGOT);
    	deadSpectatorDrop(player, Material.SLIME_BALL);
    	deadSpectatorDrop(player, Material.STRING);
    	deadSpectatorDrop(player, Material.SPIDER_EYE);
    	deadSpectatorDrop(player, Material.BONE);
    	deadSpectatorDrop(player, Material.MAGMA_CREAM);
    	deadSpectatorDrop(player, Material.EMERALD);
    	deadSpectatorDrop(player, Material.TOTEM);
    	deadSpectatorDrop(player, Material.GLASS_BOTTLE);
    	deadSpectatorDrop(player, Material.SULPHUR);
    	deadSpectatorDrop(player, Material.REDSTONE);
    	deadSpectatorDrop(player, Material.SUGAR);
    	deadSpectatorDrop(player, Material.STICK);
    	deadSpectatorDrop(player, Material.COAL);
    	deadSpectatorDrop(player, Material.SKULL_ITEM);
    	deadSpectatorDrop(player, Material.BLAZE_ROD);
    	deadSpectatorDrop(player, Material.GOLD_NUGGET);
    	deadSpectatorDrop(player, Material.RED_ROSE);
    	deadSpectatorDrop(player, Material.ENDER_PEARL);
		
		player.getInventory().clear();
		
		Bukkit.getScheduler().runTaskLater(MainGame.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				player.sendTitle("§63", MeneliaAPI.getInstance().getLang(player, "§bRespawn dans", "§bRespawn in"), 1, 20, 1);
			}
		}, 60);
		Bukkit.getScheduler().runTaskLater(MainGame.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				player.sendTitle("§62", MeneliaAPI.getInstance().getLang(player, "§bRespawn dans", "§bRespawn in"), 1, 20, 1);
			}
		}, 80);
		Bukkit.getScheduler().runTaskLater(MainGame.getInstance(), new Runnable() {
	
			@Override
			public void run() {
				player.sendTitle("§61", MeneliaAPI.getInstance().getLang(player, "§bRespawn dans", "§bRespawn in"), 1, 20, 1);
			}
		}, 100);
		Bukkit.getScheduler().runTaskLater(MainGame.getInstance(), new Runnable() {
	
			@Override
			public void run() {
				player.sendTitle("§6Go", MeneliaAPI.getInstance().getLang(player, "§bRespawn dans", "§bRespawn in"), 1, 20, 1);
				gameSpawningPlayer(player);
			}
		}, 120);
	}
	
	public void deadSpectatorDrop(Player player, Material mat) {
		for (int i = 0; i < 36; i++) {
			ItemStack slot = player.getInventory().getItem(i);
            if (slot == null || !slot.getType().equals(mat))
                continue;
            player.getLocation().getWorld().dropItem(player.getLocation().add(0, 1, 0), slot);
        }
	}
	
	public void gameSpawningPlayer(Player player) {
		if(isInGame(player)) {
			Games game = getGame(player);
			Maps map = getMap(game);
			for(GamesData data: game.getPlayers()) {
				if(data.getPlayer().getName().equals(player.getName())) {
					player.getInventory().clear();
					//Bukkit.broadcastMessage(game.getId() + " / " + data.getPlayer().getName() + " / " + locationToText(map.getPlayerLocationCyan()));
					Location middleMapLocation = new Location(map.getPlayerLocationGreen().getWorld(), (game.getId() * 5000), 120, (map.getId()*5000));
					if(data.getTeam() == 1) {
						World world = map.getPlayerLocationGreen().getWorld();
						double x = map.getPlayerLocationGreen().getX();
						double y = map.getPlayerLocationGreen().getY();
						double z = map.getPlayerLocationGreen().getZ();
						Location teleportLoc = new Location(world ,x+(5000*game.getId()), y, z);
						org.bukkit.util.Vector dir = middleMapLocation.clone().subtract(teleportLoc).toVector();
						teleportLoc.setDirection(dir);
						data.getPlayer().teleport(teleportLoc);
					}
					else if(data.getTeam() == 2) {
						World world = map.getPlayerLocationPurple().getWorld();
						double x = map.getPlayerLocationPurple().getX();
						double y = map.getPlayerLocationPurple().getY();
						double z = map.getPlayerLocationPurple().getZ();
						Location teleportLoc = new Location(world ,x+(5000*game.getId()), y, z);
						org.bukkit.util.Vector dir = middleMapLocation.clone().subtract(teleportLoc).toVector();
						teleportLoc.setDirection(dir);
						data.getPlayer().teleport(teleportLoc);
					}
					else if(data.getTeam() == 3) {
						World world = map.getPlayerLocationYellow().getWorld();
						double x = map.getPlayerLocationYellow().getX();
						double y = map.getPlayerLocationYellow().getY();
						double z = map.getPlayerLocationYellow().getZ();
						Location teleportLoc = new Location(world ,x+(5000*game.getId()), y, z);
						org.bukkit.util.Vector dir = middleMapLocation.clone().subtract(teleportLoc).toVector();
						teleportLoc.setDirection(dir);
						data.getPlayer().teleport(teleportLoc);
					}
					else {
						World world = map.getPlayerLocationCyan().getWorld();
						double x = map.getPlayerLocationCyan().getX();
						double y = map.getPlayerLocationCyan().getY();
						double z = map.getPlayerLocationCyan().getZ();
						Location teleportLoc = new Location(world ,x+(5000*game.getId()), y, z);
						org.bukkit.util.Vector dir = middleMapLocation.clone().subtract(teleportLoc).toVector();
						teleportLoc.setDirection(dir);
						data.getPlayer().teleport(teleportLoc);
					}
					data.getPlayer().setGameMode(GameMode.SURVIVAL);
					data.getPlayer().setHealth(20);
					
					ItemStack it = new ItemStack(Material.WOOD_SWORD);
					ItemMeta itM = it.getItemMeta();
					
					itM.setUnbreakable(true);
					
					it.setItemMeta(itM);
					
					data.getPlayer().getInventory().addItem(it);
					
					ItemStack it2 = new ItemStack(Material.LEATHER_HELMET);
					LeatherArmorMeta itM2 = (LeatherArmorMeta) it2.getItemMeta();
					
					if(data.getTeam() == 1) {
						itM2.setColor(Color.GREEN);
					}
					else if(data.getTeam() == 2) {
						itM2.setColor(Color.PURPLE);				
					}
					else if(data.getTeam() == 3) {
						itM2.setColor(Color.YELLOW);
					}
					else if(data.getTeam() == 4) {
						itM2.setColor(Color.TEAL);
					}
					
					itM2.setUnbreakable(true);
					
					it2.setItemMeta(itM2);
					
					player.getInventory().setHelmet(it2);
					
					ItemStack it3 = new ItemStack(Material.LEATHER_CHESTPLATE);
					LeatherArmorMeta itM3 = (LeatherArmorMeta) it2.getItemMeta();
					
					if(data.getTeam() == 1) {
						itM3.setColor(Color.GREEN);
					}
					else if(data.getTeam() == 2) {
						itM3.setColor(Color.PURPLE);				
					}
					else if(data.getTeam() == 3) {
						itM3.setColor(Color.YELLOW);
					}
					else if(data.getTeam() == 4) {
						itM3.setColor(Color.TEAL);
					}

					itM3.setUnbreakable(true);
					
					it3.setItemMeta(itM3);
					
					player.getInventory().setChestplate(it3);
				}
			}
		}		
	}
	
	public void ressetMap(Games game) {
		// Kill al mobs generators
		for(MobsGenerator mb: game.getMobSpawnList()){
			mb.cancel();
		}
		game.getMobSpawnList().clear();
		
		String mapName = "";
		
		for(Maps map: MainGame.getInstance().getMaps()) {
			if(map.getId() == game.getMapId()) {
				mapName = map.getName();
			}
		}
		
		Thread thread = new Thread(new SchematicLoad(mapName, true, game.getId()));
		thread.start();
		
		Bukkit.getScheduler().runTaskLater(MainGame.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				game.setInGame(false);
				MeneliaAPI.getInstance().getGamesManager().getGame("ascension", game.getId()).setGameStatus(0);
			}
		}, 5 * 20);
	}
	
	public void leaveGame(Player player, boolean showMessage) {
		if(isInGame(player)) {
			Games game = getGame(player);
			game.setTimer(false);
			
			player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
			
			Bukkit.getScheduler().runTaskLater(MainGame.getInstance(), new Runnable() {
				public void run() {
					if(!game.getInGame()) {
						checkToStart(game.getId());
					}
				}
			}, 5);
			game.removePlayer(player);
			if(showMessage) {
				player.sendMessage(MeneliaAPI.getInstance().colorize(MeneliaAPI.getInstance().getLang(player, "&7Vous avez quitté la partie !", "&7You left the game !")));
			}
			for(GamesData data: game.getPlayers()) {
				data.getPlayer().sendMessage(MeneliaAPI.getInstance().colorize(MeneliaAPI.getInstance().getLang(player, "&8" + player.getName() + " &7a quitté la partie !  (" + game.getPlayers().size() + "/" + MainGame.getInstance().maxInGame + ")", "&8" + player.getName() + " &7a left the game !  (" + game.getPlayers().size() + "/" + MainGame.getInstance().maxInGame + ")")));
			}
			
			ArrayList<String> players = new ArrayList<>();
			for(GamesData data: game.getPlayers()) {
				players.add(data.getPlayer().getUniqueId().toString());
			}				
			MeneliaAPI.getInstance().getGamesManager().getGame("ascension", game.getId()).setPlayers(players);
			
			MeneliaAPI.getBungeecordInstance().sendToServer(player, "hub");
		}
		else {
			player.sendMessage(MeneliaAPI.getInstance().colorize(MeneliaAPI.getInstance().getLang(player, "&cVous n'êtes pas dans une partie !", "&cYou are not in a game !")));
		}
	}
	
	
	public boolean isInGame(Player player) {
		for(Games game: MainGame.getInstance().getGames()) {
			for(GamesData data: game.getPlayers()) {
				if(player.getName().equals(data.getPlayer().getName())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public Games getGame(Player player) {
		if(isInGame(player)) {
			for(Games game: MainGame.getInstance().getGames()) {
				for(GamesData data: game.getPlayers()) {
					if(player.getName().equals(data.getPlayer().getName())) {
						return game;
					}
				}
			}
		}
		return null;
	}
	
	public Maps getMap(Games game) {
		for(Maps map: MainGame.getInstance().getMaps()) {
			if(map.getId() == game.getMapId()) {
				return map;
			}
		}
		return null;
	}
	
	
	
	
	
	public String locationToText(Location loc) {
		if(loc.getX() == 0 && loc.getY() == 0 && loc.getZ() == 0) {
			return "§7null";	
		}
		String x = "" + loc.getX();
		String y = "" + loc.getY();
		String z = "" + loc.getZ();
		
		if(loc.getX() == 0) {
			x = "§4" + x;
		}
		if(loc.getY() == 0) {
			y = "§4" + y;
		}
		if(loc.getZ() == 0) {
			z = "§4" + z;
		}
		
		return "§8x: §7" + x + " §8y: §7" + y + " §8z: §7" + z;
	}
	
	
	
	
	
	
	
	
	public File getSchematicFile(String map) {
		return new File("plugins/Menelia/Ascension/schematics/" + map + ".schematic");
	}
	
	/*public void generateMap(Player player, String map) {
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
									schem.save(MainGame.getInstance().getSchematicFile(map), ClipboardFormat.SCHEMATIC);
								} catch (IOException e) {
								}
								player.sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &aCreating complete !"));
							}
						}, 400);
					}
				}
		    }
		});
	}*/
	
	public void loadMap(Player player, String map, boolean useNumber, int gameNumber) {
		Thread thread = new Thread(new SchematicLoad(player, map, useNumber, gameNumber));
		thread.start();
	}
	
	public void loadHub(Player player, boolean useNumber, int gameNumber) {
		Thread thread = new Thread(new SchematicLoadHub(player, useNumber, gameNumber));
		thread.start();
	}
	
	public boolean mapExist(String map) {
		for(Maps loopMap: MainGame.getInstance().getMaps()) {
			if(map.equalsIgnoreCase(loopMap.getName())) {
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public void reloadJsonData() {
		JSON M = MeneliaAPI.getJsonInstance();
		
		try {
			JSONObject joc = M.JSONOpener("plugins/Menelia/Ascension/config.json");
			
			String[] lobbylocation = (M.JSONMapReaderToString(joc, "lobbylocation").split(","));
			String world = (M.JSONMapReaderToString(joc, "world"));
			MainGame.getInstance().spawnLocation = new org.bukkit.Location(Bukkit.getWorld(world), Double.parseDouble(lobbylocation[0]), Double.parseDouble(lobbylocation[1]), Double.parseDouble(lobbylocation[2]));
			
			MainGame.getInstance().maxInGame = M.JSONMapReaderToInteger(joc, "max");
			MainGame.getInstance().minToStartGame = M.JSONMapReaderToInteger(joc, "min");
			MainGame.getInstance().timeInWaitingRoom = M.JSONMapReaderToInteger(joc, "timeInWaitingRoom");
			MainGame.getInstance().mobsSpawninterval = M.JSONMapReaderToInteger(joc, "mobsSpawninterval");
			MainGame.getInstance().maxRandomMobSpawnAmount = M.JSONMapReaderToInteger(joc, "maxRandomMobSpawnAmount");
			MainGame.getInstance().firstMobSpawnTime = M.JSONMapReaderToInteger(joc, "firstMobSpawnTime");
		} catch (Exception e) {
			e.printStackTrace();
			Bukkit.broadcastMessage("§cAn error occurs while loading a map!");
			Bukkit.broadcastMessage("§cPlease report this as fast a possible to an administrator !");
			Bukkit.broadcastMessage("Error code: #AS1-" + new Date().getHours() + "." + new Date().getMinutes() + "." + new Date().getSeconds());
		}
		
		MainGame.getInstance().getMaps().clear();
		Bukkit.getConsoleSender().sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &bMaps Loaded:"));
				
		try (Stream<Path> walk = Files.walk(Paths.get("plugins/Menelia/Ascension/maps/"))) {			
			List<String> result = walk.map(x -> x.toString())
					.filter(f -> f.endsWith(".json")).collect(Collectors.toList());
			Bukkit.getConsoleSender().sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " §bDetecting " + result.size() + " maps:"));
			
			for(String filePath: result) {
				JSONObject jo = M.JSONOpener(filePath);
				
				Long mapId = M.JSONMapReaderToLong((JSONObject)jo.get("game"), "mapId");
				String mapName = M.JSONMapReaderToString((JSONObject)jo.get("game"), "mapName");
				
				Maps map = new Maps(mapId.intValue(), mapName, filePath.replace("plugins/Menelia/Ascension/maps/", "").replace(".json", ""));
				MainGame.getInstance().getMaps().add(map);
				Bukkit.getConsoleSender().sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " §b- " + mapId.intValue() + " " + mapName));
				
				// for(JSONObject jo2: M.JSONArrayReaderToJSONOject((JSONObject)jo.get("teams"))) {
				JSONArray ar = (JSONArray) jo.get("teams");
				Iterator<JSONObject> iterator = ar.iterator();
				while(iterator.hasNext()) {
					String lSplit[];
					Location l;
					JSONObject jo2 = iterator.next();
					String team = M.JSONMapReaderToString(jo2, "name");
					
					lSplit = (M.JSONMapReaderToString(jo2, "spawn")).split(",");
					l = new org.bukkit.Location(MainGame.getInstance().getSpawnLocation().getWorld(), Double.parseDouble(lSplit[0]), Double.parseDouble(lSplit[1]), Double.parseDouble(lSplit[2]), Float.parseFloat(lSplit[3]), Float.parseFloat(lSplit[4]));
					switch (team) {
					case "Green":
						map.setPlayerLocationGreen(l);						
						break;
					case "Purple":	
						map.setPlayerLocationPurple(l);					
						break;
					case "Yellow":	
						map.setPlayerLocationYellow(l);					
						break;
					case "Cyan":
						map.setPlayerLocationCyan(l);
						break;
					default:
						break;
					}
					
					lSplit = (M.JSONMapReaderToString(jo2, "build_pad")).split(",");
					l = new org.bukkit.Location(MainGame.getInstance().getSpawnLocation().getWorld(), Double.parseDouble(lSplit[0]), Double.parseDouble(lSplit[1]), Double.parseDouble(lSplit[2]), Float.parseFloat(lSplit[3]), Float.parseFloat(lSplit[4]));
					switch (team) {
					case "Green":
						map.setBuildGreen(l);					
						break;
					case "Purple":	
						map.setBuildPurple(l);					
						break;
					case "Yellow":	
						map.setBuildYellow(l);						
						break;
					case "Cyan":
						map.setBuildCyan(l);	
						break;
					default:
						break;
					}
					
					lSplit = (M.JSONMapReaderToString(jo2, "shop")).split(",");
					l = new org.bukkit.Location(MainGame.getInstance().getSpawnLocation().getWorld(), Double.parseDouble(lSplit[0]), Double.parseDouble(lSplit[1]), Double.parseDouble(lSplit[2]), Float.parseFloat(lSplit[3]), Float.parseFloat(lSplit[4]));
					switch (team) {
					case "Green":
						map.setShopGreen(l);					
						break;
					case "Purple":	
						map.setShopPurple(l);						
						break;
					case "Yellow":	
						map.setShopYellow(l);							
						break;
					case "Cyan":
						map.setShopCyan(l);	
						break;
					default:
						break;
					}
					
					lSplit = (M.JSONMapReaderToString(jo2, "sell")).split(",");
					l = new org.bukkit.Location(MainGame.getInstance().getSpawnLocation().getWorld(), Double.parseDouble(lSplit[0]), Double.parseDouble(lSplit[1]), Double.parseDouble(lSplit[2]), Float.parseFloat(lSplit[3]), Float.parseFloat(lSplit[4]));
					switch (team) {
					case "Green":
						map.setSellGreen(l);					
						break;
					case "Purple":	
						map.setSellPurple(l);						
						break;
					case "Yellow":	
						map.setSellYellow(l);							
						break;
					case "Cyan":
						map.setSellCyan(l);	
						break;
					default:
						break;
					}
					
					lSplit = (M.JSONMapReaderToString(jo2, "teleporter_top")).split(",");
					l = new org.bukkit.Location(MainGame.getInstance().getSpawnLocation().getWorld(), Double.parseDouble(lSplit[0]), Double.parseDouble(lSplit[1]), Double.parseDouble(lSplit[2]), Float.parseFloat(lSplit[3]), Float.parseFloat(lSplit[4]));
					switch (team) {
					case "Green":
						map.setUpperTeleporterGreen(l);
						break;
					case "Purple":	
						map.setUpperTeleporterPurple(l);
						break;
					case "Yellow":	
						map.setUpperTeleporterYellow(l);
						break;
					case "Cyan":
						map.setUpperTeleporterCyan(l);
						break;
					default:
						break;
					}
					
					lSplit = (M.JSONMapReaderToString(jo2, "teleporter_bottom")).split(",");
					l = new org.bukkit.Location(MainGame.getInstance().getSpawnLocation().getWorld(), Double.parseDouble(lSplit[0]), Double.parseDouble(lSplit[1]), Double.parseDouble(lSplit[2]), Float.parseFloat(lSplit[3]), Float.parseFloat(lSplit[4]));
					switch (team) {
					case "Green":
						map.setLowerTeleporterGreen(l);
						break;
					case "Purple":	
						map.setLowerTeleporterPurple(l);
						break;
					case "Yellow":	
						map.setLowerTeleporterYellow(l);
						break;
					case "Cyan":
						map.setLowerTeleporterCyan(l);
						break;
					default:
						break;
					}
					
					lSplit = (M.JSONMapReaderToString(jo2, "obsidian")).split(",");
					l = new org.bukkit.Location(MainGame.getInstance().getSpawnLocation().getWorld(), Double.parseDouble(lSplit[0]), Double.parseDouble(lSplit[1]), Double.parseDouble(lSplit[2]), Float.parseFloat(lSplit[3]), Float.parseFloat(lSplit[4]));
					switch (team) {
					case "Green":
						map.setObsidianGreen(l);						
						break;
					case "Purple":	
						map.setObsidianPurple(l);					
						break;
					case "Yellow":	
						map.setObsidianYellow(l);						
						break;
					case "Cyan":
						map.setObsidianCyan(l);	
						break;
					default:
						break;
					}
					
					lSplit = (M.JSONMapReaderToString(jo2, "chest")).split(",");
					l = new org.bukkit.Location(MainGame.getInstance().getSpawnLocation().getWorld(), Double.parseDouble(lSplit[0]), Double.parseDouble(lSplit[1]), Double.parseDouble(lSplit[2]), Float.parseFloat(lSplit[3]), Float.parseFloat(lSplit[4]));
					switch (team) {
					case "Green":
						map.setChestGreen(l);						
						break;
					case "Purple":	
						map.setChestPurple(l);					
						break;
					case "Yellow":	
						map.setChestYellow(l);						
						break;
					case "Cyan":
						map.setChestCyan(l);	
						break;
					default:
						break;
					}
				}
				//Location l = new org.bukkit.Location(MainGame.getInstance().getSpawnLocation().getWorld(), M., y, z);
				//map.setPlayerLocationGreen(l, false);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Bukkit.broadcastMessage("§cAn error occurs while loading a map!");
			Bukkit.broadcastMessage("§cPlease report this as fast a possible to an administrator !");
			Bukkit.broadcastMessage("Error code: #AS2-" + new Date().getHours() + "." + new Date().getMinutes() + "." + new Date().getSeconds());
		}
		
		
	}
	
	public void pasteSchematic(String instance, Location loc, File file) {
        Vector vector = new Vector(loc.getX(), loc.getY(), loc.getZ());
        BukkitWorld world = new BukkitWorld(loc.getWorld());
		try {
			Bukkit.getConsoleSender().sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &e" + instance + " &6request for pasting a schematic from &e" + file.getPath() + " &6in world " + loc.getWorld().getName() + " &6at x:" + loc.getX() + " y:" + loc.getY() + " z:" + loc.getZ()));
			ClipboardFormat.SCHEMATIC.load(file).paste(world, vector, false, true, null);
			Bukkit.getConsoleSender().sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &aFinished"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	/*public void saveSchematic(File file, Location pos1, Location pos2) {
        Vector bot = new Vector(pos1.getX(), pos1.getY(), pos1.getZ());
        Vector top = new Vector(pos2.getX(), pos2.getY(), pos2.getZ());
        @SuppressWarnings("deprecation")
		CuboidRegion region = new CuboidRegion(new BukkitWorld(pos1.getWorld()), bot, top);
        Schematic schem = new Schematic(region);
        try {
            schem.save(file, ClipboardFormat.SCHEMATIC);
        } catch (IOException e) {
        }
    }*/
}

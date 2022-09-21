package fr.gameurduxvi.ascension;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import fr.gameurduxvi.ascension.database.Games;
import fr.gameurduxvi.ascension.database.GamesData;
import fr.gameurduxvi.ascension.database.Maps;
import fr.gameurduxvi.meneliaapi.MeneliaAPI;
import fr.gameurduxvi.meneliaapi.EventHandlers.JoinGameEvent;

public class EventListener implements Listener {
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerLogin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		event.setJoinMessage(null);
		for(Player loopPlayer: Bukkit.getOnlinePlayers()) {
			if(MainGame.getGameFunctions().isInGame(loopPlayer)) {
				loopPlayer.hidePlayer(player);
			}
		}
	}
	
	@EventHandler
	public void PlayerQuitEvent(PlayerQuitEvent event) {
		event.setQuitMessage(null);
		if(MainGame.getGameFunctions().isInGame(event.getPlayer())) {
			MainGame.getGameFunctions().leaveGame(event.getPlayer(), true);
		}
	}
	
	@EventHandler
	public void gameJoiner(JoinGameEvent e) {
		if(e.getGameName().equals("ascension")) {
			MainGame.getGameFunctions().joinGame(e.getPlayer(), e.getGameNumber());
		}
	}
	
	@SuppressWarnings("unused")
	@EventHandler
	public void OnClickInAnvilInv(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		if(MainGame.getGameFunctions().isInGame(player)) {
			if(e.getInventory().getType().equals(InventoryType.ANVIL)) {
				if(player.getTargetBlock(null, 5).getType().equals(Material.ANVIL)) {
					Block block = player.getTargetBlock(null, 5);
					/*BlockData blockData = block.getBlockData();
					if(blockData instanceof Rotatable) {
					    System.out.println("Yep");
					    Rotatable rotatable = (Rotatable) blockData;
					    rotatable.setRotation(BlockFace.EAST);
					}else {
					    System.out.println("Nope");
					}*/
					
					/*BlockData blockData = block.getBlockData();
if(blockData instanceof Orientable) {
    System.out.println("Yep");
    Orientable orientable = (Orientable) blockData;
    orientable.setAxis(Axis.X);
}else {
    System.out.println("Nope");
}
*/
				}
			}
		}
		
	}
	
	/*@EventHandler
	public void OnInteract(PlayerInteractEvent event) {
		if(MainGame.getGameFunctions().isInGame(event.getPlayer())) {
			if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				if(event.getClickedBlock().getType().equals(Material.ANVIL)) {
					event.setCancelled(true);
					
					Player player = event.getPlayer();
					Inventory anvil = Bukkit.createInventory(null, InventoryType.ANVIL);
					player.openInventory(anvil);
				}
			}
		}
	}*/
	
	/*@EventHandler
	public void OnInventoryClose(InventoryCloseEvent e) {
		Player player = (Player) e.getPlayer();
		if(MainGame.getGameFunctions().isInGame(player)) {
			if(e.getInventory().getType().equals(InventoryType.ANVIL)) {
				AnvilInventory inv = (AnvilInventory) e.getInventory();
				Bukkit.broadcastMessage("2");
				if(!inv.getItem(0).equals(null)) {
					Bukkit.broadcastMessage("3");
					
					boolean item1 = false;
					for(ItemStack item: player.getInventory().getContents()) {
						if(item.getType().equals(null)) {
							item1 = true;
							player.getInventory().addItem(inv.getItem(0));
							break;
						}
					}
					if(!item1) {
						player.getWorld().dropItem(player.getLocation(), inv.getItem(0));
					}
					
				}
				
			}
		}
	}*/
	
	
	@EventHandler
	public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();
		if(MainGame.getGameFunctions().isInGame(player)) {
			if(player.isSneaking()) {
				Games game = MainGame.getGameFunctions().getGame(event.getPlayer());
				Maps map = MainGame.getGameFunctions().getMap(game);
				for(GamesData data: game.getPlayers()) {
					if(data.getPlayer().equals(player)) {
						
						double pitch = player.getLocation().getPitch();
						//double yaw = player.getLocation().getYaw();
						Location middleMapLocation = new Location(map.getPlayerLocationGreen().getWorld(), (game.getId() * 5000), 120, (map.getId()*5000));
						
						if(data.getTeam() == 1) {
							Location loc1 = new Location(map.getUpperTeleporterGreen().getWorld(), (game.getId() * 5000) + map.getUpperTeleporterGreen().getX() + 0.5, map.getUpperTeleporterGreen().getY(), map.getUpperTeleporterGreen().getZ() + 0.5);
							Location loc2 = new Location(map.getLowerTeleporterGreen().getWorld(), (game.getId() * 5000) + map.getLowerTeleporterGreen().getX() + 0.5, map.getLowerTeleporterGreen().getY(), map.getLowerTeleporterGreen().getZ() + 0.5);
							
							if(playerIsInArea(player, loc1, 0.8)) {
								Location teleportLoc = new Location(loc2.getWorld(), loc2.getX(), loc2.getY(), loc2.getZ());
								Vector dir = middleMapLocation.clone().subtract(teleportLoc).toVector();
								teleportLoc.setDirection(dir);
								teleportLoc.setPitch((float) pitch);
								player.teleport(teleportLoc);
							}
							else if(playerIsInArea(player, loc2, 0.8)) {
								Location teleportLoc = new Location(loc1.getWorld(), loc1.getX(), loc1.getY(), loc1.getZ());
								Vector dir = middleMapLocation.clone().subtract(teleportLoc).toVector();
								teleportLoc.setDirection(dir);
								teleportLoc.setPitch((float) pitch);
								player.teleport(teleportLoc);
							}
						}
						else if(data.getTeam() == 2) {
							Location loc1 = new Location(map.getUpperTeleporterPurple().getWorld(), (game.getId() * 5000) + map.getUpperTeleporterPurple().getX() + 0.5, map.getUpperTeleporterPurple().getY(), map.getUpperTeleporterPurple().getZ() + 0.5);
							Location loc2 = new Location(map.getLowerTeleporterPurple().getWorld(), (game.getId() * 5000) + map.getLowerTeleporterPurple().getX() + 0.5, map.getLowerTeleporterPurple().getY(), map.getLowerTeleporterPurple().getZ() + 0.5);
							
							if(playerIsInArea(player, loc1, 0.8)) {
								Location teleportLoc = new Location(loc2.getWorld(), loc2.getX(), loc2.getY(), loc2.getZ());
								Vector dir = middleMapLocation.clone().subtract(teleportLoc).toVector();
								teleportLoc.setDirection(dir);
								teleportLoc.setPitch((float) pitch);
								player.teleport(teleportLoc);
							}
							else if(playerIsInArea(player, loc2, 0.8)) {
								Location teleportLoc = new Location(loc1.getWorld(), loc1.getX(), loc1.getY(), loc1.getZ());
								Vector dir = middleMapLocation.clone().subtract(teleportLoc).toVector();
								teleportLoc.setDirection(dir);
								teleportLoc.setPitch((float) pitch);
								player.teleport(teleportLoc);
							}
						}
						else if(data.getTeam() == 3) {
							Location loc1 = new Location(map.getUpperTeleporterYellow().getWorld(), (game.getId() * 5000) + map.getUpperTeleporterYellow().getX() + 0.5, map.getUpperTeleporterYellow().getY(), map.getUpperTeleporterYellow().getZ() + 0.5);
							Location loc2 = new Location(map.getLowerTeleporterYellow().getWorld(), (game.getId() * 5000) + map.getLowerTeleporterYellow().getX() + 0.5, map.getLowerTeleporterYellow().getY(), map.getLowerTeleporterYellow().getZ() + 0.5);
							
							if(playerIsInArea(player, loc1, 0.8)) {
								Location teleportLoc = new Location(loc2.getWorld(), loc2.getX(), loc2.getY(), loc2.getZ());
								Vector dir = middleMapLocation.clone().subtract(teleportLoc).toVector();
								teleportLoc.setDirection(dir);
								teleportLoc.setPitch((float) pitch);
								player.teleport(teleportLoc);
							}
							else if(playerIsInArea(player, loc2, 0.8)) {
								Location teleportLoc = new Location(loc1.getWorld(), loc1.getX(), loc1.getY(), loc1.getZ());
								Vector dir = middleMapLocation.clone().subtract(teleportLoc).toVector();
								teleportLoc.setDirection(dir);
								teleportLoc.setPitch((float) pitch);
								player.teleport(teleportLoc);
							}
						}
						else if(data.getTeam() == 4) {
							Location loc1 = new Location(map.getUpperTeleporterCyan().getWorld(), (game.getId() * 5000) + map.getUpperTeleporterCyan().getX() + 0.5, map.getUpperTeleporterCyan().getY(), map.getUpperTeleporterCyan().getZ() + 0.5);
							Location loc2 = new Location(map.getLowerTeleporterCyan().getWorld(), (game.getId() * 5000) + map.getLowerTeleporterCyan().getX() + 0.5, map.getLowerTeleporterCyan().getY(), map.getLowerTeleporterCyan().getZ() + 0.5);
							
							if(playerIsInArea(player, loc1, 0.8)) {
								Location teleportLoc = new Location(loc2.getWorld(), loc2.getX(), loc2.getY(), loc2.getZ());
								Vector dir = middleMapLocation.clone().subtract(teleportLoc).toVector();
								teleportLoc.setDirection(dir);
								teleportLoc.setPitch((float) pitch);
								player.teleport(teleportLoc);
							}
							else if(playerIsInArea(player, loc2, 0.8)) {
								Location teleportLoc = new Location(loc1.getWorld(), loc1.getX(), loc1.getY(), loc1.getZ());
								Vector dir = middleMapLocation.clone().subtract(teleportLoc).toVector();
								teleportLoc.setDirection(dir);
								teleportLoc.setPitch((float) pitch);
								player.teleport(teleportLoc);
							}
						}
					}
				}
			}
		}
	}
	
	public boolean playerIsInArea(Player player, Location loc, double range) {
		Location ploc = player.getLocation();
		
		if(ploc.getX() >= loc.getX() - range && ploc.getX() <= loc.getX() + range) {
			if(ploc.getY() >= loc.getY() - range && ploc.getY() <= loc.getY() + range) {
				if(ploc.getZ() >= loc.getZ() - range && ploc.getZ() <= loc.getZ() + range) {
					return true;
				}
			}
		}
		return false;
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();

		if(MainGame.getGameFunctions().isInGame(player)) {
			event.setCancelled(true);
			Games game = MainGame.getGameFunctions().getGame(player);
			
			String color = "f";
			int team = 0;
			
			for(GamesData data: game.getPlayers()) {
				
				if(data.getPlayer().equals(player)) {
					team = data.getTeam();
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
				}
			}
			
			if(!event.getMessage().startsWith("!")) {
				String message = event.getMessage();
				for(GamesData data: game.getPlayers()) {
					if(data.getTeam() == team) {
						data.getPlayer().sendMessage(MeneliaAPI.getInstance().colorize(MeneliaAPI.getInstance().getLang(data.getPlayer(), "&7[Équipe]", "&7[Team]") + " &" + color + player.getName() + "&8>> &f" + message));
					}
				}
				Bukkit.getConsoleSender().sendMessage(MeneliaAPI.getInstance().colorize("§5A§6" + game.getId() + "§7T &" + color + player.getName() + "&8>> &f" + message));
			}
			else {
				for(GamesData data: game.getPlayers()) {
					String message = event.getMessage().substring(1);
					data.getPlayer().sendMessage(MeneliaAPI.getInstance().colorize("&7[Global] &" + color + player.getName() + "&8>> &f" + message));
					Bukkit.getConsoleSender().sendMessage(MeneliaAPI.getInstance().colorize("§5A§6" + game.getId() + "§7G &" + color + player.getName() + "&8>> &f" + message));
				}
			}
		}
	}
	
	@EventHandler
	public void OnClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Inventory inv = event.getInventory();
		ItemStack current = event.getCurrentItem();
		
		if(MainGame.getGameFunctions().isInGame(player)) {
			if(inv.getName().equals("container.crafting")) {
				if(MeneliaAPI.getFunctions().isEmpty(current)) return;
				if(current.getType().equals(Material.LEATHER_HELMET) || current.getType().equals(Material.LEATHER_CHESTPLATE)) {
					event.setCancelled(true);
				}
			}
		}
		
	}
	
	/*ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
	
	
	public void interceptPackets(){
        protocolManager.addPacketListener(new PacketAdapter(MainGame.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Client.BLOCK_DIG) {
            @Override
            public void onPacketReceiving(PacketEvent event){
                PacketContainer packet = event.getPacket();
                EnumWrappers.PlayerDigType digType = packet.getPlayerDigTypes().getValues().get(0);
                //Bukkit.broadcastMessage("DigType: "+digType.name() + "-" + System.currentTimeMillis());
                if(MainGame.getGameFunctions().isInGame(event.getPlayer())) {
        			Games game = MainGame.getGameFunctions().getGame((event.getPlayer()));
        				for(GamesData data: game.getPlayers()) {
        					if(data.isActiveObsidian()) {
        						if(!digType.name().equals("START_DESTROY_BLOCK")) {
        		                	data.setActiveObsidian(false);
        		                }
        					}
        				}
                }
                
            }
        });
    }*/
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if(MainGame.getGameFunctions().isInGame(player)) {
			if(event.getBlock().getType().equals(Material.OBSIDIAN)) {
				event.setCancelled(true);
			}
		}
	}
	
	BukkitTask task;
	@EventHandler
	public void obsidian(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		
		if(MainGame.getGameFunctions().isInGame(player)) {
			Games game = MainGame.getGameFunctions().getGame(player);
			if(action.equals(Action.LEFT_CLICK_BLOCK)) {
				if(event.getClickedBlock().getType().equals(Material.OBSIDIAN)) {
					for(GamesData data: game.getPlayers()) {
						if(data.getPlayer().equals(player)) {
							data.setActiveObsidian(true);
							task = Bukkit.getScheduler().runTaskTimer(MainGame.getInstance(), new Runnable() {
								
								int i = 0;
								
								@Override
								public void run() {
									if(data.isActiveObsidian()) {
										event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 1000, 1));
										i ++;
										if(i>=30) {
											event.getPlayer().playSound(player.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, (float) 0.2, (float) 0.2);
											i = -6;
										}
									}
									else {
										event.getPlayer().removePotionEffect(PotionEffectType.SLOW_DIGGING);
										task.cancel();
									}
								}
							}, 0, 1);
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onFoodLevelChange(final FoodLevelChangeEvent e) {
		if(e.getEntity().getWorld().getName().equals(MainGame.getInstance().getSpawnLocation().getWorld().getName())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDamageEvent(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			if(!e.getCause().equals(DamageCause.ENTITY_ATTACK))
			{
				Player player = (Player) e.getEntity();
				if(e.getEntityType().toString().equalsIgnoreCase("PLAYER")) {
					if(MainGame.getGameFunctions().isInGame(player)) {
						Games game = MainGame.getGameFunctions().getGame(player);
						if(!game.getInGame()) {
							e.setCancelled(true);
						}
						else {
							if(e.getDamage() >= player.getHealth()) {
								e.setCancelled(true);
								String color = "";
								for(GamesData data: game.getPlayers()) {
									if(data.getPlayer().equals(player)) {
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
									}
								}
								for(GamesData data: game.getPlayers()) {
									data.getPlayer().sendMessage("§7☠ §" + color + player.getName());
								}
								MainGame.getGameFunctions().deadSpectator(player);
							}
						}
						
					}
				}
			}
		}
	}
	
	
	
	@EventHandler
	public void onDamageEvent(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player victim = (Player) e.getEntity();
			Player attacker = (Player) e.getDamager();
			if(MainGame.getGameFunctions().isInGame(victim)) {
				Games game = MainGame.getGameFunctions().getGame(victim);
				if(!game.getInGame()) {
					e.setCancelled(true);
				}
				else {
					int victimTeam = 0;
					int attackerTeam = 0;
					for(GamesData data: game.getPlayers()) {
						if(data.getPlayer().equals(victim)) {
							victimTeam = data.getTeam();
						}
						else if(data.getPlayer().equals(attacker)) {
							attackerTeam = data.getTeam();
						}
					}
					if(victimTeam == attackerTeam) {
						e.setCancelled(true);
					}
					else {
						if(e.getDamage() >= victim.getHealth()) {
							String colorV = "";
							String colorA = "";
							for(GamesData data: game.getPlayers()) {
								if(data.getPlayer().equals(victim)) {
									data.setDeath(data.getDeath() + 1);
									if(data.getTeam() == 1) {
										colorV = "a";
									}
									else if(data.getTeam() == 2) {
										colorV = "5";
									}
									else if(data.getTeam() == 3) {
										colorV = "e";
									}
									else if(data.getTeam() == 4) {
										colorV = "3";
									}
								}
								else if(data.getPlayer().equals(attacker)) {
									data.setKills(data.getKills() + 1);
									data.setMoney(data.getMoney() + 10);
									if(data.getTeam() == 1) {
										colorA = "a";
									}
									else if(data.getTeam() == 2) {
										colorA = "5";
									}
									else if(data.getTeam() == 3) {
										colorA = "e";
									}
									else if(data.getTeam() == 4) {
										colorA = "3";
									}
								}
							}
							for(GamesData data: game.getPlayers()) {
								//&aGameurDuXVI &7⚔ &cEmeglebon
								data.getPlayer().sendMessage("§" + colorA + attacker.getName() + " §7⚔ §" + colorV + victim.getName());
							}
							e.setCancelled(true);
							MainGame.getGameFunctions().deadSpectator(victim);
						}
					}
				}
				
			}
		}
		else if(e.getEntity() instanceof Player) {
			Player victim = (Player) e.getEntity();
			Games game = MainGame.getGameFunctions().getGame(victim);
			if(e.getDamage() >= victim.getHealth()) {
				String colorV = "";
				for(GamesData data: game.getPlayers()) {
					if(data.getPlayer().equals(victim)) {
						data.setDeath(data.getDeath() + 1);
						if(data.getTeam() == 1) {
							colorV = "a";
						}
						else if(data.getTeam() == 2) {
							colorV = "5";
						}
						else if(data.getTeam() == 3) {
							colorV = "e";
						}
						else if(data.getTeam() == 4) {
							colorV = "3";
						}
					}
				}
				for(GamesData data: game.getPlayers()) {
					LivingEntity lEntity = (LivingEntity) e.getDamager();
					int health = (int) Math.round(lEntity.getHealth());
					data.getPlayer().sendMessage("§f" + e.getDamager().getCustomName().replace(health + "§4❤", "").replace("§6", "") + "§7⚔ §" + colorV + victim.getName());
				}
				e.setCancelled(true);
				MainGame.getGameFunctions().deadSpectator(victim);
			}				
		}
	}
	
	
	
	@EventHandler
	public void onDropItem(PlayerDropItemEvent e) {
		if(e.getPlayer().getGameMode().equals(GameMode.ADVENTURE)||e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
			if(MainGame.getGameFunctions().isInGame((Player) e.getPlayer())) {
				Games game = MainGame.getGameFunctions().getGame((Player) e.getPlayer());
				if(!game.getInGame()) {
					e.setCancelled(true);
				}
			}
		}
	}
	
	
	public ItemStack getItem( Material material, int amount, int dataTag, String customName, String lore) {
		ItemStack It = new ItemStack(material, amount, (byte)dataTag);
		ItemMeta metaIt = It.getItemMeta();
		
		metaIt.setDisplayName(MeneliaAPI.getInstance().colorize(customName));
		
		
		if(lore.length() != 0) {
			String[] arrOfStr = lore.split("/n");
			for (int i=0; i < arrOfStr.length; i++)
		    {
		      arrOfStr[i] = arrOfStr[i].replace("&", "§");
		    }
			metaIt.setLore(Arrays.asList(arrOfStr));
		}
		
		metaIt.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		It.setItemMeta(metaIt);
		return It;
	}
}

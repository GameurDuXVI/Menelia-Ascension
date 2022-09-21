package fr.gameurduxvi.ascension;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import fr.gameurduxvi.ascension.database.Games;
import fr.gameurduxvi.ascension.database.GamesData;
import fr.gameurduxvi.ascension.database.Maps;
import fr.gameurduxvi.meneliaapi.MeneliaAPI;

public class EventListenerHub  implements Listener{
	public void inventoryVote(Player player) {
		Inventory inv = Bukkit.createInventory(null, 54, MeneliaAPI.getInstance().getLang(player, "Vote", "Vote"));
		
		int i = 9;
		for(Maps map: MainGame.getInstance().getMaps()) {
			i++;
			if(i == 17|| i == 26 || i == 35) {
				i++;
				i++;
			}
			
			int thisMapVote = 0;
			int amount = 0;
			Games game = MainGame.getGameFunctions().getGame(player);
			
			for(GamesData data: game.getPlayers()) {
				if(map.getId() == data.getVote()) {
					thisMapVote++;
				}
			}
			
			if(thisMapVote == 0) {
				amount = 1;
			}
			else {
				amount = thisMapVote;
			}
			inv.setItem(i, MainGame.getEventListener().getItem(Material.PAPER, amount, 0, "§b" + map.getMapNameColored(), "§b" + thisMapVote + " votes"));
		}
		
		player.openInventory(inv);
	}
	
	public void addVote(Player player, int mapId) {
		for(Maps map: MainGame.getInstance().getMaps()) {
			if(map.getId() == mapId) {
				Games game = MainGame.getGameFunctions().getGame(player);
				for(GamesData data: game.getPlayers()) {
					if(data.getPlayer().getName().equals(player.getName())) {
						data.setVote(mapId);
						inventoryVote(player);
					}
				}
			}
		}
	}
	
	public void inventoryTeam(Player player) {
		Inventory inv = Bukkit.createInventory(null, 54, MeneliaAPI.getInstance().getLang(player, "Team", "Team"));
		
		Games map = MainGame.getGameFunctions().getGame(player);
		
		String GreenTeam = "";
		String PurpleTeam = "";
		String YellowTeam = "";
		String CyanTeam = "";
		
		String RandomTeam = "";
		
		for(GamesData data: map.getPlayers()) {
			if(data.getTeam() == 0) {
				if(RandomTeam == "") {
					RandomTeam = "§b- " + data.getPlayer().getName();
				}
				else {
					RandomTeam = RandomTeam + "/n" + "§b- " + data.getPlayer().getName();
				}
			}
			else if(data.getTeam() == 1) {
				if(GreenTeam == "") {
					GreenTeam = "§b- " + data.getPlayer().getName();
				}
				else {
					GreenTeam = GreenTeam + "/n" + "§b- " + data.getPlayer().getName();
				}
			}
			else if(data.getTeam() == 2) {
				if(PurpleTeam == "") {
					PurpleTeam = "§b- " + data.getPlayer().getName();
				}
				else {
					PurpleTeam = PurpleTeam + "/n" + "§b- " + data.getPlayer().getName();
				}
			}
			else if(data.getTeam() == 3) {
				if(YellowTeam == "") {
					YellowTeam = "§b- " + data.getPlayer().getName();
				}
				else {
					YellowTeam = YellowTeam + "/n" + "§b- " + data.getPlayer().getName();
				}
			}
			else if(data.getTeam() == 4) {
				if(CyanTeam == "") {
					CyanTeam = "§b- " + data.getPlayer().getName();
				}
				else {
					CyanTeam = CyanTeam + "/n" + "§b- " + data.getPlayer().getName();
				}
			}
		}		
		
		inv.setItem(11, getBanner(DyeColor.GREEN, 1, MeneliaAPI.getInstance().getLang(player, "§aVert", "§aGreen"), GreenTeam));
		inv.setItem(12, getBanner(DyeColor.PURPLE, 1, MeneliaAPI.getInstance().getLang(player, "§5Violet", "§5Purple"), PurpleTeam));
		inv.setItem(14, getBanner(DyeColor.YELLOW, 1, MeneliaAPI.getInstance().getLang(player, "§eJaune", "§eYellow"), YellowTeam));
		inv.setItem(15, getBanner(DyeColor.CYAN, 1, MeneliaAPI.getInstance().getLang(player, "§9Cyan", "§9Cyan"), CyanTeam));
		
		inv.setItem(31, getBanner(DyeColor.WHITE, 1, MeneliaAPI.getInstance().getLang(player, "§fAléatoire", "§fRandom"), RandomTeam));
		
		player.openInventory(inv);
	}
	
	@SuppressWarnings("deprecation")
	public void addToTeam(Player player, int team, boolean showInv) {
		int green = 0;
		int purple = 0;
		int yellow = 0;
		int cyan = 0;
		
		int playerTeam = 0;
		
		Games game = MainGame.getGameFunctions().getGame(player);
		for(GamesData data: game.getPlayers()) {
			if(data.getPlayer().getName().equals(player.getName())) {
				playerTeam = data.getTeam();
			}
			if(data.getTeam() == 1) {
				green++;
			}
			else if(data.getTeam() == 2) {
				purple++;
			}
			else if(data.getTeam() == 3) {
				yellow++;
			}
			else if(data.getTeam() == 4) {
				cyan++;
			}
		}
		
		if(team == 1) {
			if(playerTeam == 2) {
				if(teamCheck(green, purple)) {
					return;
				}
			}
			
			
			else if(playerTeam == 3) {
				if(teamCheck(green, yellow)) {
					return;
				}
			}
			
			
			else if(playerTeam == 4) {
				if(teamCheck(green, cyan)) {
					return;
				}
			}
			
			
			else {
				if(teamCheck(green, purple)) {
					return;
				}
				if(teamCheck(green, yellow)) {
					return;
				}
				if(teamCheck(green, cyan)) {
					return;
				}
			}
			
			
		}
		
		if(team == 2) {
			if(playerTeam == 1) {
				if(teamCheck(purple, green)) {
					return;
				}
			}
			
			
			else if(playerTeam == 3) {
				if(teamCheck(purple, yellow)) {
					return;
				}
			}
			
			
			else if(playerTeam == 4) {
				if(teamCheck(purple, cyan)) {
					return;
				}
			}
			
			
			else {
				if(teamCheck(purple, green)) {
					return;
				}
				if(teamCheck(purple, yellow)) {
					return;
				}
				if(teamCheck(purple, cyan)) {
					return;
				}
			}
			
			
		}
		
		if(team == 3) {
			if(playerTeam == 1) {
				if(teamCheck(yellow, green)) {
					return;
				}
			}
			
			
			else if(playerTeam == 2) {
				if(teamCheck(yellow, purple)) {
					return;
				}
			}
			
			
			else if(playerTeam == 4) {
				if(teamCheck(yellow, cyan)) {
					return;
				}
			}
			
			
			else {
				if(teamCheck(yellow, green)) {
					return;
				}
				if(teamCheck(yellow, purple)) {
					return;
				}
				if(teamCheck(yellow, cyan)) {
					return;
				}
			}
			
			
		}
		
		if(team == 4) {
			if(playerTeam == 1) {
				if(teamCheck(cyan, green)) {
					return;
				}
			}
			
			
			else if(playerTeam == 2) {
				if(teamCheck(cyan, purple)) {
					return;
				}
			}
			
			
			else if(playerTeam == 3) {
				if(teamCheck(cyan, yellow)) {
					return;
				}
			}
			
			
			else {
				if(teamCheck(cyan, green)) {
					return;
				}
				if(teamCheck(cyan, purple)) {
					return;
				}
				if(teamCheck(cyan, yellow)) {
					return;
				}
			}
			
			
		}
		
		for(GamesData data: game.getPlayers()) {
			if(data.getPlayer().getName().equals(player.getName())) {
				data.setTeam(team);
				break;
			}
		}
		
		if(team == 0) {
			player.getInventory().setItem(0, getBanner(DyeColor.WHITE, 1, MeneliaAPI.getInstance().getLang(player, "§bTeam", "§bTeam"), ""));
			//player.setPlayerListName("§f" + player.getName());
		}
		else if(team == 1) {
			player.getInventory().setItem(0, getBanner(DyeColor.GREEN, 1, MeneliaAPI.getInstance().getLang(player, "§bTeam", "§bTeam"), ""));
			//player.setPlayerListName("§a" + player.getName());
		}
		else if(team == 2) {
			player.getInventory().setItem(0, getBanner(DyeColor.PURPLE, 1, MeneliaAPI.getInstance().getLang(player, "§bTeam", "§bTeam"), ""));
			//player.setPlayerListName("§5" + player.getName());
		}
		else if(team == 3) {
			player.getInventory().setItem(0, getBanner(DyeColor.YELLOW, 1, MeneliaAPI.getInstance().getLang(player, "§bTeam", "§bTeam"), ""));
			//player.setPlayerListName("§e" + player.getName());
		}
		else if(team == 4) {
			player.getInventory().setItem(0, getBanner(DyeColor.CYAN, 1, MeneliaAPI.getInstance().getLang(player, "§bTeam", "§bTeam"), ""));
			//player.setPlayerListName("§9" + player.getName());
		}
		Scoreboard sb = MainGame.getInstance().manager.getMainScoreboard();
		try {
			sb.getTeam(player.getName()).unregister();
		}catch (Exception e) {
		}
		if(showInv) {
			inventoryTeam(player);
		}
		
		for(GamesData data: game.getPlayers()) {
			Scoreboard board = data.getPlayer().getScoreboard();
			
			Team green1 = null;
		    if(board.getTeam("green") == null) {
		    	green1 = board.registerNewTeam("green");
		    }
		    else {
		    	green1 = board.getTeam("green");
		    }			    
			green1.setPrefix(MeneliaAPI.getInstance().colorize("&a"));
			
			Team purple1 = null;
		    if(board.getTeam("purple") == null) {
		    	purple1 = board.registerNewTeam("purple");
		    }
		    else {
		    	purple1 = board.getTeam("purple");
		    }			    
		    purple1.setPrefix(MeneliaAPI.getInstance().colorize("&5"));
		    
		    Team yellow1 = null;
		    if(board.getTeam("yellow") == null) {
		    	yellow1 = board.registerNewTeam("yellow");
		    }
		    else {
		    	yellow1 = board.getTeam("yellow");
		    }			    
		    yellow1.setPrefix(MeneliaAPI.getInstance().colorize("&e"));
		    
		    Team cyan1 = null;
		    if(board.getTeam("cyan") == null) {
		    	cyan1 = board.registerNewTeam("cyan");
		    }
		    else {
		    	cyan1 = board.getTeam("cyan");
		    }			    
		    cyan1.setPrefix(MeneliaAPI.getInstance().colorize("&3"));
			
			for(GamesData data2: game.getPlayers()) {
				if(data2.getTeam() == 1) green1.addPlayer(data2.getPlayer());
				if(data2.getTeam() == 2) purple1.addPlayer(data2.getPlayer());
				if(data2.getTeam() == 3) yellow1.addPlayer(data2.getPlayer());
				if(data2.getTeam() == 4) cyan1.addPlayer(data2.getPlayer());
				if(data2.getTeam() == 0) {
					if(green1.getEntries().contains(data2.getPlayer().getName())) green1.removePlayer(data2.getPlayer());
					if(purple1.getEntries().contains(data2.getPlayer().getName())) purple1.removePlayer(data2.getPlayer());
					if(yellow1.getEntries().contains(data2.getPlayer().getName())) yellow1.removePlayer(data2.getPlayer());
					if(cyan1.getEntries().contains(data2.getPlayer().getName())) cyan1.removePlayer(data2.getPlayer());
				}
			}
			player.setScoreboard(board);
		}
	}
	
	public boolean teamCheck(int number1, int number2) {
		if(number1 + 1 > number2) {
			int diff = (number1 + 1) - number2;
			if(number1 < 2) {
				if(diff > 2) {
					return true;
				}
			}
			else {
				if(diff >= 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack getBanner(DyeColor color, int amount, String customName, String lore) {
		ItemStack Banner = new ItemStack(org.bukkit.Material.BANNER, amount);
		BannerMeta customMBanner1 = (BannerMeta) Banner.getItemMeta();	
		customMBanner1.setDisplayName(customName);	
		customMBanner1.setBaseColor(color);		

		if(lore.length() != 0) {
			String[] arrOfStr = lore.split("/n");
			customMBanner1.setLore(Arrays.asList(arrOfStr));
		}
		
		Banner.setItemMeta(customMBanner1);
		return Banner;
	}
	
	@EventHandler
	public void OnClick(InventoryClickEvent event) {
		
		Inventory inv = event.getInventory();
		Player player = (Player)event.getWhoClicked();
		ItemStack current = event.getCurrentItem();
		
		if(MainGame.getGameFunctions().isInGame(player)) {
			if(current == null) return;
			
			if(inv.getName().equalsIgnoreCase(MeneliaAPI.getInstance().getLang(player, "Vote", "Vote"))) {
				if(current.getType().equals(Material.PAPER)) {
					for(Maps map: MainGame.getInstance().getMaps()) {
						if(current.getItemMeta().getDisplayName().contains(map.getName())) {
							addVote(player, map.getId());
						}
					}
				}
				event.setCancelled(true);
			}
			else if(inv.getName().equalsIgnoreCase(MeneliaAPI.getInstance().getLang(player, "Team", "Team"))) {
				event.setCancelled(true);
				if(current.getType().equals(Material.BANNER)) {
					if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§aVert", "§aGreen"))) {				
						addToTeam(player, 1, true);
					}
					else if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§5Violet", "§5Purple"))) {	
						addToTeam(player, 2, true);
					}
					else if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§eJaune", "§eYellow"))) {
						addToTeam(player, 3, true);
					}
					else if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§9Cyan", "§9Cyan"))) {
						addToTeam(player, 4, true);
					}
					else {
						addToTeam(player, 0, true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void OnInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		
		if(MainGame.getGameFunctions().isInGame(player)) {
			ItemStack it = event.getItem();
			
			if(it == null) return;
			
			if(player.getWorld().getName().equalsIgnoreCase(MainGame.getInstance().getSpawnLocation().getWorld().getName()) ) {
				if(it.getType() == Material.BANNER) {
					if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
						if(it.hasItemMeta()) {
							if(it.getItemMeta().getDisplayName().equalsIgnoreCase(MeneliaAPI.getInstance().getLang(player, "§bTeam", "§bTeam"))) {
								inventoryTeam(player);
							}
						}
					}
				}
				else if(it.getType() == Material.PAPER) {
					if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
						if(it.hasItemMeta()) {
							if(it.getItemMeta().getDisplayName().equalsIgnoreCase(MeneliaAPI.getInstance().getLang(player, "§bVote", "§bVote"))) {
								inventoryVote(player);
							}
						}
					}
				}
				else if(it.getType() == Material.BED) {
					if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
						if(it.hasItemMeta()) {
							if(it.getItemMeta().getDisplayName().equalsIgnoreCase(MeneliaAPI.getInstance().getLang(player, "§bHub", "§bHub"))) {
								MainGame.getGameFunctions().leaveGame(player, true);
							}
						}
					}
				}
			}
		}
	}
}

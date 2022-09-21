package fr.gameurduxvi.ascension;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.gameurduxvi.ascension.database.Maps;
import fr.gameurduxvi.meneliaapi.MeneliaAPI;

public class CommandListener implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(cmd.getName().equalsIgnoreCase("aasc")||cmd.getName().equalsIgnoreCase("adminascension")) {
				if(player.hasPermission("adminascension.use")) {
					if(args.length > 0) {
						if(argument(args, 1).equalsIgnoreCase("hub") || argument(args, 1).equalsIgnoreCase("h")) {
							if(argument(args, 2).equalsIgnoreCase("load")||argument(args, 2).equalsIgnoreCase("l")) {
								if(args.length == 2) {
									File file = new File("plugins/Menelia/Ascension/Hub.schematic");
									if(file.exists()) {
										MainGame.getGameFunctions().loadHub(player, false, 0);
										player.sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &aLoading a new schematic for map \"Hub\""));
									}
									else {
										player.sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &cNo schematic found for this map!"));
										player.sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &cYou need to generate it before"));
										player.sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &e/aasc map generate Hub"));
									}
								}
								else if(args.length == 3) {
									File file = new File("plugins/Menelia/Ascension/Hub.schematic");
									if(file.exists()) {
										MainGame.getGameFunctions().loadHub(player, true, Integer.parseInt(args[2]));
										player.sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &aLoading a new schematic for map \"Hub\""));
									}
									else {
										player.sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &cNo schematic found for this map!"));
										player.sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &cYou need to generate it before"));
										player.sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &e/aasc map generate Hub"));
									}
								}
								else {
									player.sendMessage(MeneliaAPI.getInstance().colorize("&6/aasc hub load (game number)"));
								}
							}
							else {
								player.sendMessage(MeneliaAPI.getInstance().colorize("&6Admin Ascension command:"));
								player.sendMessage(MeneliaAPI.getInstance().colorize("&b/aasc hub &eload"));
							}
							
						}
						else if(argument(args, 1).equalsIgnoreCase("join")||argument(args, 1).equalsIgnoreCase("j")) {
							MainGame.getGameFunctions().makeJoin(player);
						}
						else if(argument(args, 1).equalsIgnoreCase("map")||argument(args, 1).equalsIgnoreCase("m")) {
							if(argument(args, 2).equalsIgnoreCase("list")||argument(args, 2).equalsIgnoreCase("l")) {
								if(MainGame.getInstance().getMaps().isEmpty()) {
									player.sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &cNo maps has been found !"));
								}
								else {
									player.sendMessage(MeneliaAPI.getInstance().colorize("&6Maps list"));
									for(Maps map: MainGame.getInstance().getMaps()) {
										player.sendMessage(MeneliaAPI.getInstance().colorize("&e - &b" + map.getName()));
									}
								}
							}
							else if(argument(args, 2).equalsIgnoreCase("load")) {
								if(args.length == 3) {
									if(MainGame.getGameFunctions().mapExist(args[2])) {
										File file = new File("plugins/Menelia/Ascension/schematics/" + args[2] + ".schematic");
										if(file.exists()) {
											MainGame.getGameFunctions().loadMap(player, args[2], false, 0);
											player.sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &aLoading a new schematic for map \"" + args[2] + "\""));
										}
										else {
											player.sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &cNo schematic found for this map!"));
										}
									}
									else {
										player.sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &cThe map hasn't be found !"));
									}
								}
								else if(args.length == 4) {
									if(MainGame.getGameFunctions().mapExist(args[2])) {
										File file = new File("plugins/Menelia/Ascension/schematics/" + args[2] + ".schematic");
										if(file.exists()) {
											MainGame.getGameFunctions().loadMap(player, args[2], true, Integer.parseInt(args[3]));
											player.sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &aLoading a new schematic for map \"" + args[2] + "\""));
										}
										else {
											player.sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &cNo schematic found for this map!"));
										}
									}
									else {
										player.sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &cThe map hasn't be found !"));
									}
								}
								else {
									player.sendMessage(MeneliaAPI.getInstance().colorize("&6/aasc map load &e<name> (map number)"));
								}
							}
							else if(argument(args, 2).equalsIgnoreCase("info")) {
								if(args.length == 3) {
									boolean found = false;
									for(Maps map: MainGame.getInstance().getMaps()) {
										if(args[2].equalsIgnoreCase(map.getName())) {
											found = true;
											player.sendMessage(MeneliaAPI.getInstance().colorize("&b" + map.getName() + "&6:"));
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - id: " + map.getId()));
											
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Build &aGreen &6: &7" + MainGame.getGameFunctions().locationToText(map.getBuildGreen())));
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Build &5Purple &6: &7" + MainGame.getGameFunctions().locationToText(map.getBuildPurple())));
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Build &eYellow &6: &7" + MainGame.getGameFunctions().locationToText(map.getBuildYellow())));
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Build Cyan &6: &7" + MainGame.getGameFunctions().locationToText(map.getBuildCyan())));
											
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Lower Teleporter &aGreen &6: &7" + MainGame.getGameFunctions().locationToText(map.getLowerTeleporterGreen())));
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Lower Teleporter &5Purple &6: &7" + MainGame.getGameFunctions().locationToText(map.getLowerTeleporterPurple())));
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Lower Teleporter &eYellow &6: &7" + MainGame.getGameFunctions().locationToText(map.getLowerTeleporterYellow())));
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Lower Teleporter &9Cyan &6: &7" + MainGame.getGameFunctions().locationToText(map.getLowerTeleporterCyan())));
											
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Upper Teleporter &aGreen &6: &7" + MainGame.getGameFunctions().locationToText(map.getUpperTeleporterGreen())));
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Upper Teleporter &5Purple &6: &7" + MainGame.getGameFunctions().locationToText(map.getUpperTeleporterPurple())));
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Upper Teleporter &eYellow &6: &7" + MainGame.getGameFunctions().locationToText(map.getUpperTeleporterYellow())));
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Upper Teleporter &9Cyan &6: &7" + MainGame.getGameFunctions().locationToText(map.getUpperTeleporterCyan())));
											
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Obsidian &aGreen &6: &7" + MainGame.getGameFunctions().locationToText(map.getObsidianGreen())));
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Obsidian &5Purple &6: &7" + MainGame.getGameFunctions().locationToText(map.getObsidianPurple())));
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Obsidian &eYellow &6: &7" + MainGame.getGameFunctions().locationToText(map.getObsidianYellow())));
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Obsidian &9Cyan &6: &7" + MainGame.getGameFunctions().locationToText(map.getObsidianCyan())));
											
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Players &aGreen &6: &7" + MainGame.getGameFunctions().locationToText(map.getPlayerLocationGreen())));
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Players &5Purple &6: &7" + MainGame.getGameFunctions().locationToText(map.getPlayerLocationPurple())));
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Players &eYellow &6: &7" + MainGame.getGameFunctions().locationToText(map.getPlayerLocationYellow())));
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Players &9Cyan &6: &7" + MainGame.getGameFunctions().locationToText(map.getPlayerLocationCyan())));
											
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Shop &aGreen &6: &7" + MainGame.getGameFunctions().locationToText(map.getShopGreen())));
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Shop &5Purple &6: &7" + MainGame.getGameFunctions().locationToText(map.getShopPurple())));
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Shop &eYellow &6: &7" + MainGame.getGameFunctions().locationToText(map.getShopYellow())));
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Shop &9Cyan &6: &7" + MainGame.getGameFunctions().locationToText(map.getShopCyan())));
											
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Sell &aGreen &6: &7" + MainGame.getGameFunctions().locationToText(map.getSellGreen())));
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Sell &5Purple &6: &7" + MainGame.getGameFunctions().locationToText(map.getSellPurple())));
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Sell &eYellow &6: &7" + MainGame.getGameFunctions().locationToText(map.getSellYellow())));
											player.sendMessage(MeneliaAPI.getInstance().colorize("&6 - Sell &9Cyan &6: &7" + MainGame.getGameFunctions().locationToText(map.getSellCyan())));
										}
									}
									if(!found) {
										player.sendMessage(MeneliaAPI.getInstance().colorize(MainGame.getInstance().pluginPrefix + " &cThe map hasn't be found !"));
									}
								}
								else {
									player.sendMessage(MeneliaAPI.getInstance().colorize("&6/aasc map info &e<name>"));
								}
							}
							else {
								player.sendMessage(MeneliaAPI.getInstance().colorize("&6Admin Ascension command:"));
								player.sendMessage(MeneliaAPI.getInstance().colorize("&b/aasc map &elist"));
								player.sendMessage(MeneliaAPI.getInstance().colorize("&b/aasc map &eload"));
								player.sendMessage(MeneliaAPI.getInstance().colorize("&b/aasc map &einfo"));
							}
						}
						else if(argument(args, 1).equalsIgnoreCase("reload")||argument(args, 1).equalsIgnoreCase("rl")) {
							MainGame.getGameFunctions().reloadJsonData();
							player.sendMessage(MainGame.getInstance().pluginPrefix + " §aJson data reloaded with succses!");
							player.sendMessage(MainGame.getInstance().pluginPrefix + " §aSee console for any errors.");
						}
						else {
							player.sendMessage(MeneliaAPI.getInstance().colorize("&6Admin Ascension command:"));
							player.sendMessage(MeneliaAPI.getInstance().colorize("&b/aasc &ehub"));
							player.sendMessage(MeneliaAPI.getInstance().colorize("&b/aasc &emap"));
							player.sendMessage(MeneliaAPI.getInstance().colorize("&b/aasc &ereload"));
						}
					}
					else {
						player.sendMessage(MeneliaAPI.getInstance().colorize("&6Admin Ascension command:"));
						player.sendMessage(MeneliaAPI.getInstance().colorize("&b/aasc &ehub"));
						player.sendMessage(MeneliaAPI.getInstance().colorize("&b/aasc &emap"));
						player.sendMessage(MeneliaAPI.getInstance().colorize("&b/aasc &ereload"));
					}
				}
			}
		}
		return false;
	}
	
	public String argument(String[] args, int i) {
		if(args.length >= i) {
			return args[i-1];
		}
		return "";
	}
}
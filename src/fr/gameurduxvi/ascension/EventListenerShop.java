package fr.gameurduxvi.ascension;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.gameurduxvi.ascension.database.Games;
import fr.gameurduxvi.ascension.database.GamesData;
import fr.gameurduxvi.meneliaapi.MeneliaAPI;

public class EventListenerShop implements Listener {
	
	@EventHandler
    public void on(PlayerInteractEntityEvent e) {
		Player player = e.getPlayer();
		if(MainGame.getGameFunctions().isInGame(player)) {
	        if(e.getRightClicked() instanceof Villager) {
	            Villager v = (Villager) e.getRightClicked();
	            if(v.getCustomName().equalsIgnoreCase("§eShop")) {
	            	e.setCancelled(true);
	            	inventoryShop(player);
	            }
	            else if(v.getCustomName().equalsIgnoreCase("§eSell")) {
	            	e.setCancelled(true);
	            	
	            	boolean exchanged = false;
	            	
	            	if(loots(player, Material.ROTTEN_FLESH, 1)) exchanged = true;
	            	if(loots(player, Material.CARROT_ITEM, 2)) exchanged = true;
	            	if(loots(player, Material.POTATO_ITEM, 2)) exchanged = true;
	            	if(loots(player, Material.IRON_INGOT, 6)) exchanged = true;
	            	if(loots(player, Material.SLIME_BALL, 2)) exchanged = true;
	            	if(loots(player, Material.STRING, 1)) exchanged = true;
	            	if(loots(player, Material.SPIDER_EYE, 2)) exchanged = true;
	            	if(loots(player, Material.BONE, 3)) exchanged = true;
	            	if(loots(player, Material.MAGMA_CREAM, 4)) exchanged = true;
	            	if(loots(player, Material.EMERALD, 6)) exchanged = true;
	            	if(loots(player, Material.TOTEM, 5)) exchanged = true;
	            	if(loots(player, Material.GLASS_BOTTLE, 5)) exchanged = true;
	            	if(loots(player, Material.SULPHUR, 5)) exchanged = true;
	            	if(loots(player, Material.REDSTONE, 4)) exchanged = true;
	            	if(loots(player, Material.SUGAR, 4)) exchanged = true;
	            	if(loots(player, Material.GLOWSTONE, 4)) exchanged = true;
	            	if(loots(player, Material.STICK, 3)) exchanged = true;
	            	if(loots(player, Material.COAL, 5)) exchanged = true;
	            	if(loots(player, Material.SKULL_ITEM, 40)) exchanged = true;
	            	if(loots(player, Material.BLAZE_ROD, 6)) exchanged = true;
	            	if(loots(player, Material.GOLD_NUGGET, 6)) exchanged = true;
	            	if(loots(player, Material.RED_ROSE, 7)) exchanged = true;
	            	if(loots(player, Material.ENDER_PEARL, 8)) exchanged = true;
	            	
	            	if(!exchanged) {
	            		player.sendMessage(MeneliaAPI.getInstance().getLang(player, "§cVous n'avez rien a échanger!", "§cYou have nothing to exchange!"));
	            	}
	            	
	            }
	        }
		}
	}
	
	public boolean loots(Player player, Material mat, int amount) {
		boolean found = false;
		int money = 0;
		int totalAmount = 0;
		
    	if(player.getInventory().contains(mat)){
    		for (int i = 0; i < 36; i++) {
    			ItemStack slot = player.getInventory().getItem(i);
                if (slot == null || !slot.getType().equals(mat))
                    continue;
                found = true;
                money += slot.getAmount() * amount;
                totalAmount += slot.getAmount();
            }
    		player.getInventory().remove(mat);
    	}
    	if(found) {
    		player.sendMessage(MeneliaAPI.getInstance().getLang(player, "§aVous avez échangé " + totalAmount + " " + mat.name() + " pour " + money + "$.", "§3You traded " + totalAmount + " " + mat.name()  + " for " + money + "$"));
    		//player.getInventory().addItem(new ItemStack(Material.DIAMOND, diamonds));
    		Games game = MainGame.getGameFunctions().getGame(player);
    		for(GamesData data: game.getPlayers()) {
				if(data.getPlayer().equals(player)) {
					data.setMoney(data.getMoney() + money);
				}
			}
    	}
		return found;
	}
	
	public void inventoryShop(Player player) {
		Inventory inv = Bukkit.createInventory(null, 54, MeneliaAPI.getInstance().getLang(player, "Boutique", "Shop"));
    	
    	ItemStack panel = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
    	ItemMeta metaPanel = panel.getItemMeta();
    	
    	metaPanel.setDisplayName("§a");
    	
    	panel.setItemMeta(metaPanel);
    	
    	for(int i = 0; i<54; i++) {
    		inv.setItem(i, panel);
    	}
    	
    	
    	ItemStack sword = new ItemStack(Material.IRON_SWORD);
    	ItemMeta metaSword = panel.getItemMeta();
    	
    	metaSword.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Epées", "§9Swords"));
    	metaSword.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
    	metaSword.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    	metaSword.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	sword.setItemMeta(metaSword);
    	
    	inv.setItem(10, sword);
    	
    	
    	ItemStack armor = new ItemStack(Material.IRON_CHESTPLATE);
    	ItemMeta metaArmor = panel.getItemMeta();
    	
    	metaArmor.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Armures", "§9Armors"));
    	metaArmor.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
    	metaArmor.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    	metaArmor.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	armor.setItemMeta(metaArmor);
    	
    	inv.setItem(12, armor);
    	
    	
    	ItemStack blocks = new ItemStack(Material.END_BRICKS);
    	ItemMeta metaBlocks = panel.getItemMeta();
    	
    	metaBlocks.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Blocs", "§9Blocks"));
    	metaBlocks.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
    	metaBlocks.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    	
    	blocks.setItemMeta(metaBlocks);
    	
    	inv.setItem(14, blocks);
    	
    	
    	ItemStack tools = new ItemStack(Material.IRON_PICKAXE);
    	ItemMeta metaTools = panel.getItemMeta();
    	
    	metaTools.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Pioches", "§9Pickaxes"));
    	metaTools.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
    	metaTools.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    	metaTools.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	tools.setItemMeta(metaTools);
    	
    	inv.setItem(16, tools);
    	
    	
    	ItemStack echants = new ItemStack(Material.ENCHANTED_BOOK);
    	ItemMeta metaEchants = panel.getItemMeta();
    	
    	metaEchants.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Enchantements", "§9Enchants"));
    	metaEchants.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
    	metaEchants.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    	
    	echants.setItemMeta(metaEchants);
    	
    	inv.setItem(30, echants);
    	
    	
    	ItemStack potions = new ItemStack(Material.POTION);
    	ItemMeta metaPotions = panel.getItemMeta();
    	
    	metaPotions.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Consommables", "§9Consumables"));
    	metaPotions.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
    	metaPotions.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    	metaPotions.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    	
    	potions.setItemMeta(metaPotions);
    	
    	inv.setItem(32, potions);
    	
    	ItemStack back = new ItemStack(Material.BARRIER);
    	ItemMeta metaBack = panel.getItemMeta();
    	
    	metaBack.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§aFermer", "§aClose"));
    	metaBack.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	back.setItemMeta(metaBack);
    	
    	inv.setItem(49, back);
    	
    	player.openInventory(inv);
	}
	
	public void inventoryShopPickaxes(Player player) {
		Inventory inv = Bukkit.createInventory(null, 54, MeneliaAPI.getInstance().getLang(player, "Pioches", "Pickaxes"));
    	
    	ItemStack panel = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
    	ItemMeta metaPanel = panel.getItemMeta();
    	
    	metaPanel.setDisplayName("§a");
    	
    	panel.setItemMeta(metaPanel);
    	
    	for(int i = 0; i<54; i++) {
    		inv.setItem(i, panel);
    	}
    	
    	ItemStack pickaxe1 = new ItemStack(Material.STONE_PICKAXE);
    	ItemMeta metaPickaxe1 = panel.getItemMeta();
    	
    	metaPickaxe1.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Pioche en Pierre", "§9Stone Pickaxe"));
    	metaPickaxe1.addEnchant(Enchantment.DIG_SPEED, 8, true);
    	metaPickaxe1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	ArrayList<String> lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaPickaxe1.setLore(lore);
    	
    	pickaxe1.setItemMeta(metaPickaxe1);
    	
    	inv.setItem(20, pickaxe1);
    	
    	ItemStack pickaxe2 = new ItemStack(Material.IRON_PICKAXE);
    	ItemMeta metaPickaxe2 = panel.getItemMeta();
    	
    	metaPickaxe2.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Pioche en Fer", "§9Iron Pickaxe"));
    	metaPickaxe2.addEnchant(Enchantment.DIG_SPEED, 8, true);
    	metaPickaxe2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaPickaxe2.setLore(lore);
    	
    	pickaxe2.setItemMeta(metaPickaxe2);
    	
    	inv.setItem(22, pickaxe2);
    	
    	ItemStack pickaxe3 = new ItemStack(Material.DIAMOND_PICKAXE);
    	ItemMeta metaPickaxe3 = panel.getItemMeta();
    	
    	metaPickaxe3.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Pioche en Diamant", "§9Diamond Pickaxe"));
    	metaPickaxe3.addEnchant(Enchantment.DIG_SPEED, 6, true);
    	metaPickaxe3.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaPickaxe3.setLore(lore);
    	
    	pickaxe3.setItemMeta(metaPickaxe3);
    	
    	inv.setItem(24, pickaxe3);

    	
    	ItemStack back = new ItemStack(Material.ARROW);
    	ItemMeta metaBack = panel.getItemMeta();
    	
    	metaBack.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§7Retour", "§7Back"));
    	metaBack.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	back.setItemMeta(metaBack);
    	
    	inv.setItem(49, back);
    	
    	player.openInventory(inv);
	}
	
	public void inventoryShopEnchant(Player player) {
		Inventory inv = Bukkit.createInventory(null, 54, MeneliaAPI.getInstance().getLang(player, "Echantements", "Enchants"));
    	
    	ItemStack panel = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
    	ItemMeta metaPanel = panel.getItemMeta();
    	
    	metaPanel.setDisplayName("§a");
    	
    	panel.setItemMeta(metaPanel);
    	
    	for(int i = 0; i<54; i++) {
    		inv.setItem(i, panel);
    	}
    	
    	ItemStack book1 = new ItemStack(Material.ENCHANTED_BOOK);
    	ItemMeta metaBook1 = panel.getItemMeta();
    	
    	metaBook1.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§1§9Livre Enchanté", "§1§9Enchanted Book"));
    	metaBook1.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
    	metaBook1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	ArrayList<String> lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaBook1.setLore(lore);
    	
    	book1.setItemMeta(metaBook1);
    	
    	inv.setItem(10, book1);
    	
    	ItemStack book2 = new ItemStack(Material.ENCHANTED_BOOK);
    	ItemMeta metaBook2 = panel.getItemMeta();
    	
    	metaBook2.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§2§9Livre Enchanté", "§2§9Enchanted Book"));
    	metaBook2.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
    	metaBook2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaBook2.setLore(lore);
    	
    	book2.setItemMeta(metaBook2);
    	
    	inv.setItem(12, book2);
    	
    	ItemStack book3 = new ItemStack(Material.ENCHANTED_BOOK);
    	ItemMeta metaBook3 = panel.getItemMeta();
    	
    	metaBook3.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§3§9Livre Enchanté", "§3§9Enchanted Book"));
    	metaBook3.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
    	metaBook3.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaBook3.setLore(lore);
    	
    	book3.setItemMeta(metaBook3);
    	
    	inv.setItem(14, book3);
    	
    	ItemStack book4 = new ItemStack(Material.ENCHANTED_BOOK);
    	ItemMeta metaBook4 = panel.getItemMeta();
    	
    	metaBook4.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§4§9Livre Enchanté", "§4§9Enchanted Book"));
    	metaBook4.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
    	metaBook4.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaBook4.setLore(lore);
    	
    	book4.setItemMeta(metaBook4);
    	
    	inv.setItem(16, book4);
    	
    	ItemStack book5 = new ItemStack(Material.ENCHANTED_BOOK);
    	ItemMeta metaBook5 = panel.getItemMeta();
    	
    	metaBook5.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§5§9Livre Enchanté", "§5§9Enchanted Book"));
    	metaBook5.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
    	metaBook5.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaBook5.setLore(lore);
    	
    	book5.setItemMeta(metaBook5);
    	
    	inv.setItem(28, book5);
    	
    	ItemStack book6 = new ItemStack(Material.ENCHANTED_BOOK);
    	ItemMeta metaBook6 = panel.getItemMeta();
    	
    	metaBook6.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§6§9Livre Enchanté", "§6§9Enchanted Book"));
    	metaBook6.addEnchant(Enchantment.DAMAGE_UNDEAD, 1, true);
    	metaBook6.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaBook6.setLore(lore);
    	
    	book6.setItemMeta(metaBook6);
    	
    	inv.setItem(30, book6);
    	
    	ItemStack book7 = new ItemStack(Material.ENCHANTED_BOOK);
    	ItemMeta metaBook7 = panel.getItemMeta();
    	
    	metaBook7.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§7§9Livre Enchanté", "§7§9Enchanted Book"));
    	metaBook7.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 1, true);
    	metaBook7.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaBook7.setLore(lore);
    	
    	book7.setItemMeta(metaBook7);
    	
    	inv.setItem(32, book7);
    	
    	ItemStack book8 = new ItemStack(Material.ENCHANTED_BOOK);
    	ItemMeta metaBook8 = panel.getItemMeta();
    	
    	metaBook8.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§8§9Livre Enchanté", "§8§9Enchanted Book"));
    	metaBook8.addEnchant(Enchantment.LOOT_BONUS_MOBS, 1, true);
    	metaBook8.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaBook8.setLore(lore);
    	
    	book8.setItemMeta(metaBook8);
    	
    	inv.setItem(34, book8);

    	
    	ItemStack back = new ItemStack(Material.ARROW);
    	ItemMeta metaBack = panel.getItemMeta();
    	
    	metaBack.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§7Retour", "§7Back"));
    	metaBack.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	back.setItemMeta(metaBack);
    	
    	inv.setItem(49, back);
    	
    	player.openInventory(inv);
	}
	
	public void inventoryShopBlocks(Player player) {
		Inventory inv = Bukkit.createInventory(null, 54, MeneliaAPI.getInstance().getLang(player, "Blocs", "Blocks"));
    	
    	ItemStack panel = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
    	ItemMeta metaPanel = panel.getItemMeta();
    	
    	metaPanel.setDisplayName("§a");
    	
    	panel.setItemMeta(metaPanel);
    	
    	for(int i = 0; i<54; i++) {
    		inv.setItem(i, panel);
    	}
    	
    	ItemStack sand = new ItemStack(Material.SAND);
    	ItemMeta metaSand = panel.getItemMeta();
    	
    	metaSand.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Sable", "§9Sand"));
    	metaSand.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	ArrayList<String> lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaSand.setLore(lore);
    	
    	sand.setItemMeta(metaSand);
    	
    	inv.setItem(21, sand);
    	
    	ItemStack ladder = new ItemStack(Material.LADDER);
    	ItemMeta metaLadder = ladder.getItemMeta();
    	
    	metaLadder.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Échelle", "§9Ladder"));
    	metaLadder.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaLadder.setLore(lore);
    	
    	ladder.setItemMeta(metaLadder);
    	
    	inv.setItem(23, ladder);

    	
    	ItemStack back = new ItemStack(Material.ARROW);
    	ItemMeta metaBack = back.getItemMeta();
    	
    	metaBack.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§7Retour", "§7Back"));
    	metaBack.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	back.setItemMeta(metaBack);
    	
    	inv.setItem(49, back);
    	
    	player.openInventory(inv);
	}
	
	public void inventoryShopSwords(Player player) {
		Inventory inv = Bukkit.createInventory(null, 54, MeneliaAPI.getInstance().getLang(player, "Epées", "Swords"));
    	
    	ItemStack panel = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
    	ItemMeta metaPanel = panel.getItemMeta();
    	
    	metaPanel.setDisplayName("§a");
    	
    	panel.setItemMeta(metaPanel);
    	
    	for(int i = 0; i<54; i++) {
    		inv.setItem(i, panel);
    	}
    	
    	ItemStack sword1 = new ItemStack(Material.GOLD_SWORD);
    	ItemMeta metaSword1 = sword1.getItemMeta();
    	
    	metaSword1.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Epée en Or", "§9Golden Sword"));
    	metaSword1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	metaSword1.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 1, true);
    	metaSword1.addEnchant(Enchantment.DAMAGE_UNDEAD, 1, true);
    	
    	ArrayList<String> lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaSword1.setLore(lore);
    	
    	sword1.setItemMeta(metaSword1);
    	
    	inv.setItem(10, sword1);
    	
    	ItemStack sword2 = new ItemStack(Material.STONE_SWORD);
    	ItemMeta metaSword2 = sword2.getItemMeta();
    	
    	metaSword2.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Epée en Pierre", "§9Stone Sword"));
    	metaSword2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaSword2.setLore(lore);
    	
    	sword2.setItemMeta(metaSword2);
    	
    	inv.setItem(12, sword2);
    	
    	ItemStack sword3 = new ItemStack(Material.IRON_SWORD);
    	ItemMeta metaSword3 = sword3.getItemMeta();
    	
    	metaSword3.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Epée en Fer", "§9Iron Sword"));
    	metaSword3.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaSword3.setLore(lore);
    	
    	sword3.setItemMeta(metaSword3);
    	
    	inv.setItem(14, sword3);
    	
    	ItemStack sword4 = new ItemStack(Material.DIAMOND_SWORD);
    	ItemMeta metaSword4 = sword4.getItemMeta();
    	
    	metaSword4.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Epée en Diamant", "§9Diamond Sword"));
    	metaSword4.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaSword4.setLore(lore);
    	
    	sword4.setItemMeta(metaSword4);
    	
    	inv.setItem(16, sword4);
    	
    	ItemStack bow1 = new ItemStack(Material.BOW);
    	ItemMeta metaBow1 = bow1.getItemMeta();
    	
    	metaBow1.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§1§9Arc", "§1§9Bow"));
    	metaBow1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaBow1.setLore(lore);
    	
    	bow1.setItemMeta(metaBow1);
    	
    	inv.setItem(29, bow1);
    	
    	ItemStack bow2 = new ItemStack(Material.BOW);
    	ItemMeta metaBow2 = bow2.getItemMeta();
    	
    	metaBow2.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§2§9Arc", "§2§9Bow"));
    	metaBow2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	metaBow2.addEnchant(Enchantment.ARROW_FIRE, 1, true);
    	metaBow2.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaBow2.setLore(lore);
    	
    	bow2.setItemMeta(metaBow2);
    	
    	inv.setItem(31, bow2);
    	
    	ItemStack arrow = new ItemStack(Material.ARROW);
    	ItemMeta metaArrow = arrow.getItemMeta();
    	
    	metaArrow.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Flèche", "§9Arrow"));
    	metaArrow.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaArrow.setLore(lore);
    	
    	arrow.setItemMeta(metaArrow);
    	
    	inv.setItem(33, arrow);
    	
    	ItemStack back = new ItemStack(Material.ARROW);
    	ItemMeta metaBack = back.getItemMeta();
    	
    	metaBack.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§7Retour", "§7Back"));
    	metaBack.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	back.setItemMeta(metaBack);
    	
    	inv.setItem(49, back);
    	
    	player.openInventory(inv);
	}
	
	public void inventoryShopConsumables(Player player) {
		Inventory inv = Bukkit.createInventory(null, 54, MeneliaAPI.getInstance().getLang(player, "Consommables", "Consumables"));
    	
    	ItemStack panel = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
    	ItemMeta metaPanel = panel.getItemMeta();
    	
    	metaPanel.setDisplayName("§a");
    	
    	panel.setItemMeta(metaPanel);
    	
    	for(int i = 0; i<54; i++) {
    		inv.setItem(i, panel);
    	}
    	
    	
    	ItemStack gApple = new ItemStack(Material.GOLDEN_APPLE);
    	ItemMeta metaGApple = gApple.getItemMeta();
    	
    	metaGApple.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Pomme en Or", "§9Golden Apple"));
    	metaGApple.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	ArrayList<String> lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaGApple.setLore(lore);
    	
    	gApple.setItemMeta(metaGApple);
    	
    	inv.setItem(10, gApple);
    	
    	
    	ItemStack splashPotion1 = new ItemStack(Material.SPLASH_POTION);
    	PotionMeta metaSplashPotion1 = (PotionMeta) splashPotion1.getItemMeta();
    	
    	metaSplashPotion1.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§1§9Potion Jetable", "§1§9Splash Potion"));
    	metaSplashPotion1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	metaSplashPotion1.addCustomEffect(new PotionEffect(PotionEffectType.JUMP, 1200, 2), true);
    	metaSplashPotion1.setColor(Color.LIME);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaSplashPotion1.setLore(lore);
    	
    	splashPotion1.setItemMeta(metaSplashPotion1);
    	
    	inv.setItem(12, splashPotion1);
    	
    	
    	ItemStack splashPotion2 = new ItemStack(Material.SPLASH_POTION);
    	PotionMeta metaSplashPotion2 = (PotionMeta) splashPotion2.getItemMeta();
    	
    	metaSplashPotion2.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§2§9Potion Jetable", "§2§9Splash Potion"));
    	metaSplashPotion2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	metaSplashPotion2.addCustomEffect(new PotionEffect(PotionEffectType.HEAL, 1200, 1), true);
    	metaSplashPotion2.setColor(Color.RED);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaSplashPotion2.setLore(lore);
    	
    	splashPotion2.setItemMeta(metaSplashPotion2);
    	
    	inv.setItem(14, splashPotion2);
    	
    	
    	ItemStack splashPotion3 = new ItemStack(Material.SPLASH_POTION);
    	PotionMeta metaSplashPotion3 = (PotionMeta) splashPotion3.getItemMeta();
    	
    	metaSplashPotion3.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§3§9Potion Jetable", "§3§9Splash Potion"));
    	metaSplashPotion3.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	metaSplashPotion3.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 1200, 1), true);
    	metaSplashPotion3.setColor(Color.AQUA);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaSplashPotion3.setLore(lore);
    	
    	splashPotion3.setItemMeta(metaSplashPotion3);
    	
    	inv.setItem(16, splashPotion3);
    	
    	
    	ItemStack xpBottle = new ItemStack(Material.EXP_BOTTLE);
    	ItemMeta metaXpBottle = xpBottle.getItemMeta();
    	
    	metaXpBottle.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Bouteille d'expérience", "§9Experience Bottle"));
    	metaXpBottle.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaXpBottle.setLore(lore);
    	
    	xpBottle.setItemMeta(metaXpBottle);
    	
    	inv.setItem(28, xpBottle);
    	
    	
    	ItemStack splashPotion4 = new ItemStack(Material.SPLASH_POTION);
    	PotionMeta metaSplashPotion4 = (PotionMeta) splashPotion4.getItemMeta();
    	
    	metaSplashPotion4.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§4§9Potion Jetable", "§4§9Splash Potion"));
    	metaSplashPotion4.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	metaSplashPotion4.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 1200, 2), true);
    	metaSplashPotion4.setColor(Color.GREEN);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaSplashPotion4.setLore(lore);
    	
    	splashPotion4.setItemMeta(metaSplashPotion4);
    	
    	inv.setItem(30, splashPotion4);
    	
    	
    	ItemStack splashPotion5 = new ItemStack(Material.SPLASH_POTION);
    	PotionMeta metaSplashPotion5 = (PotionMeta) splashPotion5.getItemMeta();
    	
    	metaSplashPotion5.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§5§9Potion Jetable", "§5§9Splash Potion"));
    	metaSplashPotion5.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	metaSplashPotion5.addCustomEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1200, 1), true);
    	metaSplashPotion5.setColor(Color.ORANGE);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaSplashPotion5.setLore(lore);
    	
    	splashPotion5.setItemMeta(metaSplashPotion5);
    	
    	inv.setItem(32, splashPotion5);
    	
    	
    	ItemStack splashPotion6 = new ItemStack(Material.SPLASH_POTION);
    	PotionMeta metaSplashPotion6 = (PotionMeta) splashPotion6.getItemMeta();
    	
    	metaSplashPotion6.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§6§9Potion Jetable", "§6§9Splash Potion"));
    	metaSplashPotion6.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	metaSplashPotion6.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 1200, 1), true);
    	metaSplashPotion6.setColor(Color.FUCHSIA);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaSplashPotion6.setLore(lore);
    	
    	splashPotion6.setItemMeta(metaSplashPotion6);
    	
    	inv.setItem(34, splashPotion6);
    	
    	
    	ItemStack back = new ItemStack(Material.ARROW);
    	ItemMeta metaBack = back.getItemMeta();
    	
    	metaBack.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§7Retour", "§7Back"));
    	metaBack.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	back.setItemMeta(metaBack);
    	
    	inv.setItem(49, back);
    	
    	player.openInventory(inv);
	}
	
	public void inventoryShopArmor(Player player) {
		Inventory inv = Bukkit.createInventory(null, 54, MeneliaAPI.getInstance().getLang(player, "Armures", "Armors"));
    	
    	ItemStack panel = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
    	ItemMeta metaPanel = panel.getItemMeta();
    	
    	metaPanel.setDisplayName("§a");
    	
    	panel.setItemMeta(metaPanel);
    	
    	for(int i = 0; i<54; i++) {
    		inv.setItem(i, panel);
    	}
    	
    	ItemStack armor1 = new ItemStack(Material.GOLD_LEGGINGS);
    	ItemMeta metaArmor1 = panel.getItemMeta();
    	
    	metaArmor1.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Pantalon en Or", "§9Golden Legging"));
    	metaArmor1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	ArrayList<String> lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaArmor1.setLore(lore);
    	
    	armor1.setItemMeta(metaArmor1);
    	
    	inv.setItem(10, armor1);
    	
    	ItemStack armor2 = new ItemStack(Material.CHAINMAIL_LEGGINGS);
    	ItemMeta metaArmor2 = panel.getItemMeta();
    	
    	metaArmor2.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Pantalon en Chaine", "§9Chainmail Legging"));
    	metaArmor2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaArmor2.setLore(lore);
    	
    	armor2.setItemMeta(metaArmor2);
    	
    	inv.setItem(12, armor2);
    	
    	ItemStack armor3 = new ItemStack(Material.IRON_LEGGINGS);
    	ItemMeta metaArmor3 = panel.getItemMeta();
    	
    	metaArmor3.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Pantalon en Fer", "§9Iron Legging"));
    	metaArmor3.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaArmor3.setLore(lore);
    	
    	armor3.setItemMeta(metaArmor3);
    	
    	inv.setItem(14, armor3);
    	
    	ItemStack armor4 = new ItemStack(Material.DIAMOND_LEGGINGS);
    	ItemMeta metaArmor4 = panel.getItemMeta();
    	
    	metaArmor4.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Pantalon en Diamant", "§9Diamond Legging"));
    	metaArmor4.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaArmor4.setLore(lore);
    	
    	armor4.setItemMeta(metaArmor4);
    	
    	inv.setItem(16, armor4);

    	
    	ItemStack armor5 = new ItemStack(Material.GOLD_BOOTS);
    	ItemMeta metaArmor5 = panel.getItemMeta();
    	
    	metaArmor5.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Bottes en Or", "§9Golden Boots"));
    	metaArmor5.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
    	metaArmor5.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaArmor5.setLore(lore);
    	
    	armor5.setItemMeta(metaArmor5);
    	
    	inv.setItem(28, armor5);

    	
    	ItemStack armor6 = new ItemStack(Material.CHAINMAIL_BOOTS);
    	ItemMeta metaArmor6 = panel.getItemMeta();
    	
    	metaArmor6.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Bottes en Chaine", "§9Chain Boots"));
    	metaArmor6.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
    	metaArmor6.addEnchant(Enchantment.THORNS, 1, true);
    	metaArmor6.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaArmor6.setLore(lore);
    	
    	armor6.setItemMeta(metaArmor6);
    	
    	inv.setItem(30, armor6);

    	
    	ItemStack armor7 = new ItemStack(Material.IRON_BOOTS);
    	ItemMeta metaArmor7 = panel.getItemMeta();
    	
    	metaArmor7.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Bottes en Fer", "§9Iron Boots"));
    	metaArmor7.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaArmor7.setLore(lore);
    	
    	armor7.setItemMeta(metaArmor7);
    	
    	inv.setItem(32, armor7);

    	
    	ItemStack armor8 = new ItemStack(Material.DIAMOND_BOOTS);
    	ItemMeta metaArmor8 = panel.getItemMeta();
    	
    	metaArmor8.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Bottes en Diamant", "§9Diamand Boots"));
    	metaArmor8.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	lore = new ArrayList<>();
    	lore.add(MeneliaAPI.getInstance().getLang(player, "§6Coute 1 d'argent", "§6Cost 1 money"));
    	metaArmor8.setLore(lore);
    	
    	armor8.setItemMeta(metaArmor8);
    	
    	inv.setItem(34, armor8);

    	
    	ItemStack back = new ItemStack(Material.ARROW);
    	ItemMeta metaBack = panel.getItemMeta();
    	
    	metaBack.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§7Retour", "§7Back"));
    	metaBack.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	back.setItemMeta(metaBack);
    	
    	inv.setItem(49, back);
    	
    	
    	player.openInventory(inv);
	}
	
	@EventHandler
    public void Craft(CraftItemEvent event) {
		if(MainGame.getGameFunctions().isInGame((Player) event.getWhoClicked())) {
			if(MainGame.getGameFunctions().isInGame((Player) event.getWhoClicked())) {
				event.setCancelled(true);
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
					OpenAnvilInventory(player);
				}
			}
		}
	}
	
	public void OpenAnvilInventory(Player player) {
		Inventory inv = Bukkit.createInventory(null, 54, MeneliaAPI.getInstance().getLang(player, "Enclume", "Anvil"));
		
		ItemStack panel = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
    	ItemMeta metaPanel = panel.getItemMeta();
    	
    	metaPanel.setDisplayName("§a");
    	
    	panel.setItemMeta(metaPanel);
    	
    	for(int i = 0; i<54; i++) {
    		inv.setItem(i, panel);
    	}
    	
    	ItemStack panel2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
    	ItemMeta metaPanel2 = panel.getItemMeta();
    	
    	metaPanel2.setDisplayName("§a");
    	
    	panel2.setItemMeta(metaPanel2);
    	
    	inv.setItem(14, panel2);
    	inv.setItem(15, panel2);
    	inv.setItem(24, panel2);
    	
    	inv.setItem(12, panel2);
    	inv.setItem(11, panel2);
    	inv.setItem(20, panel2);
    	
    	ItemStack air = new ItemStack(Material.AIR);
    	inv.setItem(13, air);
    	inv.setItem(29, air);
    	inv.setItem(33, air);
    	
    	ItemStack errorPane = new ItemStack(Material.BARRIER);
    	ItemMeta metaErrorPane = errorPane.getItemMeta();
    	
    	metaErrorPane.setDisplayName("§cError");
    	
    	errorPane.setItemMeta(metaErrorPane);
    	
    	inv.setItem(13, errorPane);
		
    	ItemStack back = new ItemStack(Material.BARRIER);
    	ItemMeta metaBack = panel.getItemMeta();
    	
    	metaBack.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§aFermer", "§aClose"));
    	metaBack.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	back.setItemMeta(metaBack);
    	
    	inv.setItem(49, back);				
		
		player.openInventory(inv);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void OnClickAnvil(InventoryClickEvent event) {
		
		Player player = (Player)event.getWhoClicked();
		InventoryView inv = player.getOpenInventory();
		//ItemStack current = event.getCurrentItem();
		
		if(MainGame.getGameFunctions().isInGame(player)) {
			//if(current == null) return;
			
			if(event.isShiftClick() && player.getOpenInventory().getTitle().equals(MeneliaAPI.getInstance().getLang(player, "Enclume", "Anvil"))) {
				//event.setCancelled(true);
				//return;
			}
			
			if(player.getOpenInventory().getTitle().equals(MeneliaAPI.getInstance().getLang(player, "Enclume", "Anvil"))) {
				
				if(event.getCursor().equals(null)) {
					event.setCursor(new ItemStack(Material.AIR));
				}
				
				ItemStack redPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
		    	ItemMeta metaRedPane = redPane.getItemMeta();
		    	
		    	metaRedPane.setDisplayName("§a");
		    	
		    	redPane.setItemMeta(metaRedPane);
		    	
		    	ItemStack greenPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
		    	ItemMeta metaGreenPane = greenPane.getItemMeta();
		    	
		    	metaGreenPane.setDisplayName("§a");
		    	
		    	greenPane.setItemMeta(metaGreenPane);
		    	
		    	ItemStack errorPane = new ItemStack(Material.BARRIER);
		    	ItemMeta metaErrorPane = errorPane.getItemMeta();
		    	
		    	metaErrorPane.setDisplayName("§cError");
		    	
		    	errorPane.setItemMeta(metaErrorPane);
		    	
		    	if(event.getClickedInventory().getTitle().equals(MeneliaAPI.getInstance().getLang(player, "Enclume", "Anvil"))){
		    		//if(event.getClickedInventory().getTitle().equals(MeneliaAPI.getInstance().getLang(player, "Enclume", "Anvil")) && event.getSlot() == 13)
						
						if(event.getSlot() == 13 && !event.getCursor().getType().equals(Material.AIR) || event.getClickedInventory().getTitle().equals(MeneliaAPI.getInstance().getLang(player, "Enclume", "Anvil")) && event.getSlot() == 13 && !MeneliaAPI.getInstance().isEmpty(inv.getItem(13)) && inv.getItem(13).getType().equals(Material.BARRIER)) {
							event.setCancelled(true);
						}
						else if(event.getSlot() != 29 && event.getSlot() != 33 && event.getSlot() != 13) {
							event.setCancelled(true);
						}
						else if(event.getSlot() == 13){
							player.getOpenInventory().setItem(29, new ItemStack(Material.AIR));
							player.getOpenInventory().setItem(33, new ItemStack(Material.AIR));
							event.setCursor(player.getOpenInventory().getItem(13));
							event.setCancelled(true);
							player.getOpenInventory().setItem(29, new ItemStack(Material.AIR));
							player.getOpenInventory().setItem(33, new ItemStack(Material.AIR));
							//OpenAnvilInventory(player);
						}
		    	}			
				
				Bukkit.getScheduler().runTaskLater(MainGame.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						boolean isBookOk = false;
						boolean isCorrectItem = false;
						
						inv.setItem(14, redPane);
				    	inv.setItem(15, redPane);
				    	inv.setItem(24, redPane);
				    	
				    	for(Material mat: MainGame.getInstance().getAnvilItems()) {
				    		if(player.getOpenInventory().getItem(29).getType().equals(mat)) {
				    			isCorrectItem = true;
				    		}
				    	}
				    	
				    	if(isCorrectItem) {
					    	inv.setItem(12, greenPane);
					    	inv.setItem(11, greenPane);
					    	inv.setItem(20, greenPane);
						}
						else {					    	
					    	inv.setItem(12, redPane);
					    	inv.setItem(11, redPane);
					    	inv.setItem(20, redPane);
					    	inv.setItem(13, errorPane);
						}			    	
				    	
				    	
				    	for(Material mat: MainGame.getInstance().getAnvilItems()) {
				    		if(player.getOpenInventory().getItem(29).getType().equals(Material.ENCHANTED_BOOK) && player.getOpenInventory().getItem(33).getType().equals(Material.ENCHANTED_BOOK)) {
				    			
				    			ItemStack book1 = player.getOpenInventory().getItem(29);
					    		ItemStack book2 = player.getOpenInventory().getItem(33);
					    		
					    		EnchantmentStorageMeta metaBook1 = (EnchantmentStorageMeta) book1.getItemMeta();
					    		EnchantmentStorageMeta metaBook2 = (EnchantmentStorageMeta) book2.getItemMeta();
					    		
					    		player.getOpenInventory().setItem(13, new ItemStack(player.getOpenInventory().getItem(29)));
					    		
					    		ItemStack finalItem = player.getOpenInventory().getItem(13);
					    		EnchantmentStorageMeta metaFinalItem = (EnchantmentStorageMeta) finalItem.getItemMeta();
		    										    		
					    		for(Enchantment ench: Enchantment.values()) {
					    			if(metaBook1.hasStoredEnchant(ench) && metaBook2.hasStoredEnchant(ench)) {
					    				int enchLevel1 = metaBook1.getStoredEnchantLevel(ench);
					    				int enchLevel2 = metaBook2.getStoredEnchantLevel(ench);
					    				
					    				if(enchLevel1 > enchLevel2) {
					    					metaFinalItem.addStoredEnchant(ench, enchLevel1, true);
					    				}
					    				else if(enchLevel1 == enchLevel2) {
					    					if(enchLevel1 < ench.getMaxLevel()) {
		    									metaFinalItem.addStoredEnchant(ench, enchLevel1 + 1, true);
		    								}
					    				}
					    				else if(enchLevel1 < enchLevel2) {
					    					metaFinalItem.addStoredEnchant(ench, enchLevel2, true);
					    				}
					    			}
					    			else if(metaBook1.hasStoredEnchant(ench)){
					    				int enchLevel = metaBook1.getStoredEnchantLevel(ench);
					    				metaFinalItem.addStoredEnchant(ench, enchLevel, true);
					    			}
					    			else if(metaBook2.hasStoredEnchant(ench)){
					    				int enchLevel = metaBook2.getStoredEnchantLevel(ench);
					    				metaFinalItem.addStoredEnchant(ench, enchLevel, true);
					    			}
					    		}
					    		
					    		finalItem.setItemMeta(metaFinalItem);
		    					player.getOpenInventory().setItem(13, finalItem);
		    					isBookOk = true;
				    		}
				    		else if(player.getOpenInventory().getItem(33).getType().equals(player.getOpenInventory().getItem(29).getType()) && !player.getOpenInventory().getItem(33).getType().equals(Material.AIR)) {
				    			player.getOpenInventory().setItem(13, new ItemStack(player.getOpenInventory().getItem(29)));
		    					
				    			ItemStack finalItem = player.getOpenInventory().getItem(13);
		    					ItemMeta metaFinalItem = finalItem.getItemMeta();
		    					
		    					for(Enchantment ench: Enchantment.values()) {
		    						if(!MeneliaAPI.getInstance().isEmpty(player.getOpenInventory().getItem(33)) && !MeneliaAPI.getInstance().isEmpty(player.getOpenInventory().getItem(29)) && player.getOpenInventory().getItem(33).getItemMeta().hasEnchant(ench) && player.getOpenInventory().getItem(29).getItemMeta().hasEnchant(ench)) {
		    							int enchLevel1 = player.getOpenInventory().getItem(29).getItemMeta().getEnchantLevel(ench);
		    							int enchLevel2 = player.getOpenInventory().getItem(33).getItemMeta().getEnchantLevel(ench);
		    							if(enchLevel1 > enchLevel2) {
		    								metaFinalItem.addEnchant(ench, enchLevel1, true);
		    							}
		    							else if(enchLevel1 == enchLevel2) {
		    								if(enchLevel1 < ench.getMaxLevel()) {
		    									metaFinalItem.addEnchant(ench, enchLevel1 + 1, true);
		    								}
		    							}
		    							else if(enchLevel1 < enchLevel2) {
		    								metaFinalItem.addEnchant(ench, enchLevel2, true);
		    							}
		    						}
		    						else if(!MeneliaAPI.getInstance().isEmpty(player.getOpenInventory().getItem(33)) && player.getOpenInventory().getItem(33).getItemMeta().hasEnchant(ench)) {
		    							int enchLevel = player.getOpenInventory().getItem(33).getItemMeta().getEnchantLevel(ench);
		    							metaFinalItem.addEnchant(ench, enchLevel, true);
		    						}
		    					}
		    					
		    					finalItem.setItemMeta(metaFinalItem);
		    					player.getOpenInventory().setItem(13, finalItem);
		    					isBookOk = true;
				    		}
				    		else if(player.getOpenInventory().getItem(33).getType().equals(Material.ENCHANTED_BOOK)) {
				    			ItemStack book = player.getOpenInventory().getItem(33);
					    		EnchantmentStorageMeta metaBook = (EnchantmentStorageMeta) book.getItemMeta();
					    							    		
					    		if(player.getOpenInventory().getItem(29).getType().equals(mat)) {
					    			player.getOpenInventory().setItem(13, new ItemStack(player.getOpenInventory().getItem(29)));
						    		
						    		ItemStack finalItem = player.getOpenInventory().getItem(13);
			    					ItemMeta metaFinalItem = finalItem.getItemMeta();
					    			for(Enchantment ench: Enchantment.values()) {
					    				int enchLevel1 = 0;
					    				int enchLevel2 = 0;
					    				
					    				if(metaBook.hasStoredEnchant(ench)) {
					    					enchLevel2 = metaBook.getStoredEnchantLevel(ench);
					    				}
					    				
					    				if(player.getOpenInventory().getItem(29).getItemMeta().hasEnchant(ench)) {
					    					enchLevel1 = player.getOpenInventory().getItem(29).getItemMeta().getEnchantLevel(ench);
					    				}
					    				
					    				if(enchLevel2 > 0) {
					    					if(enchLevel1 > enchLevel2) {
					    						if(ench.canEnchantItem(finalItem)) {
					    							metaFinalItem.addEnchant(ench, enchLevel1, true);
					    						}
					    					}
					    					else if(enchLevel1 == enchLevel2) {
					    						if(enchLevel1 < ench.getMaxLevel()) {
					    							if(ench.canEnchantItem(finalItem)) {
					    								metaFinalItem.addEnchant(ench, enchLevel1 + 1, true);
					    							}
					    						}
					    						else {
					    							if(ench.canEnchantItem(finalItem)) {
					    								metaFinalItem.addEnchant(ench, enchLevel1, true);
					    							}
					    						}
					    					}
					    					else if(enchLevel1 < enchLevel2) {
					    						if(ench.canEnchantItem(finalItem)) {
					    							metaFinalItem.addEnchant(ench, enchLevel2, true);
					    						}
					    					}
					    				}
					    			}
					    			finalItem.setItemMeta(metaFinalItem);
			    					player.getOpenInventory().setItem(13, finalItem);
			    					isBookOk = true;
					    		}
				    		}
				    	}
				    	if(isBookOk) {					    	
					    	inv.setItem(14, greenPane);
					    	inv.setItem(15, greenPane);
					    	inv.setItem(24, greenPane);
			    		}
			    		else {					    	
					    	inv.setItem(14, redPane);
					    	inv.setItem(15, redPane);
					    	inv.setItem(24, redPane);
					    	inv.setItem(13, errorPane);
			    		}				    	
					}
				}, 1);
			}
		}
	}
	
	@EventHandler
    public void onCloseInventory(InventoryCloseEvent e){
		if(e.getInventory().getTitle().equals(MeneliaAPI.getInstance().getLang((Player) e.getPlayer(), "Enclume", "Anvil"))) {
			if(e.getInventory().getItem(29) != null) {
				e.getPlayer().getInventory().addItem(e.getInventory().getItem(29));
			}
			if(e.getInventory().getItem(33) != null) {
				e.getPlayer().getInventory().addItem(e.getInventory().getItem(33));
			}
		}
	}*/
	
	
	
	/*@SuppressWarnings("deprecation")
	@EventHandler
    public void onInventoryClick(InventoryClickEvent e){
		
        if(e.getInventory().getType() == InventoryType.ANVIL){
        	ItemStack item1 = e.getInventory().getItem(0);
            ItemStack item2 = e.getInventory().getItem(1);
            
            boolean item1Condition = false;
            boolean item2Condition = false;
            
            
            if(item1.getType().equals(Material.WOOD_SWORD) || item1.getType().equals(Material.STONE_SWORD) || item1.getType().equals(Material.GOLD_SWORD) || item1.getType().equals(Material.IRON_SWORD) || item1.getType().equals(Material.DIAMOND_SWORD) || item1.getType().equals(Material.GOLD_BOOTS) || item1.getType().equals(Material.CHAINMAIL_BOOTS) || item1.getType().equals(Material.IRON_BOOTS) || item1.getType().equals(Material.DIAMOND_BOOTS) || item1.getType().equals(Material.GOLD_LEGGINGS) || item1.getType().equals(Material.CHAINMAIL_LEGGINGS) || item1.getType().equals(Material.IRON_LEGGINGS) || item1.getType().equals(Material.DIAMOND_LEGGINGS)) {
            	if(item2.getType().equals(Material.ENCHANTED_BOOK)) {
            		ItemStack result = new ItemStack(item1);
                	ItemMeta metaResult = result.getItemMeta();
                	
                	EnchantmentStorageMeta metaItem2 = (EnchantmentStorageMeta) item2.getItemMeta();
                	
                	for(Enchantment enchant: metaItem2.getStoredEnchants().keySet()) {
                		int level = metaItem2.getStoredEnchantLevel(enchant);
                		metaResult.addEnchant(enchant, level, true);
                	}
                	metaResult.setDisplayName(item1.getItemMeta().getDisplayName());
                	
                	result.setItemMeta(metaResult);
            		e.getInventory().setItem(2, result);
            		
            		if(e.getSlot() == 2) {
            			Block block = e.getWhoClicked().getTargetBlock(null, 5);
            			byte data = block.getData();
            			if (data == 4 || data == 8) {
                            data = 0;
                        }
                        if (data == 5 || data == 9) {
                            data = 1;
                        }
                        if (data == 6 || data == 10) {
                            data = 2;
                        }
                        if (data == 7 || data == 11) {
                            data = 3;
                        }
                        block.setType(Material.ANVIL);
                        block.setData(data);
            			e.setCancelled(true);
            			
            			ItemStack newItem = new ItemStack(e.getInventory().getItem(2));
            			ItemMeta metaNewItem = newItem.getItemMeta();
            			
            			metaNewItem.setDisplayName(item1.getItemMeta().getDisplayName());
            			
            			newItem.setItemMeta(metaNewItem);
            			
            			e.getWhoClicked().getInventory().addItem(newItem);
            			e.getInventory().setItem(0, null);
            			e.getInventory().setItem(1, null);
            			e.getInventory().setItem(2, null);
            		}
            		item2Condition = true;
            	}
            	item1Condition = true;
            }
            Bukkit.broadcastMessage(item1Condition + " / " + item2Condition);
            if(item1Condition && !item2Condition) {
            	e.setResult(null);
            }
            
        }
	}*/
	
	@EventHandler
	public void OnClick(InventoryClickEvent event) {
		
		Inventory inv = event.getInventory();
		Player player = (Player)event.getWhoClicked();
		ItemStack current = event.getCurrentItem();
		
		if(MainGame.getGameFunctions().isInGame(player)) {
			if(current == null) return;
			if(event.getClickedInventory().getTitle().contains("container")) {
				return;
			}
			
			if(inv.getName().equalsIgnoreCase(MeneliaAPI.getInstance().getLang(player, "Boutique", "Shop"))) {
				event.setCancelled(true);
				if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§9Armures", "§9Armors"))) {
					inventoryShopArmor(player);
				}
				else if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§9Epées", "§9Swords"))) {
					inventoryShopSwords(player);
				}
				else if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§9Blocs", "§9Blocks"))) {
					inventoryShopBlocks(player);
				}
				else if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§9Pioches", "§9Pickaxes"))) {
					inventoryShopPickaxes(player);
				}
				else if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§9Enchantements", "§9Enchants"))) {
					inventoryShopEnchant(player);		
				}
				else if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§9Consommables", "§9Consumables"))) {
					inventoryShopConsumables(player);			
				}
				else if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§aFermer", "§aClose"))) {
					player.closeInventory();
				}
			}
			else if(inv.getName().equalsIgnoreCase(MeneliaAPI.getInstance().getLang(player, "Consommables", "Consumables"))) {
				event.setCancelled(true);
				if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§7Retour", "§7Back"))) {
					inventoryShop(player);
				}
				//if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§9Pomme en Or", "§9Golden Apple"))){
				if(current.getType().equals(Material.GOLDEN_APPLE)){
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack gApple = new ItemStack(Material.GOLDEN_APPLE);
						    	ItemMeta metaGApple = gApple.getItemMeta();
						    	
						    	//metaGApple.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Pomme en Or", "§9Golden Apple"));
						    	metaGApple.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	
						    	gApple.setItemMeta(metaGApple);
						    	player.getInventory().addItem(gApple);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§1§9Potion Jetable", "§1§9Splash Potion"))){
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack splashPotion1 = new ItemStack(Material.SPLASH_POTION);
						    	PotionMeta metaSplashPotion1 = (PotionMeta) splashPotion1.getItemMeta();
						    	
						    	//metaSplashPotion1.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§1§9Potion Jetable", "§1§9Splash Potion"));
						    	metaSplashPotion1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	metaSplashPotion1.addCustomEffect(new PotionEffect(PotionEffectType.JUMP, 1200, 2), true);
						    	metaSplashPotion1.setColor(Color.LIME);
						    	
						    	splashPotion1.setItemMeta(metaSplashPotion1);
						    	player.getInventory().addItem(splashPotion1);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§2§9Potion Jetable", "§2§9Splash Potion"))){
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack splashPotion2 = new ItemStack(Material.SPLASH_POTION);
						    	PotionMeta metaSplashPotion2 = (PotionMeta) splashPotion2.getItemMeta();
						    	
						    	//metaSplashPotion2.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§2§9Potion Jetable", "§2§9Splash Potion"));
						    	metaSplashPotion2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	metaSplashPotion2.addCustomEffect(new PotionEffect(PotionEffectType.HEAL, 1200, 1), true);
						    	metaSplashPotion2.setColor(Color.RED);
						    	
						    	splashPotion2.setItemMeta(metaSplashPotion2);
						    	player.getInventory().addItem(splashPotion2);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§3§9Potion Jetable", "§3§9Splash Potion"))){
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack splashPotion3 = new ItemStack(Material.SPLASH_POTION);
						    	PotionMeta metaSplashPotion3 = (PotionMeta) splashPotion3.getItemMeta();
						    	
						    	//metaSplashPotion3.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Potion Jetable", "§9Splash Potion"));
						    	metaSplashPotion3.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	metaSplashPotion3.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 1200, 1), true);
						    	metaSplashPotion3.setColor(Color.AQUA);
						    	
						    	splashPotion3.setItemMeta(metaSplashPotion3);
						    	player.getInventory().addItem(splashPotion3);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				//if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§9Bouteille d'expérience", "§9Experience Bottle"))){
				if(current.getType().equals(Material.EXP_BOTTLE)){
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack xpBottle = new ItemStack(Material.EXP_BOTTLE);
						    	ItemMeta metaXpBottle = xpBottle.getItemMeta();
						    	
						    	//metaXpBottle.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Bouteille d'expérience", "§9Experience Bottle"));
						    	metaXpBottle.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	
						    	xpBottle.setItemMeta(metaXpBottle);
						    	player.getInventory().addItem(xpBottle);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§4§9Potion Jetable", "§4§9Splash Potion"))){
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack splashPotion4 = new ItemStack(Material.SPLASH_POTION);
						    	PotionMeta metaSplashPotion4 = (PotionMeta) splashPotion4.getItemMeta();
						    	
						    	//metaSplashPotion4.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§4§9Potion Jetable", "§4§9Splash Potion"));
						    	metaSplashPotion4.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	metaSplashPotion4.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 1200, 2), true);
						    	metaSplashPotion4.setColor(Color.GREEN);
						    	
						    	splashPotion4.setItemMeta(metaSplashPotion4);
						    	player.getInventory().addItem(splashPotion4);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§5§9Potion Jetable", "§5§9Splash Potion"))){
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack splashPotion5 = new ItemStack(Material.SPLASH_POTION);
						    	PotionMeta metaSplashPotion5 = (PotionMeta) splashPotion5.getItemMeta();
						    	
						    	//metaSplashPotion5.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§5§9Potion Jetable", "§5§9Splash Potion"));
						    	metaSplashPotion5.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	metaSplashPotion5.addCustomEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1200, 1), true);
						    	metaSplashPotion5.setColor(Color.ORANGE);
						    	
						    	splashPotion5.setItemMeta(metaSplashPotion5);
						    	player.getInventory().addItem(splashPotion5);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§6§9Potion Jetable", "§6§9Splash Potion"))){
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack splashPotion6 = new ItemStack(Material.SPLASH_POTION);
						    	PotionMeta metaSplashPotion6 = (PotionMeta) splashPotion6.getItemMeta();
						    	
						    	//metaSplashPotion6.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§6§9Potion Jetable", "§6§9Splash Potion"));
						    	metaSplashPotion6.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	metaSplashPotion6.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 1200, 1), true);
						    	metaSplashPotion6.setColor(Color.FUCHSIA);
						    	
						    	splashPotion6.setItemMeta(metaSplashPotion6);
						    	player.getInventory().addItem(splashPotion6);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
			}
			else if(inv.getName().equalsIgnoreCase(MeneliaAPI.getInstance().getLang(player, "Epées", "Swords"))) {
				event.setCancelled(true);
				if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§7Retour", "§7Back"))) {
					inventoryShop(player);
				}
				else if(current.getType().equals(Material.GOLD_SWORD)){
				//if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§9Epée en Or", "§9Golden Sword"))){
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack sword1 = new ItemStack(Material.GOLD_SWORD);
						    	ItemMeta metaSword1 = sword1.getItemMeta();
						    	
						    	//metaSword1.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Epée en Or", "§9Golden Sword"));
						    	metaSword1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	metaSword1.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 1, true);
						    	metaSword1.addEnchant(Enchantment.DAMAGE_UNDEAD, 1, true);
						    	metaSword1.setUnbreakable(true);
						    	
						    	sword1.setItemMeta(metaSword1);
						    	player.getInventory().addItem(sword1);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				//else if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§9Epée en Pierre", "§9Stone Sword"))) {
				else if(current.getType().equals(Material.STONE_SWORD)) {
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack sword2 = new ItemStack(Material.STONE_SWORD);
						    	ItemMeta metaSword2 = sword2.getItemMeta();
						    	
						    	//metaSword2.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Epée en Pierre", "§9Stone Sword"));
						    	metaSword2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	metaSword2.setUnbreakable(true);
						    	
						    	sword2.setItemMeta(metaSword2);
						    	player.getInventory().addItem(sword2);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				else if(current.getType().equals(Material.IRON_SWORD)){
				//else if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§9Epée en Fer", "§9Iron Sword"))){
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack sword3 = new ItemStack(Material.IRON_SWORD);
						    	ItemMeta metaSword3 = sword3.getItemMeta();
						    	
						    	//metaSword3.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Epée en Fer", "§9Iron Sword"));
						    	metaSword3.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	metaSword3.setUnbreakable(true);
						    	
						    	sword3.setItemMeta(metaSword3);
						    	player.getInventory().addItem(sword3);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				else if(current.getType().equals(Material.DIAMOND_SWORD)){
				//else if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§9Epée en Diamant", "§9Diamond Sword"))){
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack sword4 = new ItemStack(Material.DIAMOND_SWORD);
						    	ItemMeta metaSword4 = sword4.getItemMeta();
						    	
						    	//metaSword4.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Epée en Diamant", "§9Diamond Sword"));
						    	metaSword4.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	metaSword4.setUnbreakable(true);
						    	
						    	sword4.setItemMeta(metaSword4);
						    	player.getInventory().addItem(sword4);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				else if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§1§9Arc", "§1§9Bow"))){
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack bow1 = new ItemStack(Material.BOW);
						    	ItemMeta metaBow1 = bow1.getItemMeta();
						    	
						    	//metaBow1.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§1§9Arc", "§1§9Bow"));
						    	metaBow1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	metaBow1.setUnbreakable(true);
						    	
						    	bow1.setItemMeta(metaBow1);
						    	player.getInventory().addItem(bow1);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				else if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§2§9Arc", "§2§9Bow"))){
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack bow2 = new ItemStack(Material.BOW);
						    	ItemMeta metaBow2 = bow2.getItemMeta();
						    	
						    	//metaBow2.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§2§9Arc", "§2§9Bow"));
						    	metaBow2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	metaBow2.addEnchant(Enchantment.ARROW_FIRE, 1, true);
						    	metaBow2.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
						    	metaBow2.setUnbreakable(true);
						    	
						    	bow2.setItemMeta(metaBow2);
						    	player.getInventory().addItem(bow2);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				else if(current.getType().equals(Material.ARROW)){
				//else if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§9Flèche", "§9Arrow"))){
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack arrow = new ItemStack(Material.ARROW);
						    	ItemMeta metaArrow = arrow.getItemMeta();
						    	
						    	//metaArrow.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Flèche", "§9Arrow"));
						    	metaArrow.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	
						    	arrow.setItemMeta(metaArrow);
						    	player.getInventory().addItem(arrow);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
			}
			else if(inv.getName().equalsIgnoreCase(MeneliaAPI.getInstance().getLang(player, "Armures", "Armors"))) {
				event.setCancelled(true);
				if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§7Retour", "§7Back"))) {
					inventoryShop(player);
				}

				else if(current.getType().equals(Material.GOLD_LEGGINGS)){
				//else if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§9Pantalon en Or", "§9Golden Legging"))){
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack armor1 = new ItemStack(Material.GOLD_LEGGINGS);
						    	ItemMeta metaArmor1 = armor1.getItemMeta();
						    	
						    	//metaArmor1.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Pantalon en Or", "§9Golden Legging"));
						    	metaArmor1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	metaArmor1.setUnbreakable(true);
						    	
						    	armor1.setItemMeta(metaArmor1);
						    	player.getInventory().addItem(armor1);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				else if(current.getType().equals(Material.CHAINMAIL_LEGGINGS)){
				//else if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§9Pantalon en Chaine", "§9Chainmail Legging"))){
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack armor2 = new ItemStack(Material.CHAINMAIL_LEGGINGS);
						    	ItemMeta metaArmor2 = armor2.getItemMeta();
						    	
						    	//metaArmor2.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Pantalon en Chaine", "§9Chainmail Legging"));
						    	metaArmor2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	metaArmor2.setUnbreakable(true);
						    	
						    	armor2.setItemMeta(metaArmor2);
						    	player.getInventory().addItem(armor2);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				else if(current.getType().equals(Material.IRON_LEGGINGS)){
				//else if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§9Pantalon en Fer", "§9Iron Legging"))){
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack armor3 = new ItemStack(Material.IRON_LEGGINGS);
						    	ItemMeta metaArmor3 = armor3.getItemMeta();
						    	
						    	//metaArmor3.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Pantalon en Fer", "§9Iron Legging"));
						    	metaArmor3.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	metaArmor3.setUnbreakable(true);
						    	
						    	armor3.setItemMeta(metaArmor3);
						    	player.getInventory().addItem(armor3);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				else if(current.getType().equals(Material.DIAMOND_LEGGINGS)){
				//else if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§9Pantalon en Diamant", "§9Diamond Legging"))){
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack armor4 = new ItemStack(Material.DIAMOND_LEGGINGS);
						    	ItemMeta metaArmor4 = armor4.getItemMeta();
						    	
						    	//metaArmor4.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Pantalon en Diamant", "§9Diamond Legging"));
						    	metaArmor4.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	metaArmor4.setUnbreakable(true);
						    	
						    	armor4.setItemMeta(metaArmor4);
						    	player.getInventory().addItem(armor4);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				else if(current.getType().equals(Material.GOLD_BOOTS)){
				//else if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§9Bottes en Or", "§9Golden Boots"))){
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack armor5 = new ItemStack(Material.GOLD_BOOTS);
						    	ItemMeta metaArmor5 = armor5.getItemMeta();
						    	
						    	//metaArmor5.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Bottes en Or", "§9Golden Boots"));
						    	metaArmor5.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
						    	metaArmor5.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	metaArmor5.setUnbreakable(true);
						    	
						    	armor5.setItemMeta(metaArmor5);
						    	player.getInventory().addItem(armor5);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				else if(current.getType().equals(Material.CHAINMAIL_BOOTS)){
				//else if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§9Bottes en Chaine", "§9Chain Boots"))){
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack armor6 = new ItemStack(Material.CHAINMAIL_BOOTS);
						    	ItemMeta metaArmor6 = armor6.getItemMeta();
						    	
						    	//metaArmor6.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Bottes en Chaine", "§9Chain Boots"));
						    	metaArmor6.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
						    	metaArmor6.addEnchant(Enchantment.THORNS, 1, true);
						    	metaArmor6.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	metaArmor6.setUnbreakable(true);
						    	
						    	armor6.setItemMeta(metaArmor6);
						    	player.getInventory().addItem(armor6);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				else if(current.getType().equals(Material.IRON_BOOTS)){
				//else if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§9Bottes en Fer", "§9Iron Boots"))){
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack armor7 = new ItemStack(Material.IRON_BOOTS);
						    	ItemMeta metaArmor7 = armor7.getItemMeta();
						    	
						    	//metaArmor7.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Bottes en Fer", "§9Iron Boots"));
						    	metaArmor7.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	metaArmor7.setUnbreakable(true);
						    	
						    	armor7.setItemMeta(metaArmor7);
						    	player.getInventory().addItem(armor7);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				else if(current.getType().equals(Material.DIAMOND_BOOTS)){
				//else if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§9Bottes en Diamant", "§9Diamand Boots"))){
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack armor8 = new ItemStack(Material.DIAMOND_BOOTS);
						    	ItemMeta metaArmor8 = armor8.getItemMeta();
						    	
						    	//metaArmor8.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Bottes en Diamant", "§9Diamand Boots"));
						    	metaArmor8.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	metaArmor8.setUnbreakable(true);
						    	
						    	armor8.setItemMeta(metaArmor8);
						    	player.getInventory().addItem(armor8);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
			}
			else if(inv.getName().equalsIgnoreCase(MeneliaAPI.getInstance().getLang(player, "Blocs", "Blocks"))) {
				event.setCancelled(true);
				if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§7Retour", "§7Back"))) {
					inventoryShop(player);
				}
				//if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§9Sable", "§9Sand"))) {
				else if(current.getType().equals(Material.SAND)) {
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack sand = new ItemStack(Material.SAND);
						    	//ItemMeta metaSand = sand.getItemMeta();
						    	
						    	//metaSand.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Sable", "§9Sand"));
						    	//metaSand.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	
						    	//sand.setItemMeta(metaSand);
						    	player.getInventory().addItem(sand);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}	
				}
				else if(current.getType().equals(Material.LADDER)) {
				//if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§9Échelle", "§9Ladder"))) {
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack ladder = new ItemStack(Material.LADDER);
						    	//ItemMeta metaLadder = ladder.getItemMeta();
						    	
						    	//metaLadder.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Échelle", "§9Ladder"));
						    	//metaLadder.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	
						    	//ladder.setItemMeta(metaLadder);
						    	player.getInventory().addItem(ladder);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
			}
			else if(inv.getName().equalsIgnoreCase(MeneliaAPI.getInstance().getLang(player, "Pioches", "Pickaxes"))) {
				event.setCancelled(true);
				if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§7Retour", "§7Back"))) {
					inventoryShop(player);
				}
				else if(current.getType().equals(Material.STONE_PICKAXE)) {
				//else if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§9Pioche en Pierre", "§9Stone Pickaxe"))) {
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack pickaxe1 = new ItemStack(Material.STONE_PICKAXE);
						    	ItemMeta metaPickaxe1 = pickaxe1.getItemMeta();
						    	
						    	//metaPickaxe1.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Pioche en Pierre", "§9Stone Pickaxe"));
						    	metaPickaxe1.addEnchant(Enchantment.DIG_SPEED, 8, true);
						    	metaPickaxe1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	metaPickaxe1.setUnbreakable(true);
						    	
						    	pickaxe1.setItemMeta(metaPickaxe1);
						    	player.getInventory().addItem(pickaxe1);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				else if(current.getType().equals(Material.IRON_PICKAXE)) {
				//else if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§9Pioche en Fer", "§9Iron Pickaxe"))) {
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack pickaxe2 = new ItemStack(Material.IRON_PICKAXE);
						    	ItemMeta metaPickaxe2 = pickaxe2.getItemMeta();
						    	
						    	//metaPickaxe2.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Pioche en Fer", "§9Iron Pickaxe"));
						    	metaPickaxe2.addEnchant(Enchantment.DIG_SPEED, 8, true);
						    	metaPickaxe2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	metaPickaxe2.setUnbreakable(true);
						    	
						    	pickaxe2.setItemMeta(metaPickaxe2);
						    	player.getInventory().addItem(pickaxe2);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				else if(current.getType().equals(Material.DIAMOND_PICKAXE)) {
				//else if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§9Pioche en Diamant", "§9Diamond Pickaxe"))) {
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack pickaxe3 = new ItemStack(Material.DIAMOND_PICKAXE);
						    	ItemMeta metaPickaxe3 = pickaxe3.getItemMeta();
						    	
						    	//metaPickaxe3.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§9Pioche en Diamant", "§9Diamond Pickaxe"));
						    	metaPickaxe3.addEnchant(Enchantment.DIG_SPEED, 6, true);
						    	metaPickaxe3.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	metaPickaxe3.setUnbreakable(true);
						    	
						    	pickaxe3.setItemMeta(metaPickaxe3);
						    	player.getInventory().addItem(pickaxe3);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
			}
			else if(inv.getName().equalsIgnoreCase(MeneliaAPI.getInstance().getLang(player, "Echantements", "Enchants"))) {
				event.setCancelled(true);
				if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§7Retour", "§7Back"))) {
					inventoryShop(player);
				}
				if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§1§9Livre Enchanté", "§1§9Enchanted Book"))) {
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack book1 = new ItemStack(Material.ENCHANTED_BOOK);
								EnchantmentStorageMeta metaBook1 = (EnchantmentStorageMeta) book1.getItemMeta();
						    	
						    	//metaBook1.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§1§9Livre Enchanté", "§1§9Enchanted Book"));
						    	metaBook1.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
						    	metaBook1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	
						    	book1.setItemMeta(metaBook1);
						    	player.getInventory().addItem(book1);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§2§9Livre Enchanté", "§2§9Enchanted Book"))) {
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack book2 = new ItemStack(Material.ENCHANTED_BOOK);
								EnchantmentStorageMeta metaBook2 = (EnchantmentStorageMeta) book2.getItemMeta();
						    	
						    	//metaBook2.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§2§9Livre Enchanté", "§2§9Enchanted Book"));
						    	metaBook2.addStoredEnchant(Enchantment.DAMAGE_ALL, 1, true);
						    	metaBook2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	
						    	book2.setItemMeta(metaBook2);
						    	player.getInventory().addItem(book2);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§3§9Livre Enchanté", "§3§9Enchanted Book"))) {
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack book3 = new ItemStack(Material.ENCHANTED_BOOK);
								EnchantmentStorageMeta metaBook3 = (EnchantmentStorageMeta) book3.getItemMeta();
						    	
						    	//metaBook3.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§3§9Livre Enchanté", "§3§9Enchanted Book"));
						    	metaBook3.addStoredEnchant(Enchantment.ARROW_DAMAGE, 1, true);
						    	metaBook3.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	
						    	book3.setItemMeta(metaBook3);
						    	player.getInventory().addItem(book3);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§4§9Livre Enchanté", "§4§9Enchanted Book"))) {
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack book4 = new ItemStack(Material.ENCHANTED_BOOK);
								EnchantmentStorageMeta metaBook4 = (EnchantmentStorageMeta) book4.getItemMeta();
						    	
						    	//metaBook4.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§4§9Livre Enchanté", "§4§9Enchanted Book"));
						    	metaBook4.addStoredEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
						    	metaBook4.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	
						    	book4.setItemMeta(metaBook4);
						    	player.getInventory().addItem(book4);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§5§9Livre Enchanté", "§5§9Enchanted Book"))) {
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack book5 = new ItemStack(Material.ENCHANTED_BOOK);
								EnchantmentStorageMeta metaBook5 = (EnchantmentStorageMeta) book5.getItemMeta();
						    	
						    	//metaBook5.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§5§9Livre Enchanté", "§5§9Enchanted Book"));
						    	metaBook5.addStoredEnchant(Enchantment.FIRE_ASPECT, 1, true);
						    	metaBook5.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	
						    	book5.setItemMeta(metaBook5);
						    	player.getInventory().addItem(book5);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§6§9Livre Enchanté", "§6§9Enchanted Book"))) {
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack book6 = new ItemStack(Material.ENCHANTED_BOOK);
								EnchantmentStorageMeta metaBook6 = (EnchantmentStorageMeta) book6.getItemMeta();
						    	
						    	//metaBook6.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§6§9Livre Enchanté", "§6§9Enchanted Book"));
						    	metaBook6.addStoredEnchant(Enchantment.DAMAGE_UNDEAD, 1, true);
						    	metaBook6.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	
						    	book6.setItemMeta(metaBook6);
						    	player.getInventory().addItem(book6);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§7§9Livre Enchanté", "§7§9Enchanted Book"))) {
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack book7 = new ItemStack(Material.ENCHANTED_BOOK);
								EnchantmentStorageMeta metaBook7 = (EnchantmentStorageMeta) book7.getItemMeta();
						    	
						    	//metaBook7.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§7§9Livre Enchanté", "§7§9Enchanted Book"));
						    	metaBook7.addStoredEnchant(Enchantment.DAMAGE_ARTHROPODS, 1, true);
						    	metaBook7.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	
						    	book7.setItemMeta(metaBook7);
						    	player.getInventory().addItem(book7);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
				if(current.getItemMeta().getDisplayName().equals(MeneliaAPI.getInstance().getLang(player, "§8§9Livre Enchanté", "§8§9Enchanted Book"))) {
					Games game = MainGame.getGameFunctions().getGame(player);
					for(GamesData data: game.getPlayers()) {
						if(player.equals(data.getPlayer())) {
							if(data.getMoney() >= 1) {
								data.setMoney(data.getMoney() - 1);
								
								ItemStack book8 = new ItemStack(Material.ENCHANTED_BOOK);
								EnchantmentStorageMeta metaBook8 = (EnchantmentStorageMeta) book8.getItemMeta();
						    	
						    	//metaBook8.setDisplayName(MeneliaAPI.getInstance().getLang(player, "§8§9Livre Enchanté", "§8§9Enchanted Book"));
						    	metaBook8.addStoredEnchant(Enchantment.LOOT_BONUS_MOBS, 1, true);
						    	metaBook8.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						    	
						    	book8.setItemMeta(metaBook8);
						    	player.getInventory().addItem(book8);
							}
							else {
								notEnoughMoney(player);
							}
						}
					}
				}
			}
		}
	}
	
	private void notEnoughMoney(Player player) {
		player.sendMessage(MeneliaAPI.getInstance().getLang(player, "§cVous n'avez pas asser d'argent !", "§cYou don't have enough money !"));
		player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
	}
}

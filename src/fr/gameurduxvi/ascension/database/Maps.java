package fr.gameurduxvi.ascension.database;

import org.bukkit.Location;

public class Maps {
	private int id;
	private String mapName;
	private String mapNameColored;
	
	private Location playerLocationGreen;
	private Location playerLocationPurple;
	private Location playerLocationYellow;
	private Location playerLocationCyan;
	
	private Location buildGreen;
	private Location buildPurple;
	private Location buildYellow;
	private Location buildCyan;
	
	private Location upperTeleporterGreen;
	private Location lowerTeleporterGreen;
	
	private Location upperTeleporterPurple;
	private Location lowerTeleporterPurple;
	
	private Location upperTeleporterYellow;
	private Location lowerTeleporterYellow;
	
	private Location upperTeleporterCyan;
	private Location lowerTeleporterCyan;
	
	private Location obsidianGreen;
	private Location obsidianPurple;
	private Location obsidianYellow;
	private Location obsidianCyan;
	
	private Location shopGreen;
	private Location shopPurple;
	private Location shopYellow;
	private Location shopCyan;

	private Location sellGreen;
	private Location sellPurple;
	private Location sellYellow;
	private Location sellCyan;
	
	private Location chestGreen;
	private Location chestPurple;
	private Location chestYellow;
	private Location chestCyan;
	
	public Maps(int id, String mapName, String mapNameColored) {
		this.id = id;
		this.mapName = mapName;
		this.mapNameColored = mapNameColored;
	}
	
	
	
	
	
	
	public int getId() {
		return id;
	}
	public String getName() {
		return mapName;
	}
	public String getMapNameColored() {
		return mapNameColored;
	}
	
	
	
	public Location getPlayerLocationGreen() {
		return playerLocationGreen;
	}
	public Location getPlayerLocationPurple() {
		return playerLocationPurple;
	}
	public Location getPlayerLocationYellow() {
		return playerLocationYellow;
	}
	public Location getPlayerLocationCyan() {
		return playerLocationCyan;
	}
	
	

	public Location getBuildGreen() {
		return buildGreen;
	}
	public Location getBuildPurple() {
		return buildPurple;
	}
	public Location getBuildYellow() {
		return buildYellow;
	}
	public Location getBuildCyan() {
		return buildCyan;
	}
	
	
	
	public Location getLowerTeleporterGreen() {
		return lowerTeleporterGreen;
	}
	public Location getLowerTeleporterPurple() {
		return lowerTeleporterPurple;
	}
	public Location getLowerTeleporterYellow() {
		return lowerTeleporterYellow;
	}
	public Location getLowerTeleporterCyan() {
		return lowerTeleporterCyan;
	}

	
	
	public Location getUpperTeleporterGreen() {
		return upperTeleporterGreen;
	}
	public Location getUpperTeleporterPurple() {
		return upperTeleporterPurple;
	}
	public Location getUpperTeleporterYellow() {
		return upperTeleporterYellow;
	}
	public Location getUpperTeleporterCyan() {
		return upperTeleporterCyan;
	}
	
	
	
	
	public Location getObsidianGreen() {
		return obsidianGreen;
	}
	public Location getObsidianPurple() {
		return obsidianPurple;
	}
	public Location getObsidianYellow() {
		return obsidianYellow;
	}
	public Location getObsidianCyan() {
		return obsidianCyan;
	}
	
	
	
	public Location getShopGreen() {
		return shopGreen;
	}
	public Location getShopPurple() {
		return shopPurple;
	}
	public Location getShopYellow() {
		return shopYellow;
	}
	public Location getShopCyan() {
		return shopCyan;
	}
	
	
	
	public Location getSellGreen() {
		return sellGreen;
	}
	public Location getSellPurple() {
		return sellPurple;
	}
	public Location getSellYellow() {
		return sellYellow;
	}
	public Location getSellCyan() {
		return sellCyan;
	}
	
	
	
	public Location getChestGreen() {
		return chestGreen;
	}
	public Location getChestPurple() {
		return chestPurple;
	}
	public Location getChestYellow() {
		return chestYellow;
	}
	public Location getChestCyan() {
		return chestCyan;
	}
	
	// =======================================================

	public void setId(int id) {
		this.id = id;
	}
	public void setName(String mapName) {
		this.mapName = mapName;
	}
	
	

	public void setPlayerLocationGreen(Location playerLocationGreen) {
		this.playerLocationGreen = playerLocationGreen;
	}
	public void setPlayerLocationPurple(Location playerLocationPurple) {
		this.playerLocationPurple = playerLocationPurple;
	}
	public void setPlayerLocationYellow(Location playerLocationYellow) {
		this.playerLocationYellow = playerLocationYellow;
	}
	public void setPlayerLocationCyan(Location playerLocationCyan) {
		this.playerLocationCyan = playerLocationCyan;
	}
	
	

	public void setBuildGreen(Location buildGreen) {
		this.buildGreen = buildGreen;
	}
	public void setBuildPurple(Location buildPurple) {
		this.buildPurple = buildPurple;
	}
	public void setBuildYellow(Location buildYellow) {
		this.buildYellow = buildYellow;
	}
	public void setBuildCyan(Location buildCyan) {
		this.buildCyan = buildCyan;
	}
	
	
	

	public void setLowerTeleporterGreen(Location lowerTeleporterGreen) {
		this.lowerTeleporterGreen = lowerTeleporterGreen;
	}
	public void setLowerTeleporterPurple(Location lowerTeleporterPurple) {
		this.lowerTeleporterPurple = lowerTeleporterPurple;
	}
	public void setLowerTeleporterYellow(Location lowerTeleporterYellow) {
		this.lowerTeleporterYellow = lowerTeleporterYellow;
	}
	public void setLowerTeleporterCyan(Location lowerTeleporterCyan) {
		this.lowerTeleporterCyan = lowerTeleporterCyan;
	}
	

	
	public void setUpperTeleporterGreen(Location upperTeleporterGreen) {
		this.upperTeleporterGreen = upperTeleporterGreen;
	}
	public void setUpperTeleporterPurple(Location upperTeleporterPurple) {
		this.upperTeleporterPurple = upperTeleporterPurple;
	}
	public void setUpperTeleporterYellow(Location upperTeleporterYellow) {
		this.upperTeleporterYellow = upperTeleporterYellow;
	}
	public void setUpperTeleporterCyan(Location upperTeleporterCyan) {
		this.upperTeleporterCyan = upperTeleporterCyan;
	}
	
	

	public void setObsidianGreen(Location obsidianGreen) {
		this.obsidianGreen = obsidianGreen;
	}
	public void setObsidianPurple(Location obsidianPurple) {
		this.obsidianPurple = obsidianPurple;
	}
	public void setObsidianYellow(Location obsidianYellow) {
		this.obsidianYellow = obsidianYellow;
	}
	public void setObsidianCyan(Location obsidianCyan) {
		this.obsidianCyan = obsidianCyan;
	}
	
	
	
	public void setShopGreen(Location shopGreen) {
		this.shopGreen = shopGreen;
	}
	public void setShopPurple(Location shop2) {
		this.shopPurple = shop2;
	}
	public void setShopYellow(Location shopYellow) {
		this.shopYellow = shopYellow;
	}
	public void setShopCyan(Location shopCyan) {
		this.shopCyan = shopCyan;
	}

	
	
	public void setSellGreen(Location sellGreen) {
		this.sellGreen = sellGreen;
	}
	public void setSellPurple(Location sellPurple) {
		this.sellPurple = sellPurple;
	}
	public void setSellYellow(Location sellYellow) {
		this.sellYellow = sellYellow;
	}
	public void setSellCyan(Location sellCyan) {
		this.sellCyan = sellCyan;
	}

	
	public void setChestGreen(Location chestGreen) {
		this.chestGreen = chestGreen;
	}
	public void setChestPurple(Location chestPurple) {
		this.chestPurple = chestPurple;
	}
	public void setChestYellow(Location chestYellow) {
		this.chestYellow = chestYellow;
	}
	public void setChestCyan(Location chestCyan) {
		this.chestCyan = chestCyan;
	}
}

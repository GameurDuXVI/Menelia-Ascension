package fr.gameurduxvi.ascension.database;

import org.bukkit.entity.Player;

public class GamesData {
	private Player player;
	private int Team;
	private int vote;
	private int kills;
	private int death;
	private int money;
	private boolean activeObsidian = false;
	
	public GamesData(Player player) {
		this.player = player;
		this.Team = 0;
		this.kills = 0;
		this.death = 0;
		this.money = 0;
	}
	
	
	
	public Player getPlayer() {
		return player;
	}
	public int getTeam() {
		return Team;
	}
	public int getKills() {
		return kills;
	}
	public int getDeath() {
		return death;
	}
	public int getVote() {
		return vote;
	}
	public int getMoney() {
		return money;
	}
	public boolean isActiveObsidian() {
		return activeObsidian;
	}
	
	
	
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	public void setTeam(int team) {
		Team = team;
	}
	public void setKills(int kills) {
		this.kills = kills;
	}
	public void setDeath(int death) {
		this.death = death;
	}
	public void setVote(int vote) {
		this.vote = vote;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public void setActiveObsidian(boolean activeObsidian) {
		this.activeObsidian = activeObsidian;
	}
}

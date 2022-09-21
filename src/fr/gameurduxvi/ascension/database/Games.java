package fr.gameurduxvi.ascension.database;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import fr.gameurduxvi.ascension.Creators.MobsGenerator;

public class Games {
	private int id;
	private ArrayList<GamesData> players = new ArrayList<>();
	private boolean inGame;
	private boolean timer = false;
	private int mapId;
	private int phase;
	private ArrayList<MobsGenerator> MobSpawnList = new ArrayList<>();
	
	public Games(int id, boolean inGame) {
		this.id = id;
		this.inGame = inGame;
		this.phase = 0;
	}
	
	
	public boolean getInGame() {
		return inGame;
	}
	public int getId() {
		return id;
	}
	public ArrayList<GamesData> getPlayers() {
		return players;
	}
	public boolean getTimer() {
		return timer;
	}
	public int getMapId() {
		return mapId;
	}
	public int getPhase() {
		return phase;
	}
	public ArrayList<MobsGenerator> getMobSpawnList() {
		return MobSpawnList;
	}
	
	
	public void addPlayer(Player player) {
		players.add(new GamesData(player));
	}
	
	public void removePlayer(Player player) {
		int i = 0;
		for(GamesData data: getPlayers()) {
			if(data.getPlayer().equals(player)) {
				break;
			}
			i++;
		}
		getPlayers().remove(getPlayers().get(i));
	}
	
	public boolean isIn(Player player) {
		for(GamesData data: players) {
			if(data.getPlayer().equals(player)) {
				return true;
			}
		}
		return false;
	}
	
	public void setTimer(boolean timer) {
		this.timer = timer;
	}
	
	public void setMapId(int mapId) {
		this.mapId = mapId;
	}
	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}
	public void setPhase(int phase) {
		this.phase = phase;
	}
}

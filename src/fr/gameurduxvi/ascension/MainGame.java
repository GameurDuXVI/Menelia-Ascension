package fr.gameurduxvi.ascension;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.ScoreboardManager;

import fr.gameurduxvi.ascension.database.Games;
import fr.gameurduxvi.ascension.database.GamesData;
import fr.gameurduxvi.ascension.database.Maps;
import fr.gameurduxvi.meneliaapi.MeneliaAPI;

public class MainGame extends JavaPlugin {
	
	private static MainGame instance;
	private static EventListener EventListenerInstance;
	private static EventListenerShop EventListenerShopInstance;
	private static EventListenerHub EventListenerHubInstance;
	private static EventListenerBlocks EventListenerBlocksInstance;
	private static GameFunctions GameFunctionsInstance;
	
	
	public static MainGame getInstance() {
		return instance;
	}
	
	public static EventListener getEventListener() {
		return EventListenerInstance;
	}
	
	public static EventListenerShop getEventShopListener() {
		return EventListenerShopInstance;
	}
	
	public static EventListenerHub getEventHubListener() {
		return EventListenerHubInstance;
	}
	
	public static EventListenerBlocks getEventBlocksListener() {
		return EventListenerBlocksInstance;
	}
	
	public static GameFunctions getGameFunctions() {
		return GameFunctionsInstance;
	}
	
	public String pluginPrefix = "§5[Ascension]";
	
	public Location spawnLocation;
	public int minToStartGame;
	public int maxInGame;
	public int timeInWaitingRoom;
	public int mobsSpawninterval;
	public int firstMobSpawnTime;
	public int maxRandomMobSpawnAmount;
	
	private ArrayList<Games> games = new ArrayList<>();
	private ArrayList<Maps> maps = new ArrayList<>();
	
	public List<EntityType> level1List = new ArrayList<>();
	public List<EntityType> level2List = new ArrayList<>();
	public List<EntityType> level3List = new ArrayList<>();
	public List<EntityType> level4List = new ArrayList<>();
	public List<EntityType> level5List = new ArrayList<>();
	
	public ScoreboardManager manager;
	
	public Location getSpawnLocation() {
		return spawnLocation;
	}
	
	public ArrayList<Games> getGames() {
		return games;
	}
	
	public ArrayList<Maps> getMaps() {
		return maps;
	}
	
	
	
	@Override
	public void onEnable() {
		// Plugin loading
		Bukkit.getConsoleSender().sendMessage(MeneliaAPI.getInstance().colorize(pluginPrefix + "========================================================="));
		Bukkit.getConsoleSender().sendMessage(MeneliaAPI.getInstance().colorize(pluginPrefix + " Enabling Ascension"));
		
		instance = this;
		
		// Config file(s) setting up 
		//Data DataClass = new Data();
		//DataClass.load();
			
		manager = Bukkit.getScoreboardManager();
		
		// Event listener
		EventListenerInstance = new EventListener();
		EventListenerShopInstance = new EventListenerShop();
		EventListenerHubInstance = new EventListenerHub();
		EventListenerBlocksInstance = new EventListenerBlocks();
		GameFunctionsInstance = new GameFunctions();
		
		GameFunctionsInstance.reloadJsonData();
		
		getServer().getPluginManager().registerEvents(EventListenerInstance, this);
		getServer().getPluginManager().registerEvents(EventListenerShopInstance, this);
		getServer().getPluginManager().registerEvents(EventListenerHubInstance, this);	
		getServer().getPluginManager().registerEvents(EventListenerBlocksInstance, this);
		
		//getCommand("ascension").setExecutor(new CommandListener());
		//getCommand("asc").setExecutor(new CommandListener());
		getCommand("aasc").setExecutor(new CommandListener());
		getCommand("adminascension").setExecutor(new CommandListener());		
		
		getGames().add(new Games(1, false));
		getGames().add(new Games(2, false));
		getGames().add(new Games(3, false));
		getGames().add(new Games(4, false));
		getGames().add(new Games(5, false));
		getGames().add(new Games(6, false));
		getGames().add(new Games(7, false));
		getGames().add(new Games(8, false));
		getGames().add(new Games(9, false));
		getGames().add(new Games(10, false));
		
		MeneliaAPI.getInstance().getGamesManager().setGames("ascension", 10, maxInGame);
		
		//getEventListener().interceptPackets();
		
		
		// Channel loader
		getServer().getMessenger().registerOutgoingPluginChannel(this, "MeneliaChannel");
		//getServer().getMessenger().registerIncomingPluginChannel(this, "MeneliaChannel", new PluginCustomMessageListener());	
		
		MobsListLoad();
		
		Bukkit.getConsoleSender().sendMessage(MeneliaAPI.getInstance().colorize(pluginPrefix + "========================================================="));
	}		
	
	@Override
	public void onDisable() {		
		for(Games game: getGames()) {
			for(GamesData data: game.getPlayers()) {
				data.getPlayer().getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
				data.getPlayer().sendMessage(MeneliaAPI.getInstance().getLang(data.getPlayer(), "§cLa partie a été fermé...", "§cThe game has been closed..."));
				MeneliaAPI.getBungeecordInstance().sendToServer(data.getPlayer(), "hub");
			}
		}
		Bukkit.getConsoleSender().sendMessage(MeneliaAPI.getInstance().colorize(pluginPrefix + " Disabling Ascnesion"));
	}
	
	private void MobsListLoad() {
		level1List.add(EntityType.ZOMBIE);
		level1List.add(EntityType.SLIME);
		level1List.add(EntityType.SPIDER);
		
		level2List.add(EntityType.MAGMA_CUBE);
		level2List.add(EntityType.HUSK);
		//level2List.add(EntityType.STRAY);
		
		level3List.add(EntityType.WITCH);
		level3List.add(EntityType.CAVE_SPIDER);
		level3List.add(EntityType.STRAY);
		//level3List.add(EntityType.EVOKER);
		
		level4List.add(EntityType.WITHER_SKELETON);
		level4List.add(EntityType.BLAZE);
		level4List.add(EntityType.PIG_ZOMBIE);

		level5List.add(EntityType.IRON_GOLEM);
		level5List.add(EntityType.ENDERMAN);
		level5List.add(EntityType.VINDICATOR);
	}
}

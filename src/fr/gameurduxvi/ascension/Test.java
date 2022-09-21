package fr.gameurduxvi.ascension;

import org.bukkit.scheduler.BukkitRunnable;

public class Test extends BukkitRunnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	/*List<Material> list;
	
	private World world;
	
	private int x1;
	private int y1;
	private int z1;
	
	private int x2;
	private int y2;
	private int z2;
	
	public Test(World world) {
		this.world = world;
		this.x1 = 0;
		this.y1 = 0;
		this.z1 = 0;
		this.x2 = 0;
		this.y2 = 0;
		this.z2 = 0;
	}
	@Override
	public void run() {
		for(int i = 0; i < 5000; i++) {
			Scan();
		}
	}
	
	public void Scan() {
		y1++;
		if(y1>250) {
			y1=0;
			x1++;
		}
		if(x1>1000) {
			x1=0;
			z1 += 50;
		}		
		if(x1==1000 && y1==250 && z1==1000) {
			Bukkit.broadcastMessage("Thread closed ! (" + x1 + "," + y1 + "," + z1 + ")");
			cancel();
		}
		Location loc = new Location(world, x1, y1, z1);
		//list.add(loc.getBlock().getType());
		
		for(Player player: Bukkit.getOnlinePlayers()) {
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("Scan (" + x1 + "," + y1 + "," + z1 + ")"));
		}
	}
	public void Place() {
		y2++;
		if(y2>250) {
			y2=0;
			x2++;
		}
		if(x2>1000) {
			x2=0;
			z2 += 50;
		}		
		if(x2==1000 && y2==250 && z2==1000) {
			Bukkit.broadcastMessage("Thread closed ! (" + x2 + "," + y2 + "," + z2 + ")");
			cancel();
		}
		Location loc2 = new Location(world, x2 + 1000, y2, z2);
		loc2.getChunk().unload();
		//loc2.getBlock().setType();
		
		
		for(Player player: Bukkit.getOnlinePlayers()) {
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("Place (" + x2 + "," + y2 + "," + z2 + ")"));
		}
	}*/

}

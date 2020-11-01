package main.java.fr.ayust.command;

import main.java.fr.ayust.Util.Util;
import main.java.fr.ayust.listener.PlayerRespawn;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Duel implements Listener {
	
	private Player player1;
	private PlayerInventory inv_p1;
	private Player player2;
	private PlayerInventory inv_p2;
	private Location loc1;
	private Location loc2;
	private Player Winner;
	private Player Loser;
	
	public Duel ( Player player1, Player player2 ) {
		
		this.player1 = player1;
		this.player2 = player2;
		this.inv_p1 = player1.getInventory();
		this.inv_p2 = player2.getInventory();
		this.loc1 = player1.getLocation();
		this.loc2 = player2.getLocation();
	}
	
	public boolean contains(Player player){
		return (this.player1.equals(player) || this.player2.equals(player));
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		this.Loser = event.getEntity();
		
		if (player1.equals(Loser)) {
			this.Winner = player2;
		} else {
			this.Winner = player1;
		}
		Loser.sendMessage(Util.sysMsg("Vous avez perdu le duel!"));
		Winner.sendMessage(Util.sysMsg("Vous avez gagné le duel!"));
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
	}

}


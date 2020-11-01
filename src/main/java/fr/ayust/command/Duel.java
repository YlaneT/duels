package main.java.fr.ayust.command;

import main.java.fr.ayust.Main;
import main.java.fr.ayust.listener.FinDuel;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.PlayerInventory;

public class Duel implements Listener {
	
	private Player player1;
	private PlayerInventory inv_p1;
	private Player player2;
	private PlayerInventory inv_p2;
	private Location loc1;
	private Location loc2;
	
	public Duel ( Player player1, Player player2 ) {
		this.player1 = player1;
		this.player2 = player2;
		this.inv_p1 = player1.getInventory();
		this.inv_p2 = player2.getInventory();
		this.loc1 = player1.getLocation();
		this.loc2 = player2.getLocation();
	}
	
	
	public boolean contains ( Player player ) {
		return (this.player1.equals(player) || this.player2.equals(player));
	}
	
	public Player getPlayer1 () {
		return player1;
	}
	
	public Player getPlayer2 () {
		return player2;
	}
	
	public PlayerInventory getInv_p1 () {
		return inv_p1;
	}
	
	public PlayerInventory getInv_p2 () {
		return inv_p2;
	}
	
	public Location getLoc1 () {
		return loc1;
	}
	
	public Location getLoc2 () {
		return loc2;
	}
}


package main.java.fr.ayust.command;

import main.java.fr.ayust.Main;
import main.java.fr.ayust.listener.FinDuel;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Duel implements Listener {
	
	private Player player1;
	private ItemStack[] inv_p1;
	private ItemStack[] stuff_p1;
	private Player player2;
	private ItemStack[] inv_p2;
	private ItemStack[] stuff_p2;
	private Location loc1;
	private Location loc2;
	
	public Duel ( Player player1, Player player2 ) {
		this.player1 = player1;
		this.player2 = player2;
		this.inv_p1 = player1.getInventory().getContents();
		this.stuff_p1 = getStuff(player1);
		this.stuff_p1 = getStuff(player2);
		this.inv_p2 = player2.getInventory().getContents();
		this.loc1 = player1.getLocation();
		this.loc2 = player2.getLocation();
	}
	
	private static ItemStack[] getStuff ( Player player ) {
		ItemStack[] stuff = new ItemStack[5];
		stuff[0] = player.getInventory().getHelmet();
		stuff[1] = player.getInventory().getChestplate();
		stuff[2] = player.getInventory().getLeggings();
		stuff[3] = player.getInventory().getBoots();
		stuff[4] = player.getInventory().getItemInOffHand();
		
		return stuff;
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
	
	public ItemStack[] getInv_p1 () {
		return inv_p1;
	}
	
	public ItemStack[] getInv_p2 () {
		return inv_p2;
	}
	
	public Location getLoc1 () {
		return loc1;
	}
	
	public Location getLoc2 () {
		return loc2;
	}
	
	public void rendItem () {
		for (int i = 0; i <= 35; i++) {
			this.player1.getInventory().setItem(i, this.inv_p1[i]);
			this.player2.getInventory().setItem(i, this.inv_p2[i]);
		}
	}
	
	public void rendStuff () {
		player1.getInventory().setHelmet(stuff_p1[0]);
		player1.getInventory().setChestplate(stuff_p1[1]);
		player1.getInventory().setLeggings(stuff_p1[2]);
		player1.getInventory().setBoots(stuff_p1[3]);
		player1.getInventory().setItemInOffHand(stuff_p1[4]);
		
		player2.getInventory().setHelmet(stuff_p2[0]);
		player2.getInventory().setChestplate(stuff_p2[1]);
		player2.getInventory().setLeggings(stuff_p2[2]);
		player2.getInventory().setBoots(stuff_p2[3]);
		player2.getInventory().setItemInOffHand(stuff_p2[4]);
	}
}


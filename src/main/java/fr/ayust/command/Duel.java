package main.java.fr.ayust.command;

import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class Duel {
	private Player player1;
	private PlayerInventory inv_p1;
	private Player player2;
	private PlayerInventory inv_p2;
	
	public Duel ( Player player1, Player player2 ) {
		this.player1 = player1;
		this.player2 = player2;
		this.inv_p1 = player1.getInventory();
		this.inv_p2 = player2.getInventory();
	}
}


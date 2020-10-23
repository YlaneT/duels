package fr.ayust.command;

import main.java.fr.ayust.Util.Util;
import main.java.fr.ayust.command.Demande_Duel;
import main.java.fr.ayust.command.Duel;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

public class Duel_Manager implements CommandExecutor {
	
	private List<Demande_Duel> demandes;
	private List<Duel> duels_en_cours;
	
	public boolean onCommand ( CommandSender sender, Command command, String label, String[] args ) {
		
		if (label.equalsIgnoreCase("duel") && sender instanceof Player) {
			
			Player playerSender = ( Player ) sender;
			
			if (args.length == 0) {
				
				playerSender.sendMessage(Util.sysMsg("/duel <Player>"));
				playerSender.sendMessage(Util.sysMsg("for accept or refuse: /duel <accept/refuse>"));
				return true;
			}
			
			// S'il y a un nom derrière /duel
			if (args.length == 1) {
				String target = args[0];
				
				Player playerReceiver = Bukkit.getPlayer(target);
				
				this.demandes.add(new Demande_Duel(playerSender, playerReceiver));
				
				// TODO : Si le joueur est déjà en duel, stop
				
				// Si playerReceiver tape /duel accept
				if (args[0].equalsIgnoreCase("accept")) {
					
					for(Demande_Duel dmd : this.demandes) {
						if (dmd.getReceiver().equals(playerReceiver)) {
							playerReceiver.sendMessage(Util.sysMsg("Vous avez accepté le duel contre ") + Util.pName(playerSender.getName()));
							playerSender.sendMessage(Util.pName(playerSender.getName()) + Util.sysMsg(" a accepté votre duel."));
						}
					}
					
					playerSender.setGameMode(GameMode.SURVIVAL);
					playerReceiver.setGameMode(GameMode.SURVIVAL);
					
					playerSender.teleport(new Location(Bukkit.getWorld("world"), 134.412, 69, -208.534));
					playerReceiver.teleport(new Location(Bukkit.getWorld("world"), 129.045, 69, -208.772));
					
					this.demandes.remove(new Demande_Duel(playerSender, playerReceiver));
					this.duels_en_cours.add(new Duel(playerSender, playerReceiver));
					
					//TODO : clear les inventaires et les modifier
                    playerSender.getInventory().clear();
                    playerReceiver.getInventory().clear();
					
				}
			}
			else if (args[0].equalsIgnoreCase("refuse")) {
				
				if (demandes.containsKey(playerSender)) {
					playerSender.sendMessage(Util.sysMsg("Vous avez refusé le duel!"));
					Player firstP = demandes.get(playerSender);
					firstP.sendMessage(Util.sysMsg("Votre duel à été annulé"));
				}
			}
			else if (Bukkit.getPlayer(target) != null) {
				
				Player target = Bukkit.getPlayer(target);
				
				if (demandes.containsKey(target)) {
					playerSender.sendMessage(Util.sysMsg("§4 Il semblerait qu'on vous à voler votre cible :D"));
				}
				else {
					demandes.put(target, playerSender);
					playerSender.sendMessage(Util.sysMsg("Vous avez demander un duel à §1" + target));
					target.sendMessage(Util.sysMsg("Vous venez de recevoir une proposition de duel de §1" + playerSender.getName()));
				}
			}
			else {
				playerSender.sendMessage(Util.sysMsg("Le joueur ") + Util.pName(target) + Util.sysMsg(" n'est pas connecté!"));
			}
		}
	}

        return false;
}
    
    
    
    
}

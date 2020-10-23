package main.java.fr.ayust.command;

import main.java.fr.ayust.Util.Util;
import main.java.fr.ayust.command.Demande_Duel;
import main.java.fr.ayust.command.Duel;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Duel_Manager implements CommandExecutor {
	
	private List<Demande_Duel> demandes;
	private List<Duel> duels_en_cours;
	
	private static Duel_Manager INSTANCE;
	
	private Duel_Manager() {
		this.demandes = new ArrayList<>();
		this.duels_en_cours = new ArrayList<Duel>();
	}
	
	public static Duel_Manager getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new Duel_Manager();
		}
		
		return INSTANCE;
	}
	
	public boolean onCommand ( CommandSender sender, Command command, String label, String[] args ) {
		
		if (label.equalsIgnoreCase("duel") && sender instanceof Player) {
			
			Player playerSender = ( Player ) sender;
			Player playerReceiver;
			
			if (args.length == 0) {
				
				playerSender.sendMessage(Util.sysMsg("/duel <Player>"));
				playerSender.sendMessage(Util.sysMsg("for accept or refuse: /duel <accept/refuse>"));
				return true;
			}
			
			// S'il y a un nom derrière /duel
			else if (args.length == 1) {
				String target = args[0];
				
				playerReceiver = Bukkit.getPlayer(target);
				
				this.demandes.add(new Demande_Duel(playerSender, playerReceiver));
				playerSender.sendMessage(Util.sysMsg("Vous avez demander un duel à §1") + Util.pName(playerReceiver.getName()));
				playerReceiver.sendMessage(Util.sysMsg("Vous venez de recevoir une proposition de duel de §1") + Util.pName(playerSender.getName()));
				
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
				else if (args[0].equalsIgnoreCase("refuse")) {
					
					for(Demande_Duel dmd : this.demandes) {
						if (dmd.getReceiver().equals(playerSender)) {
							dmd.getReceiver().sendMessage(Util.sysMsg("Vous tremblez de peur face à ") + Util.pName(dmd.getSender().getName()));
							dmd.getReceiver().sendMessage(Util.pName(dmd.getSender().getName()) + Util.sysMsg(" et refuse donc le duel."));
						}
					}
				}
				else if (Bukkit.getPlayer(target) != null) {
					
					for(Demande_Duel dmd : this.demandes) {
						// On cible une personne qui a déjà été ciblée
						if (dmd.getReceiver().equals(playerReceiver) && !dmd.getSender().equals(playerSender)){
							playerSender.sendMessage(Util.sysMsg("Il semblerait qu'on vous ait volé votre cible :D"));
					}
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

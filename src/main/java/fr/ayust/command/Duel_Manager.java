package main.java.fr.ayust.command;

import main.java.fr.ayust.Util.Util;
import main.java.fr.ayust.command.Demande_Duel;
import main.java.fr.ayust.command.Duel;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Duel_Manager implements CommandExecutor {
	
	private static Duel_Manager INSTANCE;
	private static List<Demande_Duel> demandes;
	private static List<Duel> duels_en_cours;
	
	private Duel_Manager () {
		this.demandes = new ArrayList<>();
		this.duels_en_cours = new ArrayList<>();
	}
	
	public static Duel_Manager getInstance () {
		if (INSTANCE == null) {
			INSTANCE = new Duel_Manager();
		}
		
		return INSTANCE;
	}
	
	@Override
	public boolean onCommand ( @NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args ) {
		
		if (label.equalsIgnoreCase("duel") && sender instanceof Player) {
			
			Player playerSender = ( Player ) sender;
			Player playerReceiver;
			
			// Si le joueur est en duel, stop
			if (dueling(playerSender)){
				// TODO : Pour ajout de /duel forfeit ajouter condition if args == 1 && args[0].equals("forfeit")
				//  ou déplacer cette brique de code
				playerSender.sendMessage(Util.sysMsg("Vous êtes déjà en duel, vous avez mieux à faire, non ?"));
				return true; // fin de la commande mais la commande était valide.
			}
			
			// Le joueur tape "/duel"
			if (args.length == 0) {
				help(playerSender);
				// Si le joueur a une demande de duel en attente, on lui envoie aussi la liste
				for (Demande_Duel dmd : this.demandes){
					if (dmd.contains_receiver(playerSender)){
						playerSender.sendMessage(Util.sysMsg("Vous avez des demandes en attente : "));
						duel_list(playerSender);
					}
				}
				return true;
			}
			
			// S'il y a un nom derrière /duel
			else if (args.length == 1) {
				String target = args[0];
				
				playerReceiver = Bukkit.getPlayer(target);
				
				this.demandes.add(new Demande_Duel(playerSender, playerReceiver));
				// FIXME : playerReceiver.getName() may produce NullPointerException
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
					
					// On met mode aventure plutot que survie pour ne pas que les joueurs cassent les blocs
					playerSender.setGameMode(GameMode.ADVENTURE);
					playerReceiver.setGameMode(GameMode.ADVENTURE);
					
					playerSender.teleport(new Location(Bukkit.getWorld("world"), 134.412, 69, -208.534));
					playerReceiver.teleport(new Location(Bukkit.getWorld("world"), 129.045, 69, -208.772));
					
					this.demandes.remove(new Demande_Duel(playerSender, playerReceiver));
					this.duels_en_cours.add(new Duel(playerSender, playerReceiver));
					
					playerSender.getInventory().clear();
					playerReceiver.getInventory().clear();
					
					// TODO : set les équipements des deux joueurs
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
						if (dmd.getReceiver().equals(playerReceiver) && !dmd.getSender().equals(playerSender)) {
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
	
	/**
	 * Envoie à playerSender la liste de demandes de duel.
	 * @param playerSender joueur qui envoie la commande.
	 */
	private void duel_list (Player playerSender) {
		StringBuilder sb = new StringBuilder(Util.sysMsg("Liste des demandes de duel :"));
		for(Demande_Duel dmd : this.demandes) {
			sb.append("\n");
			sb.append(dmd); //La méthode toString() est appelée automatiquement
		}
		playerSender.sendMessage(sb.toString());
	}
	
	/**
	 * dueling est une fonction statique, on peut l'appeler via				Duel_Manager.dueling()
	 * mais vu qu'on est dans Duel_Manager, on l'appelera directement via 	dueling()
	 * @param player joueur a tester
	 * @return si le joueur est en duel
	 */
	private static boolean dueling (Player player){
		return duels_en_cours.contains(player); // contains renvoie un booléen
	}
	
	/**
	 * Envoie la liste des commandes pouvant suivre /duel
	 * @param player joueur qui demande l'aide
	 */
	private static void help (Player player) {
		player.sendMessage(Util.sysMsg("Commandes duel :"));
		player.sendMessage(Util.sysMsg("/duel : Show this message"));
		player.sendMessage(Util.sysMsg("/duel <accept/refuse> : Accept or refuse last duel request")); // TODO : Check : Maybe first
		player.sendMessage(Util.sysMsg("/duel list : Liste de toutes les demandes de duel"));
		player.sendMessage(Util.sysMsg("/duel forfeit ou /duel ff : Se rendre (durant un duel)"));
	}
	
}

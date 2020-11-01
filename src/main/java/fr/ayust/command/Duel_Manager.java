package main.java.fr.ayust.command;

import main.java.fr.ayust.Util.Util;
import main.java.fr.ayust.kit.KitDuel;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Duel_Manager implements CommandExecutor {
	private static Duel_Manager INSTANCE;
	private static List<Demande_Duel> demandes;
	private static List<Duel> duels_en_cours;
	
	private Duel_Manager () {
		demandes = new ArrayList<>();
		duels_en_cours = new ArrayList<>();
	}
	
	public static Duel_Manager getInstance () {
		if (INSTANCE == null) {
			INSTANCE = new Duel_Manager();
		}
		
		return INSTANCE;
	}
	
	@Override
	public boolean onCommand ( CommandSender sender, Command command, String label, String[] args ) {
		
		if (label.equalsIgnoreCase("duel") && sender instanceof Player) {
			
			Player playerSender = ( Player ) sender;
			Player playerReceiver;
			
			// Si le joueur est en duel, stop
			if (dueling(playerSender)) {
				// TODO : Pour ajout de /duel forfeit ajouter condition if args == 1 && args[0].equals("forfeit")
				//  ou déplacer cette brique de code
				playerSender.sendMessage(Util.sysMsg("Vous êtes déjà en duel, vous avez mieux à faire, non ?"));
				return true; // fin de la commande mais la commande était valide.
			}
			
			/*----------		DUEL		----------*/
			
			if (args.length == 0) {
				playerSender.sendMessage(Util.sysMsg("Utilisez /duel help pour des informations sur les commandes."));
				
				// Si le joueur a une demande de duel en attente, on lui envoie aussi la prochaine demande
				for(Demande_Duel dmd : demandes) {
					if (dmd.contains_receiver(playerSender)) {
						playerSender.sendMessage(Util.sysMsg("Vous avez des demandes en attente, voici la prochaine : "));
						playerSender.sendMessage(Util.sysMsg(dmd.toString()));
						playerSender.sendMessage(Util.sysMsg("Acceptez avec /duel accept ou refusez avec /duel refuse"));
						return true;
					}
				}
			}
			
			
			// S'il y a un nom derrière /duel
			else if (args.length == 1) {
				
				String target = args[0];
				playerReceiver = Bukkit.getPlayer(target);
				
				/*----------		HELP		----------*/
				if (target.equalsIgnoreCase("help")) {
					help(playerSender);
				}
				
				/*----------		LIST		----------*/
				else if (target.equalsIgnoreCase("list")) {
					duel_list(playerSender, false);
					return true;
				}
				
				else if (target.equalsIgnoreCase("list_all")) {
					duel_list(playerSender, true);
					return true;
				}
				
				/*----------		ACCEPT		----------*/
				else if (target.equalsIgnoreCase("accept")) {
					
					Demande_Duel demande_a_traiter = null;
					Player       initialSender     = null;
					boolean      duel_trouve       = false;
					
					
					for(Demande_Duel dmd : demandes) {
						initialSender = dmd.getSender();
						if (dmd.contains_sender(initialSender)) {
							demande_a_traiter = dmd;
							duel_trouve = true;
							break;
						}
					}
					
					if (!duel_trouve) {
						playerSender.sendMessage(Util.sysMsg("Lâche tes cheveux, personne ne t'as provoqué."));
					}
					else {
						if (dueling(demande_a_traiter.getSender())) {
							playerSender.sendMessage(Util.pName(initialSender) + Util.sysMsg(" est déjà en combat, attendez un peu"));
						}
						else {
							playerSender.sendMessage(Util.sysMsg("Vous avez accepté le duel de " + Util.pName(initialSender)));
							initialSender.sendMessage(Util.pName(playerSender) + Util.sysMsg(" a accepté votre demande de duel"));
							demandes.remove(demande_a_traiter);
							duel_accept(initialSender, playerSender);
						}
					}
					return true;
					
					/*boolean duel_found = false;
					for(Demande_Duel dmd : demandes) {
						Player initialSender;
						
						// S'il existe au moins une demande qui vous est adressée
						if (dmd.getReceiver().equals(playerSender)) {
							duel_found = true;
							initialSender = dmd.getSender();
							// Si le joueur qui l'a envoyée n'est pas en duel
							if (!dueling(initialSender)) {
								playerSender.sendMessage(Util.sysMsg("Vous avez accepté le duel contre ") + Util.pName(initialSender));
								initialSender.sendMessage(Util.pName(playerSender) + Util.sysMsg(" a accepté votre duel."));
								
								duels_en_cours.add(new Duel(dmd.getSender(), dmd.getReceiver()));
								demandes.remove(dmd);
								
								duel_accept(initialSender, playerSender);
								return true;
							}
							else {
								playerSender.sendMessage(Util.sysMsg("Le joueur ") + Util.pName(initialSender) + Util.sysMsg(" est déjà en train de combattre."));
								playerSender.sendMessage(Util.sysMsg("Vous ne pouvez pas accepter sa demande pour le moment"));
							}
						}
					}
					if (!duel_found) {
						playerSender.sendMessage(Util.sysMsg("Lâche tes cheveux, personne ne t'as provoqué en duel"));
					}*/
					
				}
				
				
				/*----------		REFUSE		----------*/
				
				else if (target.equalsIgnoreCase("refuse")) {
					Demande_Duel demande_a_traiter = null;
					boolean      duel_trouve       = false;
					for(Demande_Duel dmd : demandes) {
						
						if (dmd.contains_receiver(playerSender)) {
							demande_a_traiter = dmd;
							duel_trouve = true;
							break;
						}
					}
					
					if (!duel_trouve) {
						playerSender.sendMessage(Util.sysMsg("Lâche tes cheveux, personne ne t'as provoqué."));
						return true;
					}
					duel_refuse(demande_a_traiter);
					return true;
				}
				
				
				/*----------		JOUEUR		----------*/
				
				// Si la target n'est pas connectée, stop
				else if (!Bukkit.getWorld("world").getPlayers().contains(playerReceiver)) {
					playerSender.sendMessage(Util.sysMsg("Le joueur ") + Util.pName(playerReceiver) + Util.sysMsg(" n'est pas connecté ou n'existe pas..."));
					return true;
				}
				
				// Demande à soi-même
				else if (playerReceiver.equals(playerSender)) {
					playerSender.sendMessage(Util.sysMsg("Tu t'envoies des demandes de duel à toi même ? Tu es schyzophrène ?"));
					return true;
				}
				else {
					for(Demande_Duel dmd : demandes) {
						// On cible une personne qui nous a déjà envoyé une demande
						if (dmd.getReceiver().equals(playerSender) && dmd.getSender().equals(playerReceiver)) {
							/*
							 * TODO (mais pas nécessaire)
							 * faire en sorte que ça accepte automatiquement
							 */
							playerSender.sendMessage(Util.sysMsg("Ce joueur vous a déjà envoyé une demande de duel. Gérez vos demandes avec les commandes suivantes :"));
							playerSender.sendMessage(Util.sysMsg("/duel"));
							playerSender.sendMessage(Util.sysMsg("/duel accept ou /duel refuse"));
							return true;
						}
						// On cible une personne à qui on a déjà envoyé une demande
						else if (dmd.getReceiver().equals(playerReceiver) && dmd.getSender().equals(playerSender)) {
							playerSender.sendMessage(Util.sysMsg("Vous avez déjà envoyé une demande à ce joueur."));
							return true;
						}
					}
					demandes.add(new Demande_Duel(playerSender, playerReceiver));
					return true;
				}
			}
		}
		return false;
}
	
	/**
	 * dueling est une fonction statique, on peut l'appeler via				Duel_Manager.dueling()
	 * mais vu qu'on est dans Duel_Manager, on l'appelera directement via 	dueling()
	 *
	 * @param player joueur a tester
	 * @return si le joueur est en duel
	 */
	public static boolean dueling ( Player player ) {
		return duels_en_cours.contains(player); // contains renvoie un booléen
	}
	
	/**
	 * Envoie la liste des commandes pouvant suivre /duel
	 *
	 * @param player joueur qui demande l'aide
	 */
	private static void help ( Player player ) {
		player.sendMessage(Util.sysMsg("Commandes duel :"));
		player.sendMessage(Util.sysMsg("/duel help : Show this message"));
		player.sendMessage(Util.sysMsg("/duel <accept/refuse> : Accept or refuse first duel request"));
		player.sendMessage(Util.sysMsg("/duel list : Liste de toutes les demandes de duel vous concernant"));
		player.sendMessage(Util.sysMsg("/duel list_all : Liste de toutes les demandes de duel"));
		player.sendMessage(Util.sysMsg("/duel forfeit ou /duel ff : Se rendre (durant un duel)"));
	}
	
	/**
	 * Refuse le premier duel reçu par le joueur qui envoie la commande
	 *
	 * @param demande joueur qui envoie la commande
	 */
	private static void duel_refuse ( Demande_Duel demande ) {
		demande.getReceiver().sendMessage(Util.sysMsg("Vous tremblez de peur face à ") + Util.pName(demande.getSender()));
		demande.getSender().sendMessage(Util.pName(demande.getReceiver()) + Util.sysMsg(" a peur de vous et refuse donc le duel."));
		demandes.remove(demande);
	}
	
	/**
	 * Le Duel a été accepté.
	 * Téléportation des joueurs / Création de leurs stuffs / Changement de gamemode
	 * Mise à jour de leur vie au maximum
	 * Appel de la fonction avec eventListener death
	 *
	 * @param initialSender   joueur qui a initialement demandé le duel
	 * @param playerAccepting joueur qui accepte le duel
	 */
	private static void duel_accept ( Player initialSender, Player playerAccepting ) {
		// On met mode aventure plutot que survie pour ne pas que les joueurs cassent les blocs
		duels_en_cours.add(new Duel(initialSender, playerAccepting));
		
		initialSender.teleport(new Location(Bukkit.getWorld("world"), 81.0, 70.0, -55.0));
		playerAccepting.teleport(new Location(Bukkit.getWorld("world"), 85.0, 70.0, -51));
		
		initialSender.getInventory().clear();
		playerAccepting.getInventory().clear();
		
		KitDuel.setEquipementForDuel(initialSender);
		KitDuel.setEquipementForDuel(playerAccepting);
		
		//TODO : lancer la fonction avec event handler on Death
	}
	
	/**
	 * Envoie à playerSender la liste de demandes de duel.
	 *
	 * @param playerSender joueur qui envoie la commande.
	 */
	
	private static void duel_list ( Player playerSender, boolean all ) {
		StringBuilder sb = new StringBuilder();
		
		if (all) {
			sb.append(Util.sysMsg("Liste de toutes les demandes de duel :"));
		}
		else {
			sb.append(Util.sysMsg("Liste des demandes de duel vous concernant :"));
		}
		for(Demande_Duel dmd : demandes) {
			if (!all) {
				if (dmd.contains(playerSender)) {
					sb.append("\n" + dmd);
				}
			}
			else {
				sb.append("\n" + dmd);
			}
		}
		playerSender.sendMessage(sb.toString());
	}
	
	public static List<Duel> getDuels_en_cours () {
		return duels_en_cours;
	}
}

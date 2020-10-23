package fr.ayust.command;

import main.java.fr.ayust.Util.Util;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class Duel implements CommandExecutor {

    private Map<Player, Player> players = new HashMap<>();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(label.equalsIgnoreCase("duel") && sender instanceof Player) {

            Player p = (Player) sender;

            if(args.length == 0){

                p.sendMessage(Util.systemMessage("/duel <Player>"));
                p.sendMessage(Util.systemMessage("for accept or refuse: /duel <accept/refuse>"));
                return true;
            }

            if(args.length == 1) {
                String targetName = args[0];
                // FIXME : C'est cette ligne qui fait apparaitre le nom de la cible.
                p.sendMessage(targetName);

                if (args[0].equalsIgnoreCase("accept")) {

                    if (players.containsKey(p)) {
                        p.sendMessage(Util.systemMessage("/choose pour choisir le type de duel"));
                        Player firstP = players.get(p);
                        firstP.sendMessage(Util.systemMessage("§1" + p.getName() + "§r a accepté votre duel. Attendez qu'il/elle choisi le type de duel"));
                        
                        // FIXME : Le joueur qui envoie la demande de duel n'est pas set en survival
                        firstP.setGameMode(GameMode.SURVIVAL);
                        // TODO : Sauvegarder les inventaires des joueurs pour leur retirer pendant le duel
                        // TODO : Récupérer les coordonnées dans un yml ?
                        p.teleport(new Location(Bukkit.getWorld("world"), 134.412, 69, -208.534));
                        firstP.teleport(new Location(Bukkit.getWorld("world"), 129.045, 69, -208.772));

                        players.remove(p);
                    }
                } else if (args[0].equalsIgnoreCase("refuse")) {

                    if (players.containsKey(p)) {
                        p.sendMessage(Util.systemMessage("Vous avez refusé le duel!"));
                        Player firstP = players.get(p);
                        firstP.sendMessage(Util.systemMessage("Votre duel à été annulé"));

                    }
                } else if (Bukkit.getPlayer(targetName) != null) {

                    Player target = Bukkit.getPlayer(targetName);

                    if (players.containsKey(target)) {
                        p.sendMessage(Util.systemMessage("§4 Il semblerait qu'on vous à voler votre cible :D"));
                    } else {
                        players.put(target, p);
                        p.sendMessage(Util.systemMessage("Vous avez demander un duel à §1" + targetName));
                        target.sendMessage(Util.systemMessage("Vous venez de recevoir une proposition de duel de §1" + p.getName()));
                    }

                } else {
                    p.sendMessage(Util.systemMessage("Le joueur §1" + targetName + "§r n'est pas connecté!"));
                }
            }

            
        }

        return false;
    }
    
    
    
    
}

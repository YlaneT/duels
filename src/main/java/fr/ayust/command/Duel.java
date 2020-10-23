package fr.ayust.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Duel implements CommandExecutor {

    private Map<Player, Player> players = new HashMap<>();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(label.equalsIgnoreCase("duel") && sender instanceof Player) {

            Player p = (Player) sender;

            if(args.length == 0){

                p.sendMessage("/duel <Player>");
                p.sendMessage("for accept or refuse: /duel <accept/refuse>");
                return true;
            }

            if(args.length == 1) {
                String targetName = args[0];
                p.sendMessage(targetName);

                if (args[0].equalsIgnoreCase("accept")) {

                    if (players.containsKey(p)) {
                        p.sendMessage("§1" + p.getName() + " a accepté votre duel. Attendez qu'il/elle choisi le type de duel");
                        Player firstP = players.get(p);
                        firstP.sendMessage("Taper /choose pour choisir votre duel");

                        p.teleport(new Location(Bukkit.getWorld("world"), 134.412, 69, -208.534));
                        firstP.teleport(new Location(Bukkit.getWorld("world"), 129.045, 69, -208.772));

                        players.remove(p);
                    }
                } else if (args[0].equalsIgnoreCase("refuse")) {

                    if (players.containsKey(p)) {
                        p.sendMessage("Vous avez refusé le duel!");
                        Player firstP = players.get(p);
                        firstP.sendMessage("Votre duel à été annulé");

                    }
                } else if (Bukkit.getPlayer(targetName) != null) {

                    Player target = Bukkit.getPlayer(targetName);

                    if (players.containsKey(target)) {
                        p.sendMessage("§4 Il semblerait qu'on vous à voler votre cible :D");
                    } else {
                        players.put(target, p);
                        p.sendMessage("Vous avez demander un duel à §1" + targetName);
                        target.sendMessage("Vous venez de recevoir une proposition de duel de §1" + p.getName());
                    }

                } else {
                    p.sendMessage("Le joueur §1" + targetName + "§r n'est pas connecté!");
                }
            }

        }

        return false;
    }
}

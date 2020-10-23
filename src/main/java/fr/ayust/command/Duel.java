package fr.ayust.command;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Duel implements CommandExecutor {

    private Map<Player, Player> players = new HashMap<>();


        private static Duel INSTANCE;
        private String info = "Initial info class";

        private Duel() {
            this.demandes = new ArrayList<>();
            this.duel_en_cours = new ArrayList<>();
        }

        public static Duel getInstance() {
            if(INSTANCE == null) {
                INSTANCE = new Duel();
            }

            return INSTANCE;
        }

        // getters and setters



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
                        p.sendMessage("Votre duel contre §6" + p.getName() + "va commencer");
                        Player firstP = players.get(p);
                        firstP.sendMessage("§6" + p.getName() + "§r a accepté votre duel. Attendez qu'il/elle choisi le type de duel");


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
                        p.sendMessage("Vous avez demander un duel à §6" + targetName);
                        target.sendMessage("Vous venez de recevoir une proposition de duel de §6" + p.getName());
                    }

                } else {
                    p.sendMessage("Le joueur §6" + targetName + "§r n'est pas connecté!");
                }
            }

        }

        return false;
    }
}

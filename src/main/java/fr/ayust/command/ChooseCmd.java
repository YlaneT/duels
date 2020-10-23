package fr.ayust.command;

import fr.ayust.kit.KitDuel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChooseCmd implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] strings) {

        if(!(sender instanceof Player))
        return false;

        final Player p = ((Player) sender).getPlayer();

        if(label.equals("choose")) {
            KitDuel.openInv(p);

            p.sendMessage("§6§o Choisi le type de duel");
            return true;
        }

        return false;
    }
}

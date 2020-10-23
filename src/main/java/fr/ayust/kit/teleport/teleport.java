package main.java.fr.ayust.kit.teleport;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class teleport {

    public void onClick(Player p, Inventory inv, ItemStack current, int slot) {
        if(current.getType() == Material.DIAMOND_SWORD && current.getItemMeta().hasDisplayName() && current.getItemMeta().getDisplayName().equalsIgnoreCase("Duel classique")) {
            p.teleport(new Location(Bukkit.getWorld("world"),0,67,0));
        }
    }
}

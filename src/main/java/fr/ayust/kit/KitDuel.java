package fr.ayust.kit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class KitDuel {

    public static void openInv(Player p) {

        final Inventory inv = Bukkit.createInventory(p, 9, "§6§o Choisi le type de duel");

        List<String> list = new ArrayList<String>();
        ItemStack TridentDuel = new ItemStack(Material.TRIDENT, 1);

        final ItemMeta duelT = TridentDuel.getItemMeta();
        assert duelT != null;
        duelT.setDisplayName("Duel épique sous la pluie avec un Trident");
        duelT.setLore(list);
        duelT.addEnchant(Enchantment.RIPTIDE, 3, true);
        TridentDuel.setItemMeta(duelT);

        ItemStack NormalDuel = new ItemStack(Material.DIAMOND_SWORD, 1);

        final ItemMeta duelN = NormalDuel.getItemMeta();
        assert duelN != null;
        duelN.setDisplayName("Duel classique");
        duelN.setLore(list);
        NormalDuel.setItemMeta(duelN);

        ItemStack BowDuel = new ItemStack(Material.BOW, 1);

        final ItemMeta duelB = BowDuel.getItemMeta();
        assert duelB != null;
        duelB.setDisplayName("Duel Arc");
        duelB.setLore(list);
        BowDuel.setItemMeta(duelB);

        inv.setItem(1, TridentDuel);
        inv.setItem(4,NormalDuel);
        inv.setItem(7,BowDuel);

        p.openInventory(inv);



    }
}

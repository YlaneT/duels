package main.java.fr.ayust.kit;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class KitDuel {

    public static void openInv(Player p) {
        
        /*
         * TODO (mais pas tout de suite)
         * utiliser InventoryClickEvent
         * pour link les différents objets à une action
         */

        final Inventory inv = Bukkit.createInventory(p, 9, "§6§o Choisis le type de duel");

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
    
    public static void setEquipementForDuel ( Player player ) {
        
        player.setGameMode(GameMode.ADVENTURE);
        
        
        ItemStack shield     = new ItemStack(Material.SHIELD);
        ItemStack boots      = new ItemStack(Material.IRON_BOOTS);
        ItemStack helmet     = new ItemStack(Material.IRON_HELMET);
        ItemStack leggings   = new ItemStack(Material.IRON_LEGGINGS);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        
        
        player.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
        player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 10));
        
        player.getInventory().setBoots(boots);
        player.getInventory().setHelmet(helmet);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setItemInOffHand(shield);
        player.getInventory().setChestplate(chestplate);
        
        // Life and food levels
        player.setHealth(20.0);
        player.setFoodLevel(200);
        
        
        // CUSTOM BOW
        /*ItemStack customBow = new ItemStack(Material.BOW);
        ItemMeta  customEn  = customBow.getItemMeta();
        customEn.setDisplayName("One shot");
        
        List<String> lore = new ArrayList<>();
        lore.add("This bow was gifted to you by the devil himself.");
        lore.add("But be careful, you are not the only one who received this gift");
        customEn.setLore(lore);
        
        customEn.addEnchant(Enchantment.ARROW_DAMAGE, 666, true);
        customBow.setItemMeta(customEn);
        
        player.getInventory().setItem(3, customBow);
        player.getInventory().setItem(9, new ItemStack(Material.ARROW, 5));*/
    }
}

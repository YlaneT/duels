package fr.ayust;

import fr.ayust.command.ChooseCmd;
import fr.ayust.command.Duel;
import fr.ayust.listener.PlayerDeath;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

   @Override
   public void onEnable() {

    Duel_Manager duelmanager = new Duel_Manager;
       PluginManager pm = Bukkit.getPluginManager();
       pm.registerEvents(new PlayerDeath(), this);
        getLogger().info("[Duel] has been enable");
        getCommand("choose").setExecutor(new ChooseCmd());
        getCommand("duel").setExecutor(new Duel());
   }

    @Override
    public void onDisable() {

        getLogger().info( "[Duel] has been disable");

    }
}




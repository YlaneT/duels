package main.java.fr.ayust;


import main.java.fr.ayust.command.Duel_Manager;
import main.java.fr.ayust.listener.PlayerRespawn;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

   @Override
   public void onEnable() {

       PluginManager pm = Bukkit.getPluginManager();
       pm.registerEvents(new PlayerRespawn(), Duel_Manager.getInstance());

      
        getLogger().info("[Duel] has been enable");
        getCommand("duel").setExecutor(Duel_Manager.getInstance());
   }

    @Override
    public void onDisable() {

        getLogger().info( "[Duel] has been disable");

    }
}




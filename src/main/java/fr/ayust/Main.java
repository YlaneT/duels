package main.java.fr.ayust;

import main.java.fr.ayust.command.Duel_Manager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

   @Override
   public void onEnable() {
        getLogger().info("[Duel] has been enable");
        getCommand("duel").setExecutor(Duel_Manager.getInstance());
   }

    @Override
    public void onDisable() {

        getLogger().info( "[Duel] has been disable");

    }
}




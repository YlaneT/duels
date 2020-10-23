package fr.ayust;

import fr.ayust.command.ChooseCmd;
import fr.ayust.command.Duel;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

   @Override
   public void onEnable() {

        getLogger().info("[Duel] has been enable");
        getCommand("choose").setExecutor(new ChooseCmd());
        getCommand("duel").setExecutor(new Duel());
   }

    @Override
    public void onDisable() {

        getLogger().info( "[Duel] has been disable");

    }
}




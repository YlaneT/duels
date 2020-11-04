package main.java.fr.ayust;


import main.java.fr.ayust.command.Duel_Manager;
import main.java.fr.ayust.listener.FinDuel;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {
    
    
    
    @Override
    public void onEnable () {
        
        PluginManager pm = getServer().getPluginManager();
        FinDuel listener = new FinDuel(this);
        pm.registerEvents(listener/*.onDeath(event)*/, this);
        
        getLogger().info("[Duel] has been enable");
        getCommand("duel").setExecutor(Duel_Manager.getInstance());
    }

    @Override
    public void onDisable() {

        getLogger().info( "[Duel] has been disable");

    }
}




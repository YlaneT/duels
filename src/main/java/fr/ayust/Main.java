package main.java.fr.ayust;


import main.java.fr.ayust.command.Duel_Manager;
import main.java.fr.ayust.listener.FinDuel;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {
    
    public static PluginManager pm;
    public static ArrayList listeners;
    
    @Override
    public void onEnable () {
        
        pm = Bukkit.getPluginManager();
        
        FinDuel listener = new FinDuel();
        Main.pm.registerEvents(listener, this);
        
        getLogger().info("[Duel] has been enable");
        getCommand("duel").setExecutor(Duel_Manager.getInstance());
    }

    @Override
    public void onDisable() {

        getLogger().info( "[Duel] has been disable");

    }
}




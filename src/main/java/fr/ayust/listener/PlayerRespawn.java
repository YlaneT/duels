package main.java.fr.ayust.listener;


import org.bukkit.GameMode;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawn implements Listener {
    
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player Loser = event.getEntity();
        Player Winner = event.getEntity();
        
        Loser.sendMessage("Vous avez perdu le duel!");
        Winner.sendMessage("Vous avez gagné le duel!");
    }
    
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player Loser = event.getPlayer();
        Player Winner = event.getPlayer();
        
        Loser.setGameMode(GameMode.CREATIVE);
        Winner.setGameMode(GameMode.CREATIVE);
    }
}

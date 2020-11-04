package main.java.fr.ayust.listener;


import main.java.fr.ayust.Main;
import main.java.fr.ayust.Util.Util;
import main.java.fr.ayust.command.*;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;


public class FinDuel implements Listener {
    
    private Player loser;
    private Player winner;
    private Duel duel;
    
    public FinDuel ( Main plugin) {
    }
    
    @EventHandler
    public void onDeath ( PlayerDeathEvent event ) {
        this.loser = event.getEntity();
        this.loser.sendMessage("test à la mort d'un joueur");
        if (Duel_Manager.dueling(loser)) {
            for(Duel duel : Duel_Manager.getDuels_en_cours()) {
                if (duel.contains(loser)) {
                    this.duel = duel;
                    break;
                }
            }
            
            if (this.duel.getPlayer1().equals(loser)) {
                this.winner = this.duel.getPlayer2();
            }
            else {
                this.winner = this.duel.getPlayer1();
            }
            
            loser.sendMessage(Util.sysMsg("Vous avez perdu le duel!"));
            winner.sendMessage(Util.sysMsg("Vous avez gagné le duel!"));
            
            this.duel.getPlayer1().getInventory().clear();
            this.duel.getPlayer2().getInventory().clear();
            /* FIXME : trouver comment set l'inventaire complet avec un PlayerInventory
            // Utilisation d'une boucle ?
            this.duel.getPlayer1().getInventory() = this.duel.getInv_p1();
            this.duel.getPlayer1().getInventory() = this.duel.getInv_p1();
            */
            
            loser.spigot().respawn();
            this.duel.getPlayer1().teleport(this.duel.getLoc1());
            this.duel.getPlayer2().teleport(this.duel.getLoc2());
            
            loser.setGameMode(GameMode.CREATIVE);
            winner.setGameMode(GameMode.CREATIVE);
            
            Duel_Manager.getDuels_en_cours().remove(this.duel);
        }
    }
}


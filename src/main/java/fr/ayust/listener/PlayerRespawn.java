package main.java.fr.ayust.listener;


import main.java.fr.ayust.Util.Util;
import main.java.fr.ayust.command.Duel;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;


public class PlayerRespawn implements Listener {

    private Player Loser;

    private Player Winner;

    private Duel duel;


    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        this.Loser = event.getEntity();

        if (this.duel.getPlayer1().equals(Loser)) {
            this.Winner = this.duel.getPlayer2();
        } else {
            this.Winner = this.duel.getPlayer1();
        }
        Loser.sendMessage(Util.sysMsg("Vous avez perdu le duel!"));
        Winner.sendMessage(Util.sysMsg("Vous avez gagné le duel!"));

    }
        @EventHandler
        public void onRespawn (PlayerRespawnEvent event){
            Player Loser = event.getPlayer();
            Player Winner = event.getPlayer();

            Loser.setGameMode(GameMode.CREATIVE);
            Winner.setGameMode(GameMode.CREATIVE);
        }
    }
}

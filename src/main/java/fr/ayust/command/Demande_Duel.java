package main.java.fr.ayust.command;

import main.java.fr.ayust.Util.Util;
import org.bukkit.entity.Player;

public class Demande_Duel {
	private Player sender;
	private Player receiver;
	
	public Demande_Duel ( Player sender, Player receiver ) {
		this.sender = sender;
		this.receiver = receiver;
		this.sender.sendMessage(Util.sysMsg("Vous avez demander un duel à ") + Util.pName(this.receiver.getName()));
		this.receiver.sendMessage(Util.sysMsg("Vous venez de recevoir une proposition de duel de ") + Util.pName(this.sender.getName()));
		
	}
	
	public Player getSender () {
		return sender;
	}
	
	public Player getReceiver () {
		return receiver;
	}
	
	public boolean contains_sender(Player player){
		return this.sender.equals(player);
	}
	
	public boolean contains_receiver(Player player){
		return this.receiver.equals(player);
	}
	
	public boolean contains (Player player) {
		return this.contains_receiver(player) || this.contains_sender(player);
	}
	@Override
	public String toString(){
		return Util.sysMsg("sender : ") + Util.pName(sender.getName()) +  Util.sysMsg( " / receiver : ") + Util.pName(receiver.getName());
	}
}

package main.java.fr.ayust.command;

import main.java.fr.ayust.Util.Util;
import org.bukkit.entity.Player;

public class Demande_Duel {
	private Player sender;
	private Player receiver;
	
	public Demande_Duel ( Player sender, Player receiver ) {
		this.sender = sender;
		this.receiver = receiver;
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
	
	@Override
	public String toString(){
		return Util.sysMsg("sender : ") + Util.pName(sender.getName()) +  Util.sysMsg( " / receiver : ") + Util.pName(receiver.getName());
	}
}

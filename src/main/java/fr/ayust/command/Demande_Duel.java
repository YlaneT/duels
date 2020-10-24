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
	
	@Override
	public String toString(){
		return Util.sysMsg("sender : ") + Util.pName(sender.getName()) +  Util.sysMsg( " / receiver : ") + Util.pName(receiver.getName());
	}
}

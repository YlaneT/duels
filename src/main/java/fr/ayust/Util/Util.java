package main.java.fr.ayust.Util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.StringTokenizer;

public class Util {
	
	/**
	 * @param str ex : "message systeme"
	 * @return message système en jaune ex : "<SYSTEM> message systeme"
	 */
	public static String sysMsg (String str){
		return "§e " + str;
	}
	
	public static String pName (String str){
		return "§6 " + str;
	}
	
	/**
	 * @param coordonnees coordonnées données sous type String : "x,y,z"
	 * @return coordonnées sous type Location
	 */
    public static Location parseStringToLoc(String coordonnees){
        float           x, y, z;
        StringTokenizer st = new StringTokenizer(coordonnees,",");
        x = Float.parseFloat(st.nextToken());
        y = Float.parseFloat(st.nextToken());
        z = Float.parseFloat(st.nextToken());
        return new Location(Bukkit.getWorld("world"),x,y,z);
        // FIXME : Ne crée pas une nouvelle instance ?
        // TODO : Utilisation des autres mondes
    }
	
    
	/**
	 * @param loc coordonnées sous type Location
	 * @return coordonnées données sous type String : "x,y,z"
	 */
    public static String parseLocToString(Location loc){
        return loc.getX() + "," + loc.getY() + "," + loc.getZ();
  	}
}

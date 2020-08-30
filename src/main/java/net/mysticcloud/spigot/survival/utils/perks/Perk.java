package net.mysticcloud.spigot.survival.utils.perks;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Perk {
	
	UUID uid;
	
	public Perk(UUID uid) {
		this.uid = uid;
	}
	
	public void activate() {
		
	}
	
	protected Player getPlayer() {
		return(Bukkit.getPlayer(uid));
	}

}

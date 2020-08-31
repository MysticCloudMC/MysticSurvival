package net.mysticcloud.spigot.survival.utils.perks;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Perk {

	UUID uid;
	boolean ready = true;
	String[] reqs = new String[] {};

	public Perk(UUID uid) {
		this.uid = uid;
	}

	public void activate() {

	}

	public boolean canActivate() {
		return ready;
	}
	
	public String[] getRequirements() {
		return reqs;
	}

	protected Player getPlayer() {
		return (Bukkit.getPlayer(uid));
	}

}

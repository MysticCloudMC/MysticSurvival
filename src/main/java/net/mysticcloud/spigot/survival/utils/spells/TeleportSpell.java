package net.mysticcloud.spigot.survival.utils.spells;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class TeleportSpell extends Spell {
	
	Entity entity;
	Location loc;
	
	public TeleportSpell(Entity entity, Location loc) {
		this.entity = entity;
		this.loc = loc;
	}
	
	@Override
	public void activate() {
		entity.teleport(loc);
	}

}

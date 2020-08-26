package net.mysticcloud.spigot.survival.utils.spells;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public class TeleportSpell extends Spell {
	
	public TeleportSpell(LivingEntity entity, Location loc) {
		this.entity = entity;
		this.loc = loc;
		cost = 50;
	}
	
	@Override
	public void activate() {
		entity.teleport(loc);
	}

}

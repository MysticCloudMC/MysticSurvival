package net.mysticcloud.spigot.survival.utils.spells;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import net.mysticcloud.spigot.core.utils.particles.formats.RandomFormat;

public class Spell {
	
	int cost = 0;
	
	LivingEntity entity;
	Location loc;
	
	
	public int getCost() {
		return cost;
	}
	
	public void activate() {
		RandomFormat format = new RandomFormat();
		for(int i=0;i!=20;i++) {
			format.display(loc == null ? entity.getLocation() : loc, i);
		}
	}
}

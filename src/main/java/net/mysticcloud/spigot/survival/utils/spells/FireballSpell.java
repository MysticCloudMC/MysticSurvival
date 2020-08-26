package net.mysticcloud.spigot.survival.utils.spells;

import org.bukkit.Particle;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;

import net.mysticcloud.spigot.core.utils.particles.formats.RandomFormat;

public class FireballSpell extends Spell {
	
	
	public FireballSpell(LivingEntity entity) {
		this.entity = entity;
		cost = 100;
	}
	
	@Override
	public void activate() {
		Fireball f = entity.getWorld().spawn(entity.getEyeLocation().add(0,1,0), Fireball.class);
		f.setVelocity(entity.getEyeLocation().getDirection());
	}

}

package net.mysticcloud.spigot.survival.utils.spells;

import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;

import net.mysticcloud.spigot.core.utils.particles.formats.RandomFormat;

public class HealSpell extends Spell {
	
	LivingEntity entity;
	
	public HealSpell(LivingEntity entity) {
		this.entity = entity;
	}
	
	@Override
	public void activate() {
		entity.setHealth(entity.getMaxHealth());
		RandomFormat format = new RandomFormat();
		format.particle(Particle.COMPOSTER);
		for(int i=0;i!=20;i++) {
			format.display(entity.getLocation(), i);
		}
	}

}

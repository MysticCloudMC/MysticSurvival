package net.mysticcloud.spigot.survival.utils.spells;

import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;

public class FlameSpell extends Spell {

	public FlameSpell(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void activate() {
		entity.getWorld().spawnParticle(Particle.FLAME, entity.getEyeLocation(), 0,
				entity.getEyeLocation().getDirection().getX(), entity.getEyeLocation().getDirection().getY(),
				entity.getEyeLocation().getDirection().getZ(),2);
	}

}

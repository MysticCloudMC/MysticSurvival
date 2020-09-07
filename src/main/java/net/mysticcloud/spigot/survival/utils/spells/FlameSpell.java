package net.mysticcloud.spigot.survival.utils.spells;

import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;

import net.mysticcloud.spigot.core.utils.CoreUtils;

public class FlameSpell extends Spell {

	public FlameSpell(LivingEntity entity) {
		this.entity = entity;
		cost = 2;
	}

	@Override
	public void activate() {
		for (int i = 0; i != 20; i++) {
			entity.getWorld().spawnParticle(Particle.FLAME, entity.getEyeLocation().add(0, -0.5, 0), 0,
					entity.getEyeLocation().getDirection().getX()
							+ ((CoreUtils.getRandom().nextDouble() * (CoreUtils.getRandom().nextBoolean() ? -1 : 1))/8),
					entity.getEyeLocation().getDirection().getY()
							+ ((CoreUtils.getRandom().nextDouble() * (CoreUtils.getRandom().nextBoolean() ? -1 : 1))/8),
					entity.getEyeLocation().getDirection().getZ()
							+ ((CoreUtils.getRandom().nextDouble() * (CoreUtils.getRandom().nextBoolean() ? -1 : 1))/8),
					CoreUtils.getRandom().nextDouble()/1.5);
		}
	}

}

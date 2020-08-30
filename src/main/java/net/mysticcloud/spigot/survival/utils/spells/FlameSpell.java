package net.mysticcloud.spigot.survival.utils.spells;

import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import net.mysticcloud.spigot.core.utils.CoreUtils;

public class FlameSpell extends Spell {

	public FlameSpell(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void activate() {
		for (int i = 0; i != 20; i++) {
			entity.getWorld().spawnParticle(Particle.FLAME, entity.getEyeLocation(), 0,
					entity.getEyeLocation().getDirection().getX()
							+ ((CoreUtils.getRandom().nextDouble() * (CoreUtils.getRandom().nextBoolean() ? -1 : 1))/2),
					entity.getEyeLocation().getDirection().getY()
							+ ((CoreUtils.getRandom().nextDouble() * (CoreUtils.getRandom().nextBoolean() ? -1 : 1))/2),
					entity.getEyeLocation().getDirection().getZ()
							+ ((CoreUtils.getRandom().nextDouble() * (CoreUtils.getRandom().nextBoolean() ? -1 : 1))/2),
					1 + CoreUtils.getRandom().nextDouble());
		}
	}

}

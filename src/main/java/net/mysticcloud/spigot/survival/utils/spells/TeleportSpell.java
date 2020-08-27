package net.mysticcloud.spigot.survival.utils.spells;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;

import net.mysticcloud.spigot.core.utils.particles.ParticleFormat;
import net.mysticcloud.spigot.core.utils.particles.formats.RandomFormat;

public class TeleportSpell extends Spell {
	
	public TeleportSpell(LivingEntity entity, Location loc) {
		this.entity = entity;
		this.loc = loc;
		cost = 50;
	}
	
	@Override
	public void activate() {
		ParticleFormat format = new RandomFormat();
		format.particle(Particle.SPELL_WITCH);
		for(int i=0;i!=60;i++) {
			format.display(entity.getLocation(), i);
			format.display(loc, i);
		}
		entity.teleport(loc);
	}

}

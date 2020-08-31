package net.mysticcloud.spigot.survival.utils.perks;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;

import net.mysticcloud.spigot.core.utils.particles.ParticleFormat;
import net.mysticcloud.spigot.core.utils.particles.formats.RandomFormat;

public class MagePerkSwap extends MagePerk {
	
	LivingEntity target = null;

	public MagePerkSwap(UUID uid) {
		super(uid);
	}
	
	public void setTarget(LivingEntity target) {
		this.target = target;
	}
	
	@Override
	public void activate() {
		Location loc = target.getLocation().clone();
		ParticleFormat format = new RandomFormat();
		format.particle(Particle.SPELL_WITCH);
		for(int i=0;i!=60;i++) {
			format.display(getPlayer().getLocation(), i);
			format.display(loc, i);
		}
		target.teleport(getPlayer());
		getPlayer().teleport(loc);
	}

}

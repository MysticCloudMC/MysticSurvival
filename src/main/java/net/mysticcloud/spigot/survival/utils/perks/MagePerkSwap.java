package net.mysticcloud.spigot.survival.utils.perks;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;

import net.mysticcloud.spigot.core.utils.particles.ParticleFormat;
import net.mysticcloud.spigot.core.utils.particles.formats.RandomFormat;

public class MagePerkSwap extends MagePerk {

	public MagePerkSwap(UUID uid) {
		super(uid);
		reqs = new String[] { "Target (LivingEntity)" };
		ready = false;
	}

	@Override
	public void setTarget(LivingEntity target) {
		this.target = target;
		ready = true;
	}

	@Override
	public String[] getRequirements() {
		List<String> req = new ArrayList<>();
		if (target == null) {
			req.add("Target (LivingEntity)");
		}
		String str[] = new String[req.size()];
		for (int j = 0; j < req.size(); j++)
			str[j] = req.get(j);
		return str;
	}

	@Override
	public void activate() {
		Location loc = target.getLocation().clone();
		ParticleFormat format = new RandomFormat();
		format.particle(Particle.SPELL_WITCH);
		for (int i = 0; i != 60; i++) {
			format.display(getPlayer().getLocation(), i);
			format.display(loc, i);
		}
		target.teleport(getPlayer());
		getPlayer().teleport(loc);
	}

}

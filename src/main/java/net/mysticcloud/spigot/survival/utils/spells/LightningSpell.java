package net.mysticcloud.spigot.survival.utils.spells;

import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import net.mysticcloud.spigot.core.utils.CoreUtils;

public class LightningSpell extends Spell {

	int scalar = 16;

	public LightningSpell(LivingEntity entity) {
		this.entity = entity;
		cost = 2;
	}

	@Override
	public void activate() {

		LinkedList<Vector> points = new LinkedList<>();
		points.add(new Vector(entity.getEyeLocation().getX(), entity.getEyeLocation().getY(),
				entity.getEyeLocation().getZ()));
		for (int f = 0; f != 5; f++) {
			for (int i = 0; i != 10 * scalar; i++) {
				Vector point = points.get(points.size() - 1).clone();
				point.add(entity.getEyeLocation().getDirection().multiply((double) 1 / scalar));
				point.setX(point.getX() + (CoreUtils.getRandom().nextDouble()
						* (CoreUtils.getRandom().nextBoolean() ? (double) 1 / scalar : -(double) 1 / scalar)));
				point.setY(point.getY() + (CoreUtils.getRandom().nextDouble()
						* (CoreUtils.getRandom().nextBoolean() ? (double) 1 / scalar : -(double) 1 / scalar)));
				point.setZ(point.getZ() + (CoreUtils.getRandom().nextDouble()
						* (CoreUtils.getRandom().nextBoolean() ? (double) 1 / scalar : -(double) 1 / scalar)));
				points.add(point);
			}
		}
		for (Vector vec : points) {
			entity.getWorld().spawnParticle(Particle.END_ROD,
					new Location(entity.getWorld(), vec.getX(), vec.getY(), vec.getZ()), 0);
		}
	}

}

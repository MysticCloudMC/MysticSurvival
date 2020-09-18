package net.mysticcloud.spigot.survival.utils.spells;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

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
		HashMap<Integer, LinkedList<Vector>> points = new HashMap<>();
		points.put(0, new LinkedList<Vector>());
		points.get(0).add(new Vector(entity.getEyeLocation().getX(), entity.getEyeLocation().getY(),
				entity.getEyeLocation().getZ()));
		for (int branch = 0; branch != points.size(); branch++) {
			for (int i = 0; i != 10 * scalar; i++) {
				Vector point = points.get(branch).get(points.get(branch).size() - 1).clone();
				point.add(entity.getEyeLocation().getDirection().multiply((double) 1 / scalar));
				point.setX(point.getX() + (CoreUtils.getRandom().nextDouble()
						* (CoreUtils.getRandom().nextBoolean() ? (double) 1 / scalar : -(double) 1 / scalar)));
				point.setY(point.getY() + (CoreUtils.getRandom().nextDouble()
						* (CoreUtils.getRandom().nextBoolean() ? (double) 1 / scalar : -(double) 1 / scalar)));
				point.setZ(point.getZ() + (CoreUtils.getRandom().nextDouble()
						* (CoreUtils.getRandom().nextBoolean() ? (double) 1 / scalar : -(double) 1 / scalar)));
				points.get(branch).add(point);
				if (CoreUtils.getRandom().nextDouble() <= 0.04) {
					
					LinkedList<Vector> randomBranch = points.get(CoreUtils.getRandom().nextInt(points.size()));
					Vector npoint = randomBranch.get(randomBranch.size() - 1).clone();
					points.put(points.size(), new LinkedList<Vector>());
					points.get(points.size() - 1).add(npoint);
				}
			}
		}

		for (Entry<Integer, LinkedList<Vector>> entry : points.entrySet()) {
			for (Vector v : entry.getValue()) {

				entity.getWorld().spawnParticle(Particle.END_ROD,
						new Location(entity.getWorld(), v.getX(), v.getY(), v.getZ()), 0);

			}
		}
	}

}

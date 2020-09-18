package net.mysticcloud.spigot.survival.utils.spells;

import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class LightningSpell extends Spell {

	public LightningSpell(LivingEntity entity) {
		this.entity = entity;
		cost = 2;
	}

	@Override
	public void activate() {
		LinkedList<Vector> points = new LinkedList<>();
		points.add(new Vector(entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ()));
		for (int i = 0; i != 100; i++) {
			Vector point = points.get(points.size()-1);
			point.add(entity.getEyeLocation().getDirection());
			
//			entity.getWorld().spawnParticle(Particle.FLAME, entity.getEyeLocation().add(0, -0.5, 0), 0,
//					entity.getEyeLocation().getDirection().getX()
//							+ ((CoreUtils.getRandom().nextDouble() * (CoreUtils.getRandom().nextBoolean() ? -1 : 1))/8),
//					entity.getEyeLocation().getDirection().getY()
//							+ ((CoreUtils.getRandom().nextDouble() * (CoreUtils.getRandom().nextBoolean() ? -1 : 1))/8),
//					entity.getEyeLocation().getDirection().getZ()
//							+ ((CoreUtils.getRandom().nextDouble() * (CoreUtils.getRandom().nextBoolean() ? -1 : 1))/8),
//					CoreUtils.getRandom().nextDouble()/1.5);
		}
		for(Vector vec : points) {
			entity.getWorld().spawnParticle(Particle.END_ROD, new Location(entity.getWorld(), vec.getX(), vec.getY(), vec.getZ()),0);
		}
	}

}

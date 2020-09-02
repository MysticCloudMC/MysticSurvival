package net.mysticcloud.spigot.survival.utils.spells;

import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;

public class FireballSpell extends Spell {

	public FireballSpell(LivingEntity entity) {
		this.entity = entity;
		cost = 20;
	}

	@Override
	public void activate() {
		DragonFireball f = entity.getWorld().spawn(entity.getEyeLocation().add(0, 1, 0), DragonFireball.class);
		f.setVelocity(entity.getEyeLocation().getDirection());
	}

}

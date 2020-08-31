package net.mysticcloud.spigot.survival.runnables;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import net.mysticcloud.spigot.survival.utils.SurvivalUtils;

public class SeekerArrowRunnable implements Runnable {

	Arrow arrow;
	LivingEntity target;

	public SeekerArrowRunnable(Arrow arrow, LivingEntity target) {
		this.arrow = arrow;
		this.target = target;
	}

	@Override
	public void run() {
		if (!arrow.isDead()) {

			arrow.setVelocity(new Vector(((arrow.getLocation().getX() - target.getLocation().getX())),
					((arrow.getLocation().getY() - target.getLocation().getY())),
					((arrow.getLocation().getZ() - target.getLocation().getZ()))));
			Bukkit.getScheduler().runTaskLater(SurvivalUtils.getPlugin(), this, 1);
		}

	}

}

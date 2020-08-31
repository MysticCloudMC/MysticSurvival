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
			arrow.setVelocity(
					new Vector(Math.sqrt(Math.pow(target.getLocation().getX() - arrow.getLocation().getX(), 2)),
							Math.sqrt(Math.pow(target.getLocation().getY() - arrow.getLocation().getY(), 2)),
							Math.sqrt(Math.pow(target.getLocation().getZ() - arrow.getLocation().getZ(), 2))));
			Bukkit.getScheduler().runTaskLater(SurvivalUtils.getPlugin(), this, 1);
		}

	}

}

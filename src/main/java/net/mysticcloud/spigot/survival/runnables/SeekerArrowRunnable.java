package net.mysticcloud.spigot.survival.runnables;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import net.mysticcloud.spigot.survival.utils.SurvivalUtils;

public class SeekerArrowRunnable implements Runnable {

	Arrow arrow;
	LivingEntity target;

	public SeekerArrowRunnable(Arrow arrow, LivingEntity target) {
		this.arrow = arrow;
		this.target = target;
	}

	public void run() {
		
		
		if (this.arrow.isOnGround() || this.arrow.isDead() || this.target.isDead()) {
			return;
		}
		Vector toTarget = this.target.getLocation().clone().add(new Vector(0.0D, 0.5D, 0.0D))
				.subtract(this.arrow.getLocation()).toVector();
		Vector dirVelocity = this.arrow.getVelocity().clone().normalize();
		Vector dirToTarget = toTarget.clone().normalize();
		double angle = dirVelocity.angle(dirToTarget);
		double speed = this.arrow.getVelocity().length();
		double newSpeed = 0.9D * speed + 0.14D;
		Vector newVelocity;
		if (angle < 0.12D) {
			newVelocity = dirVelocity.clone().multiply(newSpeed);
		} else {
			Vector newDir = dirVelocity.clone().multiply((angle - 0.12) / angle)
					.add(dirToTarget.clone().multiply(0.12 / angle));
			newDir.normalize();
			newVelocity = newDir.clone().multiply(newSpeed);
		}
		this.arrow.setVelocity(newVelocity.add(new Vector(0.0D, 0.03D, 0.0D)));
		Bukkit.getScheduler().runTaskLater(SurvivalUtils.getPlugin(), new SeekerArrowRunnable(arrow, target), 1);
	}
}

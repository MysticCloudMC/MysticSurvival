package net.mysticcloud.spigot.survival.utils.spells;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import net.mysticcloud.spigot.core.utils.particles.ParticleFormat;
import net.mysticcloud.spigot.core.utils.particles.formats.RandomFormat;
import net.mysticcloud.spigot.survival.utils.SurvivalUtils;

public class InvisibilitySpell extends Spell {

	public InvisibilitySpell(LivingEntity entity) {
		this.entity = entity;
		cost = 200;
	}

	@Override
	public void activate() {
		ParticleFormat format = new RandomFormat();
		format.particle(Particle.ASH);
		for (int i = 0; i != 100; i++) {
			format.display(entity.getLocation(), i);
		}
		if (entity instanceof Player) {
			Player player = (Player) entity;
			for (Player p : Bukkit.getOnlinePlayers()) {
				if(!p.equals(player))p.hidePlayer(player);
			}
			Bukkit.getScheduler().runTaskLater(SurvivalUtils.getPlugin(), new Runnable() {

				@Override
				public void run() {
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.showPlayer(player);
					}
				}
			}, 5 * 20);
		}

	}

}

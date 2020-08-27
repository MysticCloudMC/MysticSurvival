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
				p.spigot().getHiddenPlayers().add(player);
			}
			Bukkit.getScheduler().runTaskLater(SurvivalUtils.getPlugin(), new Runnable() {

				@Override
				public void run() {
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.spigot().getHiddenPlayers().remove(player);
					}
				}
			}, 5 * 20);
		}

	}

}

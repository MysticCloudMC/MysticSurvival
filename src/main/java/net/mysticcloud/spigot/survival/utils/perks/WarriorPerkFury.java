package net.mysticcloud.spigot.survival.utils.perks;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.mysticcloud.spigot.core.utils.CoreUtils;

public class WarriorPerkFury extends WarriorPerk {

	public WarriorPerkFury(UUID uid) {
		super(uid);
	}

	@Override
	public void activate() {
		Firework fw = (Firework) Bukkit.getPlayer(uid).getWorld()
				.spawnEntity(Bukkit.getPlayer(uid).getLocation().add(0, 1, 0), EntityType.FIREWORK);
		FireworkMeta fwm = fw.getFireworkMeta();

		fwm.setPower(2);
		fwm.addEffect(FireworkEffect.builder().withColor(Color.RED).flicker(true).build());

		fw.setFireworkMeta(fwm);
		fw.detonate();

		Bukkit.getPlayer(uid).addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 5 * 20, 20));

	}

}

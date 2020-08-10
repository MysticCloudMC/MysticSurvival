package net.mysticcloud.spigot.survival.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.core.utils.MysticPlayer;
import net.mysticcloud.spigot.survival.MysticSurvival;
import net.mysticcloud.spigot.survival.utils.SurvivalUtils;

public class PlayerListener implements Listener {

	public PlayerListener(MysticSurvival plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		if(e.getEntity() instanceof Monster && e.getEntity().getKiller() != null && e.getEntity().hasMetadata("level")) {
			int level = (int) e.getEntity().getMetadata("level").get(0).value();
			CoreUtils.getMysticPlayer(e.getEntity().getKiller()).gainXP((double)level/100);
			//Drops?
			if(level > 5) {
				e.getDrops().add(new ItemStack(Material.DIAMOND));
			}
		}
	}
	
	@EventHandler
	public void onPlayerAttack(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Monster && e.getDamager() instanceof Player) {
			e.setDamage((e.getDamage()+CoreUtils.getMysticPlayer(((Player)e.getDamager())).getLevel()/2));
		}
	}

	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent e) {
		if (e.getEntity() instanceof Monster) {
			MysticPlayer player = null;
			for (Entity entity : e.getEntity().getNearbyEntities(50, 50, 50)) {
				if (entity instanceof Player) {
					player = CoreUtils.getMysticPlayer(entity.getUniqueId());
					break;
				}
			}
			if (player == null) {
				e.setCancelled(true);
				return;
			}
			String color = "&2";
			int level = CoreUtils.getRandom().nextInt(7) + (player.getLevel() - 5);
			if (level < 1)
				level = 1;

			switch (e.getEntity().getType()) {
			case ZOMBIE:
				color = "&a";
				break;
			case SKELETON:
				color = "&c";
			case DROWNED:
				color = "&a";
				break;
			case CREEPER:
				color = "&2";
				break;
			case BLAZE:
				color = "&6";
				break;
			case ENDERMAN:
				color = "&5";
				break;
			case CAVE_SPIDER:
				color = "&1";
				break;
			case SPIDER:
				color = "&1";
				break;
			default:

				break;
			}
			String name = CoreUtils.colorize(
					"&7[" + level + "] " + color + "&l" + e.getEntity().getName().substring(0, 1).toUpperCase()
							+ e.getEntity().getName().substring(1, e.getEntity().getName().length()).toLowerCase());
			Monster mon = (Monster) e.getEntity();
			mon.setMetadata("level", new FixedMetadataValue(SurvivalUtils.getPlugin(), level));
			mon.setMaxHealth(mon.getHealth() + (player.getLevel() * 1.5));
			mon.setHealth(mon.getMaxHealth());
			e.getEntity().setCustomName(name);

		}
	}

}

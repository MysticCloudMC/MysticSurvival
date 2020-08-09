package net.mysticcloud.spigot.survival.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.core.utils.MysticPlayer;
import net.mysticcloud.spigot.survival.MysticSurvival;

public class PlayerListener implements Listener {

	public PlayerListener(MysticSurvival plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent e) {
		if (e.getEntity() instanceof Monster) {
			String name = e.getEntity().getName().substring(0, 1).toUpperCase()
					+ e.getEntity().getName().substring(1, e.getEntity().getName().length()).toLowerCase();
			e.getEntity().setCustomName(CoreUtils.colorize("&8" + name));
			e.getEntity().setCustomNameVisible(true);
			MysticPlayer player = null;
			for(Entity entity : e.getEntity().getNearbyEntities(50, 50, 50)) {
				if(entity instanceof Player) {
					player = CoreUtils.getMysticPlayer(entity.getUniqueId());
					break;
				}
			}
			if(player == null) {
				e.setCancelled(true);
				return;
			}
			e.getEntity().setCustomName(CoreUtils.colorize("&7[" + player.getLevel() + "] &2&l" + name));
			
		}
	}

}

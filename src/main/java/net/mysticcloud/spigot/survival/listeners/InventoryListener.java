package net.mysticcloud.spigot.survival.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import net.mysticcloud.spigot.core.utils.GUIManager;
import net.mysticcloud.spigot.survival.MysticSurvival;
import net.mysticcloud.spigot.survival.utils.SurvivalUtils;

public class InventoryListener implements Listener {

	public InventoryListener(MysticSurvival plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if(GUIManager.getOpenInventory((Player) e.getWhoClicked()).equalsIgnoreCase("CraftingBench")) {
			if(e.getCurrentItem() == null) return;
			if(e.getCurrentItem().getType().equals(Material.AIR)) return;
			if (e.getCurrentItem().getType().equals(Material.BLACK_STAINED_GLASS_PANE)) {
				e.setCancelled(true);
				return;
			}
			if(e.getCurrentItem().getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
				e.setCancelled(true);
				SurvivalUtils.craft(e.getClickedInventory());
				return;
			}
			
		}
		try {
			if (e.getCurrentItem().getType().equals(Material.BOOK) && e.getCursor() != null
					&& !e.getCursor().getType().equals(Material.BOOK)
					&& !e.getCursor().getType().equals(Material.AIR)) {
				SurvivalUtils.enhanceInInventory(e.getCursor(), e.getCurrentItem());
				Bukkit.getScheduler().runTaskLater(SurvivalUtils.getPlugin(), new Runnable() {
					@Override
					public void run() {
						e.getCursor().setAmount(e.getCursor().getAmount() - 1);
					}
				}, 10);
			}
		} catch (NullPointerException ex) {

		}
	}


}

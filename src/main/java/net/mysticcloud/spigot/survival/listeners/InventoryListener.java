package net.mysticcloud.spigot.survival.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import net.mysticcloud.spigot.core.utils.GUIManager;
import net.mysticcloud.spigot.survival.MysticSurvival;
import net.mysticcloud.spigot.survival.utils.Division;
import net.mysticcloud.spigot.survival.utils.InventoryUtils;

public class InventoryListener implements Listener {

	public InventoryListener(MysticSurvival plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (GUIManager.getOpenInventory((Player) e.getWhoClicked()).equalsIgnoreCase("Perks")) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null)
				return;
			if (e.getCurrentItem().getType().equals(Material.AIR)
					|| e.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE))
				return;
			Bukkit.broadcastMessage(e.getCurrentItem().getType().name());
			for(Division div : Division.values()) {
				if(e.getCurrentItem().getType().equals(div.getGUIItem())) {
					InventoryUtils.openPerksMenu((Player)e.getWhoClicked(), div);
					return;
				}
			}
		}

		if (GUIManager.getOpenInventory((Player) e.getWhoClicked()).equalsIgnoreCase("Menu")) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null)
				return;
			if (e.getCurrentItem().getType().equals(Material.AIR))
				return;
			if (e.getCurrentItem().getType().equals(Material.CRAFTING_TABLE)) {
				InventoryUtils.openCraftingBench((Player) e.getWhoClicked());
			}
			if (e.getCurrentItem().getType().equals(Material.NETHER_STAR)) {
				InventoryUtils.openPerksMenu((Player) e.getWhoClicked());
			}
		}
		if (GUIManager.getOpenInventory((Player) e.getWhoClicked()).equalsIgnoreCase("CraftingBench")) {
			if (e.getCurrentItem() == null)
				return;
			if (e.getCurrentItem().getType().equals(Material.AIR))
				return;
			if (e.getCurrentItem().getType().equals(Material.BLACK_STAINED_GLASS_PANE)) {
				e.setCancelled(true);
				return;
			}
			if (e.getCurrentItem().getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
				e.setCancelled(true);
				InventoryUtils.craft(e.getClickedInventory());
				return;
			}

		}
	}

}

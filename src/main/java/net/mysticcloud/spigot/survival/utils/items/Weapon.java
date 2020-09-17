package net.mysticcloud.spigot.survival.utils.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.mysticcloud.spigot.survival.utils.Enhancement;
import net.mysticcloud.spigot.survival.utils.Tier;

public class Weapon extends Item {

	Tier tier;

	int weight;

	public Weapon(Material material, int level) {
		super(material, level);
		tier = Tier.getTier(level);
		item = new ItemStack(material);
		generateInfo(material, level);
	}

	public Weapon(ItemStack item) {
		super(item);
		this.item = item;
		if (!item.hasItemMeta()) {
			generateInfo(item.getType(), 1);
			return;
		}

		if (!item.getItemMeta().hasLore()) {
			generateInfo(item.getType(), 1);
			return;
		}
		if (!item.hasItemMeta()) {
			generateInfo(item.getType(), 1);
			return;
		}
		name = item.getItemMeta().getDisplayName();
		String a = "";
		for (String s : item.getItemMeta().getLore()) {
			a = ChatColor.stripColor(s);
			if (a.contains(":")) {
				if (a.contains("Durability:")) {
					durability = Integer.parseInt(a.split(": ")[1].split("/")[0]);
					maxDurability = Integer.parseInt(a.split(": ")[1].split("/")[1]);
				}
				if (a.contains("Speed:"))
					speed = Integer.parseInt(a.split(": ")[1]);
				if (a.contains("Weight:"))
					weight = Integer.parseInt(a.split(": ")[1]);
				if (a.contains("Damage:"))
					damage = Integer.parseInt(a.split(": ")[1]);
				if (a.contains("Disarm Chance:"))
					enhancements.put(Enhancement.DISARM, Integer.parseInt(a.split(": ")[1]));
				if (a.contains("Fire Damage:"))
					enhancements.put(Enhancement.FIRE, Integer.parseInt(a.split(": ")[1]));
				if (a.contains("Frost Damage:"))
					enhancements.put(Enhancement.FROST, Integer.parseInt(a.split(": ")[1]));
				if (a.contains("Dodge Chance:"))
					enhancements.put(Enhancement.DODGE, Integer.parseInt(a.split(": ")[1]));
				if (a.contains("Vampirism Chance:"))
					enhancements.put(Enhancement.VAMPIRISM, Integer.parseInt(a.split(": ")[1]));
				if (a.contains("Speed Modifier:"))
					enhancements.put(Enhancement.SPEED, Integer.parseInt(a.split(": ")[1]));
				if (a.contains("Armor Modifier:"))
					enhancements.put(Enhancement.PROTECTION, Integer.parseInt(a.split(": ")[1]));

				if (a.contains("Tier:")) {
					for (Tier tier : Tier.values()) {
						if (ChatColor.stripColor(tier.getName()).equals(a.split(": ")[1])) {
							this.tier = tier;
						}
					}
				}
				if (tier == null) {
					tier = Tier.FIRST;
				}
			}
		}
		finalizeEnhancements();

	}

}

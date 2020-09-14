package net.mysticcloud.spigot.survival.utils.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.survival.utils.Enhancement;
import net.mysticcloud.spigot.survival.utils.Tier;

public class Item {

	Tier tier;
	int speed;
	int durability;
	int maxDurability;
	int damage;
	int weight;
	Map<Enhancement, Integer> enhancements = new HashMap<>();
	String name;
	ItemStack item;

	public Item(Material material, int level) {
		tier = Tier.getTier(level);
		item = new ItemStack(material);
		generateInfo(material, level);
	}

	public Item(ItemStack item) {
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
					for(Tier tier : Tier.values()) {
						if(ChatColor.stripColor(tier.getName()).equals(a.split(": ")[1])) {
							this.tier = tier;
						}
					}
				}
				if(tier == null) {
					tier = Tier.FIRST;
				}
			}
		}
		finalizeEnhancements();

	}

	private void generateInfo(Material type, int level) {
		name = ItemUtils.getWeaponDescriptor(tier) + " " + ItemUtils.getWeaponType(item.getType());
		ItemMeta a = item.getItemMeta();
		List<String> lore = a.hasLore() ? a.getLore() : new ArrayList<String>();
		setDamage((int) ((level * CoreUtils.getRandom().nextDouble()) + CoreUtils.getRandom().nextInt(5)));
		setSpeed((int) ((level * CoreUtils.getRandom().nextDouble()) + CoreUtils.getRandom().nextInt(5)));
		durability = (int) (((level * CoreUtils.getRandom().nextDouble()) + CoreUtils.getRandom().nextInt(5)) * 10.0);
		maxDurability = durability;
		AttributeModifier at = new AttributeModifier(UUID.randomUUID(), "Attack Damage", damage, Operation.ADD_NUMBER,
				EquipmentSlot.HAND);
		a.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, at);
		AttributeModifier sp = new AttributeModifier(UUID.randomUUID(), "Attack Speed", speed, Operation.ADD_NUMBER,
				EquipmentSlot.HAND);
		a.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, sp);
		lore.add(CoreUtils.colorize("&7Tier: " + tier.getName()));
		lore.add(CoreUtils.colorize("&7Damage: " + damage));
		lore.add(CoreUtils.colorize("&7Speed: " + speed));
		lore.add(ItemUtils.getDurabilityString(durability, durability));
		lore.add(CoreUtils.colorize("&7------------------"));
		a.setLore(lore);
		a.setDisplayName(CoreUtils.colorize("&f" + name));
		a.addItemFlags(ItemFlag.values());
		item.setItemMeta(a);
	}

	public void enhance(Enhancement enhance, int power) {
		ItemMeta tm = item.getItemMeta();
		List<String> tlore = new ArrayList<>();
		if (item.hasItemMeta()) {
			if (item.getItemMeta().hasLore()) {
				for (String s : item.getItemMeta().getLore()) {
					if (s.contains(":")) {
						if (ChatColor.stripColor(s).split(":")[0]
								.equals(ChatColor.stripColor(enhance.getName()).split(":")[0])) {
							continue;
						}
					}
					tlore.add(s);
				}
			}
		}

		tlore.add(enhance.getName() + power);

		tm.setLore(tlore);

		item.setItemMeta(tm);

		enhancements.put(enhance, power);

		finalizeEnhancements();

	}

	private void setDamage(int damage) {
		this.damage = damage;
		ItemMeta tm = item.getItemMeta();
		tm.removeAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE);
		AttributeModifier at = new AttributeModifier(UUID.randomUUID(), "Attack Damage", damage, Operation.ADD_NUMBER,
				EquipmentSlot.HAND);
		tm.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, at);

		item.setItemMeta(tm);
	}

	private void setSpeed(int speed) {
		this.speed = speed;
		ItemMeta tm = item.getItemMeta();
		tm.removeAttributeModifier(Attribute.GENERIC_ATTACK_SPEED);
		AttributeModifier sp = new AttributeModifier(UUID.randomUUID(), "Attack Speed", speed, Operation.ADD_NUMBER,
				EquipmentSlot.HAND);
		tm.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, sp);

		item.setItemMeta(tm);
	}

	private void finalizeEnhancements() {
		ItemMeta tm = item.getItemMeta();
		AttributeModifier am;
		for (Entry<Enhancement, Integer> entry : enhancements.entrySet()) {
			switch (entry.getKey()) {
			case SPEED:
				tm.removeAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED);
				am = new AttributeModifier(UUID.randomUUID(),
						ItemUtils.getArmorType(item.getType()) + " Movement Speed", enhancements.get(entry.getKey()),
						Operation.ADD_NUMBER);
				tm.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, am);
				break;
			case PROTECTION:
				tm.removeAttributeModifier(Attribute.GENERIC_ARMOR);
				am = new AttributeModifier(UUID.randomUUID(), ItemUtils.getArmorType(item.getType()) + " Protection",
						((double) enhancements.get(entry.getKey()) / 100), Operation.ADD_NUMBER);
				tm.addAttributeModifier(Attribute.GENERIC_ARMOR, am);
				break;
			default:
				break;

			}
			item.setItemMeta(tm);
			setSpeed(speed);
			setDamage(damage);
		}
	}

	public ItemStack getItem() {
		return item;
	}
	
	public void updateItem(ItemStack item) {
		item.setAmount(this.item.getAmount());
		item.setItemMeta(this.item.getItemMeta());
	}

	public void damage(int o) {
		ItemMeta m = item.getItemMeta();
		List<String> lore = m.getLore();
		Map<String, String> replacements = new HashMap<>();
		for (String s : lore) {
			if (s.contains(":")) {
				if (ChatColor.stripColor(s).contains("Durability")) {
					int dur = Integer.parseInt(ChatColor.stripColor(s).split(": ")[1].split("/")[0]);
					int max = Integer.parseInt(ChatColor.stripColor(s).split(": ")[1].split("/")[1]);
					if (dur - o < 0) {
						item.setAmount(0);
						return;
					}
					replacements.put(s, ItemUtils.getDurabilityString(dur - o, max));
				}
			}
		}
		for (Entry<String, String> entry : replacements.entrySet()) {
			lore.set(lore.indexOf(entry.getKey()), entry.getValue());
		}
		m.setLore(lore);
		item.setItemMeta(m);
	}

	public boolean hasEnhancement(Enhancement enhancement) {
		return enhancements.containsKey(enhancement);
	}

	public int getEnhancementPower(Enhancement enhancement) {
		return enhancements.get(enhancement);
	}

}

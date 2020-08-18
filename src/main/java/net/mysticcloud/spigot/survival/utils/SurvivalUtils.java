package net.mysticcloud.spigot.survival.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.survival.MysticSurvival;

public class SurvivalUtils {
	private static MysticSurvival plugin;

	static Map<Tier, Material[]> weaponTiers = new HashMap<>();
	static Map<Tier, String[]> descriptors = new HashMap<>();
	static List<String> enhancements = new ArrayList<>();
//	static String[] descriptors = new String[] {"Xelphor's", "Shiny", "Swift", "Dull", "Chipped", "Hardy", "Sharp", "Hellish"};

	public static void start(MysticSurvival main) {
		plugin = main;
		CoreUtils.addPrefix("homes", "&a&lHome &7>&e ");
		CoreUtils.addPrefix("survival", "&5&lMystic Survival &7>&f ");
		CoreUtils.coreHandleDamage(false);

		enhancements.add("fire");
		enhancements.add("frost");

		weaponTiers.put(Tier.LOW,
				new Material[] { Material.WOODEN_AXE, Material.WOODEN_SWORD, Material.BOW, Material.STONE_AXE });
		descriptors.put(Tier.LOW, new String[] { "Dull", "Chipped", "Slow", "" });

		weaponTiers.put(Tier.MID,
				new Material[] { Material.STONE_AXE, Material.IRON_SWORD, Material.IRON_AXE, Material.GOLDEN_SWORD });
		descriptors.put(Tier.MID, new String[] { "Swift", "Shiny", "Dented" });

		weaponTiers.put(Tier.HIGH, new Material[] { Material.DIAMOND_AXE, Material.DIAMOND_SWORD, Material.CROSSBOW });
		descriptors.put(Tier.HIGH, new String[] { "Sharp", "Flashing", "Powerful" });

		weaponTiers.put(Tier.EXTREME,
				new Material[] { Material.DIAMOND_AXE, Material.DIAMOND_SWORD, Material.CROSSBOW, Material.TRIDENT });
		descriptors.put(Tier.EXTREME, new String[] { "Hellish", "Heavenly", "Xelphor's", "Satan's" });
	}

	public static MysticSurvival getPlugin() {
		return plugin;
	}

	/*
	 * 
	 * Weapon Generation
	 * 
	 */

	private static String getDescriptor(Tier tier) {
		return descriptors.get(tier)[CoreUtils.getRandom().nextInt(descriptors.get(tier).length)];
	}

	private static String getWeaponType(Material weapon) {
		if (weapon.name().contains("SWORD"))
			return "Sword";
		if (weapon.name().contains("_AXE"))
			return "Axe";
		if (weapon.name().equalsIgnoreCase("TRIDENT"))
			return "Trident";
		if (weapon.name().equalsIgnoreCase("BOW"))
			return "Bow";
		if (weapon.name().equalsIgnoreCase("CROSSBOW"))
			return "Cross Bow";
		return "Stick";
	}

	private static ItemStack randomizeEnhancements(ItemStack item, int level) {
//		boolean enhanced = false;
		Collections.shuffle(enhancements);
		for (String s : enhancements) {
			if (CoreUtils.getRandom().nextBoolean()) {
				if (s.equalsIgnoreCase("fire")) {
					ItemMeta a = item.getItemMeta();
					List<String> lore = a.hasLore() ? a.getLore() : new ArrayList<String>();
					lore.add(CoreUtils.colorize("&cFire&7 Damage: &c&l"
							+ ((int) (level * (1 / CoreUtils.getRandom().nextInt(4))) + 1) + "&7"));
					String name = a.getDisplayName();
					name = CoreUtils.colorize(name + "&f of &cFlame&f");
					a.setLore(lore);
					a.setDisplayName(name);
					item.setItemMeta(a);
				}
				if (s.equalsIgnoreCase("frost")) {
					ItemMeta a = item.getItemMeta();
					List<String> lore = a.hasLore() ? a.getLore() : new ArrayList<String>();
					lore.add(CoreUtils.colorize("&bFrost&7 Damage: &b&l"
							+ ((int) (level * (1 / CoreUtils.getRandom().nextInt(4))) + 1) + "&7"));
					String name = a.getDisplayName();
					name = CoreUtils.colorize(name + "&f of &bFrost&f");
					a.setLore(lore);
					a.setDisplayName(name);
					item.setItemMeta(a);
				}
			}
		}
		return item;
	}

	public static ItemStack weaponGenerator(int level) {

		Tier tier = Tier.getTier(level);
		ItemStack item = new ItemStack(
				weaponTiers.get(tier)[CoreUtils.getRandom().nextInt(weaponTiers.get(tier).length)]);

		String name = getDescriptor(tier) + " " + getWeaponType(item.getType());
		ItemMeta a = item.getItemMeta();
		List<String> lore = a.hasLore() ? a.getLore() : new ArrayList<String>();
		
		
		
		a.setDisplayName(CoreUtils.colorize("&f" + name));
		item.setItemMeta(a);

		item = randomizeEnhancements(item, level);

		return item;
	}

	public static void handleDrops(int level, Location location) {
		List<ItemStack> drops = new ArrayList<>();
		drops.add(weaponGenerator(level));
		for (ItemStack i : drops) {
			location.getWorld().dropItem(location, i);
		}
	}

}

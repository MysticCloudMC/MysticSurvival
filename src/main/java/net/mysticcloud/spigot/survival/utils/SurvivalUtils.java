package net.mysticcloud.spigot.survival.utils;

import java.util.ArrayList;
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

	public static void start(MysticSurvival main) {
		plugin = main;
		CoreUtils.addPrefix("homes", "&a&lHome &7>&e ");
		CoreUtils.addPrefix("survival", "&5&lMystic Survival &7>&f ");
		CoreUtils.coreHandleDamage(false);

		weaponTiers.put(Tier.LOW,
				new Material[] { Material.WOODEN_AXE, Material.WOODEN_SWORD, Material.BOW, Material.STONE_AXE });

		weaponTiers.put(Tier.MID,
				new Material[] { Material.STONE_AXE, Material.IRON_SWORD, Material.IRON_AXE, Material.GOLDEN_SWORD });

		weaponTiers.put(Tier.HIGH, new Material[] { Material.DIAMOND_AXE, Material.DIAMOND_SWORD, Material.CROSSBOW });

		weaponTiers.put(Tier.EXTREME,
				new Material[] { Material.DIAMOND_AXE, Material.DIAMOND_SWORD, Material.CROSSBOW, Material.TRIDENT });
	}

	public static MysticSurvival getPlugin() {
		return plugin;
	}

	public static ItemStack weaponGenerator(int level) {

		boolean mystic = false;

		Tier tier = Tier.getTier(level);
		ItemStack item = new ItemStack(
				weaponTiers.get(tier)[CoreUtils.getRandom().nextInt(weaponTiers.get(tier).length)]);
		ItemMeta a = item.getItemMeta();
		List<String> lore = a.hasLore() ? a.getLore() : new ArrayList<String>();
		if (CoreUtils.getRandom().nextBoolean() && !mystic) {
			
			lore.add(CoreUtils.colorize("&cFire&f Damage: &c&l" + ((int) ( level * (1 / CoreUtils.getRandom().nextInt(4)) )) + "&f"));
		}
		if (CoreUtils.getRandom().nextBoolean() && !mystic) {
			lore.add(CoreUtils.colorize("&bFrost&f Damage: &b&l" + ((int) ( level * (1 / CoreUtils.getRandom().nextInt(4)) )) + "&f"));
		}
		
		a.setLore(lore);
		
		item.setItemMeta(a);

		return item;
	}

	public static void handleDrops(int level, Location location) {
		List<ItemStack> drops = new ArrayList<>();
		drops.add(weaponGenerator(level));
		for(ItemStack i : drops) {
			location.getWorld().dropItem(location, i);
		}
	}

}

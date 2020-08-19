package net.mysticcloud.spigot.survival.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.survival.MysticSurvival;

public class SurvivalUtils {
	private static MysticSurvival plugin;

	static Map<Tier, Material[]> weaponTiers = new HashMap<>();
	static Map<Tier, String[]> weaponDescriptors = new HashMap<>();
	static List<String> weaponEnhancements = new ArrayList<>();

	static Map<Tier, Material[]> armorTiers = new HashMap<>();
	static Map<Tier, String[]> armorDescriptors = new HashMap<>();
	static List<String> armorEnhancements = new ArrayList<>();
//	static String[] descriptors = new String[] {"Xelphor's", "Shiny", "Swift", "Dull", "Chipped", "Hardy", "Sharp", "Hellish"};

	public static void start(MysticSurvival main) {
		plugin = main;
		CoreUtils.addPrefix("homes", "&a&lHome &7>&e ");
		CoreUtils.addPrefix("survival", "&5&lMystic Survival &7>&f ");
		CoreUtils.coreHandleDamage(false);

		weaponEnhancements.add("fire");
		weaponEnhancements.add("frost");
		weaponEnhancements.add("fireball");

		weaponTiers.put(Tier.LOW,
				new Material[] { Material.WOODEN_AXE, Material.WOODEN_SWORD, Material.BOW, Material.STONE_AXE });
		weaponDescriptors.put(Tier.LOW, new String[] { "Dull", "Chipped", "Slow", "" });

		weaponTiers.put(Tier.MID,
				new Material[] { Material.STONE_AXE, Material.IRON_SWORD, Material.IRON_AXE, Material.GOLDEN_SWORD });
		weaponDescriptors.put(Tier.MID, new String[] { "Swift", "Shiny", "Dented" });

		weaponTiers.put(Tier.HIGH, new Material[] { Material.DIAMOND_AXE, Material.DIAMOND_SWORD, Material.CROSSBOW });
		weaponDescriptors.put(Tier.HIGH, new String[] { "Sharp", "Flashing", "Powerful" });

		weaponTiers.put(Tier.EXTREME,
				new Material[] { Material.DIAMOND_AXE, Material.DIAMOND_SWORD, Material.CROSSBOW, Material.TRIDENT });
		weaponDescriptors.put(Tier.EXTREME, new String[] { "Hellish", "Heavenly", "Xelphor's", "Satan's" });

		armorEnhancements.add("speed");
		armorEnhancements.add("armor");

		armorTiers.put(Tier.LOW, new Material[] { Material.LEATHER_BOOTS, Material.LEATHER_CHESTPLATE,
				Material.LEATHER_HELMET, Material.LEATHER_LEGGINGS });
		armorDescriptors.put(Tier.LOW, new String[] { "Ripped", "Torn", "Dirty", "" });

		armorTiers.put(Tier.MID, new Material[] { Material.CHAINMAIL_BOOTS, Material.IRON_CHESTPLATE,
				Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_HELMET });
		armorDescriptors.put(Tier.MID, new String[] { "Protective", "Shiny", "Dented" });

		armorTiers.put(Tier.HIGH, new Material[] { Material.DIAMOND_HELMET, Material.IRON_CHESTPLATE,
				Material.IRON_HELMET, Material.IRON_LEGGINGS, Material.IRON_BOOTS });
		armorDescriptors.put(Tier.HIGH, new String[] { "Strong", "Glistening", "Holy" });

		armorTiers.put(Tier.EXTREME, new Material[] { Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE,
				Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS });
		armorDescriptors.put(Tier.EXTREME, new String[] { "Hellish", "Heavenly", "Xelphor's", "Satan's" });

	}

	public static MysticSurvival getPlugin() {
		return plugin;
	}

	/*
	 * 
	 * Weapon and Armor Generation
	 * 
	 */

	private static String getWeaponDescriptor(Tier tier) {
		return weaponDescriptors.get(tier)[CoreUtils.getRandom().nextInt(weaponDescriptors.get(tier).length)];
	}
	
	private static String getArmorDescriptor(Tier tier) {
		return armorDescriptors.get(tier)[CoreUtils.getRandom().nextInt(armorDescriptors.get(tier).length)];
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
	private static String getArmorType(Material armor) {
		if (armor.name().contains("HELMET"))
			return "Helmet";
		if (armor.name().contains("CHESTPLATE"))
			return "Chestplate";
		if (armor.name().contains("LEGGINGS"))
			return "Pants";
		if (armor.name().contains("BOOTS"))
			return "Boots";
		
		return "Stick";
	}

	private static ItemStack randomizeWeaponEnhancements(ItemStack item, int level) {
		boolean enhanced = false;

		Collections.shuffle(weaponEnhancements);
		for (String s : weaponEnhancements) {

			if (CoreUtils.getRandom().nextBoolean()) {
				if (s.equalsIgnoreCase("fireball")) {
					ItemMeta a = item.getItemMeta();
					List<String> lore = a.hasLore() ? a.getLore() : new ArrayList<String>();
					lore.add(CoreUtils.colorize("&6Fireball&7 Damage: &c&l"
							+ ((int) (level * (1 / CoreUtils.getRandom().nextInt(3)+1)) + 1) + "&7"));
					a.setLore(lore);
					a.setDisplayName(CoreUtils
							.colorize(a.getDisplayName() + "&f " + (enhanced ? "and" : "of") + " &6Fireballs&f"));
					AttributeModifier am = new AttributeModifier(UUID.randomUUID(), "Attack Speed", 0.001,
							Operation.ADD_NUMBER, EquipmentSlot.HAND);
					a.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, am);
					item.setItemMeta(a);
					enhanced = true;
				}
				if (s.equalsIgnoreCase("fire")) {
					ItemMeta a = item.getItemMeta();
					List<String> lore = a.hasLore() ? a.getLore() : new ArrayList<String>();
					lore.add(CoreUtils.colorize("&cFire&7 Damage: &c&l"
							+ ((int) (level * (1 / CoreUtils.getRandom().nextInt(3)+1)) + 1) + "&7"));
					a.setLore(lore);
					a.setDisplayName(
							CoreUtils.colorize(a.getDisplayName() + "&f " + (enhanced ? "and" : "of") + " &cFlame&f"));
					item.setItemMeta(a);
					enhanced = true;
				}
				if (s.equalsIgnoreCase("frost")) {
					ItemMeta a = item.getItemMeta();
					List<String> lore = a.hasLore() ? a.getLore() : new ArrayList<String>();
					lore.add(CoreUtils.colorize("&bFrost&7 Damage: &b&l"
							+ ((int) (level * (1 / CoreUtils.getRandom().nextInt(3)+1)) + 1) + "&7"));
					a.setLore(lore);
					a.setDisplayName(
							CoreUtils.colorize(a.getDisplayName() + "&f " + (enhanced ? "and" : "of") + " &bFrost&f"));
					item.setItemMeta(a);
					enhanced = true;
				}
			}
		}
		return item;
	}
	
	private static ItemStack randomizeArmorEnhancements(ItemStack item, int level) {
		boolean enhanced = false;

		Collections.shuffle(armorEnhancements);
		for (String s : armorEnhancements) {

			if (CoreUtils.getRandom().nextBoolean()) {
				if (s.equalsIgnoreCase("speed")) {
					ItemMeta a = item.getItemMeta();
					List<String> lore = a.hasLore() ? a.getLore() : new ArrayList<String>();
					int level2 = ((int) (level * (1 / CoreUtils.getRandom().nextInt(3)+1)) + 1);
					lore.add(CoreUtils.colorize("&aSpeed&7 Modifier: &c&l"
							+ level2));
					a.setLore(lore);
					a.setDisplayName(CoreUtils
							.colorize(a.getDisplayName() + "&f " + (enhanced ? "and" : "of") + " &aSpeed&f"));
					AttributeModifier am = new AttributeModifier(UUID.randomUUID(), getArmorType(item.getType()) + " Movement Speed", ((double)level2/100),
							Operation.ADD_NUMBER);
					a.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, am);
					item.setItemMeta(a);
					enhanced = true;
				}
				if (s.equalsIgnoreCase("armor")) {
					ItemMeta a = item.getItemMeta();
					List<String> lore = a.hasLore() ? a.getLore() : new ArrayList<String>();
					int level2 = ((int) (level * (1 / CoreUtils.getRandom().nextInt(3)+1)) + 1);
					lore.add(CoreUtils.colorize("&dArmor&7 Modifier: &d&l"
							+ level2));
					a.setLore(lore);
					a.setDisplayName(CoreUtils
							.colorize(a.getDisplayName() + "&f " + (enhanced ? "and" : "of") + " &dProtection&f"));
					AttributeModifier am = new AttributeModifier(UUID.randomUUID(), getArmorType(item.getType()) + " Protection", ((double)level2/100),
							Operation.ADD_NUMBER);
					a.addAttributeModifier(Attribute.GENERIC_ARMOR, am);
					item.setItemMeta(a);
					enhanced = true;
				}
			}
		}
		return item;
	}

	public static ItemStack weaponGenerator(int level) {

		Tier tier = Tier.getTier(level);
		ItemStack item = new ItemStack(
				weaponTiers.get(tier)[CoreUtils.getRandom().nextInt(weaponTiers.get(tier).length)]);

		String name = getWeaponDescriptor(tier) + " " + getWeaponType(item.getType());
		ItemMeta a = item.getItemMeta();
		List<String> lore = a.hasLore() ? a.getLore() : new ArrayList<String>();

		double damage = ((int) (level * (1 / CoreUtils.getRandom().nextInt(3)+1)) + 1);
		double speed = ((int) (level * (1 / CoreUtils.getRandom().nextInt(3)+1)) + 1);
		
		AttributeModifier at = new AttributeModifier(UUID.randomUUID(), "Attack Damage",
				damage, Operation.ADD_NUMBER, EquipmentSlot.HAND);
		a.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, at);
		
		AttributeModifier sp = new AttributeModifier(UUID.randomUUID(), "Attack Speed",
				speed, Operation.ADD_NUMBER, EquipmentSlot.HAND);
		a.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, sp);

		lore.add(CoreUtils.colorize("&7Damage: " + ((int)damage)));
		lore.add(CoreUtils.colorize("&7Speed: " + ((int)speed)));
		lore.add(CoreUtils.colorize("&7------------------"));
		a.setLore(lore);

		a.setDisplayName(CoreUtils.colorize("&f" + name));

		a.addItemFlags(ItemFlag.values());
		item.setItemMeta(a);

		item = randomizeWeaponEnhancements(item, level);

		return item;
	}
	
	public static ItemStack armorGenerator(int level) {

		Tier tier = Tier.getTier(level);
		ItemStack item = new ItemStack(
				armorTiers.get(tier)[CoreUtils.getRandom().nextInt(armorTiers.get(tier).length)]);

		String name = getArmorDescriptor(tier) + " " + getArmorType(item.getType());
		ItemMeta a = item.getItemMeta();

		a.setDisplayName(CoreUtils.colorize("&f" + name));

		a.addItemFlags(ItemFlag.values());
		item.setItemMeta(a);
		item = randomizeArmorEnhancements(item, level);
		ItemMeta a2 = item.getItemMeta();
		a2.addItemFlags(ItemFlag.values());
		item.setItemMeta(a2);

		return item;
	}

	public static void handleDrops(int level, Location location) {
		List<ItemStack> drops = new ArrayList<>();
		drops.add(weaponGenerator(level));
		drops.add(armorGenerator(level));
		for (ItemStack i : drops) {
			location.getWorld().dropItemNaturally(location, i);
		}
	}

}

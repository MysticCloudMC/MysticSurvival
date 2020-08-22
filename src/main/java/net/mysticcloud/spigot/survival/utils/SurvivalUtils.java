package net.mysticcloud.spigot.survival.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Player;
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

	static ItemStack[] foods = new ItemStack[] { CoreUtils.getItem("Bread") };
//	static String[] descriptors = new String[] {"Xelphor's", "Shiny", "Swift", "Dull", "Chipped", "Hardy", "Sharp", "Hellish"};

	public static void start(MysticSurvival main) {
		plugin = main;
		CoreUtils.addPrefix("homes", "&a&lHome &7>&e ");
		CoreUtils.addPrefix("survival", "&5&lMystic Survival &7>&f ");
		CoreUtils.coreHandleDamage(false);

		weaponEnhancements.add("fire");
		weaponEnhancements.add("frost");
		weaponEnhancements.add("fireball");
		weaponEnhancements.add("speed");

		weaponTiers.put(Tier.HUMAN,
				new Material[] { Material.WOODEN_AXE, Material.WOODEN_SWORD, Material.BOW, Material.STONE_AXE });
		weaponDescriptors.put(Tier.HUMAN, new String[] { "Dull", "Chipped", "Slow", "" });

		weaponTiers.put(Tier.WIZARD,
				new Material[] { Material.STONE_AXE, Material.IRON_SWORD, Material.IRON_AXE, Material.GOLDEN_SWORD });
		weaponDescriptors.put(Tier.WIZARD, new String[] { "Swift", "Shiny", "Dented" });

		weaponTiers.put(Tier.DEMI_GOD,
				new Material[] { Material.DIAMOND_AXE, Material.DIAMOND_SWORD, Material.CROSSBOW });
		weaponDescriptors.put(Tier.DEMI_GOD, new String[] { "Sharp", "Flashing", "Powerful" });

		weaponTiers.put(Tier.CELESTIAL, new Material[] { Material.DIAMOND_AXE, Material.DIAMOND_SWORD,
				Material.CROSSBOW, Material.TRIDENT, Material.DIAMOND_AXE, Material.IRON_SWORD });
		weaponDescriptors.put(Tier.CELESTIAL, new String[] { "Hellish", "Heavenly", "Xelphor's", "Satan's" });

		armorEnhancements.add("speed");
		armorEnhancements.add("armor");

		armorTiers.put(Tier.HUMAN, new Material[] { Material.LEATHER_BOOTS, Material.LEATHER_CHESTPLATE,
				Material.LEATHER_HELMET, Material.LEATHER_LEGGINGS });
		armorDescriptors.put(Tier.HUMAN, new String[] { "Ripped", "Torn", "Dirty", "" });

		armorTiers.put(Tier.WIZARD, new Material[] { Material.CHAINMAIL_BOOTS, Material.IRON_CHESTPLATE,
				Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_HELMET });
		armorDescriptors.put(Tier.WIZARD, new String[] { "Protective", "Shiny", "Dented" });

		armorTiers.put(Tier.DEMI_GOD, new Material[] { Material.DIAMOND_HELMET, Material.IRON_CHESTPLATE,
				Material.IRON_HELMET, Material.IRON_LEGGINGS, Material.IRON_BOOTS });
		armorDescriptors.put(Tier.DEMI_GOD, new String[] { "Strong", "Glistening", "Holy" });

		armorTiers.put(Tier.CELESTIAL, new Material[] { Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE,
				Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS, Material.NETHERITE_BOOTS });
		armorDescriptors.put(Tier.CELESTIAL, new String[] { "Hellish", "Heavenly", "Xelphor's", "Satan's" });

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
		return weaponDescriptors.get(tier)[new Random().nextInt(weaponDescriptors.get(tier).length)];
	}

	private static String getArmorDescriptor(Tier tier) {
		return armorDescriptors.get(tier)[new Random().nextInt(armorDescriptors.get(tier).length)];
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

			if (new Random().nextBoolean()) {
				if (s.equalsIgnoreCase("fireball") && level > Tier.DEMI_GOD.maxLevel) {
					ItemMeta a = item.getItemMeta();
					List<String> lore = a.hasLore() ? a.getLore() : new ArrayList<String>();
					lore.add(CoreUtils.colorize("&6Fireball&7 Damage: &6&l"
							+ ((int) ((level * CoreUtils.getRandom().nextDouble()) + CoreUtils.getRandom().nextInt(5)))
							+ "&7"));
					a.setLore(lore);
					a.setDisplayName(CoreUtils
							.colorize(a.getDisplayName() + "&f " + (enhanced ? "and" : "of") + " &6Fireballs&f"));
					AttributeModifier am = new AttributeModifier(UUID.randomUUID(), "Attack Speed", 0.001,
							Operation.ADD_NUMBER, EquipmentSlot.HAND);
					a.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, am);
					item.setItemMeta(a);
					enhanced = true;
				}
				if (s.equalsIgnoreCase("fire") && level > Tier.WIZARD.maxLevel) {
					ItemMeta a = item.getItemMeta();
					List<String> lore = a.hasLore() ? a.getLore() : new ArrayList<String>();
					lore.add(CoreUtils.colorize("&cFire&7 Damage: &c&l"
							+ ((int) ((level * CoreUtils.getRandom().nextDouble()) + CoreUtils.getRandom().nextInt(5)))
							+ "&7"));
					a.setLore(lore);
					a.setDisplayName(
							CoreUtils.colorize(a.getDisplayName() + "&f " + (enhanced ? "and" : "of") + " &cFlame&f"));
					item.setItemMeta(a);
					enhanced = true;
				}
				if (s.equalsIgnoreCase("frost") && level > Tier.HUMAN.maxLevel) {
					ItemMeta a = item.getItemMeta();
					List<String> lore = a.hasLore() ? a.getLore() : new ArrayList<String>();
					lore.add(CoreUtils.colorize("&bFrost&7 Damage: &b&l"
							+ ((int) ((level * CoreUtils.getRandom().nextDouble()) + CoreUtils.getRandom().nextInt(5)))
							+ "&7"));
					a.setLore(lore);
					a.setDisplayName(
							CoreUtils.colorize(a.getDisplayName() + "&f " + (enhanced ? "and" : "of") + " &bFrost&f"));
					item.setItemMeta(a);
					enhanced = true;
				}
				if (s.equalsIgnoreCase("speed")) {
					ItemMeta a = item.getItemMeta();
					List<String> lore = a.hasLore() ? a.getLore() : new ArrayList<String>();
					int level2 = (int) ((level * CoreUtils.getRandom().nextDouble())
							+ CoreUtils.getRandom().nextInt(5));
					lore.add(CoreUtils.colorize("&aSpeed&7 Modifier: &a&l" + level2));
					a.setLore(lore);
					a.setDisplayName(
							CoreUtils.colorize(a.getDisplayName() + "&f " + (enhanced ? "and" : "of") + " &aSpeed&f"));
					AttributeModifier am = new AttributeModifier(UUID.randomUUID(),
							getArmorType(item.getType()) + " Movement Speed", ((double) level2 / 100),
							Operation.ADD_NUMBER);
					a.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, am);
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

			if (new Random().nextBoolean()) {
				if (s.equalsIgnoreCase("speed")) {
					ItemMeta a = item.getItemMeta();
					List<String> lore = a.hasLore() ? a.getLore() : new ArrayList<String>();
					int level2 = (int) ((level * CoreUtils.getRandom().nextDouble())
							+ CoreUtils.getRandom().nextInt(5));
					lore.add(CoreUtils.colorize("&aSpeed&7 Modifier: &a&l" + level2));
					a.setLore(lore);
					a.setDisplayName(
							CoreUtils.colorize(a.getDisplayName() + "&f " + (enhanced ? "and" : "of") + " &aSpeed&f"));
					AttributeModifier am = new AttributeModifier(UUID.randomUUID(),
							getArmorType(item.getType()) + " Movement Speed", ((double) level2 / 100),
							Operation.ADD_NUMBER);
					a.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, am);
					item.setItemMeta(a);
					enhanced = true;
				}
				if (s.equalsIgnoreCase("armor")) {
					ItemMeta a = item.getItemMeta();
					List<String> lore = a.hasLore() ? a.getLore() : new ArrayList<String>();
					int level2 = (int) ((level * CoreUtils.getRandom().nextDouble())
							+ CoreUtils.getRandom().nextInt(5));
					lore.add(CoreUtils.colorize("&dArmor&7 Modifier: &d&l" + level2));
					a.setLore(lore);
					a.setDisplayName(CoreUtils
							.colorize(a.getDisplayName() + "&f " + (enhanced ? "and" : "of") + " &dProtection&f"));
					AttributeModifier am = new AttributeModifier(UUID.randomUUID(),
							getArmorType(item.getType()) + " Protection", ((double) level2 / 100),
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
		ItemStack item = new ItemStack(weaponTiers.get(tier)[new Random().nextInt(weaponTiers.get(tier).length)]);

		String name = getWeaponDescriptor(tier) + " " + getWeaponType(item.getType());
		ItemMeta a = item.getItemMeta();
		List<String> lore = a.hasLore() ? a.getLore() : new ArrayList<String>();

		double damage = (level * CoreUtils.getRandom().nextDouble()) + CoreUtils.getRandom().nextInt(5);
		double speed = (level * CoreUtils.getRandom().nextDouble()) + CoreUtils.getRandom().nextInt(5);

		AttributeModifier at = new AttributeModifier(UUID.randomUUID(), "Attack Damage", damage, Operation.ADD_NUMBER,
				EquipmentSlot.HAND);
		a.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, at);

		AttributeModifier sp = new AttributeModifier(UUID.randomUUID(), "Attack Speed", speed, Operation.ADD_NUMBER,
				EquipmentSlot.HAND);
		a.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, sp);

		lore.add(CoreUtils.colorize("&7Tier: " + tier.getName()));

		lore.add(CoreUtils.colorize("&7Damage: " + ((int) damage)));
		lore.add(CoreUtils.colorize("&7Speed: " + ((int) speed)));
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
		ItemStack item = new ItemStack(armorTiers.get(tier)[new Random().nextInt(armorTiers.get(tier).length)]);

		String name = getArmorDescriptor(tier) + " " + getArmorType(item.getType());
		ItemMeta a = item.getItemMeta();

		a.setDisplayName(CoreUtils.colorize("&f" + name));

		a.addItemFlags(ItemFlag.values());
		item.setItemMeta(a);
		item = randomizeArmorEnhancements(item, level);

		return item;
	}

	public static ItemStack foodGenerator() {
		ItemStack item = foods[new Random().nextInt(foods.length)];
		ItemMeta a = item.getItemMeta();
		a.addItemFlags(ItemFlag.values());
		item.setItemMeta(a);
		return item;
	}

	public static ItemStack bookGenerator(int level) {

		ItemStack item = new ItemStack(Material.BOOK);

		String name = "&2&lEnhancement Book";
		ItemMeta a = item.getItemMeta();

		a.setDisplayName(CoreUtils.colorize("&f" + name));

		a.addItemFlags(ItemFlag.values());
		item.setItemMeta(a);
		if (CoreUtils.getRandom().nextBoolean())
			item = randomizeArmorEnhancements(item, level);
		else
			item = randomizeWeaponEnhancements(item, level);

		return item;
	}

	public static void handleDrops(int level, Location location) {
		List<ItemStack> drops = new ArrayList<>();
		if (CoreUtils.getRandom().nextBoolean()) {
			drops.add(armorGenerator(level));
			if (CoreUtils.getRandom().nextBoolean()) {
				drops.add(weaponGenerator(level));
			}

		}
		if (CoreUtils.getRandom().nextBoolean()) {
			drops.add(foodGenerator());
		}

		for (ItemStack i : drops) {
			location.getWorld().dropItemNaturally(location, i);
		}
	}

	public static ItemStack enhance(ItemStack tool, ItemStack book) {
		ItemMeta tm = tool.getItemMeta();
		List<String> blore = new ArrayList<>();
		List<String> tlore = new ArrayList<>();
		if (book.hasItemMeta()) {
			if (book.getItemMeta().hasLore()) {
				for (String s : book.getItemMeta().getLore()) {
					blore.add(s);
				}
			}
		}

		if (tool.hasItemMeta()) {
			if (tool.getItemMeta().hasLore()) {
				for (String s : tool.getItemMeta().getLore()) {
					tlore.add(s);
				}
			}
		}

		Map<String, String> chgs = new HashMap<>();

		for (int i = 0; i != tlore.size(); i++) {
			for (int j = 0; j != blore.size(); j++) {
				if (tlore.get(i).contains(":")) {
					if (ChatColor.stripColor(tlore.get(i).split(":")[0])
							.equals(ChatColor.stripColor(blore.get(j).split(":")[0]))) {
						chgs.put(tlore.get(i), blore.get(j));
					}
				}
			}
		}
		for (String a : chgs.values()) {
			blore.remove(a);
		}
		for (Entry<String, String> en : chgs.entrySet()) {
			tlore.remove(en.getKey());
			tlore.add(en.getValue());

		}

		for (String s : blore) {
			tlore.add(s);
		}

		for (String l : tlore) {
			double n = 0;
			if (l.contains(": ")) {
				n = ((double) Integer.parseInt(ChatColor.stripColor(l).split(":")[1].replaceAll(" ", "")) / 100);
			}
			if (ChatColor.stripColor(l).contains("Speed Modifier:")) {
				AttributeModifier am = new AttributeModifier(UUID.randomUUID(),
						getArmorType(tool.getType()) + " Movement Speed", n, Operation.ADD_NUMBER);
				tm.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, am);

			}
			if (ChatColor.stripColor(l).contains("Armor Modifier:")) {
				AttributeModifier am = new AttributeModifier(UUID.randomUUID(),
						getArmorType(tool.getType()) + " Protection", ((double) n / 100), Operation.ADD_NUMBER);
				tm.addAttributeModifier(Attribute.GENERIC_ARMOR, am);
			}
			if (ChatColor.stripColor(l).contains("Damage:")) {
				AttributeModifier at = new AttributeModifier(UUID.randomUUID(), "Attack Damage", n,
						Operation.ADD_NUMBER, EquipmentSlot.HAND);
				tm.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, at);

			}
			if (ChatColor.stripColor(l).contains("Speed:")) {
				AttributeModifier sp = new AttributeModifier(UUID.randomUUID(), "Attack Speed", n, Operation.ADD_NUMBER,
						EquipmentSlot.HAND);
				tm.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, sp);
			}

		}

		tm.setLore(tlore);
		tool.setItemMeta(tm);
		book.setAmount(book.getAmount() - 1);
		return tool;
	}

	public static ItemStack soulbind(Player sender, ItemStack item) {
		ItemMeta a = item.getItemMeta();
		List<String> lore = a.hasLore() ? a.getLore() : new ArrayList<String>();
		lore.add(CoreUtils.colorize("&6&lSoulbound"));
		a.setLore(lore);
		item.setItemMeta(a);

		return item;
	}

}

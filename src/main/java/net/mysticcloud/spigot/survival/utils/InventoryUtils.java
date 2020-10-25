package net.mysticcloud.spigot.survival.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import net.mysticcloud.spigot.core.utils.GUIManager;
import net.mysticcloud.spigot.core.utils.InventoryCreator;
import net.mysticcloud.spigot.survival.utils.items.Item;
import net.mysticcloud.spigot.survival.utils.items.ItemUtils;
import net.mysticcloud.spigot.survival.utils.perks.Perks;

public class InventoryUtils {

	static Inventory bench = null;
	static Inventory menu = null;
	private static int[] recipeNums = new int[] { 10, 11, 12, 19, 20, 21, 28, 29, 30 };
	private static int resultNum = 24;

	public static void start() {
		InventoryCreator benchInv = new InventoryCreator("&6Crafting", null, 54);
		benchInv.addItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), " ", 'X');
		benchInv.addItem(new ItemStack(Material.AIR), 'O');
		benchInv.addItem(new ItemStack(Material.LIME_STAINED_GLASS_PANE), "&a&lCraft", 'A');

		benchInv.setConfiguration(new char[] { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'O', 'O', 'O', 'X',
				'X', 'X', 'X', 'X', 'X', 'O', 'O', 'O', 'X', 'X', 'O', 'X', 'X', 'X', 'O', 'O', 'O', 'X', 'X', 'X', 'X',
				'X', 'X', 'X', 'X', 'X', 'X', 'X', 'A', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' });

		bench = benchInv.getInventory();

		InventoryCreator menuInv = new InventoryCreator("&3&lMenu", null, 54);

		menuInv.addItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE), "&7Click an option", 'X');

		menuInv.addItem(new ItemStack(Material.CRAFTING_TABLE), "&e&lCrafting", 'A', new String[] {
				"&7- Craft custom items", "&7- Enhance your weapons and armor", "&7- Create spells and potions" });

		menuInv.addItem(new ItemStack(Material.NETHER_STAR), "&5&lPerks", 'B',
				new String[] { "&7- Access the perk menus" });

		menuInv.addItem(new ItemStack(Material.BLACK_CONCRETE), "&c&lComing soon", 'Z', new String[] { "&7- &o???" });

		menuInv.addItem(new ItemStack(Material.AIR), 'O');

		menuInv.setConfiguration(new char[] { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'A', 'X', 'B', 'X', 'Z',
				'X', 'Z', 'X', 'X', 'X', 'Z', 'X', 'Z', 'X', 'Z', 'X', 'X', 'X', 'Z', 'X', 'Z', 'X', 'Z', 'X', 'Z', 'X',
				'X', 'X', 'Z', 'X', 'Z', 'X', 'Z', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' });

		menu = menuInv.getInventory();
	}

	public static void openCraftingBench(Player player) {
		if (GUIManager.getOpenInventory(player).equals("none"))
			GUIManager.openInventory(player, bench, "CraftingBench");
		else
			GUIManager.switchInventory(player, bench, "CraftingBench");
	}

	public static void openMenu(Player player) {
		if (GUIManager.getOpenInventory(player).equals("none"))
			GUIManager.openInventory(player, menu, "Menu");
		else
			GUIManager.switchInventory(player, menu, "Menu");
	}

	public static void openPerksMenu(Player player) {

		InventoryCreator perks = new InventoryCreator("&5&lPerk Menu", null, 9);

		ArrayList<Character> conf = new ArrayList<>();

		perks.addItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), "&7Click an option.", 'X');

		for (int i = 0; i != 9; i++) {
			try {
				perks.addItem(new ItemStack(Division.values()[i].getGUIItem()), Division.values()[i].getDisplayName(),
						(char) i);
				conf.add((char) i);
			} catch (ArrayIndexOutOfBoundsException ex) {
				conf.add('X');
			}
		}
		perks.setConfiguration(conf);

		if (GUIManager.getOpenInventory(player).equals("none"))
			GUIManager.openInventory(player, perks.getInventory(), "Perk Menu");
		else
			GUIManager.switchInventory(player, perks.getInventory(), "Perk Menu");
	}

	public static void openPerksMenu(Player player, Division div) {
		String name = div.getPerkPrefix() + " Perks";
		InventoryCreator perks = new InventoryCreator("&5&l" + name, null, 27);

		ArrayList<Character> conf = new ArrayList<>();

		perks.addItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), "&7Click an option.", 'X');

		perks.addItem(new ItemStack(Material.RED_STAINED_GLASS_PANE), "&c&lLocked", 'O');

		for (int i = 0; i != 27; i++) {
			try {
				if (SurvivalUtils.getSurvivalPlayer(player).hasPerk(Perks.getPerks(div).get(i))) {
					perks.addItem(new ItemStack(Material.DIAMOND), Perks.getPerks(div).get(i).getName(), (char) i);
					conf.add((char) i);
				} else
					conf.add('O');
			} catch (IndexOutOfBoundsException ex) {
				conf.add('X');
			}
		}
		perks.setConfiguration(conf);

		if (GUIManager.getOpenInventory(player).equals("none"))
			GUIManager.openInventory(player, perks.getInventory(), ChatColor.stripColor(name));
		else
			GUIManager.switchInventory(player, perks.getInventory(), ChatColor.stripColor(name));
	}

	public static void craft(SurvivalPlayer player, Inventory inv) {
		LinkedHashMap<Integer, ItemStack> reicpe = getRecipe(inv);
		ItemStack result = getResult(reicpe, player);
		if ((inv.getItem(resultNum) == null || inv.getItem(resultNum).getType().equals(Material.AIR))
				&& !result.getType().equals(Material.AIR)) {
			for (int i : recipeNums) {
				inv.setItem(i, new ItemStack(Material.AIR));
			}
			inv.setItem(resultNum, result);
		}
	}

	private static ItemStack getResult(LinkedHashMap<Integer, ItemStack> items, SurvivalPlayer player) {
		ItemStack result = new ItemStack(Material.AIR);

//		0,1,2,
//		3,4,5,
//		6,7,8


		if (items.get(4).getType().equals(Material.PAPER)) {
			for (Entry<Integer, ItemStack> entry : items.entrySet()) {
				if (entry.getValue().getType().equals(Material.STICK)) {
					result = ItemUtils.enhanceInInventory(entry.getValue(), items.get(4));
					player.gainSubSkill(SubSkill.SPELL,1);
					break;
				}
			}
		}
		if (items.get(4).getType().equals(Material.BOOK)) {
			for (Entry<Integer, ItemStack> entry : items.entrySet()) {
				if (!entry.getValue().getType().equals(Material.BOOK)
						&& !entry.getValue().getType().equals(Material.AIR)) {
					result = ItemUtils.enhanceInInventory(entry.getValue(), items.get(4));
					player.gainSubSkill(SubSkill.ENHANCE,1);
					break;
				}
			}
		}

		return result;
	}

	private static LinkedHashMap<Integer, ItemStack> getRecipe(Inventory inv) {
		LinkedHashMap<Integer, ItemStack> items = new LinkedHashMap<>();
		for (int i : recipeNums) {
			if (inv.getItem(i) != null)
				items.put(items.size(), inv.getItem(i));
			else
				items.put(items.size(), new ItemStack(Material.AIR));
		}
		return items;

	}

}

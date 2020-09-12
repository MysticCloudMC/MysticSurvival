package net.mysticcloud.spigot.survival.utils;

import java.util.LinkedList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.mysticcloud.spigot.core.utils.GUIManager;
import net.mysticcloud.spigot.core.utils.InventoryCreator;

public class InventoryUtils {

	static Inventory bench = null;
	static Inventory menu = null;
	private static int[] recipeNums = new int[] { 10, 11, 12, 19, 20, 21, 28, 19, 30 };
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
		menuInv.addItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE), " ", 'X');
		menuInv.addItem(new ItemStack(Material.CRAFTING_TABLE), "&e&lCrafting", 'A', new String[] {
				"&7- Craft custom items", "&7- Enhance your weapons and armor", "&7- Create spells and potions" });
		menuInv.addItem(new ItemStack(Material.NETHER_STAR), "&5&lPerks", 'B', new String[] {"&7- Access the perk menus"});
		menuInv.addItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), "&c&lComing soon", 'C', new String[] {"&7- &o???"});
		menuInv.addItem(new ItemStack(Material.AIR), 'O');

		menuInv.setConfiguration(new char[] { 
				'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 
				'X', 'A', 'X', 'B', 'X', 'C', 'X', 'C', 'X', 
				'X', 'X', 'C', 'X', 'C', 'X', 'C', 'X', 'X',
				'X', 'C', 'X', 'C', 'X', 'C', 'X', 'C', 'X',
				'X', 'X', 'C', 'X', 'C', 'X', 'C', 'X', 'X',
				'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' });

		menu = menuInv.getInventory();
	}

	public static void openCraftingBench(Player player) {
		GUIManager.openInventory(player, bench, "CraftingBench");
	}

	public static void openMenu(Player player) {
		GUIManager.openInventory(player, menu, "Menu");
	}

	public static void craft(Inventory inv) {
		LinkedList<ItemStack> reicpe = getRecipe(inv);
		ItemStack result = getResult(reicpe);
		if ((inv.getItem(resultNum) == null || inv.getItem(resultNum).getType().equals(Material.AIR))
				&& !result.getType().equals(Material.AIR)) {
			for (int i : recipeNums) {
				inv.setItem(i, new ItemStack(Material.AIR));
			}
			inv.setItem(resultNum, result);
		}
	}

	private static ItemStack getResult(LinkedList<ItemStack> items) {
		ItemStack result = new ItemStack(Material.AIR);
		if (items.get(4).getType().equals(Material.BOOK)) {
			Bukkit.broadcastMessage("Item #4 is a book");
			for (ItemStack item : items) {
				Bukkit.broadcastMessage("Item..." + item.getType().name());
				if (!item.getType().equals(Material.BOOK) && !item.getType().equals(Material.AIR)) {
					Bukkit.broadcastMessage("Found another item: " + item.getType().name());
					result = SurvivalUtils.enhanceInInventory(item, items.get(4));
					Bukkit.broadcastMessage("Got result aborting loop.");
					break;
				}
			}
		}

		return result;
	}

	private static LinkedList<ItemStack> getRecipe(Inventory inv) {
		LinkedList<ItemStack> items = new LinkedList<>();
		for (int i : recipeNums) {
			if (inv.getItem(i) != null && !inv.getItem(i).getType().equals(Material.AIR))
				items.add(inv.getItem(i));
			else
				items.add(new ItemStack(Material.AIR));
		}
		return items;

	}

}

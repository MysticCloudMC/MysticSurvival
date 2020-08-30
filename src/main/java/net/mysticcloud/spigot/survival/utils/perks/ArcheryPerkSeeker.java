package net.mysticcloud.spigot.survival.utils.perks;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.mysticcloud.spigot.core.utils.CoreUtils;

public class ArcheryPerkSeeker extends ArcheryPerk {
	
	ItemStack seeker = new ItemStack(Material.ARROW);

	public ArcheryPerkSeeker(UUID uid) {
		super(uid);
		ItemMeta sm = seeker.getItemMeta();
		sm.setDisplayName(CoreUtils.colorize("&eSeeker Arrow"));
		seeker.setItemMeta(sm);
	}
	
	@Override
	public void activate() {
		getPlayer().getInventory().addItem(seeker);
	}

}

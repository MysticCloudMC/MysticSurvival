package net.mysticcloud.spigot.survival.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.core.utils.MysticPlayer;
import net.mysticcloud.spigot.survival.utils.perks.Perk;
import net.mysticcloud.spigot.survival.utils.perks.Perks;

public class SurvivalPlayer {

	MysticPlayer player;
	Division division;
	File file;
	int maxMana = 100;
	int mana = maxMana;
	int maxStamina = 100;
	int stamina = maxStamina;
	double manaMultiplier = 1;
	double staminaMultiplier = 1;
	double staminaModifier = 0;
	double manaModifier = 0;

	Map<Perk, Double> perks = new HashMap<>();

	protected SurvivalPlayer(MysticPlayer player) {
		this.player = player;

		file = new File(SurvivalUtils.getPlugin().getDataFolder().getPath() + "/players/" + player.getUUID() + ".yml");
		if (!file.exists()) {
//			file.mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
		if (fc.isSet("Division"))
			setDivision(Division.valueOf(fc.getString("Division")), true);
		if (fc.isSet("MaxMana"))
			maxMana = (Integer.parseInt(fc.getString("MaxMana")));
		if (fc.isSet("MaxStamina"))
			maxStamina = (Integer.parseInt(fc.getString("MaxStamina")));
		if (fc.isSet("StaminaMultiplier"))
			staminaMultiplier = (Double.parseDouble(fc.getString("StaminaMultiplier")));
		if (fc.isSet("ManaMultiplier"))
			manaMultiplier = (Double.parseDouble(fc.getString("ManaMultiplier")));

		mana = maxMana;
		stamina = maxStamina;
	}

	public void sendMessage(String message) {
		player.sendMessage("olympus", message);
	}

	public void addPerk(Perks perk, double power) {
		Bukkit.broadcastMessage("Adding...");
		perks.put(perk.getPerk(player.getUUID()), power);
	}

	public List<Perks> getPerks() {
		List<Perks> perks = new ArrayList<>();
		for (Perk perk : this.perks.keySet()) {
			perks.add(Perks.getPerk(Perks.getName(perk)));
		}
		return perks;
	}
	
	public boolean hasPerk(Perks perk) {
		for (Perk p : this.perks.keySet()) {
			if(Perks.getName(p).equals(perk.getName())) {
				return true;
			}
		}
		return false;
	}
	
	public Perk getPerk(Perks perk) {
		for (Perk p : this.perks.keySet()) {
			if(Perks.getName(p).equals(perk.getName())) {
				return p;
			}
		}
		return null;
	}
	

	public void setDivision(Division division) {
		setDivision(division, false);
	}

	public void setDivision(Division division, boolean loading) {
		if (this.division == null && !loading) {
			switch (division) {
			case MAGE:
				maxMana = maxMana + 100;
				manaMultiplier = manaMultiplier + 0.5;
				if (Bukkit.getPlayer(player.getUUID()) != null) {
					player.sendMessage("survival",
							"Boosting your max mana by 100 points, and your mana multiplier by 0.5 for joining the Mages!");
				}
				break;
			case WARRIER:
				maxStamina = maxStamina + 100;
				staminaMultiplier = staminaMultiplier + 0.5;
				if (Bukkit.getPlayer(player.getUUID()) != null) {
					player.sendMessage("survival",
							"Boosting your max stamina by 100 points, and your stamina multiplier by 0.5 for joining the Warriers!");
				}
				break;
			default:
				break;
			}
		}
		this.division = division;
	}

	public int getMaxMana() {
		return maxMana;
	}

	public void setMaxMana(int mana) {
		this.maxMana = mana;
	}

	public int getMana() {
		return mana;
	}

	public int getMaxStamina() {
		return maxStamina;
	}

	public void setMaxStamina(int stamina) {
		this.maxStamina = stamina;
	}

	public int getStamina() {
		return stamina;
	}

	public void replenishMana() {
		mana = (int) (mana + ((int) (1 * manaMultiplier) + manaModifier));
		if (mana > maxMana)
			mana = maxMana;
	}

	public void replenishStamina() {
		stamina = (int) (stamina + ((int) (1 * staminaMultiplier) + staminaModifier));
		if (stamina > maxStamina)
			stamina = maxStamina;
	}

	public void useMana(int i) {
		if (mana != 0)
			if (mana > i) {
				mana = mana - i;
			} else {
				mana = 0;
			}
		if (mana > maxMana)
			mana = maxMana;
		showStats();
	}

	public void useStamina(int i) {
		if (stamina != 0)
			if (stamina > i) {
				stamina = stamina - i;
			} else {
				stamina = 0;
			}
		if (stamina > maxStamina)
			stamina = maxStamina;
		showStats();
	}

	public void showStats() {
		if (Bukkit.getPlayer(player.getUUID()) != null) {
			Player p = Bukkit.getPlayer(player.getUUID());

			double manaper = ((double) (((double) mana) / ((double) maxMana)));
			String mana = "&3";
			for (int j = 0; j != 25; j++) {
				if (j == (int) (25 * manaper)) {
					mana = mana + "&7";
				}

				mana = mana + "\u258C";
//				mana = mana + (j%2==0?"\u25D6":"\u25D7");
			}

			double stper = ((double) (((double) stamina) / ((double) maxStamina)));
			String st = "&a";
			for (int j = 0; j != 25; j++) {
				if (j == (int) (25 * stper)) {
					st = st + "&7";
				}
				st = st + "\u258C";
			}

			p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
					new ComponentBuilder(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&',
							"&3&lMana&7: " + mana + "  &7(" + this.maxMana + ") &l|&r  &a&lStamina&7: " + st + " &7("
									+ this.maxStamina + ")")).create());
		}
	}

	public Division getDivision() {
		return division;
	}

	public int getLevel() {
		return player.getLevel();
	}

	public void save() {
		FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
		if (division != null) {
			fc.set("Division", division.name());
		}
		fc.set("MaxMana", maxMana);
		fc.set("MaxStamina", maxStamina);
		fc.set("StaminaMultiplier", staminaMultiplier);
		fc.set("ManaMultiplier", manaMultiplier);
		try {
			fc.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public MysticPlayer getPlayer() {
		return player;
	}

	public void activatePerk(Perks perk) {
		Bukkit.broadcastMessage("Activating..");
		getPerk(perk).activate();
	}

	

}

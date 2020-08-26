package net.mysticcloud.spigot.survival.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.core.utils.MysticPlayer;

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
			setDivision(Division.valueOf(fc.getString("Division")),true);
		if (fc.isSet("MaxMana"))
			maxMana = (Integer.parseInt(fc.getString("MaxMana")));
		if (fc.isSet("MaxStamina"))
			maxStamina = (Integer.parseInt(fc.getString("MaxStamina")));
	}
	
	public void setDivision(Division division) {
		setDivision(division,false);
	}

	public void setDivision(Division division, boolean loading) {
//		if (this.division == null && !loading) {
			switch (division) {
			case MAGE:
				maxMana = maxMana + 100;
				if(Bukkit.getPlayer(player.getUUID())!=null) {
					player.sendMessage(CoreUtils.colorize("&3Olympus"), "Boosting your max mana by 100 points for joining the Mages!");
				}
				break;
			default:
				break;
			}
//		}
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
		mana = mana + ((int) (1 * manaMultiplier));
	}

	public void replenishStamina() {
		stamina = stamina + ((int) (1 * staminaMultiplier));
	}

	public void useMana(int i) {
		if (mana != 0)
			if (mana > i) {
				mana = mana - i;
			} else {
				mana = 0;
			}
		showStats();
	}

	public void useStamina(int i) {
		if (stamina != 0)
			if (stamina > i) {
				stamina = stamina - i;
			} else {
				stamina = 0;
			}
		showStats();
	}

	public void showStats() {
		if (Bukkit.getPlayer(player.getUUID()) != null) {
			Player p = Bukkit.getPlayer(player.getUUID());

			double manaper = ((double) (((double) mana) / ((double) maxMana)));
			String mana = "&3";
			int manap = (int) (50 * manaper);
			for (int j = 0; j != 50; j++) {
				if (j == manap) {
					mana = mana + "&7";
				}
				mana = mana + "|";
			}

			double stper = ((double) (((double) stamina) / ((double) maxStamina)));
			String st = "&a";
			int stp = (int) (50 * stper);
			for (int j = 0; j != 50; j++) {
				if (j == stp) {
					st = st + "&7";
				}
				st = st + "|";
			}

			p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
					new ComponentBuilder(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&',
							"&3&lMana&7: " + mana + "  &7&l|&r  &a&lStamina&7: " + st)).create());
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
		try {
			fc.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

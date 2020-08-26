package net.mysticcloud.spigot.survival.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.mysticcloud.spigot.core.utils.MysticPlayer;

public class SurvivalPlayer {

	MysticPlayer player;
	Division division;
	File file;
	int maxMana = 100;
	int mana = maxMana;
	double manaMultiplier = 1;

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
		if (fc.isSet("Division")) {
			setDivision(Division.valueOf(fc.getString("Division")));
		}
	}

	public void setDivision(Division division) {
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

	public void replenishMana() {
		mana = mana + ((int) (1 * manaMultiplier));
		useMana(0);
	}

	public void useMana(int i) {
		if (mana != 0)
			if (mana > i) {
				mana = mana - i;
			} else {
				mana = 0;
			}

		if (Bukkit.getPlayer(player.getUUID()) != null) {
			Player p = Bukkit.getPlayer(player.getUUID());

			double percent = ((double) (((double) mana) / ((double) maxMana)));
			String status = "&3";
//			if(percent >=)
			int progress = (int) (50 * percent);
			for (int j = 0; j != 50; j++) {
				if (j == progress) {
					status = status + "&7";
				}
				status = status + "|";
			}
			p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
					new ComponentBuilder(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', status))
							.create());
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
		try {
			fc.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

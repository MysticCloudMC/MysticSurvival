package net.mysticcloud.spigot.survival.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.mysticcloud.spigot.core.utils.MysticPlayer;

public class SurvivalPlayer {

	MysticPlayer player;
	Division division;
	File file;

	protected SurvivalPlayer(MysticPlayer player) {
		this.player = player;

		file = new File(
				SurvivalUtils.getPlugin().getDataFolder().getPath() + "/players/" + player.getUUID() + ".yml");
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

	public Division getDivision() {
		return division;
	}

	public void save() {
		FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
		if (fc.isSet("Division")) {
			setDivision(Division.valueOf(fc.getString("Division")));
		}
		try {
			fc.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

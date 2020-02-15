package net.mysticcloud.spigot.survival;

import org.bukkit.plugin.java.JavaPlugin;

import net.mysticcloud.spigot.survival.commands.HomeCommand;
import net.mysticcloud.spigot.survival.utils.SurvivalUtils;

public class MysticSurvival extends JavaPlugin {
	
	public void onEnable() {
		
		SurvivalUtils.start();
		new HomeCommand(this, "home", "sethome", "removehome");
	}

}

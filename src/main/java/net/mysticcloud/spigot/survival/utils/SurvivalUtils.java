package net.mysticcloud.spigot.survival.utils;

import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.survival.MysticSurvival;

public class SurvivalUtils {
	private static MysticSurvival plugin;

	public static void start(MysticSurvival main) {
		plugin = main;
		CoreUtils.addPrefix("homes", "&a&lHome &7>&e ");
		CoreUtils.addPrefix("survival", "&5&lMystic Survival &7>&f ");
		CoreUtils.coreHandleDamage(false);
	}
	
	public static MysticSurvival getPlugin() {
		return plugin;
	}

}

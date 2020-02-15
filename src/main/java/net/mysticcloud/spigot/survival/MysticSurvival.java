package net.mysticcloud.spigot.survival;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import net.mysticcloud.spigot.survival.commands.HomeCommand;
import net.mysticcloud.spigot.survival.utils.SurvivalUtils;

public class MysticSurvival extends JavaPlugin {
	
	private int attempt = 0;
	private int maxattempts = 3;
	
	public void onEnable() {
		if(!getServer().getPluginManager().isPluginEnabled("MysticCore")){
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3&l" + getDescription().getName() + " &f>&7 Can't find MysticCore. Trying again.. Attempt " + attempt + " out of " + maxattempts));
			attempt+=1;
			if(attempt==maxattempts+1){
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3&l" + getDescription().getName() + " &f>&7 Couldn't find MysticCore after " + maxattempts + " tries. Plugin will not load."));
				getServer().getPluginManager().disablePlugin(this);
				return;
			}
			Bukkit.getScheduler().runTaskLater(this, new Runnable(){

				@Override
				public void run() {
					onEnable();
				}
				
			}, 20*3);
			return;
		} else {
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3&l" + getDescription().getName() + " &f>&7 Found MysticCore! Loading plugin.."));
		}
		
		SurvivalUtils.start();
		new HomeCommand(this, "home", "sethome", "removehome");
	}

}

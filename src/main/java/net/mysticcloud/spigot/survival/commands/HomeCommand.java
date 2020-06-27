package net.mysticcloud.spigot.survival.commands;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import net.mysticcloud.spigot.core.commands.listeners.CommandTabCompleter;
import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.core.utils.warps.Warp;
import net.mysticcloud.spigot.core.utils.warps.WarpBuilder;
import net.mysticcloud.spigot.core.utils.warps.WarpUtils;
import net.mysticcloud.spigot.survival.MysticSurvival;
import net.mysticcloud.spigot.survival.utils.HomeUtils;

public class HomeCommand implements CommandExecutor {

	public HomeCommand(MysticSurvival plugin, String... cmd) {
		for (String comd : cmd) {
			PluginCommand com = plugin.getCommand(comd);
			com.setExecutor(this);
			com.setTabCompleter(new CommandTabCompleter());
		}

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("home")) {
				List<Warp> homes = HomeUtils.getHomes(((Player) sender).getUniqueId().toString());
				if (args.length <= 1) {
					if (args.length == 0) {
						if (homes.size() == 0) {
							sender.sendMessage(CoreUtils.prefixes("homes") + "You must set a home first! /sethome");
							return true;
						}
					}

					Warp thome = homes.get(0);
					boolean choosen = false;
					if (homes.size() > 1 && args.length > 0) {
						for (Warp home : homes) {
							if (home.name().equalsIgnoreCase(args[0])) {
								thome = home;
								choosen = true;
								break;
							}
						}
					}

					((Player) sender).teleport(thome.location());
					sender.sendMessage(
							CoreUtils.prefixes("homes") + "You have teleported to home " + thome.name() + ".");
					if (!choosen) {
						String s = "";
						for (Warp home : homes)
							s = s == "" ? home.name() : s + ", " + home.name();
						sender.sendMessage(CoreUtils.prefixes("homes") + "Here's a list of your homes: " + s);
					}

				} else {
					String owner = CoreUtils.LookupUUID(args[0]) != null ? CoreUtils.LookupUUID(args[0]).toString() : "";
					String home = args[1];
					
					if(owner!="") {
						List<Warp> ohomes = HomeUtils.getHomes(owner);
						for(Warp ohome : ohomes) {
							if(ohome.name().equalsIgnoreCase(home)) {
								((Player)sender).teleport(ohome.location());
								sender.sendMessage(
										CoreUtils.prefixes("homes") + "You have teleported to " + formatUsername(args[1]) + " home " + ohome.name() + ".");
								return true;
							}
						}
						sender.sendMessage(CoreUtils.prefixes("homes") + "That home could not be found.");
						return true;
					}
					sender.sendMessage(CoreUtils.prefixes("homes") + "That player could not be found.");
					
					
					
				}
			}

			if (cmd.getName().equalsIgnoreCase("sethome")) {
				if (sender instanceof Player) {
					String name = args.length > 0 ? args[0]
							: (HomeUtils.getHomes(((Player) sender).getUniqueId().toString()).size() + 1) + "";
					WarpBuilder warp = new WarpBuilder();
					if (warp.createWarp().setType("home~" + ((Player) sender).getName()).setName(name)
							.setLocation(((Player) sender).getLocation()).getWarp() != null)
						sender.sendMessage(CoreUtils.prefixes("homes") + "Home (" + name + ") set!");
					else
						sender.sendMessage(CoreUtils.prefixes("homes") + "There was an error setting you home.");

				} else {
					sender.sendMessage(CoreUtils.prefixes("homes") + "You must be a player to use that command.");
				}
			}

		} else {
			sender.sendMessage(CoreUtils.prefixes("homes") + "You must be a player to use that command.");
		}

		return true;

	}

	private String formatUsername(String username) {
		return username.toLowerCase().endsWith("s") ? username + "'" : username + "'s";
		
	}
}

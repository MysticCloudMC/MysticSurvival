package net.mysticcloud.spigot.survival.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import net.mysticcloud.spigot.core.commands.listeners.CommandTabCompleter;
import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.survival.MysticSurvival;
import net.mysticcloud.spigot.survival.utils.SurvivalUtils;
import net.mysticcloud.spigot.survival.utils.polls.PollUtils;

public class MysticSurvivalCommand implements CommandExecutor {

	public MysticSurvivalCommand(MysticSurvival plugin, String... cmd) {
		for (String comd : cmd) {
			PluginCommand com = plugin.getCommand(comd);
			com.setExecutor(this);
			com.setTabCompleter(new CommandTabCompleter());
		}

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(
					CoreUtils.colorize(CoreUtils.prefixes("admin") + "Usage: /msurvival <subcommand> [args]"));
			sender.sendMessage(CoreUtils.colorize(CoreUtils.prefixes("admin") + "Here is a list of sub-commands:"));
			sender.sendMessage(CoreUtils.colorize("&3giveRandomArmor [level]"));
			sender.sendMessage(CoreUtils.colorize("&3giveRandomWeapon [level]"));

			return true;
		}

		if (args[0].equalsIgnoreCase("giveRandomArmor")) {
			if (sender instanceof Player) {
				sender.sendMessage(CoreUtils.prefixes("admin") + "Generating random armor...");
				((Player) sender).getInventory()
						.addItem(SurvivalUtils.armorGenerator(args.length == 2 ? Integer.parseInt(args[1])
								: (int) ((Player) sender).getMetadata("level").get(0).value()));
			}
		}
		if (args[0].equalsIgnoreCase("giveRandomWeapon")) {
			sender.sendMessage(CoreUtils.prefixes("admin") + "Generating random weapon...");
			if (sender instanceof Player) {
				((Player) sender).getInventory()
						.addItem(SurvivalUtils.weaponGenerator(args.length == 2 ? Integer.parseInt(args[1])
								: (int) ((Player) sender).getMetadata("level").get(0).value()));
			}
		}

		return true;
	}

//	private String formatUsername(String username) {
//		return username.toLowerCase().endsWith("s") ? username + "'" : username + "'s";
//
//	}
}

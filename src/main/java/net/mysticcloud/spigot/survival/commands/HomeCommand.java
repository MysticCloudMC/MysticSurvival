package net.mysticcloud.spigot.survival.commands;

import java.util.List;

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
				String type = "home";
				String owner = ((Player) sender).getUniqueId().toString();
				if (args.length == 0) {

					String homes = "";
					for (Warp home : WarpUtils.getWarps("home")) {
						if (home.metadata("Owner").equals(((Player) sender).getUniqueId().toString()))
							homes = homes == "" ? home.name() : homes + ", " + home.name();

					}
					if (homes == "" || homes.contains(",")) {
						sender.sendMessage(CoreUtils.prefixes("homes") + "Here is a list of avalible homes:");
						sender.sendMessage(homes);
						return false;
					} else {

						for (Warp home : WarpUtils.getWarps(type, homes)) {
							if (home.metadata("Owner").equals(owner)) {
								((Player) sender).teleport(home.location());
								sender.sendMessage(CoreUtils.prefixes("homes") + "Teleporting to home: " + home.name());
								return true;
							}
						}
					}

					return false;

				}

				String name = args[0];
				if (args.length > 1) {
					if (Bukkit.getPlayer(args[1]) == null) {
						sender.sendMessage(
								CoreUtils.prefixes("homes") + "Player must be online for you to visit their home.");
						return true;
					}
					owner = Bukkit.getPlayer(args[1]).getUniqueId().toString();
				}
				List<Warp> homes = WarpUtils.getWarps(type, name);
				if (homes.size() == 0) {
					sender.sendMessage(CoreUtils.prefixes("homes") + "Can't find that home...");
					return false;
				}
				for (Warp home : homes) {
					if (home.metadata("Owner").equals(owner)) {
						((Player) sender).teleport(home.location());
						sender.sendMessage(CoreUtils.prefixes("homes") + "Teleporting to home: " + home.name());
						return true;
					}
				}

				sender.sendMessage(CoreUtils.prefixes("homes")
						+ "There was an error. If you see this message please contact an admin. (Error Code: SUR-HCMD101)");

			}

			if (cmd.getName().equalsIgnoreCase("sethome")) {
				if (sender instanceof Player) {
					String name = args.length > 1 ? args[0]
							: (HomeUtils.getHomes(((Player) sender).getUniqueId()).size() + 1) + "";
					WarpBuilder warp = new WarpBuilder();
					if (warp.createWarp().setType("home").setName(name).setLocation(((Player) sender).getLocation())
							.setMetadata("Owner", ((Player) sender).getUniqueId().toString()).getWarp() != null)
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

		return false;

	}
}

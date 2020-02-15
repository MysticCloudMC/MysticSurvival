package net.mysticcloud.spigot.survival.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import net.mysticcloud.spigot.core.Main;
import net.mysticcloud.spigot.core.commands.listeners.CommandTabCompleter;
import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.core.utils.warps.Warp;
import net.mysticcloud.spigot.core.utils.warps.WarpBuilder;
import net.mysticcloud.spigot.core.utils.warps.WarpUtils;

public class HomeCommand implements CommandExecutor {

	public HomeCommand(Main plugin, String... cmd) {
		for (String comd : cmd) {
			PluginCommand com = plugin.getCommand(comd);
			com.setExecutor(this);
			com.setTabCompleter(new CommandTabCompleter());
		}

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("home")) {
			if (sender instanceof Player) {
				if (args.length == 0) {
					sender.sendMessage(CoreUtils.prefixes("homes") + "Here is a list of avalible homes:");
					String homes = "";
					for (Warp home : WarpUtils.getWarps("home")) {
						if (home.metadata("Owner").equals(((Player) sender).getUniqueId().toString()))
							homes = homes + ", " + home.name();
					}
					return false;
				}
				String type = "home";
				String owner = ((Player) sender).getUniqueId().toString();
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
						sender.sendMessage(CoreUtils.prefixes("homes") + "Teleporting to home: " + home.id());
						return true;
					}
				}

				sender.sendMessage(CoreUtils.prefixes("homes")
						+ "There was an error. If you see this message please contact an admin. (Error Code: SUR-HCMD67)");

			} else {
				sender.sendMessage(CoreUtils.prefixes("homes") + "You must be a player to use that command.");
				return true;
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("sethome")) {
			if (sender instanceof Player) {
				String name = args.length >= 1 ? args[0] : "1";
				WarpBuilder warp = new WarpBuilder();
				warp.createWarp()
						.setType("home")
						.setName(name)
						.setLocation(((Player) sender).getLocation())
						.setMetadata("Owner", ((Player)sender).getUniqueId().toString())
						.getWarp();
				sender.sendMessage(CoreUtils.prefixes("warps") + "Warp created! "
						+ (args.length >= 2 ? args[0] : "warp") + ":" + (args.length >= 2 ? args[1] : args[0]));

			} else {
				sender.sendMessage(CoreUtils.prefixes("warps") + "You must be a player to use that command.");
			}
		}
		return false;
	}
}

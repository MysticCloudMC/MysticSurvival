package net.mysticcloud.spigot.survival.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import net.mysticcloud.spigot.core.commands.listeners.CommandTabCompleter;
import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.survival.MysticSurvival;
import net.mysticcloud.spigot.survival.utils.Division;
import net.mysticcloud.spigot.survival.utils.SurvivalPlayer;
import net.mysticcloud.spigot.survival.utils.SurvivalUtils;
import net.mysticcloud.spigot.survival.utils.perks.Perks;

public class PerkCommand implements CommandExecutor {

	public PerkCommand(MysticSurvival plugin, String... cmd) {
		for (String comd : cmd) {
			PluginCommand com = plugin.getCommand(comd);
			com.setExecutor(this);
			com.setTabCompleter(new CommandTabCompleter());
		}

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(CoreUtils.prefixes("admin") + "Player only command.");
			return true;
		}
		if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
			if(args.length == 0 || args[1].equals("1")) {
				sender.sendMessage(CoreUtils.prefixes("survival") + "Here's a list of perk commands:");
				sender.sendMessage(CoreUtils.colorize("&d/perks help [page] &5- shows this list."));
				sender.sendMessage(
						CoreUtils.colorize("&d/perks list [division] &5- lists all perks or just perks in a division."));
				sender.sendMessage(CoreUtils.colorize("&d/perks m[ylist] &5- shows all perks you have."));
				sender.sendMessage(CoreUtils.colorize("&d/perks u[se] <division> <perk> &5- use or activate a perk."));
				return true;
			}
			if(args[1].equals("2")) {
				sender.sendMessage(CoreUtils.prefixes("survival") + "Here's some perk info");
				sender.sendMessage(CoreUtils.colorize("&dTargeting&5: You can target an entity with the 'targeting wand' given to you when you joined."));
				sender.sendMessage(
						CoreUtils.colorize("&dSwitching Worlds&5: Right click on the &a*lWorld Switcher&5 given to you at after the first quest."));
//				sender.sendMessage(CoreUtils.colorize("&d/perks m[ylist] &5- shows all perks you have."));
//				sender.sendMessage(CoreUtils.colorize("&d/perks u[se] <division> <perk> &5- use or activate a perk."));
				return true;
			}
		}
		if (args[0].equalsIgnoreCase("m") || args[0].equalsIgnoreCase("mylist")) {
			sender.sendMessage(CoreUtils.prefixes("survival") + "Here's a list of your perks:");
			String p = "";
			for(Perks perk : SurvivalUtils.getSurvivalPlayer((Player) sender).getPerks()) 
				p = p == "" ? perk.getName() : p + ", " + perk.getName();
			sender.sendMessage(CoreUtils.colorize(p));
			return true;
		}
		if (args[0].equalsIgnoreCase("add")) {
			if(args.length != 3) {
				sender.sendMessage(CoreUtils.prefixes("survival") + "There was an error in your syntax. /perks u[se] <division> <perk>");
				return true;
			}
			if(Division.valueOf(args[1].toUpperCase()) == null) {
				sender.sendMessage(CoreUtils.prefixes("survival") + "Unknown division.");
				//TODO list divisions
				return true;
			}
			if(Perks.getPerk(Division.valueOf(args[1].toUpperCase()), args[2]) == null) {
				sender.sendMessage(CoreUtils.prefixes("survival") + "Unknown perk.");
				return true;
			}
			Division division = Division.valueOf(args[1].toUpperCase());
			Perks perks = Perks.getPerk(division, args[2]);
			SurvivalPlayer player = SurvivalUtils.getSurvivalPlayer(((Player)sender));
			if(!player.hasPerk(perks)) {
				player.addPerk(perks, 2D);
				player.sendMessage("Added perk.");
				return true;
			}
			player.sendMessage("You already have that perk.");
		}
		if (args[0].equalsIgnoreCase("u") || args[0].equalsIgnoreCase("use")) {
			if(args.length != 3) {
				sender.sendMessage(CoreUtils.prefixes("survival") + "There was an error in your syntax. /perks u[se] <division> <perk>");
				return true;
			}
			if(Division.valueOf(args[1].toUpperCase()) == null) {
				sender.sendMessage(CoreUtils.prefixes("survival") + "Unknown division.");
				//TODO list divisions
				return true;
			}
			if(Perks.getPerk(Division.valueOf(args[1].toUpperCase()), args[2]) == null) {
				sender.sendMessage(CoreUtils.prefixes("survival") + "Unknown perk.");
				return true;
			}
			Division division = Division.valueOf(args[1].toUpperCase());
			Perks perks = Perks.getPerk(division, args[2]);
			SurvivalPlayer player = SurvivalUtils.getSurvivalPlayer(((Player)sender));
			if(!player.hasPerk(perks)) {
				sender.sendMessage(CoreUtils.prefixes("survival") + "You don't have that perk.");
				return true;
			}
			player.activatePerk(perks);
		}

		return true;

	}
}

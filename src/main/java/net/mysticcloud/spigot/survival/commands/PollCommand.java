package net.mysticcloud.spigot.survival.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import net.mysticcloud.spigot.core.commands.listeners.CommandTabCompleter;
import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.survival.MysticSurvival;
import net.mysticcloud.spigot.survival.utils.polls.PollUtils;

public class PollCommand implements CommandExecutor {

	public PollCommand(MysticSurvival plugin, String... cmd) {
		for (String comd : cmd) {
			PluginCommand com = plugin.getCommand(comd);
			com.setExecutor(this);
			com.setTabCompleter(new CommandTabCompleter());
		}

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			sender.sendMessage(CoreUtils.prefixes("polls") + "Below is a list of avalible commands:");
			sender.sendMessage(CoreUtils.colorize("&e/poll create [name] [question(arg2,...)]"));
			sender.sendMessage(CoreUtils.colorize("&e/poll options [name] [id] [answer(arg3,...)]"));
			sender.sendMessage(CoreUtils.colorize("&e/poll publish [name]"));
			sender.sendMessage(CoreUtils.colorize("&e/poll vote [id]"));
			return false;
		}
		/*
		 *  @usage /poll create [name] [question(arg2,...)]
		 */
		if(args[0].equalsIgnoreCase("create")) {
			if(args.length <= 3) {
				sender.sendMessage(CoreUtils.colorize("&e/poll create [name] [question(arg2,...)]"));
				return false;
			}
			String question = "";
			for(int i = 2; i!=args.length; i++) {
				question = question == "" ? args[i] : question + " " + args[i];
			}
			PollUtils.createPoll(args[1], question);
			sender.sendMessage(CoreUtils.prefixes("polls") + "Created poll \"" + args[1] + "\".");
			
		}
		/*
		 *  @usage /poll options [name] [id] [answer(arg3,...)]
		 */
		if(args[0].equalsIgnoreCase("options")) {
			if(args.length <= 4) {
				sender.sendMessage(CoreUtils.colorize("&e/poll options [name] [id] [answer(arg3,...)]"));
				return false;
			}
			String answer = "";
			for(int i = 3; i!=args.length; i++) {
				answer = answer == "" ? args[i] : answer + " " + args[i];
			}
			PollUtils.getPoll(args[1]).addOption(Integer.parseInt(args[2]), answer);
			sender.sendMessage(CoreUtils.prefixes("polls") + "Added option \"" + args[2] + "\" to poll \"" + args[1] + "\".");
			
		}
		/*
		 *  @usage /poll publish [name]
		 */
		if(args[0].equalsIgnoreCase("publish")) {
			if(args.length <= 2) {
				sender.sendMessage(CoreUtils.colorize("&e/poll publish [name]"));
				return false;
			}
			PollUtils.publishPoll(sender, args[1]);
			
		}
		/*
		 *  @usage /poll vote [id]
		 */
		if(args[0].equalsIgnoreCase("vote")) {
			if(sender instanceof Player) {
				if(args.length <= 2) {
					sender.sendMessage(CoreUtils.colorize("&e/poll vote [id]"));
					return false;
				}
				PollUtils.vote(((Player)sender), Integer.parseInt(args[1]));
			}
		}
		return true;
	}

//	private String formatUsername(String username) {
//		return username.toLowerCase().endsWith("s") ? username + "'" : username + "'s";
//
//	}
}

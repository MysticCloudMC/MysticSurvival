package net.mysticcloud.spigot.survival.utils.polls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.survival.MysticSurvival;
import net.mysticcloud.spigot.survival.runnables.PollTimer;

public class PollUtils {
	
	private static MysticSurvival plugin;
	
	private static Map<String, Poll> polls = new HashMap<>();
	private static List<Poll> pollQueue = new ArrayList<>();
	private static Poll poll = null;
	
	
	public static void start(MysticSurvival main) {
		plugin = main;
	}
	
	public static MysticSurvival getPlugin() {
		return plugin;
	}
	
	public static Poll createPoll(String name, String question) {
		Poll poll = new Poll(name, question);
		polls.put(name, poll);
		
		return poll;
	}

	public static Poll getPoll(String name) {
		return polls.get(name);
	}
	
	private static void publishPoll() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(CoreUtils.colorize("&e------------------[&fPoll&e]------------------"));
			player.sendMessage(CoreUtils.colorize(poll.getQuestion()));
			for(Entry<Integer,String> entry : poll.getOptions().entrySet()) {
				player.sendMessage(CoreUtils.colorize("&e" + entry.getKey() + "&f: " + entry.getValue()));
			}
			player.sendMessage(CoreUtils.colorize("&fThis poll will last &e" + poll.getTimer() + "&f seconds. &7&l(/poll vote [id])"));
			player.sendMessage(CoreUtils.colorize("&e-----------------------------------------"));
		}
		Bukkit.getScheduler().runTaskLater(plugin, new PollTimer(poll), poll.getTimer()*20);
	}
	

	public static void publishPoll(CommandSender sender, String name) {
		if(poll == null) {
			poll = getPoll(name);
			publishPoll();
		} else {
			pollQueue.add(poll);
			sender.sendMessage(CoreUtils.colorize(CoreUtils.prefixes("polls") + "You're poll has been put in the queue! (" + pollQueue.size() + ")"));
		}
	}

	public static void nextPoll() {
		poll = pollQueue.get(0);
		publishPoll();
		pollQueue.remove(0);
	}

	public static void vote(Player player, int id) {
		poll.vote(player,id);
	}
}

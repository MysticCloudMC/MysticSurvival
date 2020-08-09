package net.mysticcloud.spigot.survival.utils.polls;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import net.mysticcloud.spigot.core.utils.CoreUtils;

public class Poll {
	
	
	int timer = 30;
	String name;
	String question;
	Map<Integer,String> options = new HashMap<>();
	
	Map<UUID, Integer> votes = new HashMap<>();
	
	protected Poll(String name, String question) {
		this.name = name;
		this.question = question;
	}
	
	public String getName() {
		return name;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public void addOption(int id, String answer) {
		options.put(id,answer);
	}

	public int getTimer() {
		return timer;
	}

	public Map<Integer, String> getOptions() {
		return options;
	}

	protected void vote(Player player, int id) {
		if(!votes.containsKey(player.getUniqueId())) {
			votes.put(player.getUniqueId(), id);
			player.sendMessage(CoreUtils.prefixes("polls") + "You have voted!");
		} else {
			player.sendMessage(CoreUtils.prefixes("polls") + "You have already voted.");
		}
		
	}
	
	public Map<UUID, Integer> getVotes() {
		return votes;
	}

}

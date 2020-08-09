package net.mysticcloud.spigot.survival.runnables;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;

import net.mysticcloud.spigot.survival.utils.polls.Poll;
import net.mysticcloud.spigot.survival.utils.polls.PollUtils;

public class PollTimer implements Runnable {
	
	Poll poll;
	
	public PollTimer(Poll poll) {
		this.poll = poll;
	}

	public void run() {
		
		Map<UUID,Integer> map = poll.getVotes();
		
		List<Entry<UUID, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());

        Map<UUID, Integer> result = new LinkedHashMap<>();
        for (Entry<UUID, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
       
        
        for(Entry<UUID,Integer> entry : result.entrySet()) {
        	Bukkit.broadcastMessage("-" + entry.getValue());
        }
		
		PollUtils.nextPoll();
	}

}

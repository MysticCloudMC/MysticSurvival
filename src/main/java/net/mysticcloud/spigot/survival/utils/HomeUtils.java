package net.mysticcloud.spigot.survival.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.mysticcloud.spigot.core.utils.warps.Warp;
import net.mysticcloud.spigot.core.utils.warps.WarpUtils;

public class HomeUtils {
	
	public static List<Warp> getHomes(UUID uid){
		List<Warp> homes = new ArrayList<>();
		for(Warp home : WarpUtils.getWarps("homes"))
			if(home.metadata("Owner").equals(uid.toString()))
				homes.add(home);
		return homes;
	}

}

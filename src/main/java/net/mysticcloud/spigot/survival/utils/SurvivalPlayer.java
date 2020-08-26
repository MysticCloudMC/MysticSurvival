package net.mysticcloud.spigot.survival.utils;

import net.mysticcloud.spigot.core.utils.MysticPlayer;

public class SurvivalPlayer {
	
	
	MysticPlayer player;
	Division division;
	
	protected SurvivalPlayer(MysticPlayer player) {
		this.player = player;
	}
	
	public void setDivision(Division division) {
		this.division = division;
	}
	
	public Division getDivision() {
		return division;
	}

}

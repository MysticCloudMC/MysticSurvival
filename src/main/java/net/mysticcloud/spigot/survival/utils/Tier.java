package net.mysticcloud.spigot.survival.utils;

public enum Tier {
	LOW(0,10),
	MID(11,30),
	HIGH(31,50),
	EXTREME(51,100);
	
	int minLevel;
	int maxLevel;
	
	Tier(int minLevel, int maxLevel){
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
	}
	
	public static Tier getTier(int level) {
		for(Tier t : values()) {
			if(t.minLevel <= level && t.maxLevel >= level) return t;
		}
		return LOW;
	}

}

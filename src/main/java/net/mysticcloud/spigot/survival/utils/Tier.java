package net.mysticcloud.spigot.survival.utils;

public enum Tier {
	FIRST(0, 10), SECOND(11, 30), THIRD(31, 50), FOURTH(51, 100);

	int minLevel;
	int maxLevel;

	Tier(int minLevel, int maxLevel) {
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
	}

	public static Tier getTier(int level) {
		for (Tier t : values()) {
			if (t.minLevel <= level && t.maxLevel >= level)
				return t;
		}
		return FIRST;
	}

	public String getName() {
		switch (name()) {
		case "FIRST":
			return "&e&lWarrier";
		case "SECOND":
			return "&5&lMage";
		case "THIRD":
			return "&b&lDemi-God";
		case "FOURTH":
			return "&c&lCelestial";
		default:
			return name();
		}
	}

}

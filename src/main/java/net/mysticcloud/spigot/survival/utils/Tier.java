package net.mysticcloud.spigot.survival.utils;

public enum Tier {
	FIRST(0, 9), SECOND(10, 19), THIRD(20, 29), FOURTH(30, 39), FIFTH(40, 49), SIXTH(50, Integer.MAX_VALUE);

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
			return "&e&lHuman";
		case "SECOND":
			return "&2&lSoldier";
		case "THIRD":
			return "&7&lKnight";
		case "FOURTH":
			return "&d&lWizard";
		case "FIFTH":
			return "&b&lDemi-God";
		case "SIXTH":
			return "&c&lCelestial";
		default:
			return name();
		}
	}

}

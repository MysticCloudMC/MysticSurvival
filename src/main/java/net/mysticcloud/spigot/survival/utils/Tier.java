package net.mysticcloud.spigot.survival.utils;

public enum Tier {
	HUMAN(0, 10), WIZARD(11, 30), DEMI_GOD(31, 50), CELESTIAL(51, 100);

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
		return HUMAN;
	}

	public String getName() {
		switch (name()) {
		case "HUMAN":
			return "&e&lHuman";
		case "WIZARD":
			return "&5&lWizard";
		case "DEMI_GOD":
			return "&b&lDemi-God";
		case "CELESTIAL":
			return "&c&lCelestial";
		default:
			return name();
		}
	}

}

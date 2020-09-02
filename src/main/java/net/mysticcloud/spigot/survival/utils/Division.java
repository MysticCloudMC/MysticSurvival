package net.mysticcloud.spigot.survival.utils;

public enum Division {

	ARCHER, MAGE, WARRIER;

	public String getPerkPrefix() {
		switch (this) {
		case ARCHER:
			return "Archery";
		case MAGE:
		case WARRIER:
			return name();
		default:
			return "";
		}
	}

}

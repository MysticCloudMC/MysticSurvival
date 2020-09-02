package net.mysticcloud.spigot.survival.utils;

public enum Division {

	ARCHER, MAGE, WARRIOR;

	public String getPerkPrefix() {
		switch (this) {
		case ARCHER:
			return "Archery";
		case MAGE:
		case WARRIOR:
			return name();
		default:
			return "";
		}
	}

}

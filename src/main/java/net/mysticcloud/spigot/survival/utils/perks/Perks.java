package net.mysticcloud.spigot.survival.utils.perks;

import java.util.UUID;

import net.mysticcloud.spigot.survival.utils.Division;

public enum Perks {
	
	ARCHERY_RANGE_I("Archery-RangeI"),
	ARCHERY_RANGE_II("Archery-RangeII"),
	ARCHERY_SEEKER("Archery-Seeker"),
	MAGE_SWAP("Mage-Swap");
	
	String name;
	
	Perks(String name){
		this.name = name;
	}
	
	public static String getName(Perk perk) {
		String name = "";
		if(perk instanceof ArcheryPerk) {
			name = "Archery";
			if(perk instanceof ArcheryPerkRangeI)
				name = name + "-RangeI";
			if(perk instanceof ArcheryPerkRangeII)
				name = name + "-RangeII";
			if(perk instanceof ArcheryPerkSeeker)
				name = name + "-Seeker";
		}
		if(perk instanceof MagePerk) {
			name = "Mage";
			if(perk instanceof MagePerkSwap)
				name = name + "-Swap";
		}
		return name;
	}
	
	public String getName() {
		return name;
	}
	
	public static Perks getPerk(String name) {
		switch(name.toUpperCase()) {
		case "ARCHERY-RANGEI": return ARCHERY_RANGE_I;
		case "ARCHERY-RANGEII": return ARCHERY_RANGE_II;
		case "ARCHERY-SEEKER": return ARCHERY_SEEKER;
		case "MAGE-SWAP": return MAGE_SWAP;
		default: return null;
		}
	}
	
	public Perk getPerk(UUID uid) {
		switch(name) {
		case "Archery-RangeI": return new ArcheryPerkRangeI(uid);
		case "Archery-RangeII": return new ArcheryPerkRangeII(uid);
		case "Archery-Seeker": return new ArcheryPerkSeeker(uid);
		case "Mage-Swap": return new MagePerkSwap(uid);
		default: return new Perk(uid);
		}
	}

	public static Perks getPerk(Division division, String perk) {
		String div = "";
		switch(division.name()) {
		case "ARCHER":
			div = "ARCHERY";
			break;
		case "MAGE":
			div = division.name();
		default: break;
		}
		return getPerk(div + "-" + perk);
	}

	

}

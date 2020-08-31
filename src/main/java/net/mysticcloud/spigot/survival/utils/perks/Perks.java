package net.mysticcloud.spigot.survival.utils.perks;

import java.util.UUID;

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
		switch(name) {
		case "Archery-RangeI": return ARCHERY_RANGE_I;
		case "Archery-RangeII": return ARCHERY_RANGE_II;
		case "Archery-Seeker": return ARCHERY_SEEKER;
		case "Mage-Swap": return MAGE_SWAP;
		default: return ARCHERY_RANGE_I;
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

	

}

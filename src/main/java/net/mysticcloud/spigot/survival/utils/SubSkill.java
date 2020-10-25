package net.mysticcloud.spigot.survival.utils;

public enum SubSkill {
	CRAFTING("Crafting"),
	SPELL("SpellCraft"),
	ENHANCE("Enhancement");
	
	String name;
	
	SubSkill(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public static SubSkill fromName(String skill) throws NullPointerException {
		for(SubSkill sskill : values()) {
			if(sskill.getName().equals(skill)) return sskill;
		}
		return null;
	}

}

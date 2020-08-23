package net.mysticcloud.spigot.survival.utils;

import java.util.ArrayList;
import java.util.List;

import net.mysticcloud.spigot.core.utils.CoreUtils;

public enum Enhancement {
	DISARM("&1Disarm&7 Chance: &1&l", true),
	SPEED("&aSpeed&7 Modification: &a&l", false),
	VAMPIRISM("&4Vampirism&7 Chance: &4&l", true),
	DODGE("&eDodge&7 Chance: &e&l", true),
	FIRE("&cFire&7 Damage: &c&l", true),
	FROST("&aFrost&7 Damage: &a&l", true),
	PROTECTION("&dArmor&7 Modification: &d&l", false);
	
	String name;
	boolean weapon;
	
	Enhancement(String name, boolean weapon){
		this.name = CoreUtils.colorize(name);
		this.weapon = weapon;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isWeapon() {
		return weapon;
	}
	
	

}

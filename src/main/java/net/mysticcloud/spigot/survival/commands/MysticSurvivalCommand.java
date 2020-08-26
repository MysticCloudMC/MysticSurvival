package net.mysticcloud.spigot.survival.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import net.mysticcloud.spigot.core.commands.listeners.CommandTabCompleter;
import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.survival.MysticSurvival;
import net.mysticcloud.spigot.survival.utils.Division;
import net.mysticcloud.spigot.survival.utils.Enhancement;
import net.mysticcloud.spigot.survival.utils.SurvivalPlayer;
import net.mysticcloud.spigot.survival.utils.SurvivalUtils;

public class MysticSurvivalCommand implements CommandExecutor {

	public MysticSurvivalCommand(MysticSurvival plugin, String... cmd) {
		for (String comd : cmd) {
			PluginCommand com = plugin.getCommand(comd);
			com.setExecutor(this);
			com.setTabCompleter(new CommandTabCompleter());
		}

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("mysticcloud.admin")) {
			if (args.length == 0) {
				sender.sendMessage(
						CoreUtils.colorize(CoreUtils.prefixes("admin") + "Usage: /msurvival <subcommand> [args]"));
				sender.sendMessage(CoreUtils.colorize(CoreUtils.prefixes("admin") + "Here is a list of sub-commands:"));
				sender.sendMessage(CoreUtils.colorize("&3giveRandomArmor [level]"));
				sender.sendMessage(CoreUtils.colorize("&3giveRandomWeapon [level]"));
				sender.sendMessage(CoreUtils.colorize("&3soulbind"));
				sender.sendMessage(CoreUtils.colorize("&3giveRandomBook"));
				sender.sendMessage(CoreUtils.colorize("&3enhance <enhancement> <level>"));

				return true;
			}
			if (args[0].equalsIgnoreCase("giveRandomBook")) {
				if (sender instanceof Player) {
					sender.sendMessage(CoreUtils.prefixes("admin") + "Generating random book...");
					((Player) sender).getInventory()
							.addItem(SurvivalUtils.bookGenerator(args.length == 2 ? Integer.parseInt(args[1])
									: CoreUtils.getMysticPlayer(((Player) sender)).getLevel()));
				}
			}
			if (args[0].equalsIgnoreCase("save")) {
				if(sender instanceof Player) {
					SurvivalUtils.getSurvivalPlayer(((Player) sender).getUniqueId()).save();
				}
			}
			if (args[0].equalsIgnoreCase("division")) {
				if(sender instanceof Player) {
					SurvivalPlayer player = SurvivalUtils.getSurvivalPlayer(((Player) sender).getUniqueId());
					if(args.length == 2) {
						
						player.setDivision(Division.valueOf(args[1].toUpperCase()));
					}
					sender.sendMessage("Your division is: " + player.getDivision());
					
				}
			}
			if (args[0].equalsIgnoreCase("enhance")) {
				if (sender instanceof Player) {
					if (args.length == 2) {
						String s = "";
						for (Enhancement en : Enhancement.values()) {
							s = (s == "" ? "&f" : "&7,&f ") + en.name().toLowerCase();
						}
						s = CoreUtils.colorize(s);
						sender.sendMessage(CoreUtils.prefixes("admin")
								+ "Unknown enhancement. Here's a list of enhancements: " + s);
					}
					if (args.length == 3) {
						if (args[1].equalsIgnoreCase("remove") || args[1].equalsIgnoreCase("delete")) {
							try {
								SurvivalUtils.removeEnhancement(((Player) sender).getEquipment().getItemInMainHand(),
										Enhancement.valueOf(args[2].toUpperCase()));
							} catch (IllegalArgumentException ex) {
								String s = "";
								for (Enhancement en : Enhancement.values()) {
									s = (s == "" ? "&f" : "&7,&f ") + en.name().toLowerCase();
								}
								s = CoreUtils.colorize(s);
								sender.sendMessage(CoreUtils.prefixes("admin")
										+ "Unknown enhancement. Here's a list of enhancements: " + s);
							}
						}
						try {
							try {
								SurvivalUtils.enhance(((Player) sender).getEquipment().getItemInMainHand(),
										Enhancement.valueOf(args[1].toUpperCase()), Integer.parseInt(args[2]));
							} catch (NumberFormatException ex) {
								sender.sendMessage(
										CoreUtils.prefixes("admin") + "You didn't use a number for the level.");
								sender.sendMessage(CoreUtils.prefixes("admin")
										+ "Usage: /msurvival enhance <enhancement> <level>");
							}
						} catch (IllegalArgumentException ex) {
							String s = "";
							for (Enhancement en : Enhancement.values()) {
								s = (s == "" ? "&f" : "&7,&f ") + en.name().toLowerCase();
							}
							s = CoreUtils.colorize(s);
							sender.sendMessage(CoreUtils.prefixes("admin")
									+ "Unknown enhancement. Here's a list of enhancements: " + s);
						}

					} else {
						sender.sendMessage(
								CoreUtils.prefixes("admin") + "Usage: /msurvival enhance <enhancement> <level>");
					}

				}
			}
			if (args[0].equalsIgnoreCase("giveRandomArmor")) {
				if (sender instanceof Player) {
					sender.sendMessage(CoreUtils.prefixes("admin") + "Generating random armor...");
					((Player) sender).getInventory()
							.addItem(SurvivalUtils.armorGenerator(args.length == 2 ? Integer.parseInt(args[1])
									: CoreUtils.getMysticPlayer(((Player) sender)).getLevel()));
				}
			}
			if (args[0].equalsIgnoreCase("giveRandomWeapon")) {
				sender.sendMessage(CoreUtils.prefixes("admin") + "Generating random weapon...");
				if (sender instanceof Player) {
					((Player) sender).getInventory()
							.addItem(SurvivalUtils.weaponGenerator(args.length == 2 ? Integer.parseInt(args[1])
									: (CoreUtils.getMysticPlayer(((Player) sender)).getLevel())));
				}
			}
			if (args[0].equalsIgnoreCase("soulbind")) {
				sender.sendMessage(CoreUtils.prefixes("admin") + "Binding...");
				if (sender instanceof Player && ((Player) sender).getItemInHand() != null) {
					SurvivalUtils.soulbind((Player) sender, ((Player) sender).getItemInHand());
				}
			}

		}

		return true;

	}

//	private String formatUsername(String username) {
//		return username.toLowerCase().endsWith("s") ? username + "'" : username + "'s";
//
//	}
}

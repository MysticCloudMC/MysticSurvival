package net.mysticcloud.spigot.survival.listeners;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.mysticcloud.spigot.core.Main;
import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.core.utils.particles.formats.RandomFormat;
import net.mysticcloud.spigot.survival.MysticSurvival;
import net.mysticcloud.spigot.survival.utils.SurvivalUtils;

public class PlayerAttackListener implements Listener {

	public PlayerAttackListener(MysticSurvival plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onItemDamage(PlayerItemDamageEvent e) {
		if(!e.getItem().hasItemMeta()) return;
		if(!e.getItem().getItemMeta().hasLore()) return;
		ItemStack i = e.getItem();
		ItemMeta m = i.getItemMeta();
		List<String> lore = m.getLore();
		Map<String, String> replacements = new HashMap<>();
		for(String s : lore) {
			if(s.contains(":")) {
				if(ChatColor.stripColor(s).contains("Durability")) {
					int dur = Integer.parseInt(ChatColor.stripColor(s).split(": ")[1].split("/")[0]);
					int max = Integer.parseInt(ChatColor.stripColor(s).split(": ")[1].split("/")[1]);
					lore.set(lore.indexOf(s), SurvivalUtils.getDurabilityString(dur-1,max));
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerAttack(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Projectile) {
			if (e.getDamager().hasMetadata("targeting") && e.getEntity() instanceof LivingEntity) {
				SurvivalUtils
						.getSurvivalPlayer(
								Bukkit.getPlayer((UUID) e.getDamager().getMetadata("targeting").get(0).value()))
						.target((LivingEntity) e.getEntity());
				SurvivalUtils.removeTargeter((UUID) e.getDamager().getMetadata("targeting").get(0).value());
			}
			if (e.getDamager().hasMetadata("fire")) {
				e.getEntity()
						.setFireTicks(Integer.parseInt("" + e.getDamager().getMetadata("fire").get(0).value()) * 20);
			}
			if (e.getDamager().hasMetadata("vampirism")) {
				if (CoreUtils.getRandom().nextInt(
						100) <= Integer.parseInt("" + e.getDamager().getMetadata("vampirism").get(0).value()) * 20) {
					((LivingEntity) ((Projectile) e.getDamager()).getShooter())
							.setHealth(((LivingEntity) ((Projectile) e.getDamager()).getShooter()).getHealth()
									+ (e.getDamage() / 2));
				}
			}
			if (e.getDamager().hasMetadata("frost")) {
				if (e.getEntity() instanceof LivingEntity) {
					((LivingEntity) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
							Integer.parseInt("" + e.getDamager().getMetadata("frost").get(0).value()) * 20, 1));
					RandomFormat format = new RandomFormat();
					format.particle(Particle.REDSTONE);
					format.setDustOptions(new DustOptions(Color.AQUA, 1));
					CoreUtils.particles.put(e.getEntity().getUniqueId(), format);
					Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {

						@Override
						public void run() {
							CoreUtils.particles__remove.add(e.getEntity().getUniqueId());
						}

					}, Integer.parseInt("" + e.getDamager().getMetadata("frost").get(0).value()) * 20);
				}
			}
			return;
		}
		if (e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity) {
			Player player = (Player) e.getDamager();
			if (SurvivalUtils.getSurvivalPlayer(player).getStamina() > 10) {
				SurvivalUtils.getSurvivalPlayer(player).useStamina(10);
			} else {
				e.setCancelled(true);
				return;
			}
			if (e.getEntity() instanceof Monster) {
				e.setDamage((e.getDamage() + CoreUtils.getMysticPlayer(((Player) e.getDamager())).getLevel() * 0.3));
			}

			if (e.getEntity() instanceof Player) {
				if (((Player) e.getEntity()).getEquipment().getItemInMainHand() != null) {
					if (((Player) e.getEntity()).getEquipment().getItemInMainHand().hasItemMeta()) {
						if (((Player) e.getEntity()).getEquipment().getItemInMainHand().getItemMeta().hasLore()) {
							ItemStack s = ((Player) e.getEntity()).getEquipment().getItemInMainHand();
							for (String a : s.getItemMeta().getLore()) {
								if (ChatColor.stripColor(a).split(":")[0].equals("Dodge Chance")) {
									if (CoreUtils.getRandom().nextInt(100) < Integer
											.parseInt(ChatColor.stripColor(a).split(": ")[1])) {
										e.setCancelled(true);
										((Player) e.getEntity()).sendMessage(CoreUtils.colorize("&a&lDodge!"));
										((Player) e.getDamager()).sendMessage(CoreUtils.colorize("&c&lMiss!"));
										break;
									}

								}
							}
						}
					}
				}
			}
			if (((Player) e.getDamager()).getEquipment().getItemInMainHand() != null) {
				if (((Player) e.getDamager()).getEquipment().getItemInMainHand().hasItemMeta()) {
					if (((Player) e.getDamager()).getEquipment().getItemInMainHand().getItemMeta().hasLore()) {
						ItemStack s = ((Player) e.getDamager()).getEquipment().getItemInMainHand();
						for (String a : s.getItemMeta().getLore()) {
							if (a.contains(":")) {
								if (ChatColor.stripColor(a).split(":")[0].equals("Disarm Chance")) {
									if (((LivingEntity) e.getEntity()).getEquipment().getItemInMainHand() != null) {
										if (CoreUtils.getRandom().nextInt(100) < Integer
												.parseInt(ChatColor.stripColor(a).split(": ")[1])) {
											if (e.getEntity() instanceof Player) {
												Item i = e.getEntity().getWorld().dropItemNaturally(
														e.getEntity().getLocation(), ((LivingEntity) e.getEntity())
																.getEquipment().getItemInMainHand());
												i.setPickupDelay(40);
												((LivingEntity) e.getEntity()).getEquipment().getItemInMainHand()
														.setAmount(0);
											}

										}

									}
								}

								if (ChatColor.stripColor(a).split(":")[0].equals("Fire Damage")) {
									e.getEntity().setFireTicks(
											Integer.parseInt(ChatColor.stripColor(a).split(": ")[1]) * 20);
								}
								if (ChatColor.stripColor(a).split(":")[0].equals("Power Attack")) {
									if (ChatColor.stripColor(a).split(": ")[1].equalsIgnoreCase("true")) {
										player.getEquipment().getItemInMainHand().setDurability(
												(short) (player.getEquipment().getItemInMainHand().getDurability()
														- 4));
										e.setDamage(e.getDamage() * 3);
									}

								}

								if (ChatColor.stripColor(a).split(":")[0].equals("Frost Damage")) {
									((LivingEntity) e.getEntity())
											.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
													Integer.parseInt(ChatColor.stripColor(a).split(": ")[1]) * 20, 1));
									RandomFormat format = new RandomFormat();
									format.particle(Particle.REDSTONE);
									format.setDustOptions(new DustOptions(Color.AQUA, 1));
									CoreUtils.particles.put(e.getEntity().getUniqueId(), format);
									Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {

										@Override
										public void run() {
											CoreUtils.particles__remove.add(e.getEntity().getUniqueId());
										}

									}, Integer.parseInt(ChatColor.stripColor(a).split(": ")[1]) * 20);
								}

								if (ChatColor.stripColor(a).split(":")[0].equals("Vampirism Chance")) {
									if (e.getEntity() instanceof Player) {
										if (CoreUtils.getRandom().nextInt(100) <= Integer
												.parseInt(ChatColor.stripColor(a).split(": ")[1])) {
											RandomFormat format = new RandomFormat();
											format.particle(Particle.REDSTONE);
											format.setDustOptions(new DustOptions(Color.RED, 2));
											for (int i = 0; i != 10; i++) {
												format.display(e.getEntity().getLocation(), i);
												format.display(e.getDamager().getLocation(), i);
											}

											try {
												((LivingEntity) e.getDamager())
														.setHealth(((LivingEntity) e.getDamager()).getHealth()
																+ (e.getDamage() / 4));
											} catch (IllegalArgumentException ex) {
												((LivingEntity) e.getDamager())
														.setHealth(((LivingEntity) e.getDamager()).getMaxHealth());
											}

										}
									} else {
										RandomFormat format = new RandomFormat();
										format.particle(Particle.REDSTONE);
										format.setDustOptions(new DustOptions(Color.RED, 2));
										for (int i = 0; i != 10; i++) {
											format.display(e.getEntity().getLocation(), i);
											format.display(e.getDamager().getLocation(), i);
										}
										((LivingEntity) e.getDamager())
												.setHealth(((LivingEntity) e.getDamager()).getHealth() + (e.getDamage()
														* (Integer.parseInt(ChatColor.stripColor(a).split(": ")[1])
																/ 10)));
									}
								}

							}
						}
					}
				}
			}
		}

	}
}

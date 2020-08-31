package net.mysticcloud.spigot.survival.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.mysticcloud.spigot.core.Main;
import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.core.utils.MysticPlayer;
import net.mysticcloud.spigot.core.utils.SpawnReason;
import net.mysticcloud.spigot.core.utils.particles.formats.RandomFormat;
import net.mysticcloud.spigot.survival.MysticSurvival;
import net.mysticcloud.spigot.survival.runnables.SeekerArrowRunnable;
import net.mysticcloud.spigot.survival.utils.Enhancement;
import net.mysticcloud.spigot.survival.utils.HomeUtils;
import net.mysticcloud.spigot.survival.utils.SurvivalPlayer;
import net.mysticcloud.spigot.survival.utils.SurvivalUtils;
import net.mysticcloud.spigot.survival.utils.spells.FireballSpell;
import net.mysticcloud.spigot.survival.utils.spells.FlameSpell;
import net.mysticcloud.spigot.survival.utils.spells.HealSpell;
import net.mysticcloud.spigot.survival.utils.spells.InvisibilitySpell;
import net.mysticcloud.spigot.survival.utils.spells.Spell;
import net.mysticcloud.spigot.survival.utils.spells.TeleportSpell;

public class PlayerListener implements Listener {

	public PlayerListener(MysticSurvival plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		SurvivalUtils.getSurvivalPlayer(e.getPlayer()).save();
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		if (e.getEntity() instanceof Monster && e.getEntity().getKiller() != null
				&& e.getEntity().hasMetadata("level")) {
			int level = (int) e.getEntity().getMetadata("level").get(0).value();
			CoreUtils.getMysticPlayer(e.getEntity().getKiller())
					.gainXP(CoreUtils.getMoneyFormat(((double) level / 100) * CoreUtils.getRandom().nextInt(25)));
			// Drops?

			SurvivalUtils.handleDrops(level, e.getEntity().getLocation());
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		try {
			if (e.getCurrentItem().getType().equals(Material.BOOK) && e.getCursor() != null
					&& !e.getCursor().getType().equals(Material.BOOK)
					&& !e.getCursor().getType().equals(Material.AIR)) {
				SurvivalUtils.enhanceInInventory(e.getCursor(), e.getCurrentItem());
				e.setCancelled(true);
			}
		} catch (NullPointerException ex) {

		}
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e) {
		if (e.getItemDrop().getItemStack().hasItemMeta()) {
			if (e.getItemDrop().getItemStack().getItemMeta().hasLore()) {
				for (String s : e.getItemDrop().getItemStack().getItemMeta().getLore()) {
					if (ChatColor.stripColor(s).equalsIgnoreCase("Soulbound")) {
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if ((e.getPlayer()).getEquipment().getItemInMainHand() == null)
				return;
			if (!(e.getPlayer()).getEquipment().getItemInMainHand().hasItemMeta())
				return;
			if (!(e.getPlayer()).getEquipment().getItemInMainHand().getItemMeta().hasLore())
				return;
			if (!e.getPlayer().getEquipment().getItemInMainHand().getType().equals(Material.STICK))
				return;

			ItemStack s = (e.getPlayer()).getEquipment().getItemInMainHand();
//			List<String> lore = new ArrayList<>();
			for (String a : s.getItemMeta().getLore()) {
				if (ChatColor.stripColor(a).contains("Teleportation Spell")) {
					Spell spell = new TeleportSpell(e.getPlayer(), e.getPlayer().getTargetBlock(null, 200).getLocation()
							.add(0, 1, 0).setDirection(e.getPlayer().getEyeLocation().getDirection()));
					SurvivalPlayer player = SurvivalUtils.getSurvivalPlayer(e.getPlayer());
					if (player.getMana() >= spell.getCost()) {
						spell.activate();
						player.useMana(spell.getCost());
					} else {
						player.useMana(0);
					}
				}
				if (ChatColor.stripColor(a).contains("Heal Spell")) {
					Spell spell = new HealSpell(e.getPlayer());
					SurvivalPlayer player = SurvivalUtils.getSurvivalPlayer(e.getPlayer());
					if (player.getMana() >= spell.getCost()) {
						spell.activate();
						player.useMana(spell.getCost());
					} else {
						player.useMana(0);
					}
				}
				if (ChatColor.stripColor(a).contains("Fireball Spell")) {
					Spell spell = new FireballSpell(e.getPlayer());
					SurvivalPlayer player = SurvivalUtils.getSurvivalPlayer(e.getPlayer());
					if (player.getMana() >= spell.getCost()) {
						spell.activate();
						player.useMana(spell.getCost());
					} else {
						player.useMana(0);
					}
				}

				if (ChatColor.stripColor(a).contains("Flame Spell")) {
					Spell spell = new FlameSpell(e.getPlayer());
					SurvivalPlayer player = SurvivalUtils.getSurvivalPlayer(e.getPlayer());
					if (player.getMana() >= spell.getCost()) {
						spell.activate();
						player.useMana(spell.getCost());
					} else {
						player.useMana(0);
					}
				}

				if (ChatColor.stripColor(a).contains("Invisibility Spell")) {
					Spell spell = new InvisibilitySpell(e.getPlayer());
					SurvivalPlayer player = SurvivalUtils.getSurvivalPlayer(e.getPlayer());
					if (player.getMana() >= spell.getCost()) {
						spell.activate();
						player.useMana(spell.getCost());
					} else {
						player.useMana(0);
					}
				}

//				lore.add(a);
			}
			return;
//			ItemMeta im = s.getItemMeta();
//			im.setLore(lore);
//			s.setItemMeta(im);

		}
		if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if ((e.getPlayer()).getEquipment().getItemInMainHand() != null) {
				if ((e.getPlayer()).getEquipment().getItemInMainHand().hasItemMeta()) {
					if ((e.getPlayer()).getEquipment().getItemInMainHand().getItemMeta().hasLore()) {
						ItemStack s = (e.getPlayer()).getEquipment().getItemInMainHand();
						List<String> lore = new ArrayList<>();
						for (String a : s.getItemMeta().getLore()) {
							if (a.contains(":")) {
								if (ChatColor.stripColor(a).split(":")[0].equals("Power Attack")) {
									if (ChatColor.stripColor(a).split(": ")[1].equalsIgnoreCase("false")) {
										lore.add(Enhancement.POWER_ATTACK.getName() + "true");
										continue;
									} else {
										lore.add(Enhancement.POWER_ATTACK.getName() + "false");
										continue;
									}
								}
							}
							lore.add(a);
						}
						ItemMeta im = s.getItemMeta();
						im.setLore(lore);
						s.setItemMeta(im);
					}
				}
			}
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {

			if (((Player) e.getEntity()).getHealth() - e.getDamage() <= 0) {
				e.setCancelled(true);
				List<ItemStack> adds = new ArrayList<>();
				for (ItemStack i : ((Player) e.getEntity()).getInventory().getContents()) {
					if (i != null) {
						if (i.hasItemMeta()) {
							if (i.getItemMeta().hasLore()) {
								for (String s : i.getItemMeta().getLore()) {
									if (ChatColor.stripColor(s).equalsIgnoreCase("Soulbound")) {
										adds.add(i);
										continue;
									}
								}
							}
						}
						if (!adds.contains(i))
							e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), i);
					}

				}
				((Player) e.getEntity()).getInventory().clear();
				e.setCancelled(true);
				Bukkit.getScheduler().runTaskLater(SurvivalUtils.getPlugin(), new Runnable() {
					public void run() {
						if (HomeUtils.getHomes(e.getEntity().getUniqueId()).size() > 0) {

							e.getEntity().teleport(
									HomeUtils.getHomes(((Player) e.getEntity()).getUniqueId()).get(0).location());
						} else {
							CoreUtils.teleportToSpawn((Player) e.getEntity(), SpawnReason.DEATH);
						}
						((Player) e.getEntity()).setHealth(((Player) e.getEntity()).getMaxHealth());
						((Player) e.getEntity()).setFoodLevel(20);
						for (ItemStack i : adds) {
							((Player) e.getEntity()).getInventory().addItem(i);
						}

					}
				}, 1);

			}

		}
	}

	@EventHandler
	public void onProjectileShoot(ProjectileLaunchEvent e) {
		if (e.getEntity().getShooter() instanceof Player) {
			if (SurvivalUtils.hasSeekers(((Player) e.getEntity().getShooter()).getEquipment().getItemInMainHand())) {
				LivingEntity target = null;
				for (Entity entity : e.getEntity().getNearbyEntities(20, 20, 20)) {
					if (entity instanceof LivingEntity) {
						if (!entity.equals(e.getEntity().getShooter()))
							if (((Player) e.getEntity().getShooter()).hasLineOfSight(entity)) {
								target = (LivingEntity) entity;
								break;
							}
					}
				}
				if (target != null) {
					SurvivalUtils
							.removeSeeker(((Player) e.getEntity().getShooter()).getEquipment().getItemInMainHand());
					Bukkit.getScheduler().runTaskLater(SurvivalUtils.getPlugin(),
							new SeekerArrowRunnable((Arrow) e.getEntity(), target), 1);
				}
			}
			if (((Player) e.getEntity().getShooter()).getItemInHand().getItemMeta().hasLore()) {
				List<String> lore = ((Player) e.getEntity().getShooter()).getItemInHand().getItemMeta().getLore();
				for (String s : lore) {
					if (ChatColor.stripColor(s).contains("Fire Damage:")) {
						e.getEntity().setMetadata("fire", new FixedMetadataValue(Main.getPlugin(),
								Integer.parseInt(ChatColor.stripColor(s).split(":")[1].replaceAll(" ", ""))));
					}
					if (ChatColor.stripColor(s).contains("Frost Damage:")) {
						e.getEntity().setMetadata("frost", new FixedMetadataValue(Main.getPlugin(),
								Integer.parseInt(ChatColor.stripColor(s).split(":")[1].replaceAll(" ", ""))));
					}
					if (ChatColor.stripColor(s).contains("Vampirism Chance:")) {
						e.getEntity().setMetadata("vampirism", new FixedMetadataValue(Main.getPlugin(),
								Integer.parseInt(ChatColor.stripColor(s).split(":")[1].replaceAll(" ", ""))));
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerAttack(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Projectile) {
			if (e.getDamager().hasMetadata("fire")) {
				e.getEntity()
						.setFireTicks(Integer.parseInt("" + e.getDamager().getMetadata("fire").get(0).value()) * 20);
			}
			if (e.getDamager().hasMetadata("vampirism")) {
//				if (e.getEntity() instanceof Player) {
				if (CoreUtils.getRandom().nextInt(
						100) <= Integer.parseInt("" + e.getDamager().getMetadata("vampirism").get(0).value()) * 20) {
					((LivingEntity) ((Projectile) e.getDamager()).getShooter())
							.setHealth(((LivingEntity) ((Projectile) e.getDamager()).getShooter()).getHealth()
									+ (e.getDamage() / 2));
				}
//				} else {
//					((LivingEntity) e.getDamager())
//							.setHealth(((LivingEntity) e.getDamager()).getHealth() + (e.getDamage()
//									* (Integer.parseInt("" + e.getDamager().getMetadata("vampirism").get(0).value()))));
//				}
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

	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent e) {
		if (e.getEntity() instanceof Monster) {
			MysticPlayer player = null;
			for (Entity entity : e.getEntity().getNearbyEntities(50, 50, 50)) {
				if (entity instanceof Player) {
					player = CoreUtils.getMysticPlayer(entity.getUniqueId());
					break;
				}
			}
			if (player == null) {
				e.setCancelled(true);
				return;
			}
			String color = "&f";
			int level = CoreUtils.getRandom().nextInt(7) + (player.getLevel() - 5);
			if (level < 1)
				level = 1;

			switch (e.getEntity().getType()) {
			case ZOMBIE:
				color = "&a";
				break;
			case SKELETON:
				color = "&c";
			case DROWNED:
				color = "&a";
				break;
			case CREEPER:
				color = "&2";
				break;
			case BLAZE:
				color = "&6";
				break;
			case ENDERMAN:
				color = "&5";
				break;
			case CAVE_SPIDER:
				color = "&1";
				break;
			case SPIDER:
				color = "&1";
				break;
			default:

				break;
			}
			String name = CoreUtils.colorize(
					"&7[" + level + "] " + color + "&l" + e.getEntity().getName().substring(0, 1).toUpperCase()
							+ e.getEntity().getName().substring(1, e.getEntity().getName().length()).toLowerCase());
			Monster mon = (Monster) e.getEntity();
			mon.setMetadata("level", new FixedMetadataValue(SurvivalUtils.getPlugin(), level));
			mon.setMaxHealth(mon.getHealth() + (player.getLevel() * 1.5));
			mon.setHealth(mon.getMaxHealth());
			e.getEntity().setCustomName(name);

		}
	}

}

package com.chance.plugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.block.Block;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;

public class Main extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		System.out.println("Plugin Enabled");
		Bukkit.getPluginManager().registerEvents(this, this);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equals("double")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;

				player.sendMessage(ChatColor.AQUA + "The world is now multiplied");


			} else {
				System.out.println("Player only command");
			}
		}

		if (cmd.getName().equals("nodouble"))
			if (sender instanceof Player) {
				Player player = (Player) sender;

				player.sendMessage(ChatColor.AQUA + "The world is now not multiplied");

				HandlerList.unregisterAll();
				;
			} else {
				System.out.println("Player only command");
			}

		return false;
	}

	int breakcount = 1;
	int blockcount = 0;

	@EventHandler
	private void onMobDie(EntityDeathEvent e) {
		List<ItemStack> oldDrops = new ArrayList<>();
		List<ItemStack> newDrops = new ArrayList<>();
//		ItemStack items = (ItemStack) ((EntityDeathEvent) en).getDrops().toArray()[0];
		oldDrops.addAll(e.getDrops());
		for (ItemStack item : oldDrops) {
			item.setAmount(breakcount);
			newDrops.add(item);
		}
		for (ItemStack nItem : newDrops) {
			e.getDrops().clear();
			e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), nItem);
		}
		breakcount = breakcount * 2;
		blockcount++;
	}

	@EventHandler
	private void onBlockBreak(BlockBreakEvent e) {
		Block b = e.getBlock();
		Player p = e.getPlayer();
		ItemStack items = (ItemStack) e.getBlock().getDrops().toArray()[0];
		if (p.getGameMode() == GameMode.SURVIVAL && items != null) {
			e.setCancelled(true);
			b.setType(Material.AIR);
			items.setAmount(breakcount);
			b.getWorld().dropItem(b.getLocation(), items);
			breakcount = breakcount * 2;
			blockcount++;
			p.getScoreboard().getTeam("blocksbroken").setSuffix(blockcount + "");
			p.getScoreboard().getTeam("nextBlock").setSuffix(breakcount + "");
		}
	}
	
	public void buildSideBar(Player player)
	{
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		
		@SuppressWarnings("deprecation")
		Objective obj = board.registerNewObjective("counter", "dummy");
		obj.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD + "Counters");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		Score blank = obj.getScore("---------");
		blank.setScore(4);
		
		Team blocksBroken = board.registerNewTeam("blocksbroken");
		blocksBroken.addEntry(ChatColor.DARK_GREEN.toString());
		blocksBroken.setPrefix(ChatColor.GREEN + "Count: ");
		blocksBroken.setSuffix(blockcount + "");
		obj.getScore(ChatColor.DARK_GREEN.toString()).setScore(3);
				
		Score blank1 = obj.getScore(" ");
		blank1.setScore(2);
		
		Team next = board.registerNewTeam("nextBlock");
		next.addEntry(ChatColor.RED.toString());
		next.setPrefix(ChatColor.WHITE + "");
		next.setSuffix(ChatColor.WHITE + "" + breakcount);
		obj.getScore(ChatColor.RED.toString()).setScore(1);
		
		player.setScoreboard(board);
		
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Player player = e.getPlayer();
		buildSideBar(player);
	}
}

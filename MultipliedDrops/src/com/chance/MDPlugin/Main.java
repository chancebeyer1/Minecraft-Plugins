package com.chance.MDPlugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		System.out.println("Plugin Enabled");
	}

	@Override
	public void onDisable() {
		System.out.println("Plugin disabled");

	}

	public boolean onCommand(Command sender, Command cmd, String label, String[] args) {

		if (cmd.getName().contentEquals("double")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;

				player.sendMessage(ChatColor.AQUA + "The world is now multiplied");

				Bukkit.getPluginManager().registerEvents(this, this);;
			}
		}

		return false;
	}

	int breakcount = 0;

	@EventHandler
	public void onBreak(PlayerItemBreakEvent e) {
		if (breakcount == 0) {
			e.getBrokenItem().setAmount(1);
		} else {
			e.getBrokenItem().setAmount(breakcount * 2);
		}
		breakcount++;
	}

}

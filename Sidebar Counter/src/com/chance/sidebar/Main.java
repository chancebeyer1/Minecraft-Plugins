package com.chance.sidebar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class Main extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {

		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	public void buildSideBar(Player player)
	{
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		
		@SuppressWarnings("deprecation")
		Objective obj = board.registerNewObjective("counter", "dummy");
		obj.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD + "Counters");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		Score hi = obj.getScore("Hello!");
		hi.setScore(1);
		
		Score blank = obj.getScore(" ");
		blank.setScore(2);
		
		player.setScoreboard(board);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Player player = e.getPlayer();
		buildSideBar(player);

	}
	
	
}

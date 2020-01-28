package com.starfluxgames.staffchat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener {

	public ConfigManager cfgm;
	
	public List<UUID> staffChatPlayers = new ArrayList<UUID>();

	@Override
	public void onEnable() {
		loadConfigManager();
		setupDefaultConfig();
		loadConfig();
		new cmd_staffchat(this);
		getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onDisable()
	{
		cfgm.maincfg.set("staffchatters", staffChatPlayers);
		cfgm.saveConfig();
	}
	
	public void setupDefaultConfig() {
		cfgm.saveConfig();
		cfgm.reloadConfig();
	}

	public void loadConfig() {
		staffChatPlayers = (List<UUID>) cfgm.maincfg.getList("staffchatters");
	}

	public void loadConfigManager() {
		cfgm = new ConfigManager();
		cfgm.setup();
		cfgm.saveConfig();
		cfgm.reloadConfig();
	}
	
	@EventHandler
	public void onPlayerChat(PlayerChatEvent event)
	{
		if (event.getPlayer().hasPermission("staffchat.chat") || event.getPlayer().isOp())
		{
			if (staffChatPlayers.contains(event.getPlayer().getUniqueId()))
			{
				event.setCancelled(true);
				sendStaffMessage(event.getMessage());
			}
		}
	}
	
	public void sendStaffMessage(String message)
	{
		for(Player player : Bukkit.getServer().getOnlinePlayers())
        {
			if (player.hasPermission("staffchat.read") || player.isOp()) {
				player.sendMessage(ChatColor.RED + "[!]" + ChatColor.RESET + message);
			}
        }
	}
}

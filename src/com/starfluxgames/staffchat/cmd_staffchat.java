package com.starfluxgames.staffchat;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmd_staffchat implements CommandExecutor{

	private Main plugin;
	
	public cmd_staffchat(Main plugin)
	{
		this.plugin = plugin;
		plugin.getCommand("staffchat").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player))
		{
			sender.sendMessage(ChatColor.RED + "Only players can run this command!");
			return true;
		}
		Player player = (Player) sender;
		
		if (player.hasPermission("staffchat.chat"))
		{
			if (plugin.staffChatPlayers.contains(player.getUniqueId()))
			{
				plugin.staffChatPlayers.remove(player.getUniqueId());
				player.sendMessage(ChatColor.RED + "You are no longer sending in staff chat");
				return true;
			}else 
			{
				plugin.staffChatPlayers.add(player.getUniqueId());
				player.sendMessage(ChatColor.GREEN + "You are now sending in staff chat");
				return true;
			}
		}else
		{
			player.sendMessage(ChatColor.RED + "Invalid Permissions!");
			return false;
		}
	}
	
}
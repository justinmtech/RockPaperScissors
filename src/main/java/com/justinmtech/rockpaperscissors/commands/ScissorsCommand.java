package com.justinmtech.rockpaperscissors.commands;

import com.justinmtech.rockpaperscissors.RockPaperScissors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//TODO handle the /scissors command
public class ScissorsCommand implements CommandExecutor {
    private static RockPaperScissors plugin;
    private Player player;
    private boolean commandSuccessful;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            player = (Player) commandSender;
            if (plugin.getActiveGames().get(player) != null) {
                commandSuccessful = true;
                player.sendMessage("You chose scissors!");
            } else {
                commandSuccessful = false;
                player.sendMessage("Command failed! Are you in a match?");
            }
        }
        return commandSuccessful;
    }
}


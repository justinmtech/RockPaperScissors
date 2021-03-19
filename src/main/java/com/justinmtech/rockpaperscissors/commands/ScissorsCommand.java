package com.justinmtech.rockpaperscissors.commands;

import com.justinmtech.rockpaperscissors.RockPaperScissors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//TODO handle the /scissors command
public class ScissorsCommand implements CommandExecutor {
    private RockPaperScissors rps;
    private Player player;
    private boolean commandSuccessful;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            player = (Player) commandSender;
            if (rps.getActiveGames().get(player) != null) {
                commandSuccessful = true;
            } else {
                commandSuccessful = false;
            }
        }
        return commandSuccessful;
    }
}


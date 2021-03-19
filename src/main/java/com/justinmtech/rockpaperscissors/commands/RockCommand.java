package com.justinmtech.rockpaperscissors.commands;

import com.justinmtech.rockpaperscissors.RockPaperScissors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//TODO handle the /rock command
public class RockCommand implements CommandExecutor {
    private RockPaperScissors rps;
    private Player player;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            player = (Player) commandSender;
            if (rps.getActiveGames().get(player) != null) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }
}

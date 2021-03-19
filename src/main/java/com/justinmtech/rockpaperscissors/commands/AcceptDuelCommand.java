package com.justinmtech.rockpaperscissors.commands;

import com.justinmtech.rockpaperscissors.RockPaperScissors;
import com.justinmtech.rockpaperscissors.game.Game;
import com.justinmtech.rockpaperscissors.game.Invites;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AcceptDuelCommand implements CommandExecutor {
    private RockPaperScissors rps;
    private Player invited;
    private Player inviter;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            invited = (Player) sender;
            if (rps.getInvites().get(invited) != null) {
                new Game(inviter, invited);
            } else {
                invited.sendMessage("You do not have any pending matches!");
            }
        }
        return false;
    }
}

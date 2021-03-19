package com.justinmtech.rockpaperscissors.commands;

import com.justinmtech.rockpaperscissors.RockPaperScissors;
import com.justinmtech.rockpaperscissors.game.Game;
import com.justinmtech.rockpaperscissors.game.Invites;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DuelCommand implements CommandExecutor {
    private RockPaperScissors rps;
    private Player challenger;
    private Player opponent;
    private String input;
    private Game game;

    public DuelCommand() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            challenger = (Player) sender;
            if (Bukkit.getServer().getPlayer(args[0]) != null) {
                opponent = Bukkit.getServer().getPlayer(args[0]);
                opponent.sendMessage(challenger.getName() + " challenged you to an RPS match! Type /rps accept to accept.");
                rps.addInvite(opponent, challenger);

            } else {
                challenger.sendMessage("Player not found!");
            }

        }
            return false;
    }
}

package com.justinmtech.rockpaperscissors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    private static RockPaperScissors plugin;
    private Player player1;
    private Player player2;
    private Player sender;
    private Game game;
    private String[] args;

    public Commands(RockPaperScissors plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.sender = (Player) sender;
        this.args = args;
        player1 = (Player) sender;

        if (command.getName().equalsIgnoreCase("rps")) {

            if (args.length == 2) {
                matchRequestWithBet();
            }

            else if (args.length == 1 && args[0].equalsIgnoreCase("accept")) {
                acceptMatch();
            }

            else if (args.length == 1) {
                matchRequest();
            }

            else {
                showHelp();
            }
            return true;
        }
            return false;
    }

    private void showHelp() {
        player1.sendMessage(ChatColor.AQUA + "RPS Help");
        player1.sendMessage(ChatColor.GRAY + "- /rps");
        player1.sendMessage(ChatColor.GRAY + "- /rps <player> - challenger a player");
        player1.sendMessage(ChatColor.GRAY + "- /rps <player> [bet]");
        player1.sendMessage(ChatColor.GRAY + "- /rps accept - accept last challenge");
    }

    private void acceptMatch() {
        player2 = sender;
        System.out.println("Player 2 name: " + player2.getName());
        try {
            player1 = plugin.getInvites().get(player2);
            sendMessageToBoth(ChatColor.GREEN + "Game starting!");
            startGame();
            plugin.removeInvite(player2);
        } catch (NullPointerException e) {
            System.out.println(e.getCause());
            this.sender.sendMessage(ChatColor.RED + "No one has challenged you yet!");
        }
    }

        private void matchRequest() {
            player1 = sender;
            try {
                player2 = Bukkit.getServer().getPlayer(args[0]);
                player1.sendMessage(ChatColor.GOLD + "You challenged " + player2.getName() + " to an RPS match!");
                player2.sendMessage(ChatColor.GOLD + player1.getName() + " challenged you to an RPS match [BET: none]! Type /rps accept to accept.");
                plugin.addInvite(player2, player1);
            } catch (NullPointerException e) {
                System.out.println(e.getCause());
                this.sender.sendMessage("Player not found!");
            }
        }

        private void matchRequestWithBet() {
            player1 = sender;
            try {
                player2 = Bukkit.getServer().getPlayer(args[0]);
                int bet = Integer.parseInt(args[1]);
                player1.sendMessage(ChatColor.GOLD + "You challenged " + player2.getName() + " to an RPS match!");
                player2.sendMessage(ChatColor.GOLD + player1.getName() + " challenged you to an RPS match [BET: $" + bet + "]! Type /rps accept to accept.");
                plugin.addInvite(player2, player1);
            } catch (Exception e) {
                this.sender.sendMessage("Target player not found");
            }
        }

        private void startGame() {
            game = new Game(player1, player2);
        }

        private void sendMessageToBoth(String message) {
            player1.sendMessage(message);
            player2.sendMessage(message);
        }
}
